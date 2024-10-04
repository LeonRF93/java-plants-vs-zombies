package zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import plantaYzombie.PlantaZombie;
import utilidades.Animacion;
import utilidades.Globales;
import utilidades.Render;

public abstract class Zombie extends PlantaZombie {
	
	// Audio
	private Sound mordisco = Gdx.audio.newSound(Gdx.files.internal("audio/chompZombi.mp3"));
	private boolean unaVezMordisco;

	// Tiempos
	public float tiempoCaminar = 0f;
	private float tiempoComer = 0f;
	
	// Otros
	private int[] indicePlanta = new int[2];
	
	// Animaciones
	private Animacion animacionComer = new Animacion("img/zombies/zombie_comiendo.png", 7, 0.15f);


	public Zombie(String nombre, int coste, int vida, int damage) {
		super(nombre, coste, vida, damage);

	}
	
	public Zombie(String nombre, int coste, int vida) {
		super(nombre, coste, vida, 20);
	}
	
	@Override
	public void ejecutar() {
		
		if(!Globales.pausaActiva) {
			
			if(!detectarPlanta()) {
				super.animacionIddle();
				caminar();
			unaVezMordisco = false;
			} else {
				animacionComer.reproducirAnimacion(super.animationX, super.animationY-5);
				comer(indicePlanta[0], indicePlanta[1]);
			}
			
		}else {
			this.mordisco.pause();
			super.animacionIddle();
			super.pausarAnimacionEnFrame(1);
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
			mordisco.play();
			unaVezMordisco = true;
		}
		
		tiempoComer += Render.getDeltaTime();
		if (tiempoComer > 0.5f) {
			tiempoComer = 0f;
			mordisco.play();
			Globales.jardin.getCasillas()[i][j].getPlanta().perderVida(super.damage);
		}
	}

	private boolean detectarPlanta() {
		for (int i = 0; i < Globales.jardin.getCasillas().length; i++) {
			for (int j = 0; j < Globales.jardin.getCasillas()[i].length; j++) {
				if (Globales.jardin.getCasillas()[i][j].getPlanta() != null) {

					if (this.getHitbox().overlaps((Globales.jardin.getCasillas()[i][j].getPlanta().getHitbox()))) {
						indicePlanta[0] = i;
						indicePlanta[1] = j;
						return true;
					}
				}
			}
		}
		return false;
	}
	

}
