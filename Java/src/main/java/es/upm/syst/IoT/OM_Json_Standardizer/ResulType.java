package es.upm.syst.IoT.OM_Json_Standardizer;

/**
 * @author Guillermo, Yan Liu
 *
 */
public abstract class ResulType {
	
	
	public ResulType()
	{
		//TODO-Default Constructor
	}
	
	/**
	 * Devuelve una cadena (String) que contiene la parte de un Member o Geometry de una traza OM-JSON.
	 * 
	 * @return String
	 */
	public abstract String toStringOM();
	
}
