package com.lionstavern.pvz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pantallas.ScreenManager;
import recursos.Globales;
import recursos.Render;

public class PvzPrincipal extends Game {
	
	private ScreenManager screenMg;
	
	@Override
	public void create () {
		Render.batch = new SpriteBatch();
		screenMg = new ScreenManager(this);
		screenMg.setPartida();
		
	}
	
	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		Render.batch.dispose();
	}
}
