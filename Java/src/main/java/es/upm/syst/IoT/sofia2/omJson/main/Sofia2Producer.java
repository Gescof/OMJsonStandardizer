package es.upm.syst.IoT.sofia2.omJson.main;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.minsait.onesait.platform.client.RestClient;
import com.minsait.onesait.platform.client.TimeOutConfig;
import com.minsait.onesait.platform.comms.protocol.enums.SSAPQueryType;

import es.upm.syst.IoT.entities.mota.MotaMeasureTraza;

/**
 * @author Guillermo, Yan Liu
 * @version 1.0
 *
 */
public class Sofia2Producer {
	private static Logger log;
	private static final String URL = "http://development.onesaitplatform.com/iot-broker";
	private static final String DEVICE = "EtsisiApp";
	private static final String TOKEN = "724fcaba296742cba85e2a92357d8d86";
	private static final String ONTOLOGY_NAME = "MotaMeasures";
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
	 * Genera trazas JSON no estandarizadas
	 * y las inserta en Sofia2.
	 * @throws JsonProcessingException
	 * @throws ParseException
	 * @throws MqttClientException 
	 */
	private static void generateMotaMeasures(RestClient client) throws JsonProcessingException, ParseException {		
		List<JsonNode> collection = client.query(ONTOLOGY_NAME, "db.getCollection(" + ONTOLOGY_NAME + ")", SSAPQueryType.NATIVE);
		if(collection != null)
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
				client.insert(ONTOLOGY_NAME, jsonString);
	
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}
	}	
	
	/**
	 * @param $args
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 */
	public static void main(final String ... $args) throws JsonProcessingException, ParseException
	{
		try {
			BasicConfigurator.configure();
			RestClient client = new RestClient(URL, 
					TimeOutConfig.builder().connectTimeout(5).readTimeouts(5).writeTimeout(5).timeunit(TimeUnit.SECONDS).build());
			client.connect(TOKEN, DEVICE, "device-test", true);
			generateMotaMeasures(client);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
