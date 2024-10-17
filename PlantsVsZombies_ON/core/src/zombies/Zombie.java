package zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import plantaYzombie.PlantaZombie;
import utilidades.Animacion;
import utilidades.Globales;
import utilidades.Render;

public abstract class Zombie extends PlantaZombie {
	
	// Audio
	private Sound mordisco = Gdx.audio.newSound(Gdx.files.internal("audio/chompZombi.mp3"));
	private boolean unaVezMordisco;
	private Sound traga = Gdx.audio.newSound(Gdx.files.internal("audio/tragar.mp3"));
	private boolean unaVezTraga;

	// Tiempos
	public float tiempoCaminar = 0f;
	private float tiempoComer = 0f;
	
	// Otros
	private int[] casillaPlanta = new int[2];
	
	// Animaciones
	protected Animacion animacionComer;
	protected Animacion animacionCaminar;
	private String[] estados = {"zombie_caminando", "zombie_comiendo"};
	private int indEstado = 0;

	public Zombie(String nombre, int coste, int vida, int damage) {
		super(nombre, coste, vida, damage);

	}
	
	public Zombie(String nombre, int coste, int vida) {
		super(nombre, coste, vida, 20);
		
	}
	
	@Override
	public void ejecutar() {
		
		if(!Globales.pausaActiva) {
			
				super.animacionesAtlas.dibujar(estados[indEstado],animationX, animationY);

			
			if(!detectarPlanta()) {
//				super.dibujarAnimaciones(ANIM_IDDLE);
				animacionCaminar.reanudarAnimacion();
				indEstado = 0;
//				animacionCaminar.reproducirAnimacion(animationX, animationY);
				caminar();
				tragar();
				
			unaVezMordisco = false;
			} else {
//				animacionComer.reproducirAnimacion(super.animationX, super.animationY-5);
				comer(casillaPlanta[0], casillaPlanta[1]);
				indEstado = 1;
			}
			
		}else {
			this.mordisco.pause();
//			super.dibujarAnimaciones(ANIM_IDDLE);
//			super.animaciones.get(ANIM_IDDLE).pausarAnimacionEnFrame(1);
			animacionCaminar.reproducirAnimacion(animationX, animationY);
			animacionCaminar.pausarAnimacionEnFrame(1);
		}
	}
	
	
	// FUNCIONES PRIVADAS
	
	private void caminar() {
		if (!Globales.pausaActiva) {
			tiempoCaminar += Render.getDeltaTime();
			if (tiempoCaminar > 0.1f) {
				tiempoCaminar = 0f;
				animationX -= 1f;
				super.hitbox.x -= 1f;
			}
		}
	}

	private void comer(int i, int j) {
		if(!unaVezMordisco) {
			mordisco.play(Globales.volumenSfx);
			unaVezMordisco = true;
		}
		
		tiempoComer += Render.getDeltaTime();
		if (tiempoComer > 0.5f) {
			tiempoComer = 0f;
			mordisco.play(Globales.volumenSfx);
			Globales.jardin.getCasillas()[i][j].getPlanta().perderVida(super.damage);
			this.unaVezTraga = true;
		}
	}
	
	private void tragar() {
		if(unaVezTraga) {
			traga.play(Globales.volumenSfx);
			unaVezTraga = false;
		}
	}

	private boolean detectarPlanta() {
		for (int i = 0; i < Globales.jardin.getCasillas().length; i++) {
			for (int j = 0; j < Globales.jardin.getCasillas()[i].length; j++) {
				if (Globales.jardin.getCasillas()[i][j].getPlanta() != null) {

					if (this.getHitbox().overlaps((Globales.jardin.getCasillas()[i][j].getPlanta().getHitbox()))) {
						casillaPlanta[0] = i;
						casillaPlanta[1] = j;
						return true;
					}
				}
			}
		}
		return false;
	}
	

}
