package es.upm.syst.IoT.omJson.cassandra;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Random;

import com.datastax.driver.core.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.upm.syst.IoT.jsonJava.MotaMeasureTraza;
import es.upm.syst.IoT.omJson.cassandra.util.CassandraUtils;

public class MotaMeasureTrazaGenerator {
	private static Session session;
	private static Random random;
	private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();
	private static final int NUMIDS = 100;
	private static final int MINDAY = 1483228800;
	private static final int MAXDAY = 1546300800;
	private static final float MAXCOORZERO = 40.9f, MINCOORZERO = 40.0f;
	private static final float MAXCOORONE = -3.0f, MINCOORONE = -3.9f;
	private static final float MAXTEMP = 50.0f, MINTEMP = 0.0f;
	private static final float MAXHUM = 100.0f, MINHUM = 0.0f;
	private static final float MAXLUM = 100.0f, MINLUM = 0.0f;
	
	/**
	 * Establece la conexión con la base de datos de Azure Cosmos DB, con la API Cassandra
	 * e inserta una fila con formato json a partir de la traza (jsonString).
	 * @param jsonString
	 */
	private static void pushToCassandra(String jsonString) 
	{
		CassandraUtils utils = new CassandraUtils();
        session = utils.getSession();
        String query = "INSERT INTO profile.motaTrazas JSON '" + jsonString + "'";
        session.execute(query);
	}
	
	/**
	 * Genera trazas JSON no estandarizadas 
	 * y las almacena en la nube de Azure.
	 * @throws JsonProcessingException
	 */
	private static void generateMotaMeasuresCassandra() throws JsonProcessingException
	{
		random = new Random();
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
			pushToCassandra(jsonString);
		}
	}

	/**
	 * @param args
	 * @throws JsonProcessingException
	 */
	public static void main(final String[] args) throws JsonProcessingException {
		try {
			generateMotaMeasuresCassandra();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
