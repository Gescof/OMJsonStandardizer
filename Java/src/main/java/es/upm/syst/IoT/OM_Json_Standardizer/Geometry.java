package es.upm.syst.IoT.OM_Json_Standardizer;

/**
 * @author Guillermo, Yan Liu
 *
 */
public class Geometry extends ResulType {
	String type;
	float[] coordinates;
	
	public Geometry()
	{
		//TODO--Default Constructor
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public float[] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(float[] coordinates) {
		this.coordinates = coordinates;
	}
	
	/**
	 * @see es.upm.syst.IoT.OM_Json_Standardizer.ResulType#toStringOM()
	 * @return String
	 */
	public String toStringOM() {
		String string = "";
		
		string += "type: Point";
		string += "\n\t\tcoordinates: [" + this.coordinates[0] + ", " + this.coordinates[1] + "]";
		
		return string;
	}
	
}
