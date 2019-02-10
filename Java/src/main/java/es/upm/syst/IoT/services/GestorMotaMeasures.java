package es.upm.syst.IoT.services;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.upm.syst.IoT.entities.mota.MotaMeasureTraza;
import es.upm.syst.IoT.persistence.DAOMotaMeasures;

/**
 * <li>int NUMIDS -> Número de trazas JSON que se generan.</li>
 * <li>int MINDAY, MAXDAY -> Día de inicio (MINDAY) y final (MAXDAY) para generar las fechas (Epoch).</li>
 * <li>float MINCOORZERO, MAXCOORZERO -> Coordenada mínima y máxima a generar.</li>
 * <li>float MINTEMP, MAXTEMP -> Temperatura mínima y máxima a generar.</li>
 * <li>float MINHUM, MAXHUM -> Humedad mínima y máxima a generar.</li>
 * <li>float MINLUM,  MAXLUM -> Luminosidad mínima y máxima a generar.</li>
 * @author Guillermo, Yan Liu
 * @version 1.0
 */
public class GestorMotaMeasures {
	private static ApplicationContext context;
	private DAOMotaMeasures daoMotaMeasures;
	private static final int NUMIDS = 100;
	private static final int MINDAY = 1483228800;
	private static final int MAXDAY = 1546300800;
	private static final float MAXCOORZERO = 40.9f, MINCOORZERO = 40.0f;
	private static final float MAXCOORONE = -3.0f, MINCOORONE = -3.9f;
	private static final float MAXTEMP = 50.0f, MINTEMP = 0.0f;
	private static final float MAXHUM = 100.0f, MINHUM = 0.0f;
	private static final float MAXLUM = 100.0f, MINLUM = 0.0f;

	static {
		context = new ClassPathXmlApplicationContext("beansMotas.xml");
	}
	
	/**
	 * @return
	 */
	public DAOMotaMeasures getDaoMotaMeasures() {
		return daoMotaMeasures;
	}

	/**
	 * @param daoMotaMeasures
	 */
	public void setDaoMotaMeasures(DAOMotaMeasures daoMotaMeasures) {
		this.daoMotaMeasures = daoMotaMeasures;
	}
	
	/**
	 * @param motaTraza
	 * @return
	 */
	public boolean altaMota(MotaMeasureTraza motaTraza) {
		boolean result = false;
		result = daoMotaMeasures.altaMota(motaTraza);
		return result;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public boolean bajaMota(String id) {
		boolean result = false;
		result = daoMotaMeasures.bajaMota(id);
		return result;
	}
		
	/**
	 * @param id
	 * @return
	 */
	public MotaMeasureTraza getMotaTraza(String id) {
		return daoMotaMeasures.getMotaTraza(id);
	}
	
	/**
	 * @return
	 */
	public List<MotaMeasureTraza> getListaMotaTrazas() {
		return daoMotaMeasures.getListaMotaTrazas();
	}
	
	/**
	 * Genera trazas JSON no estandarizadas en un fichero (motaMeasures.json) alojado en la carpeta raíz.
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws ParseException
	 */
	public void generateMotaMeasuresToFile() throws UnsupportedEncodingException, JsonProcessingException, ParseException {		
		Random random = context.getBean("random", Random.class);			
		long randomDay;
		ZonedDateTime randomDate;		
		
		MotaMeasureTraza motaTraza = context.getBean("motaTraza", MotaMeasureTraza.class);
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
			
			try(PrintWriter writer = context.getBean("printWriter", PrintWriter.class);) {
				writer.println(motaTraza.toString());
			}
		}			
		System.out.println("Fichero generado.");
	}
	
}
