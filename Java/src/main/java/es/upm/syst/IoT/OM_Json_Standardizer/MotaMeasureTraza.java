package es.upm.syst.IoT.OM_Json_Standardizer;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Reperesenta una traza de una MotaMeasure.
 * @author Guillermo, Yan Liu
 *
 */
public class MotaMeasureTraza {
	private MotaMeasure MotaMeasure;
	private String _id;

	public MotaMeasureTraza()
	{
		//TODO--Default Constructor
	}

	/**
	 * Devuelve el contenido como objeto de tipo MotaMeasure.
	 * @return MotaMeasure
	 * @see MotaMeasure
	 */
	@JsonGetter("MotaMeasure")
	public MotaMeasure getMotaMeasure() {
		return MotaMeasure;
	}
	
	/**
	 * Establece el objeto MotaMeasure de la clase.
	 * @param MotaMeasure
	 * @see MotaMeasure
	 */
	@JsonSetter("MotaMeasure")
	public void setMotaMeasure(MotaMeasure MotaMeasure) {
		this.MotaMeasure = MotaMeasure;
	}	
	
	/**
	 * Devuelve el identificador de la traza que establece Azure Cosmos DB.
	 * @return _id
	 */
	@JsonIgnore
	public String get_id() {
		return _id;
	}

	/**
	 * Establece el identificador de la traza para Azure Cosmos DB.
	 * @param _id
	 */
	@JsonIgnore
	public void set_id(String _id) {
		this._id = _id;
	}
	
}
