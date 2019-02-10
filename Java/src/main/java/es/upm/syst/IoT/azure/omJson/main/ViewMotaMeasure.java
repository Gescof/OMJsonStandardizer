package es.upm.syst.IoT.azure.omJson.main;

import java.io.UnsupportedEncodingException;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.upm.syst.IoT.entities.mota.MotaMeasureTraza;
import es.upm.syst.IoT.services.GestorMotaMeasures;

/**
 * CRUD de trazas JSON no estandarizadas.
 * @author Guillermo, Yan Liu
 * @version 1.0
 */
public class ViewMotaMeasure {
	private static ApplicationContext context;
	private static GestorMotaMeasures gestorMotas;
	private static Scanner sc;
	
	static {
		context = new ClassPathXmlApplicationContext("beansMotas.xml");
		gestorMotas = context.getBean("gestorMotas", GestorMotaMeasures.class);
		sc = new Scanner(System.in);
	}
	
	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws ParseException 
	 */
	public static void main(final String[] args) throws UnsupportedEncodingException, JsonProcessingException, ParseException
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
		System.out.println("1. Generar trazas con valores aleatorios de motas a fichero (motaMeasures.json).");
		System.out.println("2. Alta en Azure Cosmos DB de una nueva traza de mota.");
		System.out.println("3. Baja de Azure Cosmos DB de una traza de mota.");
		System.out.println("4. Buscar una traza de mota en Azure Cosmos DB.");
		System.out.println("5. Listar todas las trazas de motas de Azure Cosmos DB.");
		System.out.println("0. Salir.");
	}
	
	/**
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws ParseException
	 */
	private static void generarTrazas() throws UnsupportedEncodingException, JsonProcessingException, ParseException {
		gestorMotas.generateMotaMeasuresToFile();
	}
	
	/**
	 * Operación para dar de alta una traza de mota.
	 */
	private static void altaTraza() {
		MotaMeasureTraza motaTraza = context.getBean("motaTraza", MotaMeasureTraza.class);
		if(gestorMotas.altaMota(motaTraza)) {
			System.out.println("Traza dada de alta en la base de datos.");
		} else {
			System.out.println("No se ha podido dar de alta la traza en la base de datos.");
		}
	}
	
	/**
	 * Operación para dar de baja una traza de mota.
	 */
	private static void bajaTraza() {
		System.out.println("Introduzca el id a buscar para dar de baja: ");
		String id = sc.nextLine();
		if(gestorMotas.bajaMota(id)) {
			System.out.println("Traza dada de baja de la base de datos.");
		} else {
			System.out.println("No se ha podido dar de baja a la traza en la base de datos.");
		}
	}
	
	/**
	 * Operación para buscar una traza de mota.
	 */
	private static void buscarTraza() {
		System.out.println("Introduzca el id a buscar: ");
		String id = sc.nextLine();
		MotaMeasureTraza motaTraza = gestorMotas.getMotaTraza(id);
		if(motaTraza != null) {
			System.out.println("Traza encontrada:");
			System.out.println(motaTraza.toString());
		} else {
			System.out.println("La traza con el id " + id + " no se encuentra en la base de datos.");
		}
	}
	
	/**
	 * Operación para listar todas las trazas de motas.
	 */
	private static void listarTrazas() {
		List<MotaMeasureTraza> listaMotas = gestorMotas.getListaMotaTrazas();
		if(!listaMotas.isEmpty()) {
			System.out.println(listaMotas.toString());
		} else {
			System.out.println("Lista de trazas vacia.");
		}
	}
	
}
