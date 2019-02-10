package es.upm.syst.IoT.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.upm.syst.IoT.entities.mota.MotaMeasureTraza;
import es.upm.syst.IoT.entities.omJson.Geometry;
import es.upm.syst.IoT.entities.omJson.Member;
import es.upm.syst.IoT.entities.omJson.ObservationCollecionTraza;
import es.upm.syst.IoT.entities.omJson.OmMember;
import es.upm.syst.IoT.persistence.DAOObservationCollections;

/**
 * @author Guillermo, Yan Liu
 * @version 1.0
 */
public class GestorOmCollections {
	private static ApplicationContext context;
	private DAOObservationCollections daoOmCollections;
	private static ObjectMapper objectMapper;
	
	static {
		context = new ClassPathXmlApplicationContext("beansOm.xml");
		objectMapper = context.getBean("objectMapper", ObjectMapper.class);
	}

	/**
	 * @return
	 */
	public DAOObservationCollections getDaoOmCollections() {
		return daoOmCollections;
	}

	/**
	 * @param daoOmCollections
	 */
	public void setDaoOmCollections(DAOObservationCollections daoOmCollections) {
		this.daoOmCollections = daoOmCollections;
	}
	
	/**
	 * @param omCollection
	 * @return
	 */
	public boolean altaOmCollection(ObservationCollecionTraza omCollection) {
		boolean result = false;
		result = daoOmCollections.altaOmCollection(omCollection);
		return result;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public boolean bajaOmCollection(String id) {
		boolean result = false;
		result = daoOmCollections.bajaOmCollection(id);
		return result;
	}
	
	/**
	 * @return
	 */
	public List<ObservationCollecionTraza> getListaOmCollections() {
		return daoOmCollections.getListaOmCollections();
	}
	
	/**
	 * @param id
	 * @return
	 */
	public ObservationCollecionTraza getOmCollection(String id) {
		return daoOmCollections.getOmCollection(id);
	}
	
	/**
	 * Lee el fichero motaMeasures.json y 
	 * genera una traza OMJson-Collection por cada línea de texto.
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void generateOMJsonFromFile() throws IOException 
	{
		Scanner scanner = context.getBean("scanner", Scanner.class);		
		List<JSONObject> jsonArrayList = context.getBean("jsonArrayList", List.class);
		String motaTrazaStr;			
		
		ObservationCollecionTraza omTraza = context.getBean("omCollectionTraza", ObservationCollecionTraza.class);	
		List<OmMember> members = new ArrayList<>();
		OmMember omMember;
		Geometry geometry;
		Member member;
		
		while (scanner.hasNext()) {
			motaTrazaStr = scanner.next();
			MotaMeasureTraza motaMeasure = objectMapper.readValue(motaTrazaStr, MotaMeasureTraza.class);
			omTraza.getOmCollection().setId(motaMeasure.getMotaMeasure().getMotaId());
			omTraza.getOmCollection().getPhenomenomTime().setDate(motaMeasure.getMotaMeasure().getTimestamp().getDate());
			
			omMember = context.getBean("memberGeometry", OmMember.class);
			omMember.setId("geometry" + motaMeasure.getMotaMeasure().getMotaId());		
			geometry = context.getBean("geometry", Geometry.class);
			geometry.setCoordinates(motaMeasure.getMotaMeasure().getGeometry().getCoordinates());
			members.add(omMember);
			
			omMember = context.getBean("memberTemperature", OmMember.class);
			omMember.setId("temperature" + motaMeasure.getMotaMeasure().getMotaId());
			member = context.getBean("temperature", Member.class);
			member.setValue(motaMeasure.getMotaMeasure().getMeasures().getTemperature().getValue());
			members.add(omMember);
			
			omMember = context.getBean("memberHumidity", OmMember.class);
			omMember.setId("humidity" + motaMeasure.getMotaMeasure().getMotaId());
			member = context.getBean("humidity", Member.class);
			member.setValue(motaMeasure.getMotaMeasure().getMeasures().getHumidity().getValue());
			members.add(omMember);
			
			omMember = context.getBean("memberLuminosity", OmMember.class);
			omMember.setId("luminosity" + motaMeasure.getMotaMeasure().getMotaId());
			member = context.getBean("luminosity", Member.class);
			member.setValue(motaMeasure.getMotaMeasure().getMeasures().getLuminosity().getValue());
			members.add(omMember);

			omTraza.getOmCollection().setMembers(members);
			jsonArrayList.add(new JSONObject(omTraza.toString()));
			members.clear();
		}	
		if(!jsonArrayList.isEmpty()) 
		{
			jsonArrayList.forEach((jsonObject)->System.out.println(jsonObject.toString()));
		}
	}
	
	/**
	 * Obtiene un documento sin estandarizar almacenado en la base de datos de Azure Cosmos DB
	 * y genera una traza OMJson-Collection.
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void parseMotaTrazaToOmJson(MotaMeasureTraza motaTraza) throws JsonParseException, JsonMappingException, IOException 
	{
		ObservationCollecionTraza omTraza = context.getBean("omCollectionTraza", ObservationCollecionTraza.class);	
		List<OmMember> members = new ArrayList<>();
		OmMember omMember;
		Geometry geometry;
		Member member;

		omTraza.getOmCollection().setId(motaTraza.getMotaMeasure().getMotaId());
		omTraza.getOmCollection().getPhenomenomTime().setDate(motaTraza.getMotaMeasure().getTimestamp().getDate());
			
		omMember = context.getBean("memberGeometry", OmMember.class);
		omMember.setId("geometry" + motaTraza.getMotaMeasure().getMotaId());		
		geometry = context.getBean("geometry", Geometry.class);
		geometry.setCoordinates(motaTraza.getMotaMeasure().getGeometry().getCoordinates());
		members.add(omMember);
			
		omMember = context.getBean("memberTemperature", OmMember.class);
		omMember.setId("temperature" + motaTraza.getMotaMeasure().getMotaId());
		member = context.getBean("temperature", Member.class);
		member.setValue(motaTraza.getMotaMeasure().getMeasures().getTemperature().getValue());
		members.add(omMember);
			
		omMember = context.getBean("memberHumidity", OmMember.class);
		omMember.setId("humidity" + motaTraza.getMotaMeasure().getMotaId());
		member = context.getBean("humidity", Member.class);
		member.setValue(motaTraza.getMotaMeasure().getMeasures().getHumidity().getValue());
		members.add(omMember);
			
		omMember = context.getBean("memberLuminosity", OmMember.class);
		omMember.setId("luminosity" + motaTraza.getMotaMeasure().getMotaId());
		member = context.getBean("luminosity", Member.class);
		member.setValue(motaTraza.getMotaMeasure().getMeasures().getLuminosity().getValue());
		members.add(omMember);

		omTraza.getOmCollection().setMembers(members);
		altaOmCollection(omTraza);
	}
	
}
