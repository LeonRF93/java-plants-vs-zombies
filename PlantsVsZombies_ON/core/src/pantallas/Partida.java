package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.lionstavern.pvz.PvzPrincipal;
import hud.Hud;
import jardines.Dia;
import plantaYzombie.PlantaZombie;
import plantas.Girasol;
import plantas.Lanzaguisantes;
import plantas.Nuez;
import solesCerebros.Sol;
import utilidades.Entradas;
import utilidades.Globales;
import utilidades.Imagen;
import utilidades.Render;
import utilidades.Rutas;
import zombies.ZombieBasico;

public class Partida implements Screen {
	
	// Audio
	public Music selector = Gdx.audio.newMusic(Gdx.files.internal(Rutas.MUSICA_SELECTOR));
	private Sound pausa = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_PAUSA));;
	private boolean unaVezPausa;
	private Sound bleep = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_CLICK2));

	// Plantas y zombies del jugador
	private PlantaZombie[] plantas = { new Lanzaguisantes(), new Nuez(), new Girasol(), new ZombieBasico() };
//	private Planta[] plantas = { new Lanzaguisantes(), new Nuez(), new Girasol() };
//	private Zombie[] plantas = { new ZombieBasico() };

	// Camara

	private boolean camaraAlInicio = false; // cambiar valor para tener o no la animacion inicial
	private OrthographicCamera camara;

	// Animacion de la camara inicial
	private int tiempoAnimacion;
	private float acelCamara = 1f;
	private float restarVolumen;
	private boolean unaVezCamaraInicial;
	private boolean finCamaraInicial;

	// Camara del hud
	private OrthographicCamera camaraHud;
	private float xCamara = Render.ANCHO / 2, yCamara = Render.ALTO / 2;

	// Pausa
	private Imagen pausaImg;
	private Imagen menuBoton;
	private Rectangle areaMenuBoton;
	private Imagen[] botonPausa = new Imagen[2];
	private Rectangle[] areaBotonPuasa = new Rectangle[4];
	private boolean[] sfxMusic = new boolean[2];
	private boolean[] unaVezBotones = new boolean[4];

	private boolean unaVezClickIzquierdo;

	// Otros
	private PvzPrincipal principal;
	private ScreenManager screenMg;
	private Hud hud;
	private Sol sol;

	public Partida(PvzPrincipal principal) {
		this.principal = principal;
		screenMg = new ScreenManager(principal);
	}

	@Override
	public void show() {
	
		Gdx.input.setInputProcessor(new Entradas());

		Hud.cantSoles = 5000;
		Globales.jardin = new Dia();
		
		camara = new OrthographicCamera(Render.ANCHO, Render.ALTO);
		camara.position.set(Render.ANCHO / 2, Render.ALTO / 2, 0);
		camaraHud = new OrthographicCamera(Render.ANCHO, Render.ALTO);
		camaraHud.position.set(Render.ANCHO / 2, Render.ALTO / 2, 0);

		restarVolumen = selector.getVolume();
		selector.setLooping(true);

		hud = new Hud(plantas);
		sol = new Sol();
		
		inicializarPausa();

	}

	@Override
	public void render(float delta) {
		
		camara.update();
		ajustarVolumen();

		if (!finCamaraInicial) {
			camaraInicial(camaraAlInicio);
		}

		// Capa del fondo

		Render.batch.setProjectionMatrix(camara.combined);
		Render.batch.begin();

		Globales.jardin.getFondo().dibujar();

		Render.batch.end();

		// Capa del hud, para que no se vea por encima de las plantas

		camaraHud.update();
		Render.batch.setProjectionMatrix(camaraHud.combined);

		Render.batch.begin();

		menuBoton.dibujar();
		hud.mostrarHud();
		Render.batch.end();

		// Capa de Plantas y soles

		Render.batch.setProjectionMatrix(camara.combined);
		Globales.jardin.ejecutar();
		Render.batch.begin();
		
		sol.dibujar();

		if (!Globales.pausaActiva && finCamaraInicial) {

			if (finCamaraInicial) {
				hud.clickearPlanta();
				sol.ejecutar();
			}

		}
		
//		atlasLol.dibujar();
//		zombiLol.dibujar();

		Render.batch.end();

		// Capa de pausa

		camaraHud.update();
		Render.batch.setProjectionMatrix(camaraHud.combined);
		Render.batch.begin();

		pausar();

		Render.batch.end();

	}
	

	// FUNCIONES PARA EL CONSTRUCTOR
	
	private void inicializarPausa() {
		
		pausaImg = new Imagen("img/hud/pausa.png", 406, 348);
		pausaImg.setPosition(300, 110);

		menuBoton = new Imagen("img/hud/menu.png", 132, 36);
		menuBoton.setPosition(Render.ANCHO - 132 - 5, Render.ALTO - 36);
		areaMenuBoton = new Rectangle(Render.ANCHO - 132 - 5, Render.ALTO - 36, 132, 36);

		botonPausa[0] = new Imagen("img/hud/pausaboton.png", 33, 33);
		botonPausa[0].setPosition(554, 304);
		botonPausa[1] = new Imagen("img/hud/pausaboton.png", 33, 33);
		botonPausa[1].setPosition(554, 275);

		areaBotonPuasa[0] = new Rectangle(554, 304, 33, 33);
		areaBotonPuasa[1] = new Rectangle(554, 275, 33, 33);
		areaBotonPuasa[2] = new Rectangle(404, 228, 203, 40);
		areaBotonPuasa[3] = new Rectangle(344, 130, 320, 70);
		
	}
	
	
	// FUNCIONES PRIVADAS

	private void pausar() {

		if (areaMenuBoton.contains(Entradas.getMouseX(), Entradas.getMouseY())) {
			if (clickearUnaVez()) {
				Globales.pausaActiva = !Globales.pausaActiva;
//				camaraInicial(true); //cada vez q clickeas se mueve un poco, sirve para testear las camaras
			}
		}

		if (Globales.pausaActiva) {
			if (!unaVezPausa) {
				pausa.play(Globales.volumenSfx);
				if (finCamaraInicial) {
					Globales.jardin.pausarMusica();
				}
				unaVezPausa = true;
			}
			menuPausa();
//			Globales.jardin.pausar();

			// boton back to game el cual tendría que ir adentro del menuPausa() pero
			// creeme, creeme que no queres hacer eso
			if (areaBotonPuasa[3].contains(Entradas.getMouseX(), Entradas.getMouseY())) {
				if (Entradas.getBotonMouse() == 0) {
					Globales.pausaActiva = false;
				}
			}
		} else {
			if (unaVezPausa) {
				pausa.play(Globales.volumenSfx);
				if (finCamaraInicial) {
					Globales.jardin.playMusica();
				}
				unaVezPausa = false;
			}
//			Globales.jardin.reanudar();
		}

	}

	private void menuPausa() {
		pausaImg.dibujar();

		for (int i = 0; i < areaBotonPuasa.length - 2; i++) { // le restamos 2 xq los ultimos dos botones se trabajan
																// aparte y asi no tenemos problemas con los arrays
																// boleanos que no tienen una longitud mayor a 2
			if (areaBotonPuasa[i].contains(Entradas.getMouseX(), Entradas.getMouseY())) {
				if (Entradas.getBotonMouse() == 0) {
					if (!unaVezBotones[i]) {
						sfxMusic[i] = !sfxMusic[i];

						// boton de activado o desactivado de sonidos
						if (i == 1) {
							Globales.sfxOn = !Globales.sfxOn;
						}

						// boton de activado o desactivado de musica
						if (i == 0) {
							Globales.musicaOn = !Globales.musicaOn;
						}
						bleep.play(Globales.volumenSfx);
						unaVezBotones[i] = true;
					}
				} else {
					unaVezBotones[i] = false;
				}
			}

		}

		if (!Globales.musicaOn) {
			botonPausa[0].dibujar();
		}
		if (!Globales.sfxOn) {
			botonPausa[1].dibujar();
		}

		// boton main menu
		if (areaBotonPuasa[2].contains(Entradas.getMouseX(), Entradas.getMouseY())) {
			if (Entradas.getBotonMouse() == 0) {
				Globales.pausaActiva = false;
				screenMg.setMenu();
				PantallaInicio.musica.play();
				if (Globales.musicaOn) {
					PantallaInicio.musica.setVolume(0f);
				}
				screenMg.disposePartida(this);

			}
		}

	}

	private void ajustarVolumen() {

		if (Globales.musicaOn) {
			Globales.jardin.desmutearMusica();
		} else {
			Globales.jardin.mutearMusica();
			selector.stop();
		}

		if (Globales.sfxOn) {
			Globales.volumenSfx = 1f;
		} else {
			Globales.volumenSfx = 0f;
		}

	}

	private boolean camaraInicial(boolean onOff) {

		if (!onOff) {
			if (!unaVezCamaraInicial) {
				Globales.jardin.playMusica();
				finCamaraInicial = true;
				unaVezCamaraInicial = true;
			}
			return false;
		}

		if (!unaVezCamaraInicial) {
			selector.play();
			unaVezCamaraInicial = true;
		}

		if (Globales.pausaActiva) {
			selector.stop();
		}

		if (tiempoAnimacion == 0) {
			if (camara.position.x < Render.ANCHO - 400) {
				camara.position.x += acelCamara;
				acelCamara += 0.1f;
			}
			if (camara.position.x < Render.ANCHO - 200 && camara.position.x >= 560) {
				acelCamara += 0.2f;
				camara.position.x += acelCamara;
			}
		}

		if (camara.position.x >= 760 && tiempoAnimacion < 150) {
			tiempoAnimacion += 1;
			acelCamara = 0.1f;
		}
		if (tiempoAnimacion == 150) {
			if (camara.position.x > Render.ANCHO / 2 + 10) {
				acelCamara += 0.2f;
				restarVolumen -= 0.01f;
				selector.setVolume(restarVolumen);
				camara.position.x -= acelCamara;
			} else {
				tiempoAnimacion = 151;
			}
		}

		if (tiempoAnimacion == 151) {
			selector.stop();
			selector.dispose();
			tiempoAnimacion++;
		}
		if (tiempoAnimacion >= 152 && tiempoAnimacion < 200) {
			tiempoAnimacion++;
		}
		if (tiempoAnimacion == 200) {
			Globales.jardin.playMusica();
			finCamaraInicial = true;
		}
		return true;
	}

	// llego tu hora... hermano
	private boolean clickearUnaVez() {
		if (Entradas.getBotonMouse() == 0 && !unaVezClickIzquierdo) {
//			System.out.println("clickardopolis");
			unaVezClickIzquierdo = true;
			return true;
		}
		if (Entradas.getBotonMouse() == -1) {
			unaVezClickIzquierdo = false;
			return false;
		}
		return false;
	}

	// OVERRIDES NO USADOS

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {

		// Audio

		// Imagenes
		pausaImg.dispose();
		for (int i = 0; i < botonPausa.length; i++) {
			botonPausa[i].dispose();
		}

		// Otros
		hud.dispose();

	}

}
