package juego;

import java.awt.Image;

import entorno.Entorno;

public class Tortuga {
	double x;
	double y;
	double alto;
	double ancho;
	boolean direccion; //false:derecha, true:izq.
	Image imagenDer;
	Image imagenIzq;
	double escala;
	double velocidad;
	Entorno e;
	boolean estaApoyado;
	int isla;
	
	public Tortuga(Entorno e) {
		this.x = Math.random()*800.0; //Número aleatorio menor al ancho de la ventana de juego.
		boolean xok = false;
		while(!xok) {
			if((this.x > 25 && this.x < 350) || (this.x > 400 && this.x < 800)) { // Coordenadas que tiene que cumplir "x" para que la tortuga no se genere fuera de una isla.
				xok = true;
			} else {	
				this.x = Math.random()*1150.0;
			}
		}
		this.y = 0;
		this.e = e;
		this.direccion = false;
		this.escala = 0.03;
		this.velocidad = 0.8;
		this.imagenDer = entorno.Herramientas.cargarImagen("TortugaDer.png");
		this.imagenIzq = entorno.Herramientas.cargarImagen("TortugaIzq.png");
		this.ancho = imagenDer.getWidth(null)*this.escala;
		this.alto = imagenDer.getHeight(null)*this.escala;
		this.estaApoyado = false;
		this.isla = -1;
	}
	
	public void mover(Isla i) {
		if(direccion) {
			x--;
			if(x < i.getBordeIzq()) {
				this.direccion = !this.direccion;
			}
		}
		else {
			x++;
			if(x > i.getBordeDer()) {
				this.direccion = !this.direccion;
			}
		}
	}
	
	
	
	public void movVertical() {
		if(!this.estaApoyado) {
			y = y + 2;
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
