package plantaYzombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pantallas.Partida;
import recursos.Imagen;
import recursos.Render;

public abstract class PlantaZombie {

	// Datos generales
	private String nombre;
	private int coste;
	private int vida;
	private int damage;

	// imagenes y animacion
	private Imagen imagen;
	private Imagen noCargado;
	private Imagen sinSolesSuficientes;
	
	private Animation<TextureRegion> animation;
	private TextureRegion frameActual;
	private TextureRegion[] regions;
	private float tiempoAnimacion;
	private Texture textura;
	public int animationX;
	public int animationY;
	private boolean animacionPausada;

	
	//recarga
	private int recarga;
	private float tiempoRecarga = 0f;
	private boolean esDisponibleAlInicio;
	
	protected int RECARGA_MUY_LENTA = 20;
	protected int RECARGA_LENTA = 15;
	protected int RECARGA_RAPIDA = 5;
	private float restaNoCargado;
	


	public PlantaZombie(String nombre, int coste, int vida, int damage) {
		this.nombre = nombre;
		this.coste = coste;
		this.vida = vida;
		this.damage = damage;
		this.animationX = 0;
		this.animationY = 0;
	}

	public void dibujar() {
		imagen.dibujar();
		noCargado.dibujar();
		sinSolesSuficientes.dibujar();
	}

	public void animacionIddle() {
		if (!animacionPausada) {
			tiempoAnimacion += Gdx.graphics.getDeltaTime(); // Acumula el delta time
			frameActual = animation.getKeyFrame(tiempoAnimacion, true);
		}
		Render.batch.begin();
		Render.batch.draw(frameActual, animationX, animationY);
		Render.batch.end();
	}

	public boolean reproducirCooldown() {
		if (tiempoRecarga < recarga) {
			sinSolesSuficientes.setAlpha(0.5f);
			
			if(!Partida.pausaActiva) {
				tiempoRecarga += Render.getDeltaTime();
	
				noCargado.setPosition(noCargado.getX(), noCargado.getY() + restaNoCargado);
				noCargado.setSize(noCargado.getAncho(), noCargado.getAlto() - restaNoCargado);
			}

			return false;
		} else {
			return true;
		}
	}

	public void reiniciarCooldown() {
		noCargado.setAlpha(0.5f);
		tiempoRecarga = 0f;
	}

	public boolean recargaFinalizada() {
		if (tiempoRecarga < recarga) {
			return false;
		} else {
			return true;
		}
	}

	public int getCoste() {
		return coste;
	}

	public String getNombre() {
		return nombre;
	}

	public float getTiempoRecarga() {
		return tiempoRecarga;
	}
	
	protected void setRecarga(int recarga) {
		this.recarga = recarga;
		setRestaNoCargado();
	}
	
	protected void setRestaNoCargado() {
		if(this.recarga == RECARGA_RAPIDA) {
			this.restaNoCargado = 0.22f;
		}
		if(this.recarga == RECARGA_LENTA) {
			this.restaNoCargado = 0.071f;
		}
		if(this.recarga == RECARGA_MUY_LENTA) {
			this.restaNoCargado = 0.01f;
		}
	}
	
	public void setImagen(String ruta, int ancho, int alto) {
		imagen = new Imagen(ruta, ancho, alto);
		noCargado = new Imagen("img/black.png", ancho, alto);
		sinSolesSuficientes = new Imagen("img/black.png", ancho, alto);
		noCargado.setAlpha(0.5f);
		sinSolesSuficientes.setAlpha(0.5f);
	}

	public void setNoCargadoYSinSoles(int x, int y, int ancho, int alto) {
		if (!esDisponibleAlInicio) { // esto es para setearle la posicion al "noCargado" la primera vez a aquellas
										// plantas/zombies que no estan disponibles de primeras a diferencia del girasol 
										// por ejemplo
			noCargado.setPosition(x, y);
			noCargado.setSize(ancho, alto);
			esDisponibleAlInicio = true;
		}
		if (recargaFinalizada()) {
			noCargado.setPosition(x, y);
			noCargado.setAlpha(0f);
			noCargado.setSize(ancho, alto);
		}
		sinSolesSuficientes.setPosition(x, y);
		sinSolesSuficientes.setSize(ancho, alto);
	}

	public void solesSuficientes(int cantSoles) {
			if (cantSoles >= this.coste) {
				if(recargaFinalizada()) {
					sinSolesSuficientes.setAlpha(0f);
				}
			} else {
				sinSolesSuficientes.setAlpha(0.5f);
			}
	}

	public void setAnimacion(String ruta, int cantidadFrames, float velocidadFrames) {
		textura = new Texture(ruta);
		TextureRegion[][] temp = TextureRegion.split(textura, textura.getWidth() / cantidadFrames, textura.getHeight());

		regions = new TextureRegion[cantidadFrames];
		for (int i = 0; i < regions.length; i++) {
			regions[i] = temp[0][i];
		}
		animation = new Animation<TextureRegion>(velocidadFrames, regions);
		tiempoAnimacion = 0f;
	}

	public void pausarAnimacionEnFrame(int frameIndex) {
		if (frameIndex >= 0 && frameIndex < regions.length) {
			animacionPausada = true;
			frameActual = regions[frameIndex];
		}
	}

	public void reanudarAnimacion() {
		animacionPausada = false;
	}

	public void setSize(int ancho, int alto) {
		imagen.setSize(ancho, alto);
	}

	public void setX(int x) {
		imagen.setX(x);
	}

	public void setY(int y) {
		imagen.setY(y);
	}

	public void disponibleAlInicio() {
		this.tiempoRecarga = recarga;
		this.esDisponibleAlInicio = true;
	}

	public void dispose() {

		// Imagenes
		imagen.dispose();
		textura.dispose();

	}
	
}
