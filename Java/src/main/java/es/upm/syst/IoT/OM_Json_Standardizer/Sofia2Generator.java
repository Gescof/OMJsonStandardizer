package es.upm.syst.IoT.OM_Json_Standardizer;

import com.indra.sofia2.ssap.kp.exceptions.ConnectionToSIBException;
import com.indra.sofia2.ssap.kp.exceptions.SSAPResponseTimeoutException;
import com.indra.sofia2.ssap.kp.implementations.rest.exception.ResponseMapperException;

public class Sofia2Generator {
	
	/**
	 * @param $args
	 * @throws ResponseMapperException
	 * @throws ConnectionToSIBException
	 * @throws SSAPResponseTimeoutException
	 */
	public static void main(final String ... $args) throws ResponseMapperException, ConnectionToSIBException, SSAPResponseTimeoutException 
	{
		Sofia2Producer.main("producer1");
		Sofia2Consumer.main("consumer1");
	}
}
