package es.upm.syst.IoT.entities.mota;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Representa una fecha que tenga una traza.
 * @author Guillermo, Yan Liu
 *
 */
public class Timestamp {
	@JsonDeserialize(using = MultiDateDeserializer.class)
	@JsonSerialize(using = MultiDateSerializer.class)
	private Date date;

	public Timestamp()
	{
		this.date = new Date();
	}
	
	public Timestamp(Date date) {
		this.date = date;
	}

	/**
	 * Devuelve la fecha.
	 * @return Date date
	 */
	@JsonGetter("$date")
	public Date getDate() {
		return date;
	}
	
	/**
	 * Establece la fecha.
	 * @param date
	 */
	@JsonSetter("$date")
	public void setDate(Date date) {
		this.date = date;
	}

	/* Devuelve en formato de cadena este objeto siguiendo el patrón JSON.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"$date\": \"" + date + "\"}";
	}
	
}
