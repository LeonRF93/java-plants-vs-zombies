package casillas;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import hud.Hud;
import pantallas.Partida;
import plantaYzombie.PlantaZombie;
import plantaYzombie.PlantasZombies;
import plantas.Planta;
import utilidades.Entradas;
import utilidades.Globales;
import utilidades.Render;
import utilidades.Rutas;
import utilidades.Utiles;
import zombies.Zombie;

public class Casilla {

	// Audio
	private Sound plantar = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_PLANTAR));

	// Plantas y zombies
	private PlantaZombie planta = null;
	private ArrayList<Zombie> zombies = new ArrayList<Zombie>();

	// Dimensiones de la casilla
	private Rectangle casilla;
	private ShapeRenderer contorno = new ShapeRenderer();
	private int x, y;
	public static int ancho = 70, alto = 80;
	private boolean mostrar = false; // cambiar el valor del boleano para mostrar o no el contorno de las casillas

	public Casilla(int x, int y) {

		this.casilla = new Rectangle(x, y, ancho, alto);
		this.x = x;
		this.y = y;

	}

	public void detectar() {
		
		logicaPlantaZombie();

		// si no es -1 significa que hay una planta seleccionada
		if (Hud.indiceClickeado != -1 && !Globales.pausaActiva) {

			// si la casilla no tiene una planta/zombie se puede plantar en la misma

			// este if detecta que nuestro mouse esté dentro de una d las casillas
			if (this.casilla.contains(Entradas.getMouseX(), Entradas.getMouseY())) {

				// aca detecta que hayamos hecho click
				if (Entradas.getBotonMouse() == 0) {

					PlantaZombie plantaZombieAux = PlantasZombies.obtenerPlantaZombie(Hud.nombreClickedo);

					if (plantaZombieAux instanceof Zombie) { // es un zombie

						generarZombie(plantaZombieAux);

					} else { // es una planta

						if (this.planta != null) {
							return;
						}

						generarPlanta(plantaZombieAux);

					}

					accionesHud(plantaZombieAux);

				}

			}
		}
	}
	
	public void dibujarGuisante() {
		
		// Esto es para que el guisante pase por encima de las plantas
		if (planta != null && planta.getNombre() == "Lanzaguisantes") {
			this.planta.dibujarHerencias();
		}
		
	}
	
	public void dibujarPlantaZombie() { 
		
		if (planta != null && planta.getNombre() != "Lanzaguisantes") {

			this.planta.dibujar();
			this.planta.dibujarHerencias();
		}
		
		// Esto es para que el guisante pase por encima de las plantas
		if (planta != null && planta.getNombre() == "Lanzaguisantes") {
			this.planta.dibujar();
		}
		
		
		if (this.zombies.size() > 0) {
			for (int i = 0; i < zombies.size(); i++) {
				this.zombies.get(i).dibujar();
				this.zombies.get(i).dibujarHerencias();
			}
		}
		
	}

	public void mostrarContorno() {
		if (mostrar) {

			contorno.begin(ShapeRenderer.ShapeType.Line);

			contorno.setColor(1, 0, 0, 1); // Rojo para las hitboxes
			contorno.rect(this.x, this.y, ancho, alto);

			contorno.end();

		}
	}

	
	// FUNCIONES PRIVADAS
	

	private void logicaPlantaZombie() {

		// una vez planta la planta, reproducir su animación
		if (planta != null) {

			if (this.planta.morir()) {
				this.planta = null;
			} else {
				this.planta.logica();
				this.planta.logicaHerencias();
			}
		}
		
		if (this.zombies.size() > 0) {
			for (int i = 0; i < zombies.size(); i++) {
				this.zombies.get(i).logica();
				this.zombies.get(i).logicaHerencias();
				
				if(this.zombies.get(i).morir()) {
					Globales.jardin.restarContZombies();
					this.zombies.remove(i);
				}
				
			}
		}

	}

	private void generarZombie(PlantaZombie plantaZombieAux) {

		Globales.jardin.aumentarContZombies();
		
		this.zombies.add((Zombie) plantaZombieAux);
		this.zombies.get(this.zombies.size() - 1).animationX = this.x - 15;
		this.zombies.get(this.zombies.size() - 1).animationY = this.y;

		this.zombies.get(this.zombies.size() - 1).setHitbox(this.x + 20, this.y + 10, 
				this.zombies.get(this.zombies.size() - 1).getANCHO_HITBOX(), 
				this.zombies.get(this.zombies.size() - 1).getALTO_HITBOX());
	}

	private void generarPlanta(PlantaZombie plantaZombieAux) {

		this.planta = PlantasZombies.obtenerPlantaZombie(Hud.nombreClickedo);

		this.planta.animationX = this.x - 15; // se le resta 15 para que la planta quede bien centrada
		this.planta.animationY = this.y;
		this.planta.setHitbox(this.x + 25, this.y + 10, this.planta.getANCHO_HITBOX(), this.planta.getALTO_HITBOX() - 15);

	}

	private void accionesHud(PlantaZombie plantaZombieAux) {
		Utiles.sonidoPitchRandom(this.plantar, Globales.volumenSfx, 1.05f, 0.95f);
		Hud.cantSoles -= plantaZombieAux.getCoste();
		Globales.plantaReiniciarCooldown = Hud.nombreClickedo;
		Hud.indiceClickeado = -1;
	}

	// GETTERS

	public PlantaZombie getPlanta() {
		if (planta != null) {
			return this.planta;
		}
		return null;
	}

	public ArrayList<Zombie> getZombie() {
		return this.zombies;
	}

}
