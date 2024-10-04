package personajes;

import utilidades.Imagen;

public class Moya {

	public Imagen img;
	
	Moya() {
		img = new Imagen("img/mal programado.jpg", 10, 10);
		img.x = 100;
		img.y = 100;
	}
	
//	Texture textura;
//	Sprite spr;
//	public int x=200, y=200;
//	int ancho=100, alto=100;
//	
//	public Moya() {
//		textura = new Texture("img/mal programado.jpg");
//		spr = new Sprite(textura);
//		spr.setPosition(x, y);
//		spr.setSize(ancho, alto);
//		
//	}
//	
//	public void dibujar() {
//		spr.draw(Render.batch);
//	}
}

