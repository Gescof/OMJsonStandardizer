package es.upm.syst.IoT.entities.mota;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Representa un deserializador JSON para fechas con el formato requerido.
 * @author Guillermo, Yan Liu
 *
 */
public class MultiDateDeserializer extends StdDeserializer<Date> {
    private static final long serialVersionUID = 1L;

    private static final String[] DATE_FORMATS = new String[] {
    	"yyyy-MM-dd'T'HH:mm:ss.SSZ",
    	"yyyy-MM-dd'T'HH:mm:ss.SS'Z'",
    	"yyyy-MM-dd",
    	"yyyy-MM",
    	"yyyy"
    };

    public MultiDateDeserializer() {
        this(null);
    }

    public MultiDateDeserializer(Class<?> virtualClass) {
        super(virtualClass);
    }

    /**
     * @param jsonParser, cTxt
     * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
     */
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext cTxt) throws IOException, JsonProcessingException {
    	boolean match = false;
    	Date dateTimestamp = new Date();
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if(node.textValue() != null) {
        	final String date = node.textValue();
            for (String dateFormat : DATE_FORMATS) {
            	if(!match) {
	                try {
	                	dateTimestamp = new SimpleDateFormat(dateFormat).parse(date);
	                	match = true;
	                } catch (ParseException e) {}
            	}
            	else break;
            }
            if(!match) {
            	throw new JsonParseException(jsonParser, "Unparseable date: \"" + date + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
            }
        }
        else if(node.isLong()) {
        	final long dateInstant = node.asLong();
        	dateTimestamp = new Date(dateInstant);
        }
        else {
        	throw new JsonParseException(jsonParser, "Unparseable date. Supported formats: " + Arrays.toString(DATE_FORMATS));
        }
        return dateTimestamp;
    }
}
