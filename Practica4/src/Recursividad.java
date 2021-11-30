import java.util.ArrayList;

public class Recursividad {

	/**
	 * Funci�n recursiva que invierte una string de manera que el primer car�cter
	 * pasa a ser el �ltimo y viceversa.
	 * 
	 * @param frase String a invertir
	 * @return Devuelve una concatenaci�n de el resto de la string pasada a la
	 *         funci�n mas el primer car�cter del string en la �ltima posici�n
	 */
	public static String invertirFrase(String frase) {
		if (frase == "") {
			return frase;
		} else {
			return invertirFrase(frase.substring(1)) + frase.charAt(0);
		}
	}

	/**
	 * Funci�n recursiva que invierte el orden de las palabras en una frase (solo
	 * detecta los car�cteres de separaci�n " ")
	 * 
	 * @param frase String a invertir el orden de las palabras
	 * @return devuelve concatenaci�n de la inversi�n de la String restante + la
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
	 * Funci�n c�psula que envuelve a la funci�n recursiva posiblesManosRec
	 * 
	 * @param tama�oMano tama�o de las posibles manos a crear
	 * @param baraja     ArrayList de cartas con las que se crean las manos
	 */
	public static void posiblesManos(int tama�oMano, ArrayList<Carta> baraja) {
		posiblesManosRec(tama�oMano, baraja, new ArrayList<Carta>());
	}

	/**
	 * Funci�n recursiva que imprime por consola los posibles conjuntos de cartas
	 * que se pueden crear de tama�o n con las cartas de una baraja
	 * 
	 * @param tama�oMano tama�o n de las manos
	 * @param baraja     ArrayList de cartas con las que se crean las manos
	 * @param mano       ArrayList de cartas resultado a imprimir en cada llamada
	 */
	public static void posiblesManosRec(int tama�oMano, ArrayList<Carta> baraja, ArrayList<Carta> mano) {
		if (tama�oMano == 0) {
			System.out.println(mano);
		} else {
			for (Carta carta : baraja) {
				ArrayList<Carta> manoLocal = new ArrayList<Carta>(mano);
				manoLocal.add(carta);
				ArrayList<Carta> barajaLocal = new ArrayList<Carta>(baraja);
				barajaLocal.remove(carta);
				posiblesManosRec(tama�oMano - 1, barajaLocal, manoLocal);
			}
		}
	}

	/**
	 * Funci�n recursiva copia de posiblesManos cuya �nica diferencia es que se
	 * a�ade una condici�n a la hora de imprimir las manos En este caso solo se
	 * imprimen si tienen un as
	 * 
	 * @param tama�oMano
	 * @param baraja
	 */
	public static void filtraManos(int tama�oMano, ArrayList<Carta> baraja) {
		filtraManosRec(tama�oMano, baraja, new ArrayList<Carta>());
	}

	public static void filtraManosRec(int tama�oMano, ArrayList<Carta> baraja, ArrayList<Carta> mano) {
		if (tama�oMano == 0) {
			if ((mano.contains(new Carta(1, "Diamantes"))) || (mano.contains(new Carta(1, "Tr�boles")))
					|| (mano.contains(new Carta(1, "Picas"))) || (mano.contains(new Carta(1, "Corazones")))) {
				System.out.println(mano); // Solo printea si la mano tiene un as
			}
		} else {
			for (Carta carta : baraja) {
				ArrayList<Carta> manoLocal = new ArrayList<Carta>(mano);
				manoLocal.add(carta);
				ArrayList<Carta> barajaLocal = new ArrayList<Carta>(baraja);
				barajaLocal.remove(carta);
				filtraManosRec(tama�oMano - 1, barajaLocal, manoLocal);
			}
		}
	}

	public static void main(String[] args) {

		// Test invertirFrase()
		System.out.println(invertirFrase("Hola"));
		System.out.println(invertirFrase("Adi�s"));
		System.out.println(invertirFrase("En un lugar de La Mancha de cuyo nombre no quiero acordarme"));

		// Test invertirPalabras()
		System.out.println(invertirPalabras("Hola qu� tal est�s"));
		System.out.println(invertirPalabras("Adi�s; hasta luego"));
		System.out.println(invertirPalabras("Perdona, me dejas eso ?"));

		// Test posiblesManos()
		ArrayList<Carta> baraja = new ArrayList<Carta>();
		baraja.add(new Carta(1, "Picas"));
		baraja.add(new Carta(3, "Corazones"));
		baraja.add(new Carta(7, "Tr�boles"));
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
