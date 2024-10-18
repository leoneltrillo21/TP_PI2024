package juego;

import java.awt.Image;

import entorno.Entorno;

public class Gnomo {
	double x;
	double y;
	double alto;
	double ancho;
	boolean direccion; //false:derecha, true:izq.
	Image imagenDer;
	Image imagenIzq;
	Image imagenCayendo;
	double escala;
	double velocidad;
	Entorno e;
	boolean estaApoyado;
	
	public Gnomo(Entorno e) {
		this.x = e.ancho() / 2;
		this.y = 58;
		this.e = e;
		this.direccion = false;
		this.escala = 0.9;
		this.velocidad = 0.5;
		this.imagenDer = entorno.Herramientas.cargarImagen("GnomoDer.png");
		this.imagenIzq = entorno.Herramientas.cargarImagen("GnomoIzq.png");
		this.imagenCayendo = entorno.Herramientas.cargarImagen("GnomoC.png");
		this.ancho = imagenDer.getWidth(null)*this.escala;
		this.alto = imagenDer.getHeight(null)*this.escala;
		this.estaApoyado = true;
	}
	
	public void mover(boolean direccion) {
		if(estaApoyado) {
			if(direccion) {
				x = x - velocidad;
			}else {
				x = x + velocidad;
			}
		}
	}
	
	
	public void movVertical() {
		if(!this.estaApoyado) {
			y = y + 2;
		}
	}
	
	public void mostrar() {
		if(estaApoyado) {
			if(direccion) {
				e.dibujarImagen(imagenIzq, x, y, 0, escala);
			} else {
				e.dibujarImagen(imagenDer, x, y, 0, escala);
			}
		} else {
			e.dibujarImagen(imagenCayendo, x, y, 0, escala);
		}
	}
	
	public void cambiarDireccion() {
		this.direccion = Math.random() < 0.5;
	}
	
	public boolean seCayo() {
		return this.y > e.alto();
	}
	
	
	public double getBordeDer() {
		return x + (this.ancho/2);
	}
	
	public double getBordeIzq() {
		return x - (this.ancho/2);
	}
	
	public double getBordeSup() {
		return y - (this.alto/2);
	}
	
	public double getBordeInf() {
		return y + (this.alto/2);
	}


}
