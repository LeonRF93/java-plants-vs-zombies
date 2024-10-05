package hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import plantaYzombie.PlantaZombie;
import plantas.Planta;
import utilidades.Entradas;
import utilidades.Globales;
import utilidades.Imagen;
import utilidades.Render;
import utilidades.Texto;

public class Hud {

	// Audio

	private Sound select;
	private Sound deselect;
	private Sound cancel = Gdx.audio.newSound(Gdx.files.internal("audio/chiwawa.mp3"));

	boolean unaVezClick = true;

	// Semillas
	public int xInicial = 93;
	public int DISTANCIA_ENTRE_SEEDS = 58;
	private int anchoSeed = 46 , altoSeed = 65;
	private int y = Render.ALTO - 71;
	private PlantaZombie[] plantasZombies;
	public static int indiceClickeado = -1;
	private int indPlantaClickeadaAux = -1;

	private Imagen fondo;

	private Imagen seed;
	private PlantaZombie[] seleccionadas;
	private Rectangle[] hitbox;
	public static String nombreClickedo = "";

	private boolean clickeado;
	private float delay = 0;

	private Texto[] costePlantaZombie;

	// Soles
	private Imagen sunFrame;
	private Texto textoSoles;
	public static int cantSoles = 50;
	private int X_UNIDADES = 38, X_DECENAS = 31, X_CENTENAS = 28, X_MILLARES = 27, Y_TEXTO = 484, Y_MILLARES = 482;
	private int X_ESCALA = 20, X_ESCALA_MILLARES = 15;
	private boolean unaVezMillares, unaVezNoMillares;

	// animacion del "chiwawawa!
	private boolean chiwawawaOn;
	private float tiempoCambioColor = 0f;
	private float tiempoAnimacionChiwawa = 0f;
	private boolean rojoTextoSoles;
	
	public Hud(PlantaZombie[] plantasZombies) {

		constructor(plantasZombies);
		
	}
	
	private void constructor(PlantaZombie[] plantasZombies) {
		
		fondo = new Imagen("img/hud/fondo_hud.png", 1202 / 2, 156 / 2);
		fondo.setPosition(10, Render.ALTO - 77);

		this.plantasZombies = plantasZombies;
		this.seleccionadas = plantasZombies;
		hitbox = new Rectangle[plantasZombies.length];
		seed = new Imagen("img/hud/seed.png", anchoSeed, altoSeed);

		sunFrame = new Imagen("img/hud/suncounter.png", 68, 156 / 2);
		sunFrame.setPosition(10, y - 6);
		textoSoles = new Texto("fonts/Minecraft.ttf", X_ESCALA, Color.RED, false); // lo pongo red xq sino no me deja
																					// cambiarle el color despues (ni
																					// puta idea de xq)
		textoSoles.x = X_UNIDADES;
		textoSoles.y = Y_TEXTO;
		textoSoles.texto = String.valueOf(cantSoles);

		for (int i = 0; i < plantasZombies.length; i++) {
			hitbox[i] = new Rectangle(xInicial + DISTANCIA_ENTRE_SEEDS * i, y, anchoSeed, altoSeed);
																						
		}

		costePlantaZombie = new Texto[plantasZombies.length]; // hay darle una longitud a un array antes d darle valores para q no
													// tire error

		for (int i = 0; i < plantasZombies.length; i++) {
			costePlantaZombie[i] = new Texto("fonts/Minecraft.ttf", X_ESCALA - 9, Color.BLACK, false);
			costePlantaZombie[i].y = 482;
			costePlantaZombie[i].texto = String.valueOf(plantasZombies[i].getCoste());
		}

		select = Gdx.audio.newSound(Gdx.files.internal("audio/seed.mp3"));
		deselect = Gdx.audio.newSound(Gdx.files.internal("audio/tap.mp3"));
		
	}

	public void mostrarHud() {
		textoSoles.setColor(Color.BLACK); // le pongo el color negro xq se lo tuve q poner rojo al crearlo xq sino no me
											// dejaba cambiarlo despues (ni puta idea de xq
		
		animacionChiwawa();
		fondo.dibujar();
		sunFrame.dibujar();
		funcionesTextoSoles();


		for (int i = 0; i < plantasZombies.length; i++) {
			seed.setY(y);
			plantasZombies[i].setY(y + 20);
			plantasZombies[i].setSize(33, 33);
			seed.setX(xInicial + DISTANCIA_ENTRE_SEEDS * i);
			seed.dibujar();
			plantasZombies[i].setX(xInicial + 7 + DISTANCIA_ENTRE_SEEDS * i);
			plantasZombies[i].setNoCargadoYSinSoles(xInicial + DISTANCIA_ENTRE_SEEDS * i, y, 46, 65);
			plantasZombies[i].dibujarHud();
			plantasZombies[i].reproducirCooldown();
			plantasZombies[i].solesSuficientes(cantSoles);
			
			if(plantasZombies[i].getNombre() == Globales.plantaReiniciarCooldown) {
				plantasZombies[i].reiniciarCooldown();
				Globales.plantaReiniciarCooldown = "";
			}

		}

		for (int i = 0; i < costePlantaZombie.length; i++) {
			costePlantaZombie[i].dibujar();
			costePlantaZombie[i].x = 105 + DISTANCIA_ENTRE_SEEDS * i;
		}

	}

