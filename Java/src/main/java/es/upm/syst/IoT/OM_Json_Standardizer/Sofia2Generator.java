package es.upm.syst.IoT.OM_Json_Standardizer;

import com.indra.sofia2.ssap.kp.implementations.rest.exception.ResponseMapperException;

public class Sofia2Generator {
	
	public static void main(final String ... $args) throws ResponseMapperException 
	{
		Sofia2Producer.main("producer1");
		Sofia2Consumer.main("consumer1");
	}
}
