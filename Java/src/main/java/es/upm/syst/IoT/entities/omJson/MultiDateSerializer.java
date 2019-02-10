package es.upm.syst.IoT.entities.omJson;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Representa un serializador JSON para fechas con el formato requerido.
 * @author Guillermo, Yan Liu
 *
 */
public class MultiDateSerializer extends StdSerializer<Date> {
    private static final long serialVersionUID = 1L;
	
	public MultiDateSerializer() {
        this(null);
	}
	
	public MultiDateSerializer(Class<Date> virtualClass) {
		super(virtualClass);
	}
	
	/**
	 * @param value, jGen, provider
	 * @see com.fasterxml.jackson.databind.ser.std.StdSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(final Date value, final JsonGenerator jGen, final SerializerProvider provider) throws IOException {
		SimpleDateFormat pattern;
		pattern = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
		try {
			final String date = pattern.format(value); 
			pattern.parse(date);
			jGen.writeString(date);
		} catch(ParseException e){}
	}
}
