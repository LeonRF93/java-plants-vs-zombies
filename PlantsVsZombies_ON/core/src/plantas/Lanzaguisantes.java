package plantas;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import utilidades.Globales;
import utilidades.Render;
import utilidades.Rutas;

public class Lanzaguisantes extends Planta {

	// Audio
	private Sound hit = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_PEA));
	private Sound disparo = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_THROW));
	private boolean unaVezDisparo;

	// Interaccion zombies
	private Rectangle rangoDeteccion;
	private boolean unaVezSetRango;
	private boolean zombieDetectado;
	private ShapeRenderer contornoRango = new ShapeRenderer();

	// Guisante
	private ArrayList<Guisante> guisantes = new ArrayList<>();
	private float tiempoPea = 0f;
	private float COOLDOWN_PEA = 1.5f;

	// Texturas y animacion
	private Texture sprites = new Texture(Rutas.LANZAGUISANTES_SPRITES);
	private TextureRegion iddleRegion = new TextureRegion(sprites, 0, 0, 640, 80);
	private TextureRegion peaRegion = new TextureRegion(sprites, 0, 80, 78/3, 30);

	public Lanzaguisantes() {
		super("Lanzaguisantes", 100, 100, 10);

		super.setRecarga(RECARGA_PRECOZ);
		super.setImagen(Rutas.LANZAGUISANTES_ICONO, 37, 37);

		super.agregarAnimacion(iddleRegion, 8, 0.2f);
//		super.agregarAnimacion(peaRegion, 3, 0.2f);
	}

	@Override
	protected void logicaHerencias() {
		if (!Globales.pausaActiva) {

			detectarZombie();

		}
	}

	@Override
	protected void dibujarHerencias() {
		
			if(this.guisantes.size() > 0) {
				for (int i = 0; i < guisantes.size(); i++) {
					this.guisantes.get(i).dibujar((int)this.guisantes.get(i).getHitbox().x, (int)this.guisantes.get(i).getHitbox().y);
				}
			}
	}
	
	
	// FUNCIONES PRIVADAS

	private void detectarZombie() {
		
		// no se puede usar un "else" cuando se esta detectando si un zombi entra o sale del rango del lanzaguisantes
		// por si el arraylist de los zombies vale 0, por lo que hay que hay que todo el tiempo darle un valor falso
		// al este booleano y que se ponga como verdadero solo cuando detecta un zombie
		this.zombieDetectado = false;
	
		setRango();

		for (int i = 0; i < Globales.jardin.getCasillas().length; i++) {
			for (int j = 0; j < Globales.jardin.getCasillas()[i].length; j++) {
				for (int h = 0; h < Globales.jardin.getCasillas()[i][j].getZombie().size(); h++) {

					if (this.rangoDeteccion.overlaps(Globales.jardin.getCasillas()[i][j].getZombie().get(h).getHitbox())) {
						this.zombieDetectado = true;
						impactarGuisante(i, j, h);
					}
				}

			}
		}

			disparar();


	}

	private void disparar() {
		
		// hay que tener esto fuera del array xq hay un lio con el tema del tiempo del cooldown
		if(this.zombieDetectado) {
			controlarCooldown();
		}

		for (int i = 0; i < this.guisantes.size(); i++) {
			if(this.zombieDetectado || this.guisantes.get(i).getHitbox().x <= Globales.ANCHO) {
				this.guisantes.get(i).getHitbox().x += 5;
				if(this.guisantes.get(i).getHitbox().x > Globales.ANCHO) {
					this.guisantes.remove(i);
				}
			}
		}

	}
	
	private void impactarGuisante(int casillaFila, int casillaColumna, int indiceZombie) {
		
		if(this.guisantes.size()>0) {
			for (int i = 0; i < this.guisantes.size(); i++) {
				if (this.guisantes.get(i).getHitbox().overlaps(Globales.jardin.getCasillas()[casillaFila][casillaColumna]
						.getZombie().get(indiceZombie).getHitbox())) {
		
					Globales.jardin.getCasillas()[casillaFila][casillaColumna].getZombie().get(indiceZombie).perderVida(super.damage);
					this.hit.play(Globales.volumenSfx);
					this.guisantes.get(i).getHitbox().y = -5000;
					this.guisantes.remove(i);
				}
	
			}
		}
	}
	
	private void controlarCooldown() {
		
		if (this.tiempoPea < this.COOLDOWN_PEA) {
			this.tiempoPea += Render.getDeltaTime();
		} else {
			this.tiempoPea = 0f;
			this.guisantes.add(new Guisante(peaRegion, super.animationX + 50, super.animationY + 42));
			this.disparo.play(Globales.volumenSfx);

		}
	}

	private void dibujarHitboxes() {
		contornoRango.begin(ShapeRenderer.ShapeType.Line);

		contornoRango.setColor(1, 0, 0, 1); // Rojo para las hitboxes
		contornoRango.rect(animationX + 50, animationY + 20, Render.ANCHO - 270 - animationX + 218, 50);

		contornoRango.end();
	}

	// GETTERS

	public Rectangle getDetectorZombie() {
		return rangoDeteccion;
	}

	
	// SETTERS

	public void setRango() { // se tiene que setear despues, para que las coordenadas sean bien tomadas
		if (!unaVezSetRango) {
			this.rangoDeteccion = new Rectangle(animationX + 10, animationY + 20, Render.ANCHO - 240 - animationX + 218, 50);

			unaVezSetRango = true;
		}
	}
	
	
	// DISPOSE
	
	@Override
	public void disposeHerencias() {
		this.sprites.dispose();
		
		this.hit.dispose();
		this.disparo.dispose();
	}


}
