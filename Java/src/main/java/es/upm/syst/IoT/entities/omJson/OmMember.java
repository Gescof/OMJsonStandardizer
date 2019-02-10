package es.upm.syst.IoT.entities.omJson;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Representa un miembro de una OMCollection.
 * @author Guillermo, Yan Liu
 *
 */
public class OmMember {	
	private String id;
	private String type;
	private Timestamp resultTime;
	private ResulType resultType;
	
	public OmMember()
	{
		this.resultTime = new Timestamp();
	}
	
	/**
	 * Crea un objeto OMMember con cada uno de los atributos.
	 * @param String id
	 * @param String type
	 * @param Timestamp resultTime
	 * @param ResulType resultType
	 * @see Timestamp
	 * @see ResulType
	 */
	public OmMember(String id, String type, Timestamp resultTime, ResulType resultType)
	{
		this.id = id;
		this.type = type;
		this.resultTime = resultTime;
		this.resultType = resultType;
	}
	
	/**
	 * Devuelve el identificador como cadena.
	 * @return String id
	 */
	@JsonGetter("id")
	public String getId() {
		return id;
	}
	
	/**
	 * Establece el identificador como cadena.
	 * @param id
	 */
	@JsonSetter("id")
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Devuelve el tipo de OMMember como cadena.
	 * @return String type
	 */
	@JsonGetter("type")
	public String getType() {
		return type;
	}
	
	/**
	 * Establece el tipo de OMMember como cadena.
	 * @param type
	 */
	@JsonSetter("type")
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Devuelve la fecha de observación del resultado como Timestamp.
	 * @return Timestamp resultTime
	 * @see Timestamp
	 */
	@JsonGetter("resultTime")
	public Timestamp getResultTime() {
		return resultTime;
	}
	
	/**
	 * Establece la fecha de observación del resultado como Timestamp.
	 * @param resultTime
	 * @see Timestamp
	 */
	@JsonSetter("resultTime")
	public void setResultTime(Timestamp resultTime) {
		this.resultTime = resultTime;
	}

	/**
	 * Devuelve el tipo de resultado como ResulType.
	 * @return ResulType result
	 * @see ResulType
	 */
	@JsonGetter("result")
	public ResulType getResult() {
		return resultType;
	}
	
	/**
	 * Establece el tipo de resultado como ResulType.
	 * @param result
	 * @see ResulType
	 */
	@JsonSetter("result")
	public void setResult(ResulType result) {
		this.resultType = result;
	}

	/* Devuelve en formato de cadena este objeto siguiendo el patrón OM-JSON.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"id\": \"" + id + "\", \"type\": " + type + ", \"resultTime\": " + resultTime 
				+ ", \"result\": {" + resultType + "}";
	}
	
}
