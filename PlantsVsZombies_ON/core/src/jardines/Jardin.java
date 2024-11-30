package jardines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import casillas.Casilla;
import utilidades.Globales;
import utilidades.Imagen;
import utilidades.Render;
import utilidades.Rutas;

public abstract class Jardin {
	
	// Datos generales
	private String nombre;
	private Imagen fondo;
	private Music musica;
	private Music musicaHorda;
	
	// Casillas
	private int filasCasillas;
	private int columnasCasillas;
	private Vector2 alineacionGrilla; // alineacionGrilla es la posicion inicial de la primera casilla.
	private Casilla[][] casillas;
	private int distanciaFilas = 6;  // distanciaFilas es la distancia entre cada fila
	private int distanciaColumnas = 2; // distanciaColumnas es la distancia entre cada columna
	
	// Zona de victoria de los zombies
	private ShapeRenderer contorno = new ShapeRenderer();
	private Rectangle zonaVictoriaZombies;
	private Sound loseMusic = Gdx.audio.newSound(Gdx.files.internal("audio/losemusic.mp3"));
	private boolean unaVezLose;
	private Sound grito = Gdx.audio.newSound(Gdx.files.internal("audio/NOO.mp3"));
	private boolean unaVezGrito;
	private float tiempoGrito = 0f;
	private Sound mordisco = Gdx.audio.newSound(Gdx.files.internal(Rutas.SFX_MASTICAR));
	private int contadorMordiscos = 0;
	
		// Animacion del texto de victoria
		private Imagen teHanComidoLosSesos = new Imagen(Rutas.ZOMBIES_VICTORIA, Render.ANCHO/2, Render.ALTO/2);
		private int xInicial = 260, yInicial = 160;
		private float tiempoAnimacion = 0f;
		
	// Musica
	private float tiempoMusica = 0f;
	private float volumen = 1f;
	private float volumenHorda = 0f;
	private static int cantidadZombies = 0;
	private int cantidadHorda = 10;
	
	public Jardin(String nombre, int filasCasillas, int columnasCasillas, int anchoZona, int altoZona) {
		this.nombre = nombre;
		this.filasCasillas = filasCasillas;
		this.columnasCasillas = columnasCasillas;
		this.casillas = new Casilla[filasCasillas][columnasCasillas];
		
		this.zonaVictoriaZombies = new Rectangle(0, 0, anchoZona, altoZona);
		this.teHanComidoLosSesos.setPosition(260, 160);
		
		// inicializar objetos a traves del metodo homonimo
	}

