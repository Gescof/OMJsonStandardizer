package es.upm.syst.IoT.jsonSerialized;

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
	
}
