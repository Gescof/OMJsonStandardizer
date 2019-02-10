package es.upm.syst.IoT.entities.mota;

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
	
	public MotaMeasure(Timestamp timestamp, String motaId, Geometry geometry, Measures measures) {
		this.timestamp = timestamp;
		this.motaId = motaId;
		this.geometry = geometry;
		this.measures = measures;
	}

	/**
	 * Devuelve la fecha como objeto de tipo Timestamp.
	 * @return Timestamp
	 * @see Timestamp
	 */
	@JsonGetter("timestamp")
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Establece la fecha como objeto Timestamp.
	 * @param timestamp
	 * @see Timestamp
	 */
	@JsonSetter("timestamp")
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
	@JsonSetter("geometry")
	public Geometry getGeometry() {
		return geometry;
	}
	
	/**
	 * Establece la geometría como objeto Geometry.
	 * @param geometry
	 * @see Geometry
	 */
	@JsonSetter("geometry")
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	
	/**
	 * Devuelve las medidas como objeto de tipo Measures.
	 * @return Measures
	 * @see Measures
	 */
	@JsonGetter("measures")
	public Measures getMeasures() {
		return measures;
	}
	
	/**
	 * Establece las medidas como objeto Measures.
	 * @param measures
	 * @see Measures
	 */
	@JsonSetter("measures")
	public void setMeasures(Measures measures) {
		this.measures = measures;
	}

	/* Devuelve en formato de cadena este objeto siguiendo el patrón JSON.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\"MotaMeasure\": {\"timestamp\": " + timestamp + ", \"geometry\": {" + geometry + "}, \"measures\": " + measures +
				", \"motaId\": \"" + motaId + "\"}";
	}
	
}
