package es.upm.syst.IoT.OM_Json_Standardizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Guillermo, Yan Liu
 * @version 1.0
 *
 */
public class OMJsonGenerator {

	/**
	 * Devuelve una cadena (String) que reemplaza los literales de una traza JSON no estandarizada a literales que contendrá una traza OM-JSON.
	 * 
	 * @param jsonString
	 * @return jsonString
	 */
	private static String jsonReplace(String jsonString)
	{
		jsonString = jsonString.replace("$date", "instant");
		jsonString = jsonString.replace("members", "member");
		jsonString = jsonString.replace("unit", "uom");
		//jsonString = jsonString.replace("\"", "\\" + "\"");
		
		return jsonString;
	}
	
	/**
	 * Lee el fichero motaMeasures.json y genera una traza OMJson-Collection por cada línea de texto.
	 * @throws IOException
	 */
	private static void generateOMJson() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();		
		String motaTraza = "";
		FileInputStream fstream = new FileInputStream("motaMeasures.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		while ((motaTraza = br.readLine()) != null) {
			ObservationCollecionTraza omTraza = new ObservationCollecionTraza();
			omTraza.omCollection = new ObservationCollection();
			ArrayList<OMMember> members = new ArrayList<OMMember>();
			MotaMeasureTraza motaMeasure = objectMapper.readValue(motaTraza, MotaMeasureTraza.class);
			members.add(new OMMember("geometry" + motaMeasure.MotaMeasure.getMotaId(), "Geometry Observation", motaMeasure.MotaMeasure.getTimestamp(), motaMeasure.MotaMeasure.getGeometry()));
			members.add(new OMMember("temperature" + motaMeasure.MotaMeasure.getMotaId(), "Category Observation", motaMeasure.MotaMeasure.getTimestamp(), motaMeasure.MotaMeasure.measures.getTemperature()));
			members.add(new OMMember("humidity" + motaMeasure.MotaMeasure.getMotaId(), "Category Observation", motaMeasure.MotaMeasure.getTimestamp(), motaMeasure.MotaMeasure.measures.getHumidity()));
			members.add(new OMMember("luminosity" + motaMeasure.MotaMeasure.getMotaId(), "Category Observation", motaMeasure.MotaMeasure.getTimestamp(), motaMeasure.MotaMeasure.measures.getLuminosity()));
			omTraza.omCollection.setId(motaMeasure.MotaMeasure.getMotaId());
			omTraza.omCollection.setPhenomenomTime(motaMeasure.MotaMeasure.getTimestamp());
			omTraza.omCollection.setMembers(members);		
			String jsonString = objectMapper.writeValueAsString(omTraza.omCollection);
			jsonString = jsonReplace(jsonString);
			System.out.println(jsonString);
		}
		br.close();
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
