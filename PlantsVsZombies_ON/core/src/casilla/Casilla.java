package casilla;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import hud.Hud;
import pantallas.Partida;
import plantas.Planta;
import plantas.Plantas;
import recursos.Entradas;
import recursos.Globales;

public class Casilla {

	private Planta planta = null;
	private Rectangle casilla;
	private ShapeRenderer contorno = new ShapeRenderer();;
	private int x, y;
	public static int ancho = 70, alto = 80;
	private boolean mostrar = false; // cambiar el valor del boleano para mostrar o no el contorno de las casillas
	
	private Sound plantar = Gdx.audio.newSound(Gdx.files.internal("audio/plant.mp3"));

	private boolean dentroCasilla;
	
	public Casilla(int x, int y) {

		this.casilla = new Rectangle(x, y, ancho, alto);

		this.x = x;
		this.y = y;

	}

	public void detectar() {
		
		// una vez planta la planta, reproducir su animación
		if(planta != null) {
			planta.animacionIddle();
		}
		
		// si no es -1 significa que hay una planta seleccionada
		if (Hud.indPlantaClickeada != -1 && !Partida.pausaActiva) {

			if (this.planta == null) {

				// este if detecta que nuestro mouse esté dentro de una d las casillas
				if (this.casilla.contains(Entradas.getMouseX(), Entradas.getMouseY())) {
					
					dentroCasilla = true;

					// aca detecta que hayamos hecho click
					if (Entradas.getBotonMouse() == 0) {
						
						this.planta = Plantas.obtenerPlanta(Hud.nombrePlantaClickeda);
		
						this.planta.animationX = this.x - 15; // se le resta 15 para que la planta quede bien centrada
						this.planta.animationY = this.y;
						plantar.play(Globales.volumenSfx);
						Hud.cantSoles -= this.planta.getCoste();
						
						Globales.plantaReiniciarCooldown = Hud.nombrePlantaClickeda;
						
						Hud.indPlantaClickeada = -1;
					}

				} else {
					dentroCasilla = false;
				}
			}
		}
	}

	public void mostrar() {
		if (mostrar) {

			contorno.begin(ShapeRenderer.ShapeType.Line);

			contorno.setColor(1, 0, 0, 1); // Rojo para las hitboxes
			contorno.rect(this.x, this.y, ancho, alto);

			contorno.end();

		}
	}
	
	public Planta getPlanta() {
		return planta;
	}

//	casillas = new Rectangle[5][9];
//	for (int i = 0; i < casillas.length; i++) {
//		for (int j = 0; j < casillas[i].length; j++) {
//			casillas[i][j] = new Rectangle(posicionInicial + casillasX * j, casillasY, 70, 80);
//
//		}
//		casillasY -= 85;
//	}

}
