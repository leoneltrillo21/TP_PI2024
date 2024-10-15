package juego;

import java.awt.Image;

import entorno.Entorno;

public class Personaje {
	double y;
	double x;
	double alto;
	double ancho;
	boolean direccion; //false: derecha, true: izquierda.
	Image imagenIzq;
	Image imagenDer;
	double escala;
	double velocidad;
	Entorno e;
	boolean estaApoyado, disparando;
	boolean estaSaltando;
	int contadorSalto;
	
	public Personaje(double y, double x, Entorno ent) {
		this.x = x;
		this.y = y;
		this.direccion = false;
		this.escala = 0.2;
		this.velocidad = 0;
		this.imagenDer = entorno.Herramientas.cargarImagen("ImagePersonajeDer.png");
		this.imagenIzq = entorno.Herramientas.cargarImagen("ImagePersonajeIzq.png");
		this.e = ent;
		this.ancho = imagenDer.getWidth(null)*this.escala;
		this.alto = imagenIzq.getHeight(null)*this.escala;
		this.estaApoyado = false;
		this.estaSaltando = false;
		this.contadorSalto = 0;
	}
	
	public void movVertical() {
		if(!this.estaApoyado && !this.estaSaltando) {
			y = y + 2;
		}
		if(this.estaSaltando && !this.estaApoyado) {
			y = y - 3;
			contadorSalto++;
		}
		if(getBordeSup() < 0) {
			cancelarSalto();
		}
		if(contadorSalto==40) {
			estaSaltando = false;
			contadorSalto = 0;
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
	
	public void mover(double y) {
		this.x = this.x + y;
		if(getBordeDer() > this.e.ancho()) {
			this.x = e.ancho()-(this.ancho/2);
		}
		if(getBordeIzq() < 0) {
			this.x = this.ancho/2;
		}
		if(y >= 0) {
			this.direccion = false;
		}
		else {
			this.direccion = true;
		}
	}
	
	
	public void saltar() {
		this.estaApoyado = false;
		this.estaSaltando = true;
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

	public void cancelarSalto() {
		this.estaSaltando = false;
		contadorSalto = 0;
	}

	public void disparar() {
		this.disparando = true;
	}

}
