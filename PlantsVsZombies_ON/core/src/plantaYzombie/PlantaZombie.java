package plantaYzombie;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import utilidades.Animacion;
import utilidades.Globales;
import utilidades.Imagen;
import utilidades.Render;

public abstract class PlantaZombie {

	// Datos generales
	private String nombre;
	private int coste;
	private int vida;
	protected int damage;

	// imagenes
	private Imagen imagen;
	private Imagen noCargado;
	private Imagen sinSolesSuficientes;
	
	// animaciones
	protected ArrayList<Animacion> animaciones = new ArrayList<>();
	protected int ANIM_IDDLE = 0;
	public int animationX = 0 , animationY = 0;

	
	//recarga
	private int recarga;
	private float tiempoRecarga = 0f;
	private boolean esDisponibleAlInicio;
	
	protected int RECARGA_MUY_LENTA = 20;
	protected int RECARGA_LENTA = 15;
	protected int RECARGA_RAPIDA = 5;
	protected int RECARGA_PRECOZ = 0; //para testear sirve
	private float restaNoCargado;
	
	//hitbox
	protected Rectangle hitbox;
	private ShapeRenderer contorno = new ShapeRenderer();


	public PlantaZombie(String nombre, int coste, int vida, int damage) {
		this.nombre = nombre;
		this.coste = coste;
		this.vida = vida;
		this.damage = damage;
	}

	public abstract void ejecutar();
	
	public void dibujarHud() {
		imagen.dibujar();
		noCargado.dibujar();
		sinSolesSuficientes.dibujar();
	}
	
	public void dibujarAnimaciones(int indice) {
		this.animaciones.get(indice).reproducirAnimacion(animationX, animationY);
	}
	
	public void dibujarHitbox() {
		
		if(this.hitbox != null) {
			
			contorno.begin(ShapeRenderer.ShapeType.Line);
	
			contorno.setColor(1, 0, 0, 1); // Rojo para las hitboxes
			contorno.rect(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height);
	
			contorno.end();
		}
	}
	
	public void perderVida(int cantidad) {
		this.vida -= cantidad;
	}
	
	public boolean morir() {
		if(this.vida <= 0) {
			return true;
		}
		return false;
		
	}

	public boolean reproducirCooldown() {
		if (tiempoRecarga < recarga) {
			sinSolesSuficientes.setAlpha(0.5f);
			
			if(!Globales.pausaActiva) {
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
	
	
	// GETTERS

	public int getCoste() {
		return coste;
	}

	public String getNombre() {
		return nombre;
	}

	public float getTiempoRecarga() {
		return tiempoRecarga;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public Animacion getAnimacion(int indice) {
		return this.animaciones.get(indice);
	}
	
	public int getANIM_IDDLE() {
		return ANIM_IDDLE;
	}
	
	// SETTERS
	
	public void setHitbox(int x, int y, int ancho, int alto) {
		this.hitbox = new Rectangle(x, y, ancho, alto);
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

	public void setAnimacionIddle(String ruta, int cantidadFrames, float velocidadFrames) {
		this.animaciones.add(new Animacion(ruta, cantidadFrames, velocidadFrames));
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
		
		// Animaciones
		for (int i = 0; i < this.animaciones.size(); i++) {
			this.animaciones.get(i).dispose();
		}

	}
	
}
