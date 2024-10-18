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
	private Image casaGnomo;
	private Gnomo[] gnomos;
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 810, 600);
//		fondo = Herramientas.cargarImagen("Fondo.gif");
		this.pep = new Personaje(350, entorno.ancho()/2, entorno);
		this.casaGnomo = Herramientas.cargarImagen("CasaGnomo.png");
		this.islas = new Isla[15];
		this.tortugas = new Tortuga[9];
		this.gnomos = new Gnomo[4];
		// Inicializar lo que haga falta para el juego:
		
		//Islas:
		int k = 0;
		for(int i = 1; i <= 5; i++) {
			for(int j = 1; j <= i; j++) {
				if(i == 5) {
					this.islas[k] = new Isla((j*entorno.ancho()/(i+1))-92+(2*j)*(i*3), i*110, entorno);
				}
				if(i == 4) {
					this.islas[k] = new Isla((j*entorno.ancho()/(i+1))-60+(2*j)*(i*3), i*110, entorno);
				}
				if(i == 3) {
					this.islas[k] = new Isla((j*entorno.ancho()/(i+1)) - (2*j) +5, i*110, entorno);
				}
				if(i == 2) {
					this.islas[k] = new Isla((j*entorno.ancho()/(i+1)) - (2*j) +5, i*110, entorno);
				}
				if(i == 1) {
					this.islas[k] = new Isla(entorno.ancho()/2, 110, entorno);
				}
				k++;
			}
		}
		
		//Tortuga:
		for(int i = 0; i < this.tortugas.length; i++) {
			this.tortugas[i] = new Tortuga(entorno);
		}
		//Gnomo:
		for(int i = 0; i < this.gnomos.length; i++) {
			this.gnomos[i] = new Gnomo(entorno);
		}
		this.entorno.iniciar();
		
	}

	
	public void tick()
	{
//		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);
		entorno.dibujarImagen(casaGnomo, entorno.ancho()/2, 58, 0, 0.17);
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
		
		//Gnomo:
		for(Gnomo gnomo : gnomos) {
			if(gnomo != null) {
				gnomo.movVertical();
			}
		}
		for(int i = 0; i < gnomos.length; i++) {
			if(gnomos[i] != null) {
			if(pisandoIslaGnomo(gnomos[i], islas)) {
				this.gnomos[i].estaApoyado = true;
				this.gnomos[i].mover(this.gnomos[i].direccion);
			} else {
				this.gnomos[i].estaApoyado = false;
				this.gnomos[i].cambiarDireccion();
			}} else {
				gnomos[i] = new Gnomo(entorno);
			}
		}
		for(int i = 0; i < gnomos.length; i++) {
			if(gnomos[i] != null && gnomos[i].seCayo()) {
				gnomos[i] = null;
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
		for(Gnomo g : this.gnomos) {
			if(g != null) {
				g.mostrar();
			}
		}

		//Salir: (Cierra ventana cuando el personaje cae.
		if(this.pep.getBordeSup() > this.entorno.alto()) {
			System.exit(0);
		}
	}
	
	public void chequearTeclas() {
		if(entorno.estaPresionada(entorno.TECLA_DERECHA) && !tocoPared(pep, islas)) {
			this.pep.mover(2);
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && !tocoPared(pep, islas)) {
			this.pep.mover(-2);
		}
		if(entorno.sePresiono(entorno.TECLA_ARRIBA) && (pisandoIsla(pep, islas)>=0)) {
			this.pep.saltar();
		}
		if(entorno.sePresiono('c') && bola == null && pep.estaApoyado) {
			this.pep.disparar();
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
		return ((Math.abs(p.getBordeSup()- i.getBordeInf()) < 2)
				&& (p.getBordeDer() > (i.getBordeIzq()))
				&& (p.getBordeIzq() < (i.getBordeDer())));
	}
	
	private boolean tocoPared(Personaje p, Isla i) {
		return (Math.abs(p.y - i.y) < i.alto && Math.abs(p.getBordeDer() - i.getBordeIzq()) < 2)
				||
				(Math.abs(p.y - i.y) < i.alto && Math.abs(p.getBordeIzq() - i.getBordeDer()) < 2);
	}
	
	private boolean tocoPared(Personaje p, Isla[] islas) {
		for(int i = 0; i < this.islas.length; i++){
			if(tocoPared(p, this.islas[i])){
				return true;
			}
		}
		return false;
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

	private boolean pisandoIslaGnomo(Gnomo g, Isla i) {
		return (Math.abs(g.getBordeInf() - i.getBordeSup()) < 1) && (g.getBordeDer() > i.getBordeIzq()) && (g.getBordeIzq() < i.getBordeDer());
	}
		
	private boolean pisandoIslaGnomo(Gnomo g, Isla[] islas) {
		for(int i = 0; i < this.islas.length; i++) {
			if(pisandoIslaGnomo(g, this.islas[i])) {
				return true;
			}
		}
		return false;
	}
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
	
}
