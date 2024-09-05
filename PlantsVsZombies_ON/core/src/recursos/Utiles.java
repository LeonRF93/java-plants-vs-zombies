package recursos;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Utiles {

	public static Scanner s = new Scanner(System.in);
	public static Random r = new Random();

	
	
	public static int ingresarEntero(int min, int max) {
		int numero = 0;
		boolean error = false;

		do {
			error = false;
			try {
				numero = s.nextInt();
				s.nextLine();
				if (numero > max || numero < min) {
					System.out.println(
							"Error, debe ingresar un numero entre " + min + " y " + max + ". Vuelva a ingresar");
					error = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("Error, debe ingresar un numero entero, vuelva a ingresar:");
				s.nextLine();
				error = true;
			} catch (Exception e) {
				System.out.println("Error inesperado.");
				error = true;
			}
		} while (error);

		return numero;
	}

	
	
	public static void esperar(int milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	
	// sonidos que se le pueden ajustar el sonido
	public static Clip crearSonido(String rutaDelSonido) {
		Clip clip=null;
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File(rutaDelSonido).getAbsoluteFile());

			// Obtener un clip de audio
			clip = AudioSystem.getClip();

			// Abrir el clip de audio con el audio cargado
			clip.open(audioInputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return clip;
	}
	
	public static void reproducirSonido(Clip clip, float volumen) {
		// Obtén el control de volumen
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        // Establece el volumen deseado (en dB)
        gainControl.setValue(volumen);
		
		clip.start();
	}
	
	public static void detenerSonido(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            // clip.close(); // Libera los recursos asociados con el Clip
        }
	}
	
	public static void reproducirSonidoRepitiendose(Clip clip, float volumen) {
		// Obtén el control de volumen
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        // Establece el volumen deseado (en dB)
        gainControl.setValue(volumen);
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
	}

}
