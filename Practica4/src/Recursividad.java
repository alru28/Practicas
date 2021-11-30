import java.util.ArrayList;

public class Recursividad {

	/**
	 * Función recursiva que invierte una string de manera que el primer carácter
	 * pasa a ser el último y viceversa.
	 * 
	 * @param frase String a invertir
	 * @return Devuelve una concatenación de el resto de la string pasada a la
	 *         función mas el primer carácter del string en la última posición
	 */
	public static String invertirFrase(String frase) {
		if (frase == "") {
			return frase;
		} else {
			return invertirFrase(frase.substring(1)) + frase.charAt(0);
		}
	}

	/**
	 * Función recursiva que invierte el orden de las palabras en una frase (solo
	 * detecta los carácteres de separación " ")
	 * 
	 * @param frase String a invertir el orden de las palabras
	 * @return devuelve concatenación de la inversión de la String restante + la
	 *         primera palabra
	 */
	public static String invertirPalabras(String frase) {
		if (frase == "") {
			return frase;
		}
		String[] subFrase = frase.split(" ", 2);
		String primeraPalabra = subFrase[0];
		String fraseRestante;
		if (subFrase.length > 1) {
			fraseRestante = subFrase[1];
		} else {
			fraseRestante = "";

		}
		return invertirPalabras(fraseRestante) + primeraPalabra + " ";
	}

	/**
	 * Función cápsula que envuelve a la función recursiva posiblesManosRec
	 * 
	 * @param tamañoMano tamaño de las posibles manos a crear
	 * @param baraja     ArrayList de cartas con las que se crean las manos
	 */
	public static void posiblesManos(int tamañoMano, ArrayList<Carta> baraja) {
		posiblesManosRec(tamañoMano, baraja, new ArrayList<Carta>());
	}

	/**
	 * Función recursiva que imprime por consola los posibles conjuntos de cartas
	 * que se pueden crear de tamaño n con las cartas de una baraja
	 * 
	 * @param tamañoMano tamaño n de las manos
	 * @param baraja     ArrayList de cartas con las que se crean las manos
	 * @param mano       ArrayList de cartas resultado a imprimir en cada llamada
	 */
	public static void posiblesManosRec(int tamañoMano, ArrayList<Carta> baraja, ArrayList<Carta> mano) {
		if (tamañoMano == 0) {
			System.out.println(mano);
		} else {
			for (Carta carta : baraja) {
				ArrayList<Carta> manoLocal = new ArrayList<Carta>(mano);
				manoLocal.add(carta);
				ArrayList<Carta> barajaLocal = new ArrayList<Carta>(baraja);
				barajaLocal.remove(carta);
				posiblesManosRec(tamañoMano - 1, barajaLocal, manoLocal);
			}
		}
	}

	/**
	 * Función recursiva copia de posiblesManos cuya única diferencia es que se
	 * añade una condición a la hora de imprimir las manos En este caso solo se
	 * imprimen si tienen un as
	 * 
	 * @param tamañoMano
	 * @param baraja
	 */
	public static void filtraManos(int tamañoMano, ArrayList<Carta> baraja) {
		filtraManosRec(tamañoMano, baraja, new ArrayList<Carta>());
	}

	public static void filtraManosRec(int tamañoMano, ArrayList<Carta> baraja, ArrayList<Carta> mano) {
		if (tamañoMano == 0) {
			if ((mano.contains(new Carta(1, "Diamantes"))) || (mano.contains(new Carta(1, "Tréboles")))
					|| (mano.contains(new Carta(1, "Picas"))) || (mano.contains(new Carta(1, "Corazones")))) {
				System.out.println(mano); // Solo printea si la mano tiene un as
			}
		} else {
			for (Carta carta : baraja) {
				ArrayList<Carta> manoLocal = new ArrayList<Carta>(mano);
				manoLocal.add(carta);
				ArrayList<Carta> barajaLocal = new ArrayList<Carta>(baraja);
				barajaLocal.remove(carta);
				filtraManosRec(tamañoMano - 1, barajaLocal, manoLocal);
			}
		}
	}

	public static void main(String[] args) {

		// Test invertirFrase()
		System.out.println(invertirFrase("Hola"));
		System.out.println(invertirFrase("Adiós"));
		System.out.println(invertirFrase("En un lugar de La Mancha de cuyo nombre no quiero acordarme"));

		// Test invertirPalabras()
		System.out.println(invertirPalabras("Hola qué tal estás"));
		System.out.println(invertirPalabras("Adiós; hasta luego"));
		System.out.println(invertirPalabras("Perdona, me dejas eso ?"));

		// Test posiblesManos()
		ArrayList<Carta> baraja = new ArrayList<Carta>();
		baraja.add(new Carta(1, "Picas"));
		baraja.add(new Carta(3, "Corazones"));
		baraja.add(new Carta(7, "Tréboles"));
		baraja.add(new Carta(12, "Diamantes"));
		System.out.println("POSIBLES MANOS TEST 1");
		posiblesManos(4, baraja);
		System.out.println("POSIBLES MANOS TEST 2");
		posiblesManos(3, baraja);
		System.out.println("POSIBLES MANOS TEST 3");
		posiblesManos(2, baraja);

		// Test filtraManos()
		System.out.println("FILTRA MANOS TEST 1");
		filtraManos(4, baraja);
		System.out.println("FILTRA MANOS TEST 2");
		filtraManos(3, baraja);
		System.out.println("FILTRA MANOS TEST 3");
		filtraManos(2, baraja);
	}

}
