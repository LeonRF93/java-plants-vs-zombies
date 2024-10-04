package com.lionstavern.pvz;

import java.io.FileOutputStream;
import java.io.PrintStream;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import utilidades.Render;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {

//		try {
//			// Redirigir la salida a un archivo para chequear errores al hacer doble click
//			// al .jar
//			PrintStream out = new PrintStream(new FileOutputStream("output.log"));
//			System.setOut(out);
//			System.setErr(out);
//			System.out.println("Java version: " + System.getProperty("java.version"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(Render.ANCHO, Render.ALTO);
		config.setResizable(false);
		config.setTitle("Plants Vs. Zombies Over the Net");
		config.setWindowIcon("icons/icon128.png", "icons/icon64.png", "icons/icon32.png", "icons/icon16.png");
		new Lwjgl3Application(new PvzPrincipal(), config);

	}
}
