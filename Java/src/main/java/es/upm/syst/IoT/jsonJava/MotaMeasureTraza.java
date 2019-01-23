package es.upm.syst.IoT.jsonJava;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Reperesenta una traza de una MotaMeasure.
 * @author Guillermo, Yan Liu
 *
 */
@JsonIgnoreProperties({"_id"})
public class MotaMeasureTraza {
	private MotaMeasure motaMeasure;
	private String id;

	public MotaMeasureTraza()
	{
		this.motaMeasure = new MotaMeasure();
	}

	/**
	 * Devuelve el contenido como objeto de tipo MotaMeasure.
	 * @return MotaMeasure
	 * @see MotaMeasure
	 */
	@JsonGetter("MotaMeasure")
	public MotaMeasure getMotaMeasure() {
		return motaMeasure;
	}
	
	/**
	 * Establece el objeto MotaMeasure de la clase.
	 * @param MotaMeasure
	 * @see MotaMeasure
	 */
	@JsonSetter("MotaMeasure")
	public void setMotaMeasure(MotaMeasure motaMeasure) {
		this.motaMeasure = motaMeasure;
	}	
	
	/**
	 * Devuelve el identificador de la traza que establece Azure Cosmos DB.
	 * @return _id
	 */
	@JsonGetter("_id")	
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador de la traza para Azure Cosmos DB.
	 * @param _id
	 */
	@JsonSetter("_id")	
	public void setId(String id) {
		this.id = id;
	}
	
}
