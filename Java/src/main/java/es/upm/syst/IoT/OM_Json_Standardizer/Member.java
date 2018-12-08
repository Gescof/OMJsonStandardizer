package es.upm.syst.IoT.OM_Json_Standardizer;

/**
 * @author Guillermo, Yan Liu
 *
 */
public class Member extends ResulType {
	float value;
	String unit;
	
	public Member()
	{
		//TODO--Default Constructor
	}
	
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	/**
	 * @see es.upm.syst.IoT.OM_Json_Standardizer.ResulType#toStringOM()
	 * @return String
	 */
	public String toStringOM() {
		String string = "";
		
		string += "value: " + this.value;
		string += "\n\t\tuom: " + this.unit;
		
		return string;
	}
	
}
