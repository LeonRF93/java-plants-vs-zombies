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
import utilidades.Utiles;

public abstract class Zombie extends PlantaZombie {
	
	// Audio
	private Sound mordisco = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_MASTICAR));
	private boolean unaVezMordisco;
	private Sound traga = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_TRAGAR));
	private boolean unaVezTraga;
	private Sound pop = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_POP));
	private boolean unaVezPop;

	// Tiempos
	public float tiempoCaminar = 0f;
	private float tiempoComer = 0f;
	
	// Otros
	private int[] casillaPlanta = new int[2];
	private int MITAD_DE_VIDA;
	
	protected int ANCHO_HITBOX = 10;
	protected int ALTO_HITBOX = 70;
	
	// Animaciones
	protected final int ANIM_CAMINAR = 1;
	protected final int ANIM_COMER = 2;
	protected final int ANIM_CAMINAR_UNBRAZO = 3;
	protected final int ANIM_COMER_UNBRAZO = 4;

	public Zombie(String nombre, int coste, int vida, int damage) {
		super(nombre, coste, vida, damage);
		super.ANCHO_HITBOX = 25;
		super.ALTO_HITBOX = 70;
		
		this.MITAD_DE_VIDA = super.vida/2;

	}
	
	// esto es xq muchos, por no decir todos, van a sacar la misma cantidad de vida
	public Zombie(String nombre, int coste, int vida) {
		super(nombre, coste, vida, 20);
		super.ANCHO_HITBOX = 25;
		super.ALTO_HITBOX = 70;
		
		this.MITAD_DE_VIDA = super.vida/2;
	}
	
	@Override
	public void logica() {

		if(!Globales.pausaActiva) {
			
			if(super.vida <= MITAD_DE_VIDA && !this.unaVezPop) {
				Utiles.sonidoPitchRandom(this.pop, Globales.volumenSfx, 1.05f, 0.95f);
				this.unaVezPop = true;
			}
			
			if(!detectarPlanta()) {
				
				if(super.vida <= MITAD_DE_VIDA) {
					super.estado_anim = ANIM_CAMINAR_UNBRAZO;
				} else {
					super.estado_anim = ANIM_CAMINAR;
				}
				
				caminar();
				tragar();
				
			unaVezMordisco = false;
			} else {

				if(super.vida <= MITAD_DE_VIDA) {
					super.estado_anim = ANIM_COMER_UNBRAZO;
				} else {
					super.estado_anim = ANIM_COMER;
				}
				
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
			Utiles.sonidoPitchRandom(this.mordisco, Globales.volumenSfx, 1.05f, 0.95f);
			unaVezMordisco = true;
		}
		
		tiempoComer += Render.getDeltaTime();
		if (tiempoComer > 0.5f) {
			tiempoComer = 0f;
			Utiles.sonidoPitchRandom(this.mordisco, Globales.volumenSfx, 1.05f, 0.95f);
			Globales.jardin.getCasillas()[i][j].getPlanta().perderVida(super.damage);
			this.unaVezTraga = true;
		}
	}
	
	private void tragar() {
		if(unaVezTraga) {
			Utiles.sonidoPitchRandom(this.traga, Globales.volumenSfx, 1.05f, 0.95f);
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
