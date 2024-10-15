package juego;

import java.awt.Image;

import entorno.Entorno;

public class BolaFuego {
	double x, y, alto, ancho, escala, velocidad;
	Image imagenDer, imagenIzq;
	boolean direccion, fin; //false:derecha, true:izquierda.
	Entorno e;
	
	public BolaFuego(double x, double y, boolean direccion, Entorno ent) {
		this.x = x;
		this.y = y;
		this.escala = 0.2;
		this.velocidad = 4;
		this.imagenDer = entorno.Herramientas.cargarImagen("BFuegoDer.png");
		this.imagenIzq = entorno.Herramientas.cargarImagen("BFuegoIzq.png");
		this.direccion = direccion;
		this.ancho = imagenDer.getWidth(null)*this.escala;
		this.alto = imagenIzq.getHeight(null)*this.escala;
		this.e =ent;
	}
	
	public void mover() {
		if(!direccion) {
			x = x + velocidad;
		} else {
			x = x - velocidad;
		}		
	}

	public void mostrar() {
		if(direccion) {
			e.dibujarImagen(imagenIzq, x, y, 0, escala);
		}
		else {
			e.dibujarImagen(imagenDer, x, y, 0, escala);
		}
	}
	
	public double getBordeDer() {
		return x + (this.ancho/2) - 2; //Ajuste de escala.
	}
	
	public double getBordeIzq() {
		return x - (this.ancho/2) + 2;
	}
	
	public double getBordeSup() {
		return y - (this.alto/2);
	}
	
	public double getBordeInf() {
		return y + (this.alto/2);
	}

}
