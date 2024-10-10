package utilidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Imagen {
	
	public Texture t;
	public Sprite s;
	public int x=0, y=0;
	
	public Imagen(String ruta, int ancho, int alto) {
		t = new Texture(ruta);
		s = new Sprite(t);
		s.setPosition(x, y);
		s.setSize(ancho, alto);
	}
	
	public void dibujar() {
		s.draw(Render.batch);
	}
	
	public float getAncho() {
		return s.getWidth();
	}
	
	public float getAlto() {
		return s.getHeight();
	}
	
	public float getX() {
		return s.getX();
	}
	
	public float getY() {
		return s.getY();
	}
	
	public void setX(int x) {
		s.setX(x);
	}
	
	public void setY(int y) {
		s.setY(y);
	}
	
	public void setPosition(float x, float y) {
		s.setPosition(x, y);
	}
	
	public void setSize(float ancho, float alto) {
		s.setSize(ancho, alto);
	}
	
	public void setAlpha(float valor) {
		s.setAlpha(valor);
	}
	
	public void dispose() {
		t.dispose();
	}
	
	
	
}
