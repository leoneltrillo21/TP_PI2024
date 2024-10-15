package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	private Entorno entorno;
	private Personaje pep;
	private Isla[] islas;
	private Tortuga[] tortugas;
	private BolaFuego bola;
//	private Image fondo;
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 810, 600);
//		fondo = Herramientas.cargarImagen("Fondo.gif");
		this.pep = new Personaje(350, entorno.ancho()/2, entorno);
		this.islas = new Isla[15];
		this.tortugas = new Tortuga[9];
		// Inicializar lo que haga falta para el juego:
		
		//Islas:
		int k = 0;
		for(int i = 1; i <= 5; i++) {
			for(int j = 1; j <= i; j++) {
				this.islas[k] = new Isla((j*entorno.ancho()/(i+1))-52+(20*j), i*110, entorno); //Coordenadas necesarias para formar una pirámide.
				k++;
			}
		}
		
		//Tortuga:
		for(int i = 0; i < this.tortugas.length; i++) {
			this.tortugas[i] = new Tortuga(entorno);
		}
		
		this.entorno.iniciar();
	}

	
	public void tick()
	{
//		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);
		//Procesamiento de un instante de tiempo:
		chequearTeclas();
		
		//Personaje:
		pep.movVertical();
		int k = pisandoIsla(pep, islas);
		if(k >= 0 && !pep.estaSaltando) {
			this.pep.estaApoyado = true;
		} else {
			this.pep.estaApoyado = false;
		}
		if(tocoTecho(pep, islas)) {
			pep.cancelarSalto();
		}
		//Bola de Fuego:
		if( bola != null && (bola.x < -1 || bola.x > entorno.ancho() + 1)) {
			bola = null;
		}
		//Tortuga:
		for(Tortuga t : tortugas) {
			t.movVertical();
		}
		int islaActual = 0;
		for(int i = 0; i < tortugas.length; i++) {
			int r = pisandoIslaTortuga(tortugas[i], islas);
			if(r >= 0) {
				if(r != islaActual) {
				this.tortugas[i].estaApoyado = true;
				tortugas[i].mover(islas[r]);
				islaActual = r;
				}
			} else {
				this.tortugas[i].estaApoyado = false;
			}
		}
		
		
		//Dibujar:
		this.pep.mostrar();
		if(pep.disparando && bola != null) {
			this.bola.mostrar();
			this.bola.mover();
		}
		for(Isla i: islas) {
			i.mostrar();
		}
		for(Tortuga t : this.tortugas) {
			t.mostrar();
		}
		
		//Salir: (Cierra ventana cuando el personaje cae.
		if(this.pep.getBordeSup() > this.entorno.alto()) {
			System.exit(0);
		}
	}
	
	public void chequearTeclas() {
		if(entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			this.pep.mover(2);
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			this.pep.mover(-2);
		}
		if(entorno.sePresiono(entorno.TECLA_ARRIBA) && (pisandoIsla(pep, islas)>=0)) {
			this.pep.saltar();
		}
		if(entorno.sePresiono('c') && bola == null && pep.estaApoyado) {
			pep.disparar();
			bola = new BolaFuego(pep.x, pep.y, pep.direccion, entorno);
		}

	}
	
	private boolean pisandoIsla(Personaje p, Isla i) {
		return (Math.abs(p.getBordeInf() - (i.getBordeSup()-0.5)) < 1) && (p.getBordeDer() > i.getBordeIzq()) && (p.getBordeIzq() < i.getBordeDer());
	}
		
	private int pisandoIsla(Personaje p, Isla[] islas) {
		for(int i = 0; i < this.islas.length; i++) {
			if(pisandoIsla(p, this.islas[i])) {
				return i;
			}
		}
		return -1;
	}

	
	private boolean tocoTecho(Personaje p, Isla i) {
		return (Math.abs(p.getBordeSup()- i.getBordeInf()) < 2) && (p.getBordeDer() > (i.getBordeIzq()+1)) && (p.getBordeIzq() < (i.getBordeDer()+1));
	}
		
	private boolean tocoTecho(Personaje p, Isla[] islas) {
		for(int i = 0; i < this.islas.length; i++) {
			if(tocoTecho(p, this.islas[i])) {
				return true;
			}
		}
		return false;
	}
	
	
	private boolean pisandoIslaTortuga(Tortuga t, Isla i) {
		return (Math.abs(t.getBordeInf() - i.getBordeSup()) < 1) && t.getBordeDer() > i.getBordeIzq() && t.getBordeIzq() < i.getBordeDer();
	}
	
	private int pisandoIslaTortuga(Tortuga t, Isla[] islas) {
		for(int i = 0; i < this.islas.length; i++) {
			if(pisandoIslaTortuga(t, this.islas[i])) {
				return i;
			}
		}
		return -1;
	}

	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
	
}
