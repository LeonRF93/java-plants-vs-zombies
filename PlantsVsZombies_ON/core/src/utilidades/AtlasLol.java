package utilidades;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import pantallas.Partida;

public class AtlasLol extends Sprite {
	
	private TextureAtlas atlas;
	
	private Animation<TextureRegion> caminando;
	private Animation<TextureRegion> comiendo;
	private Array<TextureRegion> frames;
	private float tiempoEstado;
	private int tiempoTest = 0;

	public AtlasLol() {
		
		atlas = new TextureAtlas("img/zombies/zombie_basico_atlas/zombie_basico.pack");

		this.frames = new Array<TextureRegion>();
		
		for (int i = 0; i < 7; i++) {
			frames.add(new TextureRegion(atlas.findRegion("zombie_caminando"), i*(700/7), 0, (700/7), 100));
		}
		
		caminando = new Animation<TextureRegion>(0.2f, frames);
		frames.clear();
		
		for (int i = 0; i < 7; i++) {
			frames.add(new TextureRegion(atlas.findRegion("zombie_comiendo"), i*(546/7), 0, (546/7), 100));
		}
		
		comiendo = new Animation<TextureRegion>(0.2f, frames);
		frames.clear();
		
		tiempoEstado = 0;
		
		setBounds(500, 300, 100, 100);
	}
	
	public void update() {
		
		tiempoTest += 1;
		
		tiempoEstado += Render.getDeltaTime();
		
		if(tiempoTest < 100) {
		setRegion(caminando.getKeyFrame(tiempoEstado, true)); // true repetible
		} else {
			setSize(546/7, 102);
			setRegion(comiendo.getKeyFrame(tiempoEstado, true)); // true repetible
		}
		
	}
	
	public void dibujar() {
		
		update();
		draw(Render.batch);
		
	}
	
}
