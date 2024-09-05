package hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import plantas.Planta;
import recursos.Entradas;
import recursos.Globales;
import recursos.Imagen;
import recursos.Render;
import recursos.Texto;

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
	private Planta[] plantas;
	public static int indPlantaClickeada = -1;
	private int indPlantaClickeadaAux = -1;

	private Imagen fondo;

	private Imagen seed;
	private Planta[] plantasSeleccionadas;
	private Rectangle[] hitbox;
	public static String nombrePlantaClickeda = "";

	private boolean clickeado;
	private float delay = 0;

	private Texto[] costePlantas;

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
	
	public Hud(Planta[] plantas) {

		fondo = new Imagen("img/hud/fondo_hud.png", 1202 / 2, 156 / 2);
		fondo.setPosition(10, Render.ALTO - 77);

		this.plantas = plantas;
		this.plantasSeleccionadas = plantas;
		hitbox = new Rectangle[plantas.length];
		seed = new Imagen("img/hud/seed.png", anchoSeed, altoSeed);

		sunFrame = new Imagen("img/hud/suncounter.png", 68, 156 / 2);
		sunFrame.setPosition(10, y - 6);
		textoSoles = new Texto("fonts/Minecraft.ttf", X_ESCALA, Color.RED, false); // lo pongo red xq sino no me deja
																					// cambiarle el color despues (ni
																					// puta idea de xq)
		textoSoles.x = X_UNIDADES;
		textoSoles.y = Y_TEXTO;
		textoSoles.texto = String.valueOf(cantSoles);

		for (int i = 0; i < plantas.length; i++) {
			hitbox[i] = new Rectangle(xInicial + DISTANCIA_ENTRE_SEEDS * i, y, anchoSeed, altoSeed);
																						
		}

		costePlantas = new Texto[plantas.length]; // hay darle una longitud a un array antes d darle valores para q no
													// tire error

		for (int i = 0; i < plantas.length; i++) {
			costePlantas[i] = new Texto("fonts/Minecraft.ttf", X_ESCALA - 9, Color.BLACK, false);
			costePlantas[i].y = 482;
			costePlantas[i].texto = String.valueOf(plantas[i].getCoste());
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


		for (int i = 0; i < plantas.length; i++) {
			seed.setY(y);
			plantas[i].setY(y + 20);
			plantas[i].setSize(33, 33);
			seed.setX(xInicial + DISTANCIA_ENTRE_SEEDS * i);
			seed.dibujar();
			plantas[i].setX(xInicial + 7 + DISTANCIA_ENTRE_SEEDS * i);
			plantas[i].setNoCargadoYSinSoles(xInicial + DISTANCIA_ENTRE_SEEDS * i, y, 46, 65);
			plantas[i].dibujar();
			plantas[i].reproducirCooldown();
			plantas[i].solesSuficientes(cantSoles);
			
			if(plantas[i].getNombre() == Globales.plantaReiniciarCooldown) {
				plantas[i].reiniciarCooldown();
				Globales.plantaReiniciarCooldown = "";
			}

		}

		for (int i = 0; i < costePlantas.length; i++) {
			costePlantas[i].dibujar();
			costePlantas[i].x = 105 + DISTANCIA_ENTRE_SEEDS * i;
		}

	}

	public int clickearPlanta() {

		if (clickeado) { // esto es para evitar que, si tenemos el mouse cerca de una casilla, la planta
							// se plante ni bien la elegimos
			delayClick();
		}

		for (int i = 0; i < hitbox.length; i++) {

			// aca detecta que pases el mouse por una semilla
			if (hitbox[i].contains(Entradas.getMouseX(), Entradas.getMouseY())) {
				if (Entradas.getBotonMouse() == 0) {
					if (unaVezClick) {
						if (plantas[i].getCoste() > cantSoles || !plantas[i].recargaFinalizada()) {
							cancel.play(Globales.volumenSfx);
							if(plantas[i].getCoste() > cantSoles) { // la anmacion de chiwawawa solo se active cuando no tenes soles
								chiwawawaOn = true;
							}
						} else {
							nombrePlantaClickeda = plantas[i].getNombre();
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

			// que la planta siga al mouse
			if (indPlantaClickeada != -1) {
				plantasSeleccionadas[indPlantaClickeada].setX(Entradas.getMouseX() - 20);
				plantasSeleccionadas[indPlantaClickeada].setY(Entradas.getMouseY() - 10);
				plantasSeleccionadas[indPlantaClickeada].dibujar();

				// si haces click derecho o clickeas fuera del jardin, la planta desaparece
				if (Entradas.getBotonMouse() == 1) {
					deselect.play(Globales.volumenSfx);
					indPlantaClickeada = -1;

				}

			}
		}

		return indPlantaClickeada;
	}

	private void delayClick() {
		delay += Render.getDeltaTime();

		if (delay > 0.1f) {
			indPlantaClickeada = indPlantaClickeadaAux;
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
		for (int i = 0; i < costePlantas.length; i++) {
			costePlantas[i].dispose();
		}

		// Otros
		for (int i = 0; i < plantas.length; i++) {
			plantas[i].dispose();
		}

		for (int i = 0; i < plantasSeleccionadas.length; i++) {
			plantasSeleccionadas[i].dispose();
		}

	}
}
