package es.upm.syst.IoT.OM_Json_Standardizer;

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
	
	/**
	 * Devuelve la temperatura como objeto de tipo Member.
	 * @return Member temperature
	 * @see Member
	 */
	public Member getTemperature() {
		return temperature;
	}
	
	/**
	 * Establece la temperatura como objeto Member.
	 * @param temperature
	 * @see Member
	 */
	public void setTemperature(Member temperature) {
		this.temperature = temperature;
	}
	
	/**
	 * Devuelve la humedad como objeto de tipo Member.
	 * @return Member humidity
	 * @see Member
	 */
	public Member getHumidity() {
		return humidity;
	}
	
	/**
	 * Establece la humedad como objeto Member.
	 * @param humidity
	 * @see Member
	 */
	public void setHumidity(Member humidity) {
		this.humidity = humidity;
	}
	
	/**
	 * Devuelve la luminosidad como objeto de tipo Member.
	 * @return Member luminosity
	 * @see Member
	 */
	public Member getLuminosity() {
		return luminosity;
	}
	
	/**
	 * Establece la luminosidad como objeto Member.
	 * @param luminosity
	 * @see Member
	 */
	public void setLuminosity(Member luminosity) {
		this.luminosity = luminosity;
	}
	
}
