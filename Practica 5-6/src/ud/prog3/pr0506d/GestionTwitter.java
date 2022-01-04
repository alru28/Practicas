package ud.prog3.pr0506d;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class GestionTwitter {

	public static HashMap<String, UsuarioTwitter> mapaUsuariosId;
	public static HashMap<String, UsuarioTwitter> mapaUsuariosNick;
	public static TreeMap<Integer, ArrayList<UsuarioTwitter>> mapaUsuariosNumAmigos;
	
	public static void main(String[] args) {
		mapaUsuariosId = new HashMap<>();
		mapaUsuariosNick = new HashMap<>();
		mapaUsuariosNumAmigos = new TreeMap<>();
		try {
			// TODO Configurar el path y ruta del fichero a cargar
			String fileName = "C:/Users/theal/eclipse-workspace/Practica 5-6/data.csv";
			CSV.processCSV( new File( fileName ) );
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Recorrido de mapa para ver amigos
		int usuariosConAmigos = 0;
		ArrayList<String> nicksOrdenados = new ArrayList<>(mapaUsuariosNick.keySet());
		Collections.sort(nicksOrdenados);
		for (String nick : nicksOrdenados) {
			int numeroAmigosEstan = 0;
			for (String id : mapaUsuariosNick.get(nick).getFriends()) {
				if (mapaUsuariosId.containsKey(id)) {
					numeroAmigosEstan++;
				}
			}
			if (numeroAmigosEstan > 0) {
				usuariosConAmigos++;
				mapaUsuariosNick.get(nick).setAmigosEnSistema(numeroAmigosEstan);
				if (!mapaUsuariosNumAmigos.containsKey(numeroAmigosEstan)) {
					mapaUsuariosNumAmigos.put(numeroAmigosEstan, new ArrayList<UsuarioTwitter>());
				}
				mapaUsuariosNumAmigos.get(numeroAmigosEstan).add(mapaUsuariosNick.get(nick));
				System.out.println("Usuario " + nick + " tiene " + (mapaUsuariosNick.get(nick).getFriends().size() - numeroAmigosEstan) + " amigos fuera del sistema y " + numeroAmigosEstan + " amigos dentro del sistema");
			}
		}
		System.out.println("Hay " + usuariosConAmigos + " usuarios con algunos amigos dentro del sistema.");
		
		// Recorrido del TreeMap
		ArrayList<Integer> numAmigosOrd = new ArrayList<>(mapaUsuariosNumAmigos.keySet());
		Collections.sort(numAmigosOrd);
		for (int i = numAmigosOrd.size() - 1 ; i > -1 ; i--) {
			ArrayList<UsuarioTwitter> listaUsers = (ArrayList<UsuarioTwitter>) mapaUsuariosNumAmigos.get(numAmigosOrd.get(i)).clone();
			Collections.sort(listaUsers, new Comparator<UsuarioTwitter>() { 

				@Override
				public int compare(UsuarioTwitter o1, UsuarioTwitter o2) {
					return o1.getScreenName().compareTo(o2.getScreenName());
				}
				
			});
			for (UsuarioTwitter usuario : listaUsers) {
				System.out.println(usuario.getScreenName() + " - " + usuario.getAmigosEnSistema() + " amigo(s) en el sistema.");
			}
		}
	}

	public static HashMap<String, UsuarioTwitter> getMapaUsuariosId() {
		return mapaUsuariosId;
	}

	public static void setMapaUsuariosId(HashMap<String, UsuarioTwitter> mapaUsuarios) {
		GestionTwitter.mapaUsuariosId = mapaUsuarios;
	}

	public static HashMap<String, UsuarioTwitter> getMapaUsuariosNick() {
		return mapaUsuariosNick;
	}

	public static void setMapaUsuariosNick(HashMap<String, UsuarioTwitter> mapaUsuariosNick) {
		GestionTwitter.mapaUsuariosNick = mapaUsuariosNick;
	}

	public static TreeMap<Integer, ArrayList<UsuarioTwitter>> getMapaUsuariosNumAmigos() {
		return mapaUsuariosNumAmigos;
	}

	public static void setMapaUsuariosNumAmigos(TreeMap<Integer, ArrayList<UsuarioTwitter>> mapaUsuariosNumAmigos) {
		GestionTwitter.mapaUsuariosNumAmigos = mapaUsuariosNumAmigos;
	}

	public static UsuarioTwitter getUsuario(String id) {
		return mapaUsuariosId.get(id);
	}
}
