package es.upm.syst.IoT.entities.omJson;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Representa una colección de observaciones.
 * @author Guillermo, Yan Liu
 *
 */
public class ObservationCollection {
	private String id;
	private Timestamp phenomenomTime;
	private List<OmMember> members;
	
	public ObservationCollection()
	{
		this.phenomenomTime = new Timestamp();
		this.members = new ArrayList<>();
	}
	
	public ObservationCollection(String id, Timestamp phenomenomTime, List<OmMember> members) {
		this.id = id;
		this.phenomenomTime = phenomenomTime;
		this.members = members;
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
		this.id = "observation collection " + id;
	}
	
	/**
	 * Devuelve la fecha de observación del fenómeno como Timestamp.
	 * @return Timestamp phenomenomTime
	 * @see Timestamp
	 */
	@JsonGetter("phenomenomTime")
	public Timestamp getPhenomenomTime() {
		return phenomenomTime;
	}
	
	/**
	 * Establece la fecha de observación del fenómeno como Timestamp.
	 * @param phenomenomTime
	 */
	@JsonSetter("phenomenomTime")
	public void setPhenomenomTime(Timestamp phenomenomTime) {
		this.phenomenomTime = phenomenomTime;
	}
	
	/**
	 * Devuelve la lista de miembros de la colección como ArrayList<OMMember>.
	 * @return ArrayList<OMMember> members
	 * @see OmMember
	 */
	@JsonGetter("member")
	public List<OmMember> getMembers() {
		return members;
	}
	
	/**
	 * Establece la lista de miembros de la colección como ArrayList<OMMember>.
	 * @param members
	 * @see OmMember
	 */
	@JsonSetter("member")
	public void setMembers(List<OmMember> members) {
		this.members = members;
	}

	/* Devuelve en formato de cadena este objeto siguiendo el patrón OM-JSON.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\"id\": observation-collection " + id + ", \"phenomenomTime\": {" + phenomenomTime + "}, \"member\": [" + members + "]";
	}
	
}
