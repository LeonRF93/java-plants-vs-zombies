package plantas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import utilidades.Render;
import utilidades.Rutas;

public class Guisante {
	
	// Textura
	private TextureRegion textura;
	
	// Bounds
	private Rectangle hitbox;
	public int X_INICIAL = 0;
	
	public Guisante(TextureRegion region, int x, int y) {
		this.hitbox = new Rectangle(x, y, 78/3, 30);
		this.X_INICIAL = x;
		this.textura = region;
	}
	
	
	public void dibujar(int x, int y) {
			Render.batch.begin();
			Render.batch.draw(this.textura, x, y);
			Render.batch.end();
	}
	
	
	// GETTERS
	
	public int getX_INICIAL() {
		return X_INICIAL;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
}
