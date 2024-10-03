package plantaYzombie;

import java.lang.reflect.InvocationTargetException;

public enum PlantasZombies {
	
	// Plantas
	LANZAGUISANTES("Lanzaguisantes","Lanzaguisantes", "plantas."), 
	GIRASOL("Girasol","Girasol", "plantas."), 
	NUEZ("Nuez","Nuez", "plantas."),
	
	// Zombies
	ZOMBIE("ZombieBasico","Zombie", "zombies.");
	
	private String nombreClase;
	private String nombre;
	private String paquete;
	
	private PlantasZombies(String nombreClase, String nombre, String paquete) {
		this.nombreClase = nombreClase;
		this.nombre = nombre;
		this.paquete = paquete;
	}
	
	public static PlantaZombie obtenerPlantaZombie(String nombre) {

		
		
		PlantaZombie plantaZombie = null;
		for (int i = 0; i < values().length; i++) {
			if ( values()[i].getNombre() == nombre ) {
				try {
					Class clase = Class.forName(values()[i].getPaquete()+values()[i].getNombreClase());
					plantaZombie = (PlantaZombie) clase.getDeclaredConstructor().newInstance();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		return plantaZombie;
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
	
	private String getPaquete() {
		return paquete;
	}
	
}
