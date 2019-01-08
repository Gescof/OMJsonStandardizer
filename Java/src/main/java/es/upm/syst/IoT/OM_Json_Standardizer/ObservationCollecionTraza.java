package es.upm.syst.IoT.OM_Json_Standardizer;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Reperesenta una traza de una colección de observaciones.
 * @author Guillermo, Yan Liu
 *
 */
public class ObservationCollecionTraza {
	private ObservationCollection omCollection;
	private String _id;
	
	public ObservationCollecionTraza()
	{
		//TODO--Default Constructor
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
	@JsonIgnore
	public String get_id() {
		return _id;
	}

	/**
	 * Establece el identificador de la traza para Azure Cosmos DB.
	 * @param _id
	 */
	@JsonIgnore
	public void set_id(String _id) {
		this._id = _id;
	}
	
}
