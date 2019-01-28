package es.upm.syst.IoT.sofia2.omJson;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Random;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indra.sofia2.ssap.kp.SSAPMessageGenerator;
import com.indra.sofia2.ssap.kp.implementations.rest.SSAPResourceAPI;
import com.indra.sofia2.ssap.kp.implementations.rest.exception.ResponseMapperException;
import com.indra.sofia2.ssap.kp.implementations.rest.resource.SSAPResource;
import com.indra.sofia2.ssap.ssap.SSAPMessage;

import es.upm.syst.IoT.jsonSerialized.MotaMeasureTraza;

/**
 * @author Guillermo, Yan Liu
 * @version 1.0
 *
 */
public class Sofia2Producer {
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
	 */
	private static void generateMotaMeasures(SSAPResourceAPI api, SSAPResource ssapResource) throws JsonProcessingException, ParseException {		
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
			ssapResource.setData(jsonString);
			
			Response responseInsert = api.insert(ssapResource);
			
			if(responseInsert.getStatus() != 200) {
				System.out.println("Error Insertando");
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}			
	}	
	
	/**
	 * @param $args
	 * @throws ResponseMapperException
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 */
	public static void main(final String ... $args) throws ResponseMapperException, JsonProcessingException, ParseException 
	{
		SSAPResourceAPI api = new SSAPResourceAPI("http://sofia2.com/sib/services/api_ssap/");
		
		SSAPResource ssapResource = new SSAPResource();
		ssapResource.setJoin(true);
		ssapResource.setToken("");
		ssapResource.setInstanceKP("");
		
		Response responseJoin = api.insert(ssapResource);
		
		if(responseJoin.getStatus() == 200) {
			String sessionKey = api.responseAsSsap(responseJoin).getSessionKey();
			
			System.out.println("Sessionkey recibida " + sessionKey);
			
			SSAPResource ssapResourceMedida = new SSAPResource();
			ssapResourceMedida.setSessionKey(sessionKey);
			ssapResourceMedida.setOntology("SensorTemperaturaEjBienvenida");
			
			generateMotaMeasures(api, ssapResourceMedida);
			
//			SSAPMessage msgInsert = SSAPMessageGenerator.getInstance().generateInsertMessage(ssapResourceMedida.getSessionKey(), ssapResourceMedida.getOntology(), datos);
		}
	}
}
