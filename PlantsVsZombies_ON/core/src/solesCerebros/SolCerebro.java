package solesCerebros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import hud.Hud;
import utilidades.Entradas;
import utilidades.Globales;
import utilidades.Imagen;
import utilidades.Render;
import utilidades.Rutas;
import utilidades.Utiles;

public class SolCerebro {

	// Datos generales
	private int VALOR = 25;
	private int CANTIDAD_MAXIMA = 8000;
	private float VALOR_DURACION = 7;
	private float duracion = VALOR_DURACION; // el tiempo que tarda en desaparecer

	// Audio
	private Sound sonidoClick = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_SUN));
	private Sound sonidoThrow = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_THROW));
	private boolean unaVezClick = false;
	private boolean unaVezThrow = false;

	// Tiempos
	private float tiempo;
	private int tiempoEnCaer = Utiles.r.nextInt(this.TIEMPO_MAXIMO - this.TIEMPO_MINIMO) + this.TIEMPO_MINIMO; // el tiempo que tarda en caer
	private final int TIEMPO_MINIMO = 8, TIEMPO_MAXIMO = 15;
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

	public void logica() {
		caer();
		clickear();
	}

	public void dibujar() {
		imagen.dibujar();
	}

	public void generarSol(int xCasilla, int yCasilla) { // para el girasol y el generador de cerebros
		
		clickear();
		imagen.setPosition(xCasilla, yCasilla);
		
		if (this.activarDesaparicion) {
			animacionDesaparecer(false);
		}

		this.tiempo += Gdx.graphics.getDeltaTime();

		if (this.tiempo > this.tiempoEnCaer) {
			
			if(!unaVezThrow) {
				Utiles.sonidoPitchRandom(this.sonidoThrow, Globales.volumenSfx, 1.05f, 0.95f);
				unaVezThrow = true;
			}
			
			this.hitbox.x = xCasilla;
			this.hitbox.y = yCasilla;
			
			this.activarDesaparicion = false;
			this.imagen.setAlpha(1f);
			this.restaAlpha = 1f;
			this.unaVezClick = false;

			this.duracion -= Gdx.graphics.getDeltaTime();

			if (this.duracion < 0) {
				desaparecer();
			}

		}
		
	}
	
	// FUNCIONES PRIVADAS

	protected void caer() {

		this.hitbox.x = xRandom;

		if (this.activarDesaparicion) {
			animacionDesaparecer(true);
		}

		this.tiempo += Gdx.graphics.getDeltaTime();

		if (this.tiempo > this.tiempoEnCaer) {
			this.activarDesaparicion = false;
			this.imagen.setAlpha(1f);
			this.restaAlpha = 1f;
			this.unaVezClick = false;

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

	private void animacionDesaparecer(boolean randomizar) {

		this.tiempoAnimacion += Gdx.graphics.getDeltaTime();
		if (tiempoAnimacion > 0.01f) {

			if (restaAlpha > 0) {
				this.restaAlpha -= 0.1f;
				this.imagen.setAlpha(restaAlpha);
				this.tiempoAnimacion = 0;
			} else {
				if(randomizar)this.imagen.setX(xRandom);
				this.y = Render.ALTO - 120;
				this.hitbox.y = y + 3000; // le sumamos 100 xq sino la hitbox se puede seguir clickeando por mas que esté
											// invisible
			}
		}
	}

	private void desaparecer() {

		this.activarDesaparicion = true;
		this.duracion = VALOR_DURACION;
		this.tiempo = 0;
		this.unaVezThrow = false;

	}

	private void hacerClick() {

		// reproducir todo una sola vez
		if (!unaVezClick) {
			Utiles.sonidoPitchRandom(this.sonidoClick, Globales.volumenSfx, 1.05f, 0.95f);
			if (Hud.cantSoles <= CANTIDAD_MAXIMA) {
				Hud.cantSoles += VALOR;
			}

			// reiniciamos los valores del sol
			this.xRandom = Utiles.r.nextInt(((Render.ANCHO - 100) - 400)) + 400;
			this.yFinal = Utiles.r.nextInt(Render.ALTO / 2);
			this.tiempoEnCaer = Utiles.r.nextInt(this.TIEMPO_MAXIMO - this.TIEMPO_MINIMO) + this.TIEMPO_MINIMO;
			this.unaVezClick = true;
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
