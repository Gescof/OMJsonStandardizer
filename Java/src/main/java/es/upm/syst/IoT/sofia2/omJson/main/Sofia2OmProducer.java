package es.upm.syst.IoT.sofia2.omJson.main;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.minsait.onesait.platform.client.RestClient;
import com.minsait.onesait.platform.client.TimeOutConfig;

public class Sofia2OmProducer {
	private static Logger log;
	private static final String URL = "http://development.onesaitplatform.com/iot-broker";
	private static final String DEVICE = "EtsisiApp";
	private static final String TOKEN = "724fcaba296742cba85e2a92357d8d86";
	private static final String ONTOLOGY_NAME = "OMJson";
	
	private static void generateOmJson(RestClient client) {
		
	}
	
	/**
	 * @param $args
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 */
	public static void main(final String ... $args) throws JsonProcessingException, ParseException
	{
		try {
			BasicConfigurator.configure();
			RestClient client = new RestClient(URL, 
					TimeOutConfig.builder().connectTimeout(5).readTimeouts(5).writeTimeout(5).timeunit(TimeUnit.SECONDS).build());
			client.connect(TOKEN, DEVICE, "device-test", true);
			generateOmJson(client);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
