package es.upm.syst.IoT.OM_Json_Standardizer;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Representa una fecha que tenga una traza.
 * @author Guillermo, Yan Liu
 *
 */
public class Timestamp {

	@JsonFormat(
			shape = JsonFormat.Shape.STRING,
			pattern = "yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
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
