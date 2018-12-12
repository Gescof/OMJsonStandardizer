package es.upm.syst.IoT.OM_Json_Standardizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Generador de trazas OM-JSON.
 * <p>Atributos:
 * <li>ArrayList<JSONObject> jsonArrayList -> Lista de JSONObject que contiene a cada una de las trazas OM-JSON.</li>
 * <li>ObjectMapper objectMapper -> Usado para mapear de objeto Java a JSON y viceversa.</li>
 * <li>FileInputStream fstream -> Fichero de lectura del que se recogen las trazas JSON sin formato.</li>
 * <li>BufferedReader buffer -> Buffer que gestiona la entrada de la lectura del fichero.</li>
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
		String motaTraza = "";
		int cont = 0;

		while ((motaTraza = buffer.readLine()) != null) {
			ObservationCollecionTraza omTraza = new ObservationCollecionTraza();
			omTraza.setOmCollection(new ObservationCollection());
			ArrayList<OMMember> members = new ArrayList<OMMember>();
			MotaMeasureTraza motaMeasure = objectMapper.readValue(motaTraza, MotaMeasureTraza.class);
			members.add(new OMMember("geometry" + motaMeasure.getMotaMeasure().getMotaId(), "Geometry Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getGeometry()));
			members.add(new OMMember("temperature" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getTemperature()));
			members.add(new OMMember("humidity" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getHumidity()));
			members.add(new OMMember("luminosity" + motaMeasure.getMotaMeasure().getMotaId(), "Category Observation", motaMeasure.getMotaMeasure().getTimestamp(), motaMeasure.getMotaMeasure().getMeasures().getLuminosity()));
			omTraza.getOmCollection().setId(motaMeasure.getMotaMeasure().getMotaId());
			omTraza.getOmCollection().setPhenomenomTime(motaMeasure.getMotaMeasure().getTimestamp());
			omTraza.getOmCollection().setMembers(members);
			String jsonString = objectMapper.writeValueAsString(omTraza.getOmCollection());
			jsonString = jsonReplace(jsonString);
			jsonArrayList.add(new JSONObject(jsonString));
			System.out.println(jsonArrayList.get(cont++));
		}
		buffer.close();
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
