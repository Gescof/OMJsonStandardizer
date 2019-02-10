package es.upm.syst.IoT.azure.omJson.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.upm.syst.IoT.entities.omJson.ObservationCollecionTraza;
import es.upm.syst.IoT.services.GestorOmCollections;

/**
 * CRUD de trazas OM-JSON. 
 * @author Guillermo, Yan Liu
 * @version 1.0
 */
public class ViewOmJson {
	private static ApplicationContext context;
	private static GestorOmCollections gestorOmCollections;
	private static Scanner sc;
	
	static {
		context = new ClassPathXmlApplicationContext("beansOm.xml");
		gestorOmCollections = context.getBean("gestorOmCollections", GestorOmCollections.class);
		sc = new Scanner(System.in);
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseException 
	 */
	public static void main(final String[] args) throws IOException, ParseException
	{
		String opcionStr;
		do {
			mostrarMenu();
			opcionStr = sc.nextLine();
			switch (opcionStr) {
			case "1":
				generarTrazas();
				break;
			case "2":
				altaTraza();
				break;
			case "3":
				bajaTraza();
				break;
			case "4":
				buscarTraza();
				break;
			case "5":
				listarTrazas();
				break;
			case "0":
				System.out.println("Fin del programa.");
				break;
			default:
				System.out.println("Entrada no valida.");
			}	
		} while(!opcionStr.equals("0"));
	}
	
	/**
	 * Muestra el menú de operaciones por consola.
	 */
	private static void mostrarMenu() {
		System.out.println("\nSeleccione una opcion...");
		System.out.println("1. Generar trazas de motas de fichero (motaMeasures.json) a OM-Collections.");
		System.out.println("2. Alta en Azure Cosmos DB de una nueva traza OM-Collection.");
		System.out.println("3. Baja de Azure Cosmos DB de una traza OM-Collection.");
		System.out.println("4. Buscar una traza de OM-Collection en Azure Cosmos DB.");
		System.out.println("5. Listar todas las trazas de OM-Collections de Azure Cosmos DB.");
		System.out.println("0. Salir.");
	}
	
	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	private static void generarTrazas() throws ParseException, IOException {
		gestorOmCollections.generateOMJsonFromFile();
	}
	
	/**
	 * Operación para dar de alta una OM-Collection.
	 */
	private static void altaTraza() {
		ObservationCollecionTraza omTraza = context.getBean("omCollectionTraza", ObservationCollecionTraza.class);
		if(gestorOmCollections.altaOmCollection(omTraza)) {
			System.out.println("Traza dada de alta en la base de datos.");
		} else {
			System.out.println("No se ha podido dar de alta la traza en la base de datos.");
		}
	}
	
	/**
	 * Operación para dar de baja una OM-Collection.
	 */
	private static void bajaTraza() {
		System.out.println("Introduzca el id a buscar para dar de baja: ");
		String id = sc.nextLine();
		if(gestorOmCollections.bajaOmCollection(id)) {
			System.out.println("Traza dada de baja de la base de datos.");
		} else {
			System.out.println("No se ha podido dar de baja a la traza en la base de datos.");
		}
	}
	
	/**
	 * Operación para buscar una OM-Collection.
	 */
	private static void buscarTraza() {
		System.out.println("Introduzca el id a buscar: ");
		String id = sc.nextLine();
		ObservationCollecionTraza omTraza = gestorOmCollections.getOmCollection(id);
		if(omTraza != null) {
			System.out.println("Traza encontrada:");
			System.out.println(omTraza.toString());
		} else {
			System.out.println("La traza con el id " + id + " no se encuentra en la base de datos.");
		}
	}
	
	/**
	 * Operación para listar todas las OM-Collection.
	 */
	private static void listarTrazas() {
		List<ObservationCollecionTraza> listaOmCollections = gestorOmCollections.getListaOmCollections();
		if(!listaOmCollections.isEmpty()) {
			System.out.println(listaOmCollections.toString());
		} else {
			System.out.println("Lista de trazas vacia.");
		}
	}
	
}
