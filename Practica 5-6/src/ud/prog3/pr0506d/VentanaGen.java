package ud.prog3.pr0506d;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.sun.jdi.connect.Connector.SelectedArgument;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Properties;
import java.util.TreeMap;

public class VentanaGen extends JFrame {

	private static JProgressBar barraProgreso;
	private static JTextArea salidaConsola;
	private static JPanel panelPrincipal;
	private static JPanel panelCentral;
	private static JPanel panelSuperior;
	private static JTable tablaUsuariosAmigos;
	private static DefaultTableModel modelo;
	private static JTextField etiqueta;
	private static JTree arbolUsuarios;
	private static DefaultTreeModel modeloArbol;

	@SuppressWarnings("serial")
	public VentanaGen() {
		this.setTitle("Ventana UsuarioTwitter General");
		this.setSize(1200, 500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		salidaConsola = new JTextArea();
		salidaConsola.setText("");
		etiqueta = new JTextField("Introduzca una etiqueta");
		
		// JTree
		modeloArbol = new DefaultTreeModel(null);
		arbolUsuarios = new JTree(modeloArbol);

		panelPrincipal = new JPanel(new BorderLayout());
		panelCentral = new JPanel(new GridLayout(1, 3));
		panelSuperior = new JPanel(new GridLayout(1, 3));

		modelo = new DefaultTableModel(
				new Object[] { "Id", "Nick", "Followers", "Amigos", "Idioma", "Última conexión" }, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			

		};
		tablaUsuariosAmigos = new JTable(modelo);
		tablaUsuariosAmigos.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				// TODO Auto-generated method stub
				Component comp =  super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				
				String id = table.getModel().getValueAt(row, 0).toString();
				if (GestionTwitter.getUsuario(id).getTags().contains(etiqueta.getText())) {
					comp.setBackground(Color.GREEN);
				}
				
				return comp;
			}
			
		});
		tablaUsuariosAmigos.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		tablaUsuariosAmigos.setBackground(Color.LIGHT_GRAY);
		tablaUsuariosAmigos.getTableHeader().setReorderingAllowed(false);
		tablaUsuariosAmigos.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int filaUser = tablaUsuariosAmigos.getSelectedRow();
					UsuarioTwitter usuario = GestionTwitter.getUsuario((String)modelo.getValueAt(filaUser, 0));
					DefaultMutableTreeNode root = new DefaultMutableTreeNode(usuario.getScreenName());
					for (String nickAmigo : usuario.getFriends()) {
						DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(nickAmigo);
						root.add(hijo);
					}
					arbolUsuarios = new JTree(root);
					System.out.println("He creado el arbol del usuario " + usuario.getScreenName());
				} catch (Exception excepcion){
					System.out.println("No se ha seleccionado una fila");
				}
				
				
			}
		});
		
		etiqueta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pintarTabla();
				
			}
		});
		
		
		barraProgreso = new JProgressBar(0, 40000);
		barraProgreso.setValue(0);
		barraProgreso.setStringPainted(true);

		panelSuperior.add(new JPanel());
		panelSuperior.add(etiqueta);
		panelSuperior.add(new JPanel());
		panelCentral.add(new JScrollPane(salidaConsola));
		panelCentral.add(new JScrollPane(tablaUsuariosAmigos));
		panelCentral.add(new JScrollPane(arbolUsuarios));
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		panelPrincipal.add(barraProgreso, BorderLayout.SOUTH);
		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
		this.getContentPane().add(panelPrincipal);

	}

	public static void main(String[] args) {
		VentanaGen vg = new VentanaGen();
		vg.setVisible(true);

		GestionTwitter.setMapaUsuariosId(new HashMap<>());
		GestionTwitter.setMapaUsuariosNick(new HashMap<>());
		GestionTwitter.setMapaUsuariosNumAmigos(new TreeMap<>());

		JFileChooser fileChooser = new JFileChooser();

		try (InputStream input = new FileInputStream(
				"C:/Users/theal/eclipse-workspace/Practica 5-6/archivo.properties")) {
			Properties prop = new Properties();
			prop.load(input);
			// fileChooser.setSelectedFile(new File(prop.getProperty("archivo.ruta")));
			fileChooser.setCurrentDirectory(new File("/Users/theal/eclipse-workspace/Practica 5-6/"));
			fileChooser.setSelectedFile(new File(prop.getProperty("archivo.ruta")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int result = fileChooser.showOpenDialog(vg);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			try (OutputStream output = new FileOutputStream(
					"/Users/theal/eclipse-workspace/Practica 5-6/archivo.properties")) {
				Properties prop = new Properties();
				prop.setProperty("archivo.ruta", selectedFile.getCanonicalPath());
				prop.store(output, null);
			} catch (IOException io) {
				io.printStackTrace();
			}
		}

		Thread hiloTareas = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					// String fileName = "C:/Users/theal/eclipse-workspace/Practica 5-6/data.csv";
					CSV.processCSV(fileChooser.getSelectedFile());
					barraProgreso.setString("CSV Cargado al 100%");
					// TODO Barra Progreso
				} catch (Exception e) {
					e.printStackTrace();
				}

				// Recorrido de mapa para ver amigos
				int usuariosConAmigos = 0;
				ArrayList<String> nicksOrdenados = new ArrayList<>(GestionTwitter.getMapaUsuariosNick().keySet());
				Collections.sort(nicksOrdenados);
				for (String nick : nicksOrdenados) {
					int numeroAmigosEstan = 0;
					for (String id : GestionTwitter.getMapaUsuariosNick().get(nick).getFriends()) {
						if (GestionTwitter.getMapaUsuariosId().containsKey(id)) {
							numeroAmigosEstan++;
						}
					}
					if (numeroAmigosEstan > 0) {
						usuariosConAmigos++;
						GestionTwitter.getMapaUsuariosNick().get(nick).setAmigosEnSistema(numeroAmigosEstan);
						if (!GestionTwitter.getMapaUsuariosNumAmigos().containsKey(numeroAmigosEstan)) {
							GestionTwitter.getMapaUsuariosNumAmigos().put(numeroAmigosEstan,
									new ArrayList<UsuarioTwitter>());
						}
						GestionTwitter.getMapaUsuariosNumAmigos().get(numeroAmigosEstan)
								.add(GestionTwitter.getMapaUsuariosNick().get(nick));
						// System.out.println("Usuario " + nick + " tiene " +
						// (mapaUsuariosNick.get(nick).getFriends().size() - numeroAmigosEstan) + "
						// amigos fuera del sistema y " + numeroAmigosEstan + " amigos dentro del
						// sistema");
						// salidaConsola.setText(salidaConsola.getText() + "\nUsuario " + nick + " tiene
						// " + (GestionTwitter.getMapaUsuariosNick().get(nick).getFriends().size() -
						// numeroAmigosEstan) + " amigos fuera del sistema y " + numeroAmigosEstan + "
						// amigos dentro del sistema");
						salidaConsola.insert(
								"Usuario " + nick + " tiene "
										+ (GestionTwitter.getMapaUsuariosNick().get(nick).getFriends().size()
												- numeroAmigosEstan)
										+ " amigos fuera del sistema y " + numeroAmigosEstan
										+ " amigos dentro del sistema\n",
								salidaConsola.getText().length());
					}
				}
				// System.out.println("Hay " + usuariosConAmigos + " usuarios con algunos amigos
				// dentro del sistema.");
				salidaConsola.setText(salidaConsola.getText() + "\nHay " + usuariosConAmigos
						+ " usuarios con algunos amigos dentro del sistema.");

				// Recorrido del TreeMap
				ArrayList<Integer> numAmigosOrd = new ArrayList<>(GestionTwitter.getMapaUsuariosNumAmigos().keySet());
				Collections.sort(numAmigosOrd);
				for (int i = numAmigosOrd.size() - 1; i > -1; i--) {
					@SuppressWarnings("unchecked")
					ArrayList<UsuarioTwitter> listaUsers = (ArrayList<UsuarioTwitter>) GestionTwitter
							.getMapaUsuariosNumAmigos().get(numAmigosOrd.get(i)).clone();
					Collections.sort(listaUsers, new Comparator<UsuarioTwitter>() {

						@Override
						public int compare(UsuarioTwitter o1, UsuarioTwitter o2) {
							return o1.getScreenName().compareTo(o2.getScreenName());
						}

					});
					for (UsuarioTwitter usuario : listaUsers) {
						if (usuario.getAmigosEnSistema() > 10) {
							// System.out.println(usuario.getScreenName() + " - " +
							// usuario.getAmigosEnSistema() + " amigo(s) en el sistema.");
							// salidaConsola.setText(salidaConsola.getText() + "\n" +
							// usuario.getScreenName() + " - " + usuario.getAmigosEnSistema() + " amigo(s)
							// en el sistema.");
							salidaConsola.insert("\n" + usuario.getScreenName() + " - " + usuario.getAmigosEnSistema()
									+ " amigo(s) en el sistema.", salidaConsola.getText().length());
							modelo.addRow(new Object[] {usuario.getId(), usuario.getScreenName(), usuario.getFollowersCount(), usuario.getFriendsCount(), usuario.getLang(),new Date(usuario.getLastSeen())});
						}
					}
				}
			}
		});
		hiloTareas.run();

	}

	public static void actualizaBarra() {
		VentanaGen.barraProgreso.setValue(barraProgreso.getValue() + 1);
	}
	public static void pintarTabla() {
		tablaUsuariosAmigos.repaint();
	}

}
