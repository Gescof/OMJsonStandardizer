package es.upm.syst.IoT.persistence;

import java.util.List;

import es.upm.syst.IoT.entities.omJson.ObservationCollecionTraza;

/**
 * @author Guillermo, Yan Liu
 * @version 1.0
 */
public interface DAOObservationCollections {

	/**
	 * Alta de una OmCollectionTraza.
	 * @param omCollection
	 * @return
	 */
	public boolean altaOmCollection(ObservationCollecionTraza omCollection);
	
	/**
	 * Baja de una OmCollectionTraza.
	 * @param id
	 * @return
	 */
	public boolean bajaOmCollection(String id);	
	
	/**
	 * Búsqueda de una OmCollectionTraza.
	 * @param id
	 * @return
	 */
	public ObservationCollecionTraza getOmCollection(String id);
	
	/**
	 * Lista todas las OmCollectionTraza.
	 * @return
	 */
	public List<ObservationCollecionTraza> getListaOmCollections();
	
}
