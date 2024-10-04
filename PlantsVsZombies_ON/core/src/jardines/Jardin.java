package jardines;

import java.util.Iterator;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;

import casilla.Casilla;
import utilidades.Imagen;
import utilidades.Render;

public abstract class Jardin {
	
	// Datos generales
	private String nombre;
	private Imagen fondo;
	private Music musica;
	
	// Casillas
	private int filasCasillas;
	private int columnasCasillas;
	private Vector2 alineacionGrilla; // alineacionGrilla es la posicion inicial de la primera casilla.
	private Casilla[][] casillas;
	private int distanciaFilas = 6;  // distanciaFilas es la distancia entre cada fila
	private int distanciaColumnas = 2; // distanciaColumnas es la distancia entre cada columna
	
	public Jardin(String nombre, int filasCasillas, int columnasCasillas) {
		this.nombre = nombre;
		this.filasCasillas = filasCasillas;
		this.columnasCasillas = columnasCasillas;
		this.casillas = new Casilla[filasCasillas][columnasCasillas];
		
		// inicializar objetos a traves del metodo homonimo
	}

	public void ejecutar() {
		
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				mostrarCasilla(i, j);
				detectarCasilla(i, j);
			}
		}

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
				
				// plantas
				if(casillas[i][j].getPlanta() != null) {
				casillas[i][j].getPlanta().pausarAnimacionEnFrame(1);
				}
				
				// zombies
				if(casillas[i][j].getZombie().size() > 0) {
					
					for (int k = 0; k < casillas[i][j].getZombie().size(); k++) {
						casillas[i][j].getZombie().get(k).pausarAnimacionEnFrame(1);
					}

				}
			}
		}

	}
	
	
	public void reanudar() {
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				
				// plantas
				if(casillas[i][j].getPlanta() != null) {
				casillas[i][j].getPlanta().reanudarAnimacion();
				}
				
				// zombies
				if(casillas[i][j].getZombie().size() > 0) {
					
					for (int k = 0; k < casillas[i][j].getZombie().size(); k++) {
						casillas[i][j].getZombie().get(k).reanudarAnimacion();
					}

				}
				
			}
		}
	}
	
	
	// FUNCIONES PRIVADAS / PROTEGIDAS
	
	protected void inicializarObjetos(Imagen fondo, Music musica, Vector2 alineacionGrilla) {
		this.fondo = fondo;
		this.musica = musica;
		this.musica.setLooping(true);
		this.alineacionGrilla = alineacionGrilla;
		crearCasillas();
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
	
	
	// GETTERS
	
	public Casilla[][] getCasillas() {
		return casillas;
	}

	public Imagen getFondo() {
		return fondo;
	}

}
