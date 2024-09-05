package plantas;

import java.lang.reflect.InvocationTargetException;

public enum Plantas {
	
	LANZAGUISANTES("Lanzaguisantes","Lanzaguisantes"), 
	GIRASOL("Girasol","Girasol"), 
	NUEZ("Nuez","Nuez");
	
	private String nombreClase;
	private String nombre;
	
	private Plantas(String nombreClase, String nombre) {
		this.nombreClase = nombreClase;
		this.nombre = nombre;
	}
	
	public static Planta obtenerPlanta(String nombre) {

		Planta planta = null;
		for (int i = 0; i < values().length; i++) {
			if ( values()[i].getNombre() == nombre ) {
				try {
					Class clase = Class.forName("plantas."+values()[i].getNombreClase());
					planta = (Planta) clase.getDeclaredConstructor().newInstance();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		return planta;
	}
	
	public static int getSize() {
		return values().length;
	}
	
	private String getNombre() {
		return this.nombre;
	}
	
	private String getNombreClase() {
		return this.nombreClase;
	}
	
}
