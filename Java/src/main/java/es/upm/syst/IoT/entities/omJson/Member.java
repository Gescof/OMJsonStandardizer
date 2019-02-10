package es.upm.syst.IoT.entities.omJson;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Representa el resultado de un miembro de una traza.
 * @author Guillermo, Yan Liu
 *
 */
public class Member implements ResulType {
	private float value;
	private String uom;
	
	public Member()
	{}
	
	public Member(float value, String unit) {
		this.value = value;
		this.uom = unit;
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
	@JsonGetter("uom")
	public String getUnit() {
		return uom;
	}
	
	/**
	 * Establece la unidad de medida como cadena.
	 * @param uom
	 */
	@JsonSetter("uom")
	public void setUnit(String uom) {
		this.uom = uom;
	}
	
	/**
	 * Devuelve en formato de cadena este objeto siguiendo el patrón OM-JSON.
	 * @see es.upm.syst.IoT.entities.mota.ResulType#toStringOM()
	 * @return String
	 */
	@Override
	public String toString() {
		return "\"value\": " + value + ", \"uom\": \"" + uom + "\"";
	}
	
}
