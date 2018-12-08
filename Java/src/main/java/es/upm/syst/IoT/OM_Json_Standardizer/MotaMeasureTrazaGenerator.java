package es.upm.syst.IoT.OM_Json_Standardizer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Guillermo, Yan Liu
 * @version 1.0
 * 
 */
public class MotaMeasureTrazaGenerator {
	private static Random random;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final int numIds = 100;
	private static final int minDay = (int) LocalDate.of(2017, 1, 1).toEpochDay();
	private static final int maxDay = (int) LocalDate.of(2019, 1, 1).toEpochDay();
	private static final float maxCoorZero = 40.9f, minCoorZero = 40.0f;
	private static final float maxCoorOne = -3.0f, minCoorOne = -3.9f;
	private static final float maxTemp = 50.0f, minTemp = 0.0f;
	private static final float maxHum = 100.0f, minHum = 0.0f;
	private static final float maxLum = 100.0f, minLum = 0.0f;
	
	/**
	 * Genera trazas JSON no estandarizadas en un fichero (motaMeasures.json) alojado en la carpeta raíz.
	 * 
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 */
	private static void generateMotaMeasures() throws FileNotFoundException, UnsupportedEncodingException, JsonProcessingException {		
		random = new Random();			
		Timestamp timestamp = new Timestamp();		
		long randomDay;
		LocalDate randomDate;	
		String strDate;		
		String jsonString;
		float[] coordinates = {0f, 0f};		

		PrintWriter writer = new PrintWriter("motaMeasures.json", "UTF-8");
		MotaMeasureTraza motaTraza = new MotaMeasureTraza();
		motaTraza.MotaMeasure = new MotaMeasure();

		Geometry geometry = new Geometry();
		geometry.setType("Point");
		motaTraza.MotaMeasure.setGeometry(geometry);

		Measures measures = new Measures();
		motaTraza.MotaMeasure.setMeasures(measures);

		for(int i = 0; i < numIds; i++) {
			MotaMeasureTraza mota = new MotaMeasureTraza();
			mota = motaTraza;
			mota.MotaMeasure.setMotaId("mota" + (i + 1));
			
			randomDay = minDay + random.nextInt(maxDay - minDay);
			randomDate = LocalDate.ofEpochDay(randomDay);
			strDate = randomDate.toString();
			timestamp.setDate(strDate);
			mota.MotaMeasure.setTimestamp(timestamp);
			
			coordinates[0] = (random.nextFloat() * (maxCoorZero - minCoorZero) + minCoorZero);
			coordinates[1] = (random.nextFloat() * (maxCoorOne - minCoorOne) + minCoorOne);
			geometry.setCoordinates(coordinates);
			mota.MotaMeasure.setGeometry(geometry);
			
			measures.temperature.setValue(random.nextFloat() * (maxTemp - minTemp) + minTemp);
			measures.humidity.setValue(random.nextFloat() * (maxHum - minHum) + minHum);
			measures.luminosity.setValue(random.nextFloat() * (maxLum - minLum) + minLum);
			mota.MotaMeasure.setMeasures(measures);
			
			jsonString = objectMapper.writeValueAsString(mota);
			writer.println(jsonString);
		}		
		writer.close();
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
			System.out.println("Fichero generado.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
