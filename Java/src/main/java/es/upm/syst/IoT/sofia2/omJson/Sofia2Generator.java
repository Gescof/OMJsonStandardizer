package es.upm.syst.IoT.sofia2.omJson;

import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.indra.sofia2.ssap.kp.exceptions.ConnectionToSIBException;
import com.indra.sofia2.ssap.kp.exceptions.SSAPResponseTimeoutException;
import com.indra.sofia2.ssap.kp.implementations.rest.exception.ResponseMapperException;

/**
 * @author Guillermo, Yan Liu
 * @version 1.0
 *
 */
public class Sofia2Generator {
	
	/**
	 * @param $args
	 * @throws ResponseMapperException
	 * @throws ConnectionToSIBException
	 * @throws SSAPResponseTimeoutException
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 */
	public static void main(final String ... $args) throws ResponseMapperException, ConnectionToSIBException, SSAPResponseTimeoutException, JsonProcessingException, ParseException 
	{
		Sofia2Producer.main("producer1");
		Sofia2Consumer.main("consumer1");
	}
}
