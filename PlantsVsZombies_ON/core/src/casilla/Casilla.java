package casilla;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import hud.Hud;
import pantallas.Partida;
import plantaYzombie.PlantaZombie;
import plantaYzombie.PlantasZombies;
import recursos.Entradas;
import recursos.Globales;
import zombies.Caminable;
import zombies.Zombie;

public class Casilla {

	private PlantaZombie planta = null;
	private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	private Rectangle casilla;
	private ShapeRenderer contorno = new ShapeRenderer();;
	private int x, y;
	public static int ancho = 70, alto = 80;
	private boolean mostrar = false; // cambiar el valor del boleano para mostrar o no el contorno de las casillas
	
	private Sound plantar = Gdx.audio.newSound(Gdx.files.internal("audio/plant.mp3"));

	private boolean esZombie = true;
	private boolean dentroCasilla;
	
	public Casilla(int x, int y) {

		this.casilla = new Rectangle(x, y, ancho, alto);

		this.x = x;
		this.y = y;

	}
	
	public void detectar() {
		
		// una vez planta la planta, reproducir su animación
		if(planta != null) {
			planta.animacionIddle();
			this.planta.dibujarHitbox(this.planta.getHitbox().x, this.planta.getHitbox().y, this.planta.getHitbox().width, this.planta.getHitbox().height);
		}
		
	    if (this.zombies.size()>0) {
	        for (int i = 0; i < zombies.size(); i++) {
	        	this.zombies.get(i).animacionIddle();
				this.zombies.get(i).funcionar();
				this.zombies.get(i).dibujarHitbox(this.zombies.get(i).getHitbox().x, this.zombies.get(i).getHitbox().y, this.zombies.get(i).getHitbox().width, this.zombies.get(i).getHitbox().height);
			}
	    }
	
		
		// si no es -1 significa que hay una planta seleccionada
		if (Hud.indiceClickeado != -1 && !Partida.pausaActiva) {

			// si la casilla no tiene una planta/zombie se puede plantar en la misma

				// este if detecta que nuestro mouse esté dentro de una d las casillas
				if (this.casilla.contains(Entradas.getMouseX(), Entradas.getMouseY())) {
					
					dentroCasilla = true;

					// aca detecta que hayamos hecho click
					if (Entradas.getBotonMouse() == 0) {
						
						PlantaZombie plantaZombieAux = PlantasZombies.obtenerPlantaZombie(Hud.nombreClickedo);
						
						 if (plantaZombieAux instanceof Zombie) { // es un zombie

							 	this.zombies.add((Zombie)plantaZombieAux);
							 	this.zombies.get(this.zombies.size()-1).animationX = this.x - 15;
							 	this.zombies.get(this.zombies.size()-1).animationY = this.y;
							 	
							 	this.zombies.get(this.zombies.size()-1).setHitbox(this.x+15, this.y+10, this.ancho-40, this.alto-15);
							 	
							 	
					        } else { // es una planta
					        	
					        	if(this.planta != null) {
					        		return;
					        	}
					    
									this.planta = PlantasZombies.obtenerPlantaZombie(Hud.nombreClickedo);
									
									this.planta.animationX = this.x - 15; // se le resta 15 para que la planta quede bien centrada
									this.planta.animationY = this.y;
									this.planta.setHitbox(this.x+5, this.y+10, this.ancho-20, this.alto-15);

					        }
						


						plantar.play(Globales.volumenSfx);
						Hud.cantSoles -= plantaZombieAux.getCoste();

						Globales.plantaReiniciarCooldown = Hud.nombreClickedo;

						Hud.indiceClickeado = -1;
					}

				} else {
					dentroCasilla = false;
				}
			}
		}
	
	public PlantaZombie getPlanta() {
		if(planta != null) {
			return this.planta;
		}
		return null;
	}
	
	public ArrayList<Zombie> getZombie() {
		return this.zombies;
	}
	
	public void mostrar() {
		if (mostrar) {

			contorno.begin(ShapeRenderer.ShapeType.Line);

			contorno.setColor(1, 0, 0, 1); // Rojo para las hitboxes
			contorno.rect(this.x, this.y, ancho, alto);

			contorno.end();

		}
	}
	
	public void colisiones() {
		if(this.planta != null && this.zombies.size() > 0) {
			for (int i = 0; i < zombies.size(); i++) {
					if(this.getPlanta().getHitbox().overlaps(zombies.get(i).getHitbox())) {
						System.out.println("sas");
					}
			}
		}
	}

}
