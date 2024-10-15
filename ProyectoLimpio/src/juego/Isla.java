package juego;

import java.awt.Image;

import entorno.Entorno;

public class Isla {
	double x;
	double y;
	double alto;
	double ancho;
	boolean direccion;
	Image imagen;
	double escala;
	double velocidad;
	Entorno e;
	
	public Isla(double x, double y, Entorno ent) {
		this.x = x;
		this.y = y;
		direccion = false;
		this.escala = 0.45;
		this.velocidad = 1;
		this.imagen = entorno.Herramientas.cargarImagen("IslaFlotante.png");
		this.e = ent;
		this.ancho = imagen.getWidth(null)*this.escala;
		this.alto = imagen.getHeight(null)*this.escala;
	}
	
	public void mostrar() {
		e.dibujarImagen(imagen, x, y, 0, escala);
	}
	
	public double getBordeDer() {
		return x + (this.ancho/2); //Ajuste de escala.
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
