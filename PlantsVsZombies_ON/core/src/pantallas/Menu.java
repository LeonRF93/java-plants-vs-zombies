package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.lionstavern.pvz.PvzPrincipal;

import recursos.Entradas;
import recursos.Globales;
import recursos.Imagen;
import recursos.Render;
import recursos.Texto;

public class Menu implements Screen {

	private ScreenManager screenMg;
	private PvzPrincipal principal;

	// Audio

	private Sound bleep = Gdx.audio.newSound(Gdx.files.internal("audio/bleep.mp3"));
	boolean unaVezBleep;

	private Sound clickOpcion = Gdx.audio.newSound(Gdx.files.internal("audio/buttonclick.mp3"));
	boolean unaVezClickOpcion;

	private Sound start = Gdx.audio.newSound(Gdx.files.internal("audio/start.mp3"));
	boolean unaVezStart;
	float tiempoStart = 0;

	// botones

	private Imagen[] botones = new Imagen[5];
	private int x = -700;

	private Rectangle[] areaBotones = new Rectangle[4];
	private boolean[] botonClickeado = new boolean[4];
	private boolean unaVezClick;
	private int indiceOpcion = 0;

	// Menu de opciones
	private Imagen menu;
	private boolean menuActivo;

	private boolean[] unaVezSfx = new boolean[2];
	private boolean[] sfxMusic = { true, true };
	private Rectangle[] sfxMusicArea = new Rectangle[2];
	private Imagen[] sfxMusicImg = new Imagen[2];

	private Rectangle backToGame;

	private ShapeRenderer sr;

	// Nombre de jugador
	private Texto fuente;

	// Otros

	private float tiempo = 0;

	// Inputs ?
	private boolean unaVezClickIzquierdo;

	public Menu(PvzPrincipal principal) {
		this.principal = principal;
		this.screenMg = new ScreenManager(principal);
	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(new Entradas());

		botones[0] = new Imagen("img/menu/fondox.png", Render.ANCHO, Render.ALTO);
		botones[0].setX(x);
		botones[1] = new Imagen("img/menu/menuJugar.png", Render.ANCHO, Render.ALTO);
		botones[2] = new Imagen("img/menu/menuOpciones.png", Render.ANCHO, Render.ALTO);
		botones[3] = new Imagen("img/menu/menuAyuda.png", Render.ANCHO, Render.ALTO);
		botones[4] = new Imagen("img/menu/menuSalir.png", Render.ANCHO, Render.ALTO);

		fuente = new Texto("fonts/Minecraft.ttf", 15, Color.WHITE, false);
		fuente.x = -200;
		fuente.y = 463;

		sr = new ShapeRenderer();

		areaBotones[0] = new Rectangle(520, 420, 280, 50);
		areaBotones[1] = new Rectangle(680, 70, 90, 30);
		areaBotones[2] = new Rectangle(780, 40, 55, 30);
		areaBotones[3] = new Rectangle(870, 50, 50, 30);

		menu = new Imagen("img/menu/opciones.png", 406, 348);
		menu.setPosition(300, 110);

		backToGame = new Rectangle(350, 130, 310, 70);

		sfxMusicArea[0] = new Rectangle(555, 258, 33, 33);
		sfxMusicArea[1] = new Rectangle(555, 225, 33, 33);
		
		//esto se hace xq sino, en el cambio de pantallas el sonido se re bugea
		sfxMusic[0] = Globales.musicaOn;
		sfxMusic[1] = Globales.sfxOn;

		sfxMusicImg[0] = new Imagen("img/hud/pausaboton.png", 33, 33);
		sfxMusicImg[0].setPosition(555, 258);
		sfxMusicImg[1] = new Imagen("img/hud/pausaboton.png", 33, 33);
		sfxMusicImg[1].setPosition(555, 225);
	}

	@Override
	public void render(float delta) {

		Render.limpiarPantalla(0, 0, 0, 1);

		ajustarVolumen();

		Render.batch.begin();

		Render.limpiarPantalla(0, 0, 0, 1);
		botones[0].setX(x);

		if (animacionInicio()) {

			if (!botonClickeado[0]) {

				if (clickearOpciones()) {
					botones[indiceOpcion].dibujar();
				} else {
					botones[0].dibujar();
				}

			}

			ejecutarOpcion();
		}

		fuente.texto = ("Jugador");
		fuente.dibujar();

		Render.batch.end();

		mostrarContornoOpciones(false); // cambiar booleano para mostrar o no los contornos

	}

