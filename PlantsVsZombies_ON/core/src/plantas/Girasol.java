package plantas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import solesCerebros.SolCerebro;
import utilidades.Globales;
import utilidades.Render;
import utilidades.Rutas;

public class Girasol extends Planta {
		
	// Sol
	private SolCerebro sol;
	
	// Texturas
	private Texture sprites = new Texture(Rutas.GIRASOL_SPRITES);
	private TextureRegion iddleRegion = new TextureRegion(sprites, 0, 0, 800, 80);

	public Girasol() {
		super("Girasol", 50, 100, 0);
		
		super.setRecarga(RECARGA_RAPIDA);
		super.setImagen(Rutas.GIRASOL_ICONO, 37, 37);
		
		super.agregarAnimacion(iddleRegion, 10, 0.2f);
		super.disponibleAlInicio();
		
		this.sol = new SolCerebro(Rutas.HUD_SUN);
	}

	
	@Override
	protected void logicaHerencias() {
		if(!Globales.pausaActiva) {
			if(this.sol != null) {
				this.sol.generarSol(this.animationX, this.animationY);
			}	
		}
	}
	
	@Override
	protected void dibujarHerencias() {
		Render.batch.begin();
		this.sol.dibujar();
		Render.batch.end();
	}
	
	@Override
	public void disposeHerencias() {
		this.sol.dispose();
	}
	

}
