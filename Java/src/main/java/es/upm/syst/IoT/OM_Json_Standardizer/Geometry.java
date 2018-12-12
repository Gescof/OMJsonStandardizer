package es.upm.syst.IoT.OM_Json_Standardizer;

/**
 * Representa la geometría que contiene una traza.
 * @author Guillermo, Yan Liu
 *
 */
public class Geometry extends ResulType {
	private String type;
	private float[] coordinates;
	
	public Geometry()
	{
		//TODO--Default Constructor
	}
	
	/**
	 * Devuelve el tipo de geometría como cadena.
	 * @return String type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Establece el tipo de geometría como cadena.
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Devuelve las coordenadas.
	 * @return float[] coordinates
	 */
	public float[] getCoordinates() {
		return coordinates;
	}
	
	/**
	 * Establece las coordenadas.
	 * @param coordinates
	 */
	public void setCoordinates(float[] coordinates) {
		this.coordinates = coordinates;
	}
	
	/**
	 * Devuelve un objeto Geometry a una cadena con el formato OM-JSON.
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
