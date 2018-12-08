package es.upm.syst.IoT.OM_Json_Standardizer;

/**
 * @author Guillermo, Yan Liu
 *
 */
public class Measures {
	Member temperature;
	Member humidity;
	Member luminosity;
	
	public Measures()
	{
		//TODO--Default Constructor
	}
	
	public Member getTemperature() {
		return temperature;
	}
	public void setTemperature(Member temperature) {
		this.temperature = temperature;
	}
	
	public Member getHumidity() {
		return humidity;
	}
	public void setHumidity(Member humidity) {
		this.humidity = humidity;
	}
	
	public Member getLuminosity() {
		return luminosity;
	}
	public void setLuminosity(Member luminosity) {
		this.luminosity = luminosity;
	}
	
}
