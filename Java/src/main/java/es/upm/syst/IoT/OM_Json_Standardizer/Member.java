package es.upm.syst.IoT.OM_Json_Standardizer;

/**
 * Representa el resultado de un miembro de una traza.
 * @author Guillermo, Yan Liu
 *
 */
public class Member extends ResulType {
	private float value;
	private String unit;
	
	public Member()
	{
		//TODO--Default Constructor
	}
	
	/**
	 * Devuelve el valor de este objeto.
	 * @return float value
	 */
	public float getValue() {
		return value;
	}
	
	/**
	 * Establece el valor para este objeto.
	 * @param value
	 */
	public void setValue(float value) {
		this.value = value;
	}
	
	/**
	 * Devuelve la unidad de medida como cadena.
	 * @return String unit
	 */
	public String getUnit() {
		return unit;
	}
	
	/**
	 * Establece la unidad de medida como cadena.
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	/**
	 * Devuelve un objeto Member a una cadena con el formato OM-JSON.
	 * @see es.upm.syst.IoT.OM_Json_Standardizer.ResulType#toStringOM()
	 * @return String
	 */
	public String toStringOM() {
		String string = "";
		string += "value: " + this.value;
		string += "\n\t\tuom: " + this.unit;
		
		return string;
	}
	
	/**
	 * Devuelve el objeto Member estandarizado a OM-JSON.
	 * @return Member
	 */
	public Member standarizeMember() {
		if(unit.compareTo("Cº") == 0) {
			unit = "https://en.wikipedia.org/wiki/Celsius";
		}
		else if(unit.compareTo("RH") == 0) {
			unit = "https://en.wikipedia.org/wiki/Relative_humidity";
		}
		else if(unit.compareTo("lx") == 0) {
			unit = "https://en.wikipedia.org/wiki/Lux";
		}
		return this;
	}
	
}
