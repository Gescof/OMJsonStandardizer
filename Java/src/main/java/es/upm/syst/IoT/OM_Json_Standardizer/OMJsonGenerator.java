package es.upm.syst.IoT.OM_Json_Standardizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONObject;
import org.bson.Document;

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
	private static final String connectionString = "mongodb://guillermo:UWwucsNOJx0mr1NxvAMNaiZnellePZRBfwCakZp8MPaqZytxqPjvMYqKv8fDK7KfT7Yj6umTKEHo1kWta3UF5Q==@guillermo.documents.azure.com:10255/?ssl=true&replicaSet=globaldb";

	/**
	 * Establece la conexión con la base de datos de Azure Cosmos DB, con la API MongoDB
	 * e inserta un documento con formato json a partir de la traza (jsonString).
	 * @param jsonString
	 */
	private static void pushToMongoDB(String jsonString)
	{
		MongoClientURI uri = new MongoClientURI(connectionString);
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase("db");
			MongoCollection<Document> collection = database.getCollection("coll");
			Document document = new Document(Document.parse(jsonString));
			collection.insertOne(document);

//			Document queryResult = collection.find().first();
//			System.out.println(queryResult.toJson());

			System.out.println("Completed successfully");

		} finally {
			if (mongoClient != null) {
				mongoClient.close();
			}
		}
	}
	
	/**
	 * Devuelve una cadena (String) que reemplaza los literales de una traza JSON no estandarizada a literales que contendrá una traza OM-JSON.
	 * @param jsonString
	 * @return String jsonString
	 */
	private static String jsonReplace(String jsonString)
	{
		jsonString = jsonString.replace("$date", "instant");
		jsonString = jsonString.replace("members", "member");
		jsonString = jsonString.replace("unit", "uom");
		//jsonString = jsonString.replace("\"", "\\" + "\"");
		//MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://aliciayan:8Xz5XzSA6O9cRayKkuwtqkAGz1roB3hKeiYd3470dcbzHOO71wvj0uOyAsKhsJCLqLuIijkgxcOhJGaA54ppcg==@aliciayan.documents.azure.com:10255/?ssl=true&replicaSet=globaldb"));
		return jsonString;
	}
	
	/**
	 * Lee el fichero motaMeasures.json y genera una traza OMJson-Collection por cada línea de texto.
	 * @throws IOException
	 */
	private static void generateOMJson() throws IOException {
		objectMapper = new ObjectMapper();
		jsonArrayList = new ArrayList<JSONObject>();
		fstream = new FileInputStream("motaMeasures.json");
		buffer = new BufferedReader(new InputStreamReader(fstream));			
		ArrayList<OMMember> members = new ArrayList<OMMember>();	
		String motaTrazaStr = "";		
		
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
		jsonArrayList.forEach((jsonObject)->pushToMongoDB(jsonObject.toString()));
	}
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		generateOMJson();
	}
}
