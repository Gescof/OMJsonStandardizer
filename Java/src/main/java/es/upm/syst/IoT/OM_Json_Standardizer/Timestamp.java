package es.upm.syst.IoT.OM_Json_Standardizer;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Representa una fecha que tenga una traza.
 * @author Guillermo, Yan Liu
 *
 */
public class Timestamp {

	private String date;

	public Timestamp()
	{
		//TODO--Default Constructor
	}
	
	/**
	 * Devuelve la fecha como cadena.
	 * @return String date
	 */
	@JsonGetter("$date")
	public String getDate() {
		return date;
	}
	
	/**
	 * Establece la fecha como cadena.
	 * @param date
	 */
	@JsonSetter("$date")
	public void setDate(String date) {
		this.date = date;
	}
	
}
