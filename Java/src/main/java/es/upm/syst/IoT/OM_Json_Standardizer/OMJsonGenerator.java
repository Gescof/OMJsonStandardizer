package es.upm.syst.IoT.OM_Json_Standardizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONObject;
import org.bson.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * Generador de trazas OM-JSON.
 * <p>Atributos:
 * <li>ArrayList<JSONObject> jsonArrayList -> Lista de JSONObject que contiene a cada una de las trazas OM-JSON.</li>
 * <li>ObjectMapper objectMapper -> Usado para mapear de objeto Java a JSON y viceversa.</li>
 * <li>FileInputStream fstream -> Fichero de lectura del que se recogen las trazas JSON sin formato.</li>
 * <li>BufferedReader buffer -> Buffer que gestiona la entrada de la lectura del fichero.</li>
 * <li>String connectionString -> Cadena que contiene el token para establecer la conexión con la base de datos de Azure.</li>
 * </p>
 * 
 * @author Guillermo, Yan Liu
 * @version 1.0
 *
 */
public class OMJsonGenerator {
	private static ArrayList<JSONObject> jsonArrayList;
	private static ObjectMapper objectMapper;
	private static FileInputStream fstream;	
	private static BufferedReader buffer;
	private static final String CONNECTIONSTRING = "mongodb://guillermo:UWwucsNOJx0mr1NxvAMNaiZnellePZRBfwCakZp8MPaqZytxqPjvMYqKv8fDK7KfT7Yj6umTKEHo1kWta3UF5Q==@guillermo.documents.azure.com:10255/?ssl=true&replicaSet=globaldb";
	
	/**
	 * Establece la conexión con la base de datos de Azure Cosmos DB, con la API MongoDB
	 * e inserta un documento con formato json a partir de la traza (jsonString).
	 * @param jsonString
	 */
	private static void pushToMongoDB(String jsonString)
	{
		MongoClientURI uri = new MongoClientURI(CONNECTIONSTRING);
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase("db");
			MongoCollection<Document> collection = database.getCollection("coll");
			Document document = new Document(Document.parse(jsonString));
			collection.insertOne(document);
			System.out.println("Completed successfully");

		} finally {
			if (mongoClient != null) {
				mongoClient.close();
			}
		}
	}
	
	/**
	 * Establece la conexión con la base de datos de Azure Cosmos DB, con la API MongoDB
	 * y consulta los documentos de la colección de trazas sin estandarizar.
	 * @param jsonString
	 */
	private static String getMongoDBDoc()
	{
		String strResult = "";
		MongoClientURI uri = new MongoClientURI(CONNECTIONSTRING);
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase("db");
			MongoCollection<Document> collection = database.getCollection("motaTrazas");
			Document queryResult = collection.find().first();
			strResult = queryResult.toJson();
			strResult = strResult.replace("date", "$date");
			System.out.println("Query completed successfully");

		} finally {
			if (mongoClient != null) {
				mongoClient.close();
			}
		}
		return strResult;
	}
	
	/**
	 * Devuelve una cadena (String) que reemplaza los literales de una traza JSON no estandarizada 
	 * a literales que contendrá una traza OM-JSON.
	 * @param jsonString
	 * @return String jsonString
	 */
	private static String jsonReplace(String jsonString)
	{
		jsonString = jsonString.replace("$date", "instant");
		jsonString = jsonString.replace("members", "member");
		jsonString = jsonString.replace("unit", "uom");
//		jsonString = jsonString.replace("\"", "\\" + "\"");
		
		return jsonString;
	}
	
	/**
	 * Lee el fichero motaMeasures.json y genera una traza OMJson-Collection por cada línea de texto.
	 * @throws IOException
	 */
	private static void generateOMJson() throws IOException 
	{
		objectMapper = new ObjectMapper();
		jsonArrayList = new ArrayList<JSONObject>();
		fstream = new FileInputStream("motaMeasures.json");
		buffer = new BufferedReader(new InputStreamReader(fstream));			
		ArrayList<OMMember> members = new ArrayList<OMMember>();	
		String motaTrazaStr;		
		
		while ((motaTrazaStr = buffer.readLine()) != null) {
			ObservationCollecionTraza omTraza = new ObservationCollecionTraza();
			omTraza.setOmCollection(new ObservationCollection());
			MotaMeasureTraza motaMeasure = objectMapper.readValue(motaTrazaStr, MotaMeasureTraza.class);
			members.add(new OMMember("geometry" + motaMeasure.getMotaMeasure().getMotaId(), "Geometry Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getGeometry()));
			members.add(new OMMember("temperature" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getTemperature().standarizeMember()));
			members.add(new OMMember("humidity" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getHumidity().standarizeMember()));
			members.add(new OMMember("luminosity" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getLuminosity().standarizeMember()));
			omTraza.getOmCollection().setId(motaMeasure.getMotaMeasure().getMotaId());
			omTraza.getOmCollection().setPhenomenomTime(motaMeasure.getMotaMeasure().getTimestamp());
			omTraza.getOmCollection().setMembers(members);
			String jsonString = objectMapper.writeValueAsString(omTraza.getOmCollection());
			jsonString = jsonReplace(jsonString);
			jsonArrayList.add(new JSONObject(jsonString));
			members.clear();
		}
		buffer.close();		
		jsonArrayList.forEach((jsonObject)->System.out.println(jsonObject.toString()));
	}
	
	/**
	 * Obtiene los documentos sin estandarizar almacenados en la base de datos de Azure Cosmos DB
	 * y genera una traza OMJson-Collection por cada línea de texto.
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private static void generateOMJsonToMongoDB() throws JsonParseException, JsonMappingException, IOException 
	{
		objectMapper = new ObjectMapper();
		jsonArrayList = new ArrayList<JSONObject>();		
		ArrayList<OMMember> members = new ArrayList<OMMember>();	
		String motaTrazaStr;
		
		if ((motaTrazaStr = getMongoDBDoc()) != null) {
			ObservationCollecionTraza omTraza = new ObservationCollecionTraza();
			omTraza.setOmCollection(new ObservationCollection());
			MotaMeasureTraza motaMeasure = objectMapper.readValue(motaTrazaStr, MotaMeasureTraza.class);
			members.add(new OMMember("geometry" + motaMeasure.getMotaMeasure().getMotaId(), "Geometry Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getGeometry()));
			members.add(new OMMember("temperature" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getTemperature().standarizeMember()));
			members.add(new OMMember("humidity" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getHumidity().standarizeMember()));
			members.add(new OMMember("luminosity" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getLuminosity().standarizeMember()));
			omTraza.getOmCollection().setId(motaMeasure.getMotaMeasure().getMotaId());
			omTraza.getOmCollection().setPhenomenomTime(motaMeasure.getMotaMeasure().getTimestamp());
			omTraza.getOmCollection().setMembers(members);
			String jsonString = objectMapper.writeValueAsString(omTraza.getOmCollection());
			jsonString = jsonReplace(jsonString);
			jsonArrayList.add(new JSONObject(jsonString));
			members.clear();
		}
		jsonArrayList.forEach((jsonObject)->pushToMongoDB(jsonObject.toString()));
	}
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		generateOMJson();
		generateOMJsonToMongoDB();
	}
}
