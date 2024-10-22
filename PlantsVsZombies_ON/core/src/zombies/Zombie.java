package zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import plantaYzombie.PlantaZombie;
import utilidades.Animacion;
import utilidades.Globales;
import utilidades.Render;
import utilidades.Rutas;

public abstract class Zombie extends PlantaZombie {
	
	// Audio
	private Sound mordisco = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_MASTICAR));
	private boolean unaVezMordisco;
	private Sound traga = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_TRAGAR));
	private boolean unaVezTraga;

	// Tiempos
	public float tiempoCaminar = 0f;
	private float tiempoComer = 0f;
	
	// Otros
	private int[] casillaPlanta = new int[2];
	
	// Animaciones
	protected final int ANIM_CAMINAR = 1, ANIM_COMER = 2;

	public Zombie(String nombre, int coste, int vida, int damage) {
		super(nombre, coste, vida, damage);

	}
	
	// esto es xq muchos, por no decir todos, van a sacar la misma cantidad de vida
	public Zombie(String nombre, int coste, int vida) {
		super(nombre, coste, vida, 20);
	}
	
	@Override
	public void ejecutar() {
		logica();
		dibujar();
	}
	
	@Override
	public void logica() {

		if(!Globales.pausaActiva) {
			
			if(!detectarPlanta()) {
				super.estado_anim = ANIM_CAMINAR;
				caminar();
				tragar();
				
			unaVezMordisco = false;
			} else {
				super.estado_anim = ANIM_COMER;
				comer(casillaPlanta[0], casillaPlanta[1]);
			}
			
		}else {
			this.mordisco.pause();
		}
	}
	
	@Override
	public void dibujar() {
	
		super.dibujarAnimacion();
		
		if(!Globales.pausaActiva) {
			super.reanudarAnimacion();
		} else {
			super.pausarAnimacion();
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
