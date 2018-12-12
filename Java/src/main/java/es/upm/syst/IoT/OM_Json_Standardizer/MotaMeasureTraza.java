package es.upm.syst.IoT.OM_Json_Standardizer;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Reperesenta una traza de una MotaMeasure.
 * @author Guillermo, Yan Liu
 *
 */
public class MotaMeasureTraza {
	private MotaMeasure MotaMeasure;
	
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
	
}
