
public class Carta {

	public static String[] posiblesPalos = new String[] { "Corazones", "Diamantes", "Tréboles", "Picas" };
	private int numero;
	private String palo;

	Carta() {
		this.numero = 0;
		this.palo = "Nulo";
	}

	public Carta(int numero, String palo) {
		super();
		setNumero(numero);
		setPalo(palo);
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		if ((1 <= numero) && (numero <= 13)) {
			this.numero = numero;
		} else {
			this.numero = 0;
		}
	}

	public String getPalo() {
		return palo;
	}

	public void setPalo(String palo) {
		for (String posiblePalo : posiblesPalos) {
			if (palo.equals(posiblePalo)) {
				this.palo = palo;
				return;
			}
		}
		this.palo = "Nulo";
	}

	@Override
	public String toString() {
		return numero + " de " + palo;
	};

}
