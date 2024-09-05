package mapas;

import java.util.Iterator;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;

import casilla.Casilla;
import recursos.Imagen;
import recursos.Render;

public class Mapa {

	private String nombre;
	private Imagen fondo;
	private Music musica;
	private int filasCasillas;
	private int columnasCasillas;
	private Vector2 alineacionGrilla; // alineacionGrilla es la posicion inicial de la primera casilla.
	private Casilla[][] casillas;

	private int distanciaFilas = 6;  // distanciaFilas es la distancia entre cada fila
	private int distanciaColumnas = 2; // distanciaColumnas es la distancia entre cada columna

	public Mapa(String nombre, Imagen fondo, Music musica, int filasCasillas, int columnasCasillas,
			Vector2 alineacionGrilla) {
		this.nombre = nombre;
		this.fondo = fondo;
		this.musica = musica;
		this.filasCasillas = filasCasillas;
		this.columnasCasillas = columnasCasillas;
		this.alineacionGrilla = alineacionGrilla;
		this.casillas = new Casilla[filasCasillas][columnasCasillas];
		this.musica.setLooping(true);

		crearCasillas();

	}

	public void correr() {
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				mostrarCasilla(i, j);
				detectarCasilla(i, j);
			}
		}


	}

	private void detectarCasilla(int i, int j) {
		casillas[i][j].detectar();
	}

	private void mostrarCasilla(int i, int j) {
		casillas[i][j].mostrar();
	}

	private void crearCasillas() {
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				// se multiplica por "j" porque j es la cantidad de casillas que hay
				casillas[i][j] = new Casilla(((int) alineacionGrilla.x + (distanciaColumnas + Casilla.ancho) * j),
						(int) alineacionGrilla.y);

			}
			alineacionGrilla.y -= Casilla.alto + distanciaFilas;
		}

	}

	public Casilla[][] getCasillas() {
		return casillas;
	}

	public void playMusica() {
		musica.play();
	}
	
	public void pausarMusica() {
		musica.pause();
	}
	
	public void mutearMusica() {
		musica.setVolume(0f);
	}
	
	public void desmutearMusica() {
		musica.setVolume(1f);
	}
	
	public void pausar() {
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				if(casillas[i][j].getPlanta() != null) {
				casillas[i][j].getPlanta().pausarAnimacionEnFrame(1);
				}
			}
		}
	}
	
	public void reanudar() {
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				if(casillas[i][j].getPlanta() != null) {
				casillas[i][j].getPlanta().reanudarAnimacion();
				}
			}
		}
	}

	public Imagen getFondo() {
		return fondo;
	}

}
