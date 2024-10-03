package zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import pantallas.Partida;
import plantaYzombie.PlantaZombie;
import recursos.Globales;
import recursos.Render;

public abstract class Zombie extends PlantaZombie {

	public float tiempoCaminar = 0f;
	private float tiempoComer = 0f;
	private Sound masticada = Gdx.audio.newSound(Gdx.files.internal("audio/chompZombi.mp3"));

	public Zombie(String nombre, int coste, int vida, int damage) {
		super(nombre, coste, vida, damage);
	}

	public void caminar() {
		if (!Partida.pausaActiva) {
			tiempoCaminar += Render.getDeltaTime();
			if (tiempoCaminar > 0.1f) {
				tiempoCaminar = 0f;
				animationX -= 1f;
				super.hitbox.x -= 1f;
			}
		}
	}

	public void comer() {
		tiempoComer += Render.getDeltaTime();
		if (tiempoComer > 0.5f) {
			tiempoComer = 0f;
			masticada.play();
		}
	}

	private boolean detectarPlanta() {
		for (int i = 0; i < Globales.mapa.getCasillas().length; i++) {
			for (int j = 0; j < Globales.mapa.getCasillas()[i].length; j++) {
				if (Globales.mapa.getCasillas()[i][j].getPlanta() != null) {

					if (this.getHitbox().overlaps((Globales.mapa.getCasillas()[i][j].getPlanta().getHitbox()))) {
						System.out.println("sas");
						return true;
					}
				}
			}
		}
		return false;
	}

	public void funcionar() {
		if(!detectarPlanta()) {
		caminar();
		} else {
			comer();
		}
	}

}