	public void ejecutar() {
		
		controlarMusica();
		controlarZombies();
		
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				casillas[i][j].detectar();
			}
		}

	}
	
	public void logica() {
		controlarMusica();
		controlarZombies();
		
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				casillas[i][j].detectar();
			}
		}
	}
	
	public void dibujar() {
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				mostrarCasilla(i, j);
				casillas[i][j].dibujarPlantaZombie();
			}
		}
	}
	
	public void aumentarContZombies() {
		this.cantidadZombies++;
	}
	
	public void restarContZombies() {
		this.cantidadZombies--;
	}
	
	public void controlarZombies() {
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				for (int k = 0; k < casillas[i][j].getZombie().size(); k++) {
					if(this.zonaVictoriaZombies.contains(casillas[i][j].getZombie().get(k).getHitbox())) {
						if(!unaVezLose) {
							this.loseMusic.play(Globales.volumenSfx);
							this.unaVezLose = true;
						}
					}
				}
			}
		}
		
		if(unaVezLose) {
			sonidosVictoriaZombies();
		}
		
		if(contadorMordiscos > 1) {
			animacionVictoriaZombies();
			Render.batch.begin();
			teHanComidoLosSesos.dibujar();
			Render.batch.end();
		}
	}
	
	public void sonidosVictoriaZombies() {
		
		this.tiempoGrito += Globales.getDeltaTime();
		if(this.tiempoGrito > 4) {
			if(!this.unaVezGrito) {
				this.grito.play(Globales.volumenSfx);
				this.unaVezGrito = true;
			}
			
			if(this.tiempoGrito > 4 && this.contadorMordiscos < 5) {
				this.tiempoGrito = 3.5f;
				this.mordisco.play(Globales.volumenSfx);
				this.contadorMordiscos++;
			}
		}
		
		
	}
	
	public void animacionVictoriaZombies() {
		
		if(this.teHanComidoLosSesos.getAncho() < Render.ALTO*1.3) {
		
			this.tiempoAnimacion += Render.getDeltaTime();
			if(this.tiempoAnimacion > 0.01f) {
				
				float centroX = teHanComidoLosSesos.getX() + teHanComidoLosSesos.getAncho()/2;
				float centroY = teHanComidoLosSesos.getY() + teHanComidoLosSesos.getAlto()/2;
				
				this.teHanComidoLosSesos.setSize(teHanComidoLosSesos.getAncho()+2, teHanComidoLosSesos.getAlto()+2);
				this.teHanComidoLosSesos.setPosition(centroX - teHanComidoLosSesos.getAncho()/2, centroY - teHanComidoLosSesos.getAlto()/2);
				this.tiempoAnimacion = 0f;
			}
		}
	}

	public void dibujarGuisante() {
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				casillas[i][j].dibujarGuisante();
			}
		}
	}
	
	private void controlarMusica() {
		
		if(Globales.pausaActiva) {
			this.musica.pause();
			this.musicaHorda.pause();
		} else {
			if(Globales.musicaOn) {
				if(unaVezLose) {
					mutearMusica();
				} else {
					alternarMusicaHorda();
				}
			} else {
				mutearMusica();
			}
		}
		
	}
	
	private void alternarMusicaHorda() {
		
		this.tiempoMusica += Render.getDeltaTime();
		
		if(cantidadZombies > cantidadHorda) {

			if(this.tiempoMusica > 0.1 && this.volumenHorda <= 1f) {
				this.volumen -= 0.1;
				this.volumenHorda += 0.1f;
				this.tiempoMusica = 0f;
				if(this.volumen <= 0.1f) {
					this.volumen = 0;
				}
			}
		
			
		} else {

			if(this.tiempoMusica > 0.1 && this.volumen <= 1f) {
				this.volumen += 0.1;
				this.volumenHorda -= 0.1f;
				this.tiempoMusica = 0f;
				if(this.volumenHorda <= 0.1f) {
					this.volumenHorda = 0;
				}
			}
		}
		
		musica.setVolume(this.volumen);
		musicaHorda.setVolume(this.volumenHorda);
		
	}

	public void playMusica() {
		musica.play();
		musicaHorda.play();
	}
	
	public void pausarMusica() {
		musica.pause();
	}
	
	public void mutearMusica() {
		musica.setVolume(0f);
		musicaHorda.setVolume(0f);
	}
	
	public void desmutearMusica() {
		musica.setVolume(1f);
		musicaHorda.setVolume(1f);
	}

	
	// FUNCIONES PRIVADAS / PROTEGIDAS
	
	protected void inicializarObjetos(Imagen fondo, Music musica, Vector2 alineacionGrilla) {
		this.fondo = fondo;
		this.musica = musica;
		this.musica.setLooping(true);
		this.alineacionGrilla = alineacionGrilla;
		crearCasillas();
	}
	
	protected void inicializarObjetos(Imagen fondo, Music musica, Music musicaHorda, Vector2 alineacionGrilla) {
		this.fondo = fondo;
		this.musica = musica;
		this.musicaHorda = musicaHorda;
		this.musica.setLooping(true);
		this.alineacionGrilla = alineacionGrilla;
		crearCasillas();
	}

	private void mostrarCasilla(int i, int j) {
		casillas[i][j].mostrarContorno();
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
	
	private void dibujarZona() {
		contorno.begin(ShapeRenderer.ShapeType.Line);
		
		contorno.setColor(1, 0, 0, 1); // Rojo para las hitboxes
		contorno.rect(this.zonaVictoriaZombies.x, this.zonaVictoriaZombies.y, this.zonaVictoriaZombies.width, this.zonaVictoriaZombies.height);

		contorno.end();
	}
	
	
	// GETTERS
	
	public Casilla[][] getCasillas() {
		return casillas;
	}

	public Imagen getFondo() {
		return fondo;
	}
	
	
	// DISPOSE
	
	public void dispose() {
		this.fondo.dispose();
		this.musica.dispose();
		this.musicaHorda.dispose();
		this.loseMusic.dispose();
		this.grito.dispose();
		this.mordisco.dispose();
	}

}
