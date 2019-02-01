package es.upm.syst.IoT.jsonJava;

import java.util.ArrayList;

/**
 * Representa una colección de observaciones.
 * @author Guillermo, Yan Liu
 *
 */
public class ObservationCollection {
	private String id;
	private Timestamp phenomenomTime;
	private ArrayList<OMMember> members;
	
	public ObservationCollection()
	{
		this.phenomenomTime = new Timestamp();
		this.members = new ArrayList<OMMember>();
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
		this.id = "observation collection " + id;
	}
	
	/**
	 * Devuelve la fecha de observación del fenómeno como Timestamp.
	 * @return Timestamp phenomenomTime
	 * @see Timestamp
	 */
	public Timestamp getPhenomenomTime() {
		return phenomenomTime;
	}
	
	/**
	 * Establece la fecha de observación del fenómeno como Timestamp.
	 * @param phenomenomTime
	 */
	public void setPhenomenomTime(Timestamp phenomenomTime) {
		this.phenomenomTime = phenomenomTime;
	}
	
	/**
	 * Devuelve la lista de miembros de la colección como ArrayList<OMMember>.
	 * @return ArrayList<OMMember> members
	 * @see OMMember
	 */
	public ArrayList<OMMember> getMembers() {
		return members;
	}
	
	/**
	 * Establece la lista de miembros de la colección como ArrayList<OMMember>.
	 * @param members
	 * @see OMMember
	 */
	public void setMembers(ArrayList<OMMember> members) {
		this.members = members;
	}
	
}
