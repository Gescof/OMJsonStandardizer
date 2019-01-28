package es.upm.syst.IoT.jsonSerialized;

/**
 * Representa el tipo de resultado para una OMCollection. Puede ser un OMMember o una Geometry.
 * @author Guillermo, Yan Liu
 * @see OMMember
 * @see Geometry
 */
public abstract class ResulType {
	
	public ResulType()
	{
		//TODO-Default Constructor
	}
	
	/**
	 * Devuelve una cadena que contiene la parte de un Member o Geometry de una traza OM-JSON.
	 * @return String
	 */
	public abstract String toStringOM();
	
}
