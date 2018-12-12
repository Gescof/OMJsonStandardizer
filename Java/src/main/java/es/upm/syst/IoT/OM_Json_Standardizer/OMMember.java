package es.upm.syst.IoT.OM_Json_Standardizer;

/**
 * Representa un miembro de una OMCollection.
 * @author Guillermo, Yan Liu
 *
 */
public class OMMember {	
	private String id;
	private String type;
	private Timestamp resultTime;
	private ResulType result;
	
	public OMMember()
	{
		//TODO--Default Constructor
	}
	
	/**
	 * Crea un objeto OMMember con cada uno de los atributos.
	 * @param String id
	 * @param String type
	 * @param Timestamp resultTime
	 * @param ResulType result
	 * @see Timestamp
	 * @see ResulType
	 */
	public OMMember(String id, String type, Timestamp resultTime, ResulType result)
	{
		this.id = id;
		this.type = type;
		this.resultTime = resultTime;
		this.result = result;
	}
	
	/**
	 * Devuelve el identificador como cadena.
	 * @return String id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Establece el identificador como cadena.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Devuelve el tipo de OMMember como cadena.
	 * @return String type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Establece el tipo de OMMember como cadena.
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Devuelve la fecha de observación del resultado como Timestamp.
	 * @return Timestamp resultTime
	 * @see Timestamp
	 */
	public Timestamp getResultTime() {
		return resultTime;
	}
	
	/**
	 * Establece la fecha de observación del resultado como Timestamp.
	 * @param resultTime
	 * @see Timestamp
	 */
	public void setResultTime(Timestamp resultTime) {
		this.resultTime = resultTime;
	}

	/**
	 * Devuelve el tipo de resultado como ResulType.
	 * @return ResulType result
	 * @see ResulType
	 */
	public ResulType getResult() {
		return result;
	}
	
	/**
	 * Establece el tipo de resultado como ResulType.
	 * @param result
	 * @see ResulType
	 */
	public void setResult(ResulType result) {
		this.result = result;
	}
	
}
