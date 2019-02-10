package es.upm.syst.IoT.entities.mota;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Representa las medidas que contiene una traza.
 * @author Guillermo, Yan Liu
 *
 */
public class Measures {
	private Member temperature;
	private Member humidity;
	private Member luminosity;
	
	/**
	 * <p>Establece las unidades de medida para cada Member:
	 * <li>Member temperature ("Cº")</li>
	 * <li>Member humidity ("RH")</li>
	 * <li>Member luminosity ("lx")</li>
	 * </p>
	 */
	public Measures()
	{
		temperature = new Member();
		temperature.setUnit("Cº");
		humidity = new Member();
		humidity.setUnit("RH");
		luminosity = new Member();
		luminosity.setUnit("lx");
	}
	
	public Measures(Member temperature, Member humidity, Member luminosity) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.luminosity = luminosity;
	}

	/**
	 * Devuelve la temperatura como objeto de tipo Member.
	 * @return Member temperature
	 * @see Member
	 */
	@JsonGetter("temperature")
	public Member getTemperature() {
		return temperature;
	}
	
	/**
	 * Establece la temperatura como objeto Member.
	 * @param temperature
	 * @see Member
	 */
	@JsonSetter("temperature")
	public void setTemperature(Member temperature) {
		this.temperature = temperature;
	}
	
	/**
	 * Devuelve la humedad como objeto de tipo Member.
	 * @return Member humidity
	 * @see Member
	 */
	@JsonGetter("humidity")
	public Member getHumidity() {
		return humidity;
	}
	
	/**
	 * Establece la humedad como objeto Member.
	 * @param humidity
	 * @see Member
	 */
	@JsonSetter("humidity")
	public void setHumidity(Member humidity) {
		this.humidity = humidity;
	}
	
	/**
	 * Devuelve la luminosidad como objeto de tipo Member.
	 * @return Member luminosity
	 * @see Member
	 */
	@JsonGetter("luminosity")
	public Member getLuminosity() {
		return luminosity;
	}
	
	/**
	 * Establece la luminosidad como objeto Member.
	 * @param luminosity
	 * @see Member
	 */
	@JsonSetter("luminosity")
	public void setLuminosity(Member luminosity) {
		this.luminosity = luminosity;
	}

	/* Devuelve en formato de cadena este objeto siguiendo el patrón JSON.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"temperature\": " + temperature + ", \"humidity\": " + humidity + ", \"luminosity\": " + luminosity + "}";
	}
	
}