	// no usado... todavia
	private boolean clickearUnaVez() {
		if (Entradas.getBotonMouse() == 0 && !unaVezClickIzquierdo) {
			System.out.println("clickardopolis");
			unaVezClickIzquierdo = true;
			return true;
		}
		if (Entradas.getBotonMouse() == -1) {
			unaVezClickIzquierdo = false;
			return false;
		}
		return false;
	}

	private boolean animacionInicio() {
		if (x < 0) {
			botones[0].dibujar();
			x += 50;
			fuente.x += 5;
		} else {
			x = 0;
			fuente.x = 170;
			return true;
		}
		return false;
	}

	private boolean clickearOpciones() {

		if (!botonClickeado[1]) {

			for (int i = 0; i < areaBotones.length; i++) {
				if (areaBotones[i].contains(Entradas.getMouseX(), Entradas.getMouseY())) {

					indiceOpcion = i + 1; // le sumamos 1 xq la primera posicion pertenece al fondo
					if (!unaVezBleep) {
						bleep.play(Globales.volumenSfx);
						unaVezBleep = true;
					}

					if (Entradas.getBotonMouse() == 0) {
						if (!unaVezClick) {
							botonClickeado[i] = !botonClickeado[i];
							clickOpcion.play(Globales.volumenSfx);
							unaVezClick = true;
						}
					} else {
						unaVezClick = false;
					}

					return true;
				}
			}
			unaVezBleep = false;
		}
		return false;

	}

	public void ejecutarOpcion() {
		if (botonClickeado[0]) {
			opcionJugar();
		}
		if (botonClickeado[1]) {
			mostrarMenu();

		}
		if (botonClickeado[2]) {

		}
		if (botonClickeado[3]) {
			System.exit(1);
		}

	}

	public void opcionJugar() {

		if (!unaVezStart) {
			start.play(Globales.volumenSfx);
			PantallaInicio.musica.stop();
			unaVezStart = true;
		}

		if (tiempoStart < 5) {

			tiempoStart += Render.getDeltaTime();

			tiempo += Render.getDeltaTime();
			if (tiempo < 0.1f) {
				botones[0].dibujar();
			}
			if (tiempo > 0.1f && tiempo < 0.2f) {
				botones[1].dibujar();
			}
			if (tiempo > 0.2f) {
				botones[0].dibujar();
				tiempo = 0;
			}
		} else {
			botones[1].dibujar();
			screenMg.setPartida();
			screenMg.disposeMenu(this);
		}

	}

	public void mostrarMenu() {
		menu.dibujar();

		// boton back to game
		if (backToGame.contains(Entradas.getMouseX(), Entradas.getMouseY())) {
			if (Entradas.getBotonMouse() == 0) {
				clickOpcion.play(Globales.volumenSfx);
				botonClickeado[1] = false;
			}
		}

		// botones de soindo y musica
		for (int i = 0; i < sfxMusicArea.length; i++) {
			if (sfxMusicArea[i].contains(Entradas.getMouseX(), Entradas.getMouseY())) {
				if (Entradas.getBotonMouse() == 0) {
					if (!unaVezSfx[i]) {
						clickOpcion.play(Globales.volumenSfx);
						sfxMusic[i] = !sfxMusic[i];
						unaVezSfx[i] = true;
					}
				} else {
					unaVezSfx[i] = false;
				}
			}

		}

		if (!Globales.musicaOn) {
			sfxMusicImg[0].dibujar();
		}
		if (!Globales.sfxOn) {
			sfxMusicImg[1].dibujar();
		}

	}

	private void ajustarVolumen() {

		Globales.musicaOn = sfxMusic[0];
		Globales.sfxOn = sfxMusic[1];

		if (Globales.musicaOn) {
			PantallaInicio.musica.setVolume(0.5f);
		} else {
			PantallaInicio.musica.setVolume(0f);
		}

		if (Globales.sfxOn) {
			Globales.volumenSfx = 1.0f;
		} else {
			Globales.volumenSfx = 0f;
		}

	}

	private void mostrarContornoOpciones(boolean onOff) {
		if (onOff) {
			sr.begin(ShapeType.Line);
			sr.setColor(Color.RED);
			sr.rect(555, 258, 33, 33);
			sr.rect(555, 225, 33, 33);
			sr.end();
		}
	}

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
		for (int i = 0; i < botones.length; i++) {
			botones[i].dispose();
		}
		fuente.dispose();
	}

}
