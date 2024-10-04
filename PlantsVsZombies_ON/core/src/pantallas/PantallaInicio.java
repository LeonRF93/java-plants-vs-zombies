package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.lionstavern.pvz.PvzPrincipal;

import utilidades.Entradas;
import utilidades.Imagen;
import utilidades.Render;

public class PantallaInicio implements Screen {
	
    private PvzPrincipal principal;
    private ScreenManager screenMg;

    private Imagen fondo;
    private float cont = 0;
    private int tiempo = 0;
    private boolean fadeIn = true;
	
	public static Music musica = Gdx.audio.newMusic(Gdx.files.internal("audio/pvz.mp3") );
	
	public PantallaInicio(PvzPrincipal principal) {
		this.principal = principal;
		screenMg = new ScreenManager(principal);
	}

	@Override
	public void show() {
		
		Gdx.input.setInputProcessor(new Entradas());
		
		fondo = new Imagen("img/lionsTavern.png", Render.ANCHO, Render.ALTO);
		fondo.s.setAlpha(0);

		musica.setLooping(true);
		musica.setVolume(0.5f);
		musica.play();
	}

	@Override
	public void render(float delta) {

		Render.limpiarPantalla(0, 0, 0, 1);

		if (tiempo == 130 || Entradas.getBotonMouse() == 0) {
//			principal.setScreen(new Menu(principal));
			screenMg.setMenu();
			screenMg.disposePantallaInicio(this);
		} else {
			tiempo++;
		}

		if (tiempo > 30 && fadeIn) {
			if (cont < 1) {
				cont += 0.1f;
			}
			if (cont > 1) {
				cont = 1;
				fadeIn = false;
			}
		}
		if (tiempo > 100 && !fadeIn) {
			if (cont > 0) {
				cont -= 0.1f;
			}
			if (cont < 0) {
				cont = 0;
				fadeIn = false;
			}
		}

		Render.batch.begin();
		fondo.dibujar();
		fondo.s.setAlpha(cont);
		Render.batch.end();

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
		fondo.dispose();
		// importante, no poner "musica.dispose()" asi sigue sonando en el menu
	}

}
