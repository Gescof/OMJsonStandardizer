package es.upm.syst.IoT.entities.omJson;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Representa la geometría que contiene una traza.
 * @author Guillermo, Yan Liu
 *
 */
public class Geometry implements ResulType {
	private String type;
	private float[] coordinates;
	
	public Geometry()
	{}
	
	public Geometry(String type, float[] coordinates) {
		this.type = type;
		this.coordinates = coordinates;
	}

	/**
	 * Devuelve el tipo de geometría como cadena.
	 * @return String type
	 */
	@JsonGetter("type")
	public String getType() {
		return type;
	}
	
	/**
	 * Establece el tipo de geometría como cadena.
	 * @param type
	 */
	@JsonSetter("type")
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Devuelve las coordenadas.
	 * @return float[] coordinates
	 */
	@JsonGetter("coordinates")
	public float[] getCoordinates() {
		return coordinates;
	}
	
	/**
	 * Establece las coordenadas.
	 * @param coordinates
	 */
	@JsonSetter("coordinates")
	public void setCoordinates(float[] coordinates) {
		this.coordinates = coordinates;
	}
	
	/**
	 * Devuelve en formato de cadena este objeto siguiendo el patrón OM-JSON.
	 * @see es.upm.syst.IoT.entities.mota.ResulType#toString()
	 * @return String
	 */
	@Override
	public String toString() {
		return "\"type\": " + type + ", \"coordinates\": " + Arrays.toString(coordinates);
	}
	
}
