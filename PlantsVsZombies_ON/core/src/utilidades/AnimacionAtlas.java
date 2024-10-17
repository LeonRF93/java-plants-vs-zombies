package utilidades;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;

public class AnimacionAtlas extends Sprite {
    
    private TextureAtlas atlas;
    
    // Mapa para almacenar múltiples animaciones
    private HashMap<String, Animation<TextureRegion>> animaciones;
    private Animation<TextureRegion> animacionActual;
    
    private float tiempoEstado;

    public AnimacionAtlas(String atlasRuta) {
        
        this.atlas = new TextureAtlas(atlasRuta);
        this.animaciones = new HashMap<>();
        this.tiempoEstado = 0f;

    }
    
    // Agrega una animación al mapa con un nombre específico
    public void agregarAnimacion(String region, int regionWidth, int regionHeight, float velocidad) {
        Array<TextureRegion> frames = new Array<>();
        
        for (int i = 0; i < 7; i++) {
            frames.add(new TextureRegion(atlas.findRegion(region), i*regionWidth, 0, regionWidth, regionHeight));
        }
        
        Animation<TextureRegion> animacion = new Animation<>(velocidad, frames);
        animaciones.put(region, animacion);
    }
    
    // Método para seleccionar la animación actual
    public void setAnimacionActual(String region, int ancho, int alto) {
        if (animaciones.containsKey(region)) {
        	this.setSize(ancho, alto);
            this.animacionActual = animaciones.get(region);
        }
    }

    private void update() {
            this.tiempoEstado += Render.getDeltaTime();
            setRegion(animacionActual.getKeyFrame(tiempoEstado, true)); // true para que se repita
    }
    
    public void dibujar(String region,int x, int y) {
    	if (animacionActual != null) {
    		
    		this.animacionActual = animaciones.get(region);
    		
	        update();
	        this.setPosition(x, y);
	        
			Render.batch.begin();
	        	this.draw(Render.batch);
			Render.batch.end();
    	}
    }
}
