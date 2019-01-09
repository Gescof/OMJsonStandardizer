package es.upm.syst.IoT.OM_Json_Standardizer;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Reperesenta una traza de una colección de observaciones.
 * @author Guillermo, Yan Liu
 *
 */
@JsonIgnoreProperties({"_id"})
public class ObservationCollecionTraza {
	private ObservationCollection omCollection;
	private String id;
	
	public ObservationCollecionTraza()
	{
		this.omCollection = new ObservationCollection();
	}
		
	/**
	 * Devuelve el contenido como objeto de tipo ObservationCollection.
	 * @return ObservationCollection omCollection
	 * @see ObservationCollection
	 */
	public ObservationCollection getOmCollection() {
		return omCollection;
	}
	
	/**
	 * Establece el objeto ObservationCollection de la clase.
	 * @param omCollection
	 * @see ObservationCollection
	 */
	public void setOmCollection(ObservationCollection omCollection) {
		this.omCollection = omCollection;
	}
	
	/**
	 * Devuelve el identificador de la traza que establece Azure Cosmos DB.
	 * @return _id
	 */
	@JsonGetter("_id")
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador de la traza para Azure Cosmos DB.
	 * @param _id
	 */
	@JsonSetter("_id")
	public void setId(String id) {
		this.id = id;
	}
	
}
