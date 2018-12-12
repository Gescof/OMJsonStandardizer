package es.upm.syst.IoT.OM_Json_Standardizer;

/**
 * Reperesenta una traza de una colección de observaciones.
 * @author Guillermo, Yan Liu
 *
 */
public class ObservationCollecionTraza {
	private ObservationCollection omCollection;
	
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
	
}
