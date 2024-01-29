package almacen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static final String nombreFichero = "articulos.dat";
	public static Articulo articulo;
	static ArrayList<Articulo> listaArticulo = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String cadenaConexion = "jdbc:mysql://localhost:3306/bbdd";
		String user = "root";
		String pass = "";
		
		File fn = new File(nombreFichero);
		if (!fn.exists()) {
			try {
				fn.createNewFile();
				System.out.println("Archivo creado.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			
		}
		
		
		
		try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass)){
			
			
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("No se ha podido establecer la conexion con la BD");
			e1.printStackTrace();
		}
		
	    cargarArticulos();
	
	
		
		Scanner sc = new Scanner(System.in);
		int opcion;
		do {
			mostrarMenu();
			opcion = sc.nextInt();
			
			switch (opcion) {
			case 1:
				System.out.println("Añadir nuevo articulo.");
				addArticulo(listaArticulo);
				
			break;
			case 2:
				System.out.println("Borrar articulo.");
				borrarArticulo(listaArticulo);
			break;
			case 3:
				System.out.println("Consultar articulo.");
				buscarArticulo(listaArticulo);
			break;
			case 4:
				System.out.println("Listar articulos.");
				listarArticulos(listaArticulo);
				
			break;
			case 5:
				System.out.println("Salir.");
				guardarArticulos(listaArticulo);
			break;
			
			default:
				System.out.println("Opcion no valida. De uno a cinco.");
			}
		
		}while(opcion != 5);
	}
	
	private static void cargarArticulos() throws IOException {
        File file = new File(nombreFichero);
        if(file.exists()) {
        	try(FileInputStream fis = new FileInputStream(nombreFichero);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {
        		while(true) {
        			listaArticulo.add((Articulo) ois.readObject());
        			
        		}
        	}catch(EOFException e) {
        		
        	}catch(IOException e) {
        		
        	}catch(ClassNotFoundException e) {
        		
        	}
        }else {
        	file.createNewFile();
        }
	}

	private static void mostrarMenu() {
		System.out.println("1. Añadir nuevo articulo.");
		System.out.println("2. Eliminar un articulo por Id.");
		System.out.println("3. Consultar un articulo por Id.");
		System.out.println("4. Listar todos los articulos.");
		System.out.println("5. Salir.");
		System.out.println("Elige una opción de 1 al 5:");
	}

	private static void addArticulo(ArrayList<Articulo> listaArticulo) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Introduce id");
		int id = sc.nextInt();
		sc.nextLine();
		
		for(Articulo articulo:listaArticulo) {
			if(articulo.getId() == id) {
				System.out.println("El id ya existe.");
				return;
			}
		}
		
		System.out.println("Introduce nombre");
		String nombre = sc.nextLine();
		
		System.out.println("Introduce descripcion");
		String descripcion = sc.nextLine();
		
		System.out.println("Introduce stock");
		int stock = sc.nextInt();
		
		sc.nextLine();
		System.out.println("Introduce precio");
		double precio = sc.nextDouble();
		
		sc.nextLine();
		
		Articulo articulo = new Articulo(id,nombre,descripcion,stock,precio);
		listaArticulo.add(articulo);
		/*
		*/
	}
	
	private static void borrarArticulo(ArrayList<Articulo> listaArticulo) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce id del articulo a borrar:");
		int id = sc.nextInt();
		
		boolean encontrado = false;
		for(Articulo articulo:listaArticulo) {
			if(articulo.getId() == id) {
				listaArticulo.remove(articulo);
				encontrado = true;
				System.out.println("Articulo borrado.");
				break;
			}
		}
		
		if(encontrado) {
			System.out.println("Articulo eliminado");
		}else {
			System.out.println("No se encontro articulo con id indicada.");
		}
	}
	
	private static void guardarArticulos(ArrayList<Articulo> listaArticulo) {
		try (FileOutputStream fos = new FileOutputStream(nombreFichero);
				 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
	
			for(Articulo articulo : listaArticulo) {
            	oos.writeObject(articulo);
            }
	        
            
        } catch (IOException e) {
            System.err.println("Error al guardar los artículos: " + e.getMessage());
        }
		System.out.println("Programa finalizado.");
	}
	
	private static void listarArticulos(ArrayList<Articulo> listaArticulo) {
		if(listaArticulo.isEmpty()) {
			System.out.println("No hay articulos para listar");
			return;
		}
		System.out.println("Lista de articulos:");
		for(Articulo articulo:listaArticulo) {
			System.out.println(articulo);
		}
	}
	
	private static void buscarArticulo(ArrayList<Articulo> listarArticulo) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Id del articulo:");
		int id = sc.nextInt();
		
		for(Articulo articulo:listaArticulo) {
			if(articulo.getId() == id) {
				System.out.println("Articulo: " + articulo);
				return;
			}
		}
		System.out.println("No se encontro el articulo con id: " + id);
	}

}
