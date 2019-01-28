package es.upm.syst.IoT.jsonSerialized;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Reperesenta una MotaMeasure.
 * @author Guillermo, Yan Liu
 *
 */
public class MotaMeasure {
	private Timestamp timestamp;
	private String motaId;
	private Geometry geometry;
	private Measures measures;
	
	public MotaMeasure()
	{
		this.timestamp = new Timestamp();
		this.geometry = new Geometry();
		this.measures = new Measures();
	}
	
	/**
	 * Devuelve la fecha como objeto de tipo Timestamp.
	 * @return Timestamp
	 * @see Timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Establece la fecha como objeto Timestamp.
	 * @param timestamp
	 * @see Timestamp
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * Devuelve el identificador como cadena.
	 * @return String MotaId
	 */
	@JsonGetter("MotaId")
	public String getMotaId() {
		return motaId;
	}
	
	/**
	 * Establece el identificador de la mota como cadena.
	 * @param motaId
	 */
	@JsonSetter("MotaId")
	public void setMotaId(String motaId) {
		this.motaId = motaId;
	}
	
	/**
	 * Devuelve la geometría como objeto de tipo Geometry.
	 * @return Geometry
	 * @see Geometry
	 */
	public Geometry getGeometry() {
		return geometry;
	}
	
	/**
	 * Establece la geometría como objeto Geometry.
	 * @param geometry
	 * @see Geometry
	 */
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	
	/**
	 * Devuelve las medidas como objeto de tipo Measures.
	 * @return Measures
	 * @see Measures
	 */
	public Measures getMeasures() {
		return measures;
	}
	
	/**
	 * Establece las medidas como objeto Measures.
	 * @param measures
	 * @see Measures
	 */
	public void setMeasures(Measures measures) {
		this.measures = measures;
	}
	
}
