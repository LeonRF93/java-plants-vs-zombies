package plantas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import utilidades.Entradas;
import utilidades.Globales;
import utilidades.Render;
import utilidades.Rutas;

public class Lanzaguisantes extends Planta {

	// Audio
	private Sound hit = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_PEA));
	private Sound disparo = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_THROW));
	private boolean unaVezDisparo;
	
	// Interaccion zombies
	private Rectangle detectorZombie;
	private boolean unaVezDetector;
	private boolean zombieDetectado;
	private ShapeRenderer contornoDetector = new ShapeRenderer();
	
	// Guisante
	private float tiempoPea = 0f;
	private float COOLDOWN_PEA = 2f;
	private final int ANIM_PEA = 1;
	private Rectangle peaHitbox;
	private int peaX = 0;
	private boolean activarDisparo;
	private boolean disparar;
	
	// Texturas
	private Texture sprites = new Texture(Rutas.LANZAGUISANTES_SPRITES);
	private TextureRegion iddleRegion = new TextureRegion(sprites, 0, 0, 640, 80);
	private TextureRegion peaRegion = new TextureRegion(sprites, 0, 80, 78, 30);

	public Lanzaguisantes() {
		super("Lanzaguisantes", 100, 100, 20);
	
		super.setRecarga(RECARGA_PRECOZ);
		super.setImagen(Rutas.LANZAGUISANTES_ICONO, 37, 37);

		super.agregarAnimacion(iddleRegion, 8, 0.2f);
		super.agregarAnimacion(peaRegion, 3, 0.2f);
	}

	@Override
	protected void logicaHerencias() {
		if(!Globales.pausaActiva) {
			
			detectarZombie();

		}
	}
	
	@Override
	protected void dibujarHerencias() {
//		dibujarHitboxes();
		
		if(this.zombieDetectado) {
			super.pausarAnimacionEnFrame(ANIM_PEA, 0);
			super.dibujarAnimacion(this.ANIM_PEA, this.peaX, super.animationY + 42);
		}
	}
	
	private boolean detectarZombie() {
		
		setHitboxes();
		
		for (int i = 0; i < Globales.jardin.getCasillas().length; i++) {
			for (int j = 0; j < Globales.jardin.getCasillas()[i].length; j++) {
				
				for (int h = 0; h < Globales.jardin.getCasillas()[i][j].getZombie().size(); h++) {
					if(this.detectorZombie.overlaps(Globales.jardin.getCasillas()[i][j].getZombie().get(h).getHitbox())) {
					
						this.zombieDetectado = true;
						
						controlarDiparo();
						
						if(this.peaHitbox.overlaps(Globales.jardin.getCasillas()[i][j].getZombie().get(h).getHitbox())) {
							this.hit.play();
							this.peaX = super.animationX + 50;
							this.unaVezDisparo = false;
						}
						
					}
				}
				
			}
		}
		return false;
	}
	
	private void controlarDiparo() {
		
		if(this.tiempoPea < this.COOLDOWN_PEA) {
			this.tiempoPea += Render.getDeltaTime();
		} else {
			this.tiempoPea = 0f;
			System.out.println("pum");
			this.activarDisparo = !this.activarDisparo;
		}

		
		// Mover guisante
		if(this.activarDisparo) {
			this.peaX += 5;
			this.peaHitbox.x = peaX;
		}
		
		if(!this.unaVezDisparo) {
			this.disparo.play();
			this.unaVezDisparo = true;
		}
		
	}
	
	private void dibujarHitboxes() {
		contornoDetector.begin(ShapeRenderer.ShapeType.Line);

		contornoDetector.setColor(1, 0, 0, 1); // Rojo para las hitboxes
		contornoDetector.rect(animationX + 50, animationY + 20, Render.ANCHO-270 - animationX + 218, 50);

		contornoDetector.end();
		
//		contornoDetector.begin(ShapeRenderer.ShapeType.Line);
//
//		contornoDetector.setColor(1, 0, 0, 1); // Rojo para las hitboxes
//		contornoDetector.rect(super.animationX + 50, super.animationY + 42, peaHitbox.getWidth(), peaHitbox.getHeight());
//
//		contornoDetector.end();
	}
	
	// GETTERS
	
	public Rectangle getDetectorZombie() {
		return detectorZombie;
	}
	
	// SETTERS
	
	public void setHitboxes() { // se tiene que setear despues, para que las coordenadas sean bien tomadas
		if(!unaVezDetector) {
			this.detectorZombie = new Rectangle(animationX + 50, animationY + 20, Render.ANCHO-270 - animationX + 218, 50);
			unaVezDetector = true;
			
			this.peaHitbox = new Rectangle(super.animationX + 50, super.animationY + 42, 78/3, 30);
			this.peaX = super.animationX + 50;
			}
		}
	
}
