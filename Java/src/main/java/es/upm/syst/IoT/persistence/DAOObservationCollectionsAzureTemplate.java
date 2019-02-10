package es.upm.syst.IoT.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import es.upm.syst.IoT.entities.omJson.ObservationCollecionTraza;

/**
 * @author Guillermo, Yan Liu
 * @version 1.0
 */
public class DAOObservationCollectionsAzureTemplate implements DAOObservationCollections {
	private static ApplicationContext context;
	private static ObjectMapper objectMapper;
	private static ObservationCollecionTraza omCollectionTraza;
	private static final String CONNECTIONSTRING = "mongodb://guillermo:UWwucsNOJx0mr1NxvAMNaiZnellePZRBfwCakZp8MPaqZytxqPjvMYqKv8fDK7KfT7Yj6umTKEHo1kWta3UF5Q==@guillermo.documents.azure.com:10255/?ssl=true&replicaSet=globaldb";
	
	static {
		context = new ClassPathXmlApplicationContext("beansOm.xml");
		omCollectionTraza = context.getBean("omCollectionTraza", ObservationCollecionTraza.class);
		objectMapper = context.getBean("objectMapper", ObjectMapper.class);
	}

	@Override
	public boolean altaOmCollection(ObservationCollecionTraza omCollection) {
		boolean result = false;
		pushToMongoDB(omCollection.toString());
		return result;
	}
	
	/**
	 * Establece la conexión con la base de datos de Azure Cosmos DB, con la API MongoDB
	 * e inserta un documento con formato json a partir de la traza (jsonString).
	 * @param jsonString
	 */
	private void pushToMongoDB(String jsonString)
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

	/* 
	 * @see es.upm.syst.IoT.persistence.DAOObservationCollections#bajaOmCollection(java.lang.String)
	 */
	@Override
	public boolean bajaOmCollection(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* 
	 * @see es.upm.syst.IoT.persistence.DAOObservationCollections#getOmCollection(java.lang.String)
	 */
	@Override
	public ObservationCollecionTraza getOmCollection(String id) {
		String omCollectionStr = getMongoDBDoc(id);
		try {
			omCollectionTraza = objectMapper.readValue(omCollectionStr, ObservationCollecionTraza.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return omCollectionTraza;
	}
	
	/**
	 * Establece la conexión con la base de datos de Azure Cosmos DB, con la API MongoDB
	 * y consulta los documentos de la colección de trazas sin estandarizar.
	 * @param jsonString
	 */
	private String getMongoDBDoc(String id)
	{
		String strResult = "";
		MongoClientURI uri = new MongoClientURI(CONNECTIONSTRING);
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase("db");
			MongoCollection<Document> collection = database.getCollection("coll");
			Bson filter = Filters.eq("id", id);
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

	/*
	 * @see es.upm.syst.IoT.persistence.DAOObservationCollections#getListaOmCollections()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObservationCollecionTraza> getListaOmCollections() {
		List<String> omCollectionListStr = getMongoDBDocs();
		List<ObservationCollecionTraza> omCollectionList = context.getBean("omCollectionList", List.class);
		omCollectionListStr.forEach(motaTrazaStr->{
			try {
				omCollectionList.add(objectMapper.readValue(motaTrazaStr, ObservationCollecionTraza.class));
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return omCollectionList;
	}
	
	/**
	 * Establece la conexión con la base de datos de Azure Cosmos DB, con la API MongoDB
	 * y consulta los documentos de la colección de trazas sin estandarizar.
	 * @param jsonString
	 */
	private List<String> getMongoDBDocs()
	{
		List<String> strResults = new ArrayList<>();
		MongoClientURI uri = new MongoClientURI(CONNECTIONSTRING);
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase("db");
			MongoCollection<Document> collection = database.getCollection("coll");
			for(Document document : collection.find()) {
				strResults.add(document.toJson());
			}
			System.out.println("Query completed successfully");
		} finally {
			if (mongoClient != null) {
				mongoClient.close();
			}
		}
		return strResults;
	}

}
