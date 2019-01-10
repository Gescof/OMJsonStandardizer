package es.upm.syst.IoT.OM_Json_Standardizer;

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
	@JsonDeserialize(using = MultiDateDeserializer.class)
	public Date getDate() {
		return date;
	}
	
	/**
	 * Establece la fecha.
	 * @param date
	 */
	@JsonSetter("$date")
	@JsonSerialize(using = MultiDateSerializer.class)
	public void setDate(Date date) {
		this.date = date;
	}
	
}
