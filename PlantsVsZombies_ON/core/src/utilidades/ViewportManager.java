package utilidades;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ViewportManager {

	private FitViewport viewport;
    private OrthographicCamera camara;

    public ViewportManager() {
        camara = new OrthographicCamera();
        viewport = new FitViewport(Render.ANCHO_MONITOR, Render.ALTO_MONITOR, camara);

        camara.position.set(Render.ANCHO_MONITOR/2, Render.ALTO_MONITOR/2, 0);
        camara.update();
        viewport.apply(); // Aplica el viewport de inmediato
    }

    public void setSize(int ancho, int alto) {
        viewport.update(ancho, alto);
        camara.position.set(Render.ANCHO_MONITOR/2, Render.ALTO_MONITOR/2, 0);
        camara.update();
    }

    public void setProjectionMatrix(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camara.combined);
    }

    public void update() {
        camara.update();
    }

    public OrthographicCamera getCamara() {
        return camara;
    }
	
}
