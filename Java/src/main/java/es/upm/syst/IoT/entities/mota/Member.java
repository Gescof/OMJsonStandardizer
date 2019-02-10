package es.upm.syst.IoT.entities.mota;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Representa el resultado de un miembro de una traza.
 * @author Guillermo, Yan Liu
 *
 */
public class Member {
	private float value;
	private String unit;
	
	public Member()
	{}
	
	public Member(float value, String unit) {
		this.value = value;
		this.unit = unit;
	}

	/**
	 * Devuelve el valor de este objeto.
	 * @return float value
	 */
	@JsonGetter("value")
	public float getValue() {
		return value;
	}
	
	/**
	 * Establece el valor para este objeto.
	 * @param value
	 */
	@JsonSetter("value")
	public void setValue(float value) {
		this.value = value;
	}
	
	/**
	 * Devuelve la unidad de medida como cadena.
	 * @return String unit
	 */
	@JsonGetter("unit")
	public String getUnit() {
		return unit;
	}
	
	/**
	 * Establece la unidad de medida como cadena.
	 * @param unit
	 */
	@JsonSetter("unit")
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/* Devuelve en formato de cadena este objeto siguiendo el patrón JSON.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"value\": " + value + ", \"unit\": \"" + unit + "\"}";
	}
	
}
