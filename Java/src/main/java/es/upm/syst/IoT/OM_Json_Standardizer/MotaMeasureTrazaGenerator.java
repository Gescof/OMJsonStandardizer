package es.upm.syst.IoT.OM_Json_Standardizer;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.io.PrintWriter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Generador de trazas JSON no estandarizadas.
 * <p>Atributos:
 * <li>Logger logger -> Registro de las actividades del programa.</li>
 * <li>Random random -> Generador aleatorio de valores de los atributos de las trazas JSON.</li>
 * <li>PrintWriter writer -> Búfer de escritura del fichero que se crea.</li>
 * <li>ObjectMapper OBJECTMAPPER -> Usado para mapear de objeto Java a JSON y viceversa.</li>
 * <li>int NUMIDS -> Número de trazas JSON que se generan.</li>
 * <li>int MINDAY, MAXDAY -> Día de inicio (MINDAY) y final (MAXDAY) para generar las fechas (Epoch).</li>
 * <li>float MINCOORZERO, MAXCOORZERO -> Coordenada mínima y máxima a generar.</li>
 * <li>float MINTEMP, MAXTEMP -> Temperatura mínima y máxima a generar.</li>
 * <li>float MINHUM, MAXHUM -> Humedad mínima y máxima a generar.</li>
 * <li>float MINLUM,  MAXLUM -> Luminosidad mínima y máxima a generar.</li>
 * <li>String CONNECTIONSTRING -> Cadena que contiene el token para establecer la conexión con la base de datos de Azure.</li>
 * </p>
 * 
 * @author Guillermo, Yan Liu
 * @version 1.0
 * 
 */
public class MotaMeasureTrazaGenerator {
	private static Logger logger;
	private static Random random;
	private static PrintWriter writer;
	private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();
	private static final int NUMIDS = 100;
	private static final int MINDAY = 1483228800;
	private static final int MAXDAY = 1546300800;
	private static final float MAXCOORZERO = 40.9f, MINCOORZERO = 40.0f;
	private static final float MAXCOORONE = -3.0f, MINCOORONE = -3.9f;
	private static final float MAXTEMP = 50.0f, MINTEMP = 0.0f;
	private static final float MAXHUM = 100.0f, MINHUM = 0.0f;
	private static final float MAXLUM = 100.0f, MINLUM = 0.0f;
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
			MongoCollection<Document> collection = database.getCollection("motaTrazas");
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
	 * Genera trazas JSON no estandarizadas en un fichero (motaMeasures.json) alojado en la carpeta raíz.
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 */
	private static void generateMotaMeasures() throws FileNotFoundException, UnsupportedEncodingException, JsonProcessingException {		
		random = new Random();			
		writer = new PrintWriter("motaMeasures.json", "UTF-8");			
		String jsonString;	
		
		long randomDay;
		ZonedDateTime randomDate;		
		
		MotaMeasureTraza motaTraza = new MotaMeasureTraza();
		float[] coordinates = new float[2];
		motaTraza.getMotaMeasure().getGeometry().setType("Point");

		for(int i = 0; i < NUMIDS; i++) {
			motaTraza.getMotaMeasure().setMotaId("mota" + (i + 1));
			
			randomDay = MINDAY + random.nextInt(MAXDAY - MINDAY);
			Instant instant = Instant.ofEpochSecond(randomDay);
			randomDate = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
			motaTraza.getMotaMeasure().getTimestamp().setDate(Date.from(randomDate.toInstant()));
			
			coordinates[0] = random.nextFloat() * (MAXCOORZERO - MINCOORZERO) + MINCOORZERO;
			coordinates[1] = random.nextFloat() * (MAXCOORONE - MINCOORONE) + MINCOORONE;
			motaTraza.getMotaMeasure().getGeometry().setCoordinates(coordinates);
			
			motaTraza.getMotaMeasure().getMeasures().getTemperature().setValue(random.nextFloat() * (MAXTEMP - MINTEMP) + MINTEMP);
			motaTraza.getMotaMeasure().getMeasures().getHumidity().setValue(random.nextFloat() * (MAXHUM - MINHUM) + MINHUM);
			motaTraza.getMotaMeasure().getMeasures().getLuminosity().setValue(random.nextFloat() * (MAXLUM - MINLUM) + MINLUM);
			
			jsonString = OBJECTMAPPER.writeValueAsString(motaTraza);
			pushToMongoDB(jsonString);
			writer.println(jsonString);
		}		
		writer.close();			
		System.out.println("Fichero generado.");
	}
	
	/**
	 * @param args
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 */
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, JsonProcessingException
	{
		try {
			generateMotaMeasures();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(Level.INFO, e.getMessage(), e);
		}
	}
}