	public int clickearPlanta() {

		if (clickeado) { // esto es para evitar que, si tenemos el mouse cerca de una casilla, la planta/zombie
							// se plante ni bien la elegimos
			delayClick();
		}

		for (int i = 0; i < hitbox.length; i++) {

			// aca detecta que pases el mouse por una semilla
			if (hitbox[i].contains(Entradas.getMouseX(), Entradas.getMouseY())) {
				if (Entradas.getBotonMouse() == 0) {
					if (unaVezClick) {
						if (plantasZombies[i].getCoste() > cantSoles || !plantasZombies[i].recargaFinalizada()) {
							cancel.play(Globales.volumenSfx);
							if(plantasZombies[i].getCoste() > cantSoles) { // la anmacion de chiwawawa solo se active cuando no tenes soles
								chiwawawaOn = true;
							}
						} else {
							nombreClickedo = plantasZombies[i].getNombre();
							select.play(Globales.volumenSfx);
							clickeado = true;
							indPlantaClickeadaAux = i;
						}

						unaVezClick = false;
					}
				} else {
					unaVezClick = true;
				}
			}

			// que la planta/zombie siga al mouse
			if (indiceClickeado != -1) {
				seleccionadas[indiceClickeado].setX(Entradas.getMouseX() - 20);
				seleccionadas[indiceClickeado].setY(Entradas.getMouseY() - 10);
				seleccionadas[indiceClickeado].dibujarHud();

				// si haces click derecho o clickeas fuera del jardin, la planta/zombie desaparece
				if (Entradas.getBotonMouse() == 1) {
					deselect.play(Globales.volumenSfx);
					indiceClickeado = -1;

				}

			}
		}

		return indiceClickeado;
	}

	private void delayClick() {
		delay += Render.getDeltaTime();

		if (delay > 0.1f) {
			indiceClickeado = indPlantaClickeadaAux;
			delay = 0;
			clickeado = false;
		}

	}

	private void animacionChiwawa() {

		if (chiwawawaOn) {
			tiempoAnimacionChiwawa += Render.getDeltaTime();
			if (tiempoAnimacionChiwawa < 0.4f) {
				if (rojoTextoSoles) {
					textoSoles.setColor(Color.RED);
				} else {
					textoSoles.setColor(Color.BLACK);
				}

				tiempoCambioColor += Render.getDeltaTime();
				if (tiempoCambioColor > 0.05f) {
					rojoTextoSoles = !rojoTextoSoles;
					tiempoCambioColor = 0f;
				}
			} else {
				tiempoAnimacionChiwawa = 0f;
				tiempoCambioColor = 0f;
				chiwawawaOn = false;
			}

		}
	}

	private void funcionesTextoSoles() {
		textoSoles.dibujar();

		if (cantSoles < 10) {
			textoSoles.x = X_UNIDADES;
		}
		if (cantSoles >= 10 && cantSoles < 100) {
			textoSoles.x = X_DECENAS;
		}
		if (cantSoles >= 100 && cantSoles < 1000) {
			textoSoles.x = X_CENTENAS;
		}
		if (cantSoles >= 1000) {
			unaVezNoMillares = false;
			if (!unaVezMillares) {
				textoSoles.modificarEscala(X_ESCALA_MILLARES);
				textoSoles.x = X_MILLARES;
				textoSoles.y = Y_MILLARES;
				unaVezMillares = true;
			}
		} else {
			unaVezMillares = false;
			if (!unaVezNoMillares) {
				textoSoles.modificarEscala(X_ESCALA);
				textoSoles.y = Y_TEXTO;
				unaVezNoMillares = true;
			}
		}

		textoSoles.texto = String.valueOf(cantSoles);
	}
	
	public void dispose() {

		// Audio
		select.dispose();
		deselect.dispose();
		cancel.dispose();

		// Imagenes
		seed.dispose();
		sunFrame.dispose();
		textoSoles.dispose();

		// Texto
		for (int i = 0; i < costePlantaZombie.length; i++) {
			costePlantaZombie[i].dispose();
		}

		// Otros
		for (int i = 0; i < plantasZombies.length; i++) {
			plantasZombies[i].dispose();
		}

		for (int i = 0; i < seleccionadas.length; i++) {
			seleccionadas[i].dispose();
		}

	}
}
