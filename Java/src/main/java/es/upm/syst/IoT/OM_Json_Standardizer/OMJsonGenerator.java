package es.upm.syst.IoT.OM_Json_Standardizer;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * Generador de trazas OM-JSON.
 * <p>Atributos:
 * <li>ArrayList<JSONObject> jsonArrayList -> Lista de JSONObject que contiene a cada una de las trazas OM-JSON.</li>
 * <li>ObjectMapper objectMapper -> Usado para mapear de objeto Java a JSON y viceversa.</li>
 * <li>File inputFile -> Fichero de lectura del que se recogen las trazas JSON sin formato.</li>
 * <li>Scanner scanner -> Buffer que gestiona la entrada de la lectura del fichero.</li>
 * <li>String CONNECTIONSTRING -> Cadena que contiene el token para establecer la conexión con la base de datos de Azure.</li>
 * </p>
 * 
 * @author Guillermo, Yan Liu
 * @version 1.0
 *
 */
public class OMJsonGenerator {
	private static ArrayList<JSONObject> jsonArrayList;
	private static ObjectMapper objectMapper;
	private static File inputFile;	
	private static Scanner scanner;
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
			Bson filter = Filters.eq("MotaMeasure.MotaId", "mota2");
			Document queryResult = collection.find(filter).first();
			strResult = queryResult.toJson();
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
		return jsonString;
	}
	
	/**
	 * Lee el fichero motaMeasures.json 
	 * y genera una traza OMJson-Collection por cada línea de texto.
	 * @throws IOException
	 */
	private static void generateOMJson() throws IOException 
	{
		objectMapper = new ObjectMapper();
		jsonArrayList = new ArrayList<JSONObject>();
		inputFile = new File("motaMeasures.json");
		scanner = new Scanner(inputFile);			
		String motaTrazaStr = "";			
		
		ObservationCollecionTraza omTraza = new ObservationCollecionTraza();	
		ArrayList<OMMember> members = new ArrayList<OMMember>();
		
		while (scanner.hasNext()) {
			motaTrazaStr = scanner.next();
			MotaMeasureTraza motaMeasure = objectMapper.readValue(motaTrazaStr, MotaMeasureTraza.class);
			omTraza.getOmCollection().setId(motaMeasure.getMotaMeasure().getMotaId());
			omTraza.getOmCollection().setPhenomenomTime(motaMeasure.getMotaMeasure().getTimestamp());
			members.add(new OMMember("geometry" + motaMeasure.getMotaMeasure().getMotaId(), "Geometry Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getGeometry()));
			members.add(new OMMember("temperature" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getTemperature().standarizeMember()));
			members.add(new OMMember("humidity" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getHumidity().standarizeMember()));
			members.add(new OMMember("luminosity" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getLuminosity().standarizeMember()));
			omTraza.getOmCollection().setMembers(members);
			String jsonString = objectMapper.writeValueAsString(omTraza.getOmCollection());
			jsonString = jsonReplace(jsonString);
			jsonArrayList.add(new JSONObject(jsonString));
			members.clear();
		}
		scanner.close();	
		if(!jsonArrayList.isEmpty()) 
		{
			jsonArrayList.forEach((jsonObject)->System.out.println(jsonObject.toString()));
		}
	}
	
	/**
	 * Obtiene todos los documentos sin estandarizar almacenados 
	 * en la base de datos de Azure Cosmos DB
	 * y genera una traza OMJson-Collection por cada uno.
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private static void insertOMJsonToMongoDBMany() throws JsonParseException, JsonMappingException, IOException 
	{
		MongoClientURI uri = new MongoClientURI(CONNECTIONSTRING);
		MongoClient mongoClient = null;		
		
		objectMapper = new ObjectMapper();
		jsonArrayList = new ArrayList<JSONObject>();			
		
		ObservationCollecionTraza omTraza = new ObservationCollecionTraza();
		ArrayList<OMMember> members = new ArrayList<OMMember>();
		try {
			mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase("db");
			MongoCollection<Document> collection = database.getCollection("motaTrazas");
			System.out.println("Query completed successfully");
			FindIterable<Document> all = collection.find();
			for(Document document : all) {
				String motaTrazaStr = document.toJson();
				MotaMeasureTraza motaMeasure = objectMapper.readValue(motaTrazaStr, MotaMeasureTraza.class);
				omTraza.getOmCollection().setId(motaMeasure.getMotaMeasure().getMotaId());
				omTraza.getOmCollection().setPhenomenomTime(motaMeasure.getMotaMeasure().getTimestamp());
				members.add(new OMMember("geometry" + motaMeasure.getMotaMeasure().getMotaId(), "Geometry Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getGeometry()));
				members.add(new OMMember("temperature" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getTemperature().standarizeMember()));
				members.add(new OMMember("humidity" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getHumidity().standarizeMember()));
				members.add(new OMMember("luminosity" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getLuminosity().standarizeMember()));
				omTraza.getOmCollection().setMembers(members);
				String jsonString = objectMapper.writeValueAsString(omTraza.getOmCollection());
				jsonString = jsonReplace(jsonString);
				jsonArrayList.add(new JSONObject(jsonString));
				members.clear();
			}
		} finally {
			if (mongoClient != null) {
				mongoClient.close();
			}
		}
		if(!jsonArrayList.isEmpty()) 
		{
			jsonArrayList.forEach((jsonObject)->pushToMongoDB(jsonObject.toString()));
		}
	}
	
	/**
	 * Obtiene un documento sin estandarizar almacenados 
	 * en la base de datos de Azure Cosmos DB
	 * y genera una traza OMJson-Collection.
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private static void insertOMJsonToMongoDBOne() throws JsonParseException, JsonMappingException, IOException 
	{
		objectMapper = new ObjectMapper();
		jsonArrayList = new ArrayList<JSONObject>();	
		String motaTrazaStr;
		
		ObservationCollecionTraza omTraza = new ObservationCollecionTraza();
		ArrayList<OMMember> members = new ArrayList<OMMember>();
		
		if ((motaTrazaStr = getMongoDBDoc()) != null) {
			MotaMeasureTraza motaMeasure = objectMapper.readValue(motaTrazaStr, MotaMeasureTraza.class);
			omTraza.getOmCollection().setId(motaMeasure.getMotaMeasure().getMotaId());
			omTraza.getOmCollection().setPhenomenomTime(motaMeasure.getMotaMeasure().getTimestamp());
			members.add(new OMMember("geometry" + motaMeasure.getMotaMeasure().getMotaId(), "Geometry Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getGeometry()));
			members.add(new OMMember("temperature" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getTemperature().standarizeMember()));
			members.add(new OMMember("humidity" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getHumidity().standarizeMember()));
			members.add(new OMMember("luminosity" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getLuminosity().standarizeMember()));
			omTraza.getOmCollection().setMembers(members);
			String jsonString = objectMapper.writeValueAsString(omTraza.getOmCollection());
			jsonString = jsonReplace(jsonString);
			jsonArrayList.add(new JSONObject(jsonString));
			members.clear();
		}
		if(!jsonArrayList.isEmpty()) 
		{
			jsonArrayList.forEach((jsonObject)->pushToMongoDB(jsonObject.toString()));
		}
	}
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		generateOMJson();
		insertOMJsonToMongoDBMany();
		insertOMJsonToMongoDBOne();
	}
}
