package utilidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animacion {

	private Animation<TextureRegion> animation;
	private TextureRegion frameActual;
	private TextureRegion[] regions;
	private float tiempoAnimacion;
	private Texture textura;
	public int animationX;
	public int animationY;
	private boolean animacionPausada;
	
	public Animacion(String ruta, int cantidadFrames, float velocidadFrames) {
		
		this.textura = new Texture(ruta);
		TextureRegion[][] temp = TextureRegion.split(textura, textura.getWidth() / cantidadFrames, textura.getHeight());

		this.regions = new TextureRegion[cantidadFrames];
		for (int i = 0; i < regions.length; i++) {
			this.regions[i] = temp[0][i];
		}
		this.animation = new Animation<TextureRegion>(velocidadFrames, regions);
		this.tiempoAnimacion = 0f;
		
	}
	
	public void reproducirAnimacion(int animationX, int animationY) {
		
		if (!this.animacionPausada) {
			this.tiempoAnimacion += Gdx.graphics.getDeltaTime(); // Acumula el delta time
			this.frameActual = animation.getKeyFrame(tiempoAnimacion, true);
		}
		Render.batch.begin();
		Render.batch.draw(this.frameActual, animationX, animationY);
		Render.batch.end();
	}
	
}
