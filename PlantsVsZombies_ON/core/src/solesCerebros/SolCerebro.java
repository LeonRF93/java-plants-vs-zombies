package solesCerebros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import hud.Hud;
import utilidades.Entradas;
import utilidades.Globales;
import utilidades.Imagen;
import utilidades.Render;
import utilidades.Utiles;

public class SolCerebro {

	// Datos generales
	private int VALOR = 25;
	private int CANTIDAD_MAXIMA = 8000;
	private float duracion = 7; // el tiempo que tarda en desaparecer

	// Audio
	private Sound sonidoClick = Gdx.audio.newSound(Gdx.files.internal("audio/solCerebro.mp3"));
	private boolean unaVezSonido = false;

	// Tiempos
	private float tiempo;
	private int tiempoEnCaer = 2; // el tiempo que tarda en caer (va a ser random)
	private final int TIEMPO_MINIMO = 5, TIEMPO_MAXIMO = 10;
	private float tiempoAnimacion = 0f;

	// Datos de la imagen y hitbox
	private Imagen imagen;
	private Rectangle hitbox;
	private int y = Render.ALTO - 120;

	// Animaciones
	private boolean activarDesaparicion = false;
	private float restaAlpha = 1f;

	// Valores de spawneo
	private int xRandom;
	private int yFinal;

	public SolCerebro(String rutaImagen) {
		this.imagen = new Imagen(rutaImagen, 70, 70);
		this.imagen.setY(y);
		this.yFinal = Utiles.r.nextInt((Render.ALTO / 2) + 30);
//		this.xRandom = Utiles.r.nextInt(200, (Render.ANCHO - 100)); NO USAR EL RANDOM ASÍ, XQ ALGUNAS VERSIONES DE 
//				    												JAVA NO SON COMPATIBLES CON ESA FORMA, 
//																	mejor hacer asi: (max - min) + min	
		this.xRandom = Utiles.r.nextInt(((Render.ANCHO - 100) - 200)) + 200;
		// (max - min) + min
		this.imagen.setX(xRandom);
		this.imagen.setAlpha(0f);

		hitbox = new Rectangle(xRandom, y, 70, 70);
		hitbox.setY(y);
	}

	public void ejecutar() {
		caer();
		clickear();
	}

	public void dibujar() {
		imagen.dibujar();
	}

	// FUNCIONES PRIVADAS

	private void caer() {

		this.hitbox.x = xRandom;

		if (this.activarDesaparicion) {
			animacionDesaparecer();
		}

		this.tiempo += Gdx.graphics.getDeltaTime();

		if (this.tiempo > this.tiempoEnCaer) {
			this.activarDesaparicion = false;
			this.imagen.setAlpha(1f);
			this.restaAlpha = 1f;
			this.unaVezSonido = false;

			if (this.y > this.yFinal) {
				this.y -= 2;
				this.hitbox.y = y;
			} else {

				this.duracion -= Gdx.graphics.getDeltaTime();

				if (this.duracion < 0) {
					desaparecer();
				}

			}
			this.imagen.setY(y);

		}
	}

	private void clickear() {

		// si el mouse esta en el sol
		if (hitbox.contains(Entradas.getMouseX(), Entradas.getMouseY())) {
			// si hacemos click en el sol
			if (Entradas.getBotonMouse() == 0) {

				desaparecer();
				hacerClick();

			}
		}
	}

	private void animacionDesaparecer() {

		this.tiempoAnimacion += Gdx.graphics.getDeltaTime();
		if (tiempoAnimacion > 0.01f) {

			if (restaAlpha > 0) {
				this.restaAlpha -= 0.1f;
				this.imagen.setAlpha(restaAlpha);
				this.tiempoAnimacion = 0;
			} else {
				this.imagen.setX(xRandom);
				this.y = Render.ALTO - 120;
				this.hitbox.y = y + 300; // le sumamos 100 xq sino la hitbox se puede seguir clickeando por mas que esté
											// invisible
			}
		}
	}

	private void desaparecer() {

		this.activarDesaparicion = true;
		this.duracion = 2;
		this.tiempo = 0;

	}

	private void hacerClick() {

		// reproducir todo una sola vez
		if (!unaVezSonido) {
			this.sonidoClick.play(Globales.volumenSfx);
			if (Hud.cantSoles <= CANTIDAD_MAXIMA) {
				Hud.cantSoles += VALOR;
			}

			// reiniciamos los valores del sol
			this.xRandom = Utiles.r.nextInt(((Render.ANCHO - 100) - 400)) + 400;
			this.yFinal = Utiles.r.nextInt(Render.ALTO / 2);
			this.tiempoEnCaer = Utiles.r.nextInt(this.TIEMPO_MAXIMO - this.TIEMPO_MINIMO) + this.TIEMPO_MINIMO;
			this.unaVezSonido = true;
		}

	}

	// GETTERS

	public int getValor() {
		return VALOR;
	}

	public int getTiempoCaida() {
		return tiempoEnCaer;
	}

	public float getDuracion() {
		return duracion;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public Rectangle getArea() {
		return hitbox;
	}

	public void dispose() {
		this.imagen.dispose();
		this.sonidoClick.dispose();
	}

}
