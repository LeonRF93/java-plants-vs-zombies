package plantas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import utilidades.Entradas;
import utilidades.Globales;
import utilidades.Render;
import utilidades.Rutas;

public class Lanzaguisantes extends Planta {

	// Interaccion zombies
	private Rectangle detectorZombie;
	private boolean unaVezDetector;
	private int anchoDetector = 200, altoDetector = 50;
	private ShapeRenderer contornoDetector = new ShapeRenderer();
	
	// Texturas
	private Texture sprites = new Texture(Rutas.LANZAGUISANTES_SPRITES);
	private TextureRegion iddleRegion = new TextureRegion(sprites, 0, 0, 640, 80);

	public Lanzaguisantes() {
		super("Lanzaguisantes", 100, 100, 20);
	
		super.setRecarga(RECARGA_PRECOZ);
		super.setImagen(Rutas.LANZAGUISANTES_ICONO, 37, 37);

		super.agregarAnimacion(iddleRegion, 8, 0.2f);
		
	}

	@Override
	protected void logicaHerencias() {
		detectarZombie();
	}
	
	@Override
	protected void dibujarHerencias() {
		dibujarHitboxes();
	}
	
	private boolean detectarZombie() {
		
		setDetectorZombies();
		
		for (int i = 0; i < Globales.jardin.getCasillas().length; i++) {
			for (int j = 0; j < Globales.jardin.getCasillas()[i].length; j++) {
				
				for (int h = 0; h < Globales.jardin.getCasillas()[i][j].getZombie().size(); h++) {
					if(this.detectorZombie.overlaps(Globales.jardin.getCasillas()[i][j].getZombie().get(h).getHitbox())) {
						System.out.println("lol");
					}
				}
				
			}
		}
		return false;
	}
	
	private void dibujarHitboxes() {
		contornoDetector.begin(ShapeRenderer.ShapeType.Line);

		contornoDetector.setColor(1, 0, 0, 1); // Rojo para las hitboxes
		contornoDetector.rect(animationX + 50, animationY + 20, Render.ANCHO-270 - animationX + 218, altoDetector);

		contornoDetector.end();
	}
	
	// GETTERS
	
	public Rectangle getDetectorZombie() {
		return detectorZombie;
	}
	
	// SETTERS
	
	public void setDetectorZombies() { // se tiene que setear despues, para que las coordenadas sean bien tomadas
		if(!unaVezDetector) {
			this.detectorZombie = new Rectangle(animationX + 50, animationY + 20, Render.ANCHO-270 - animationX + 218, altoDetector);
			unaVezDetector = true;
			}
		}
	
}
