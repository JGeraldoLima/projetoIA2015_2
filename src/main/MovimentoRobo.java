package main;

public class MovimentoRobo {
	public double frente = 0;
	public double tras = 0;
	public double direita = 0;
	public double esquerda = 0;
	
	
	public MovimentoRobo(){
		
	}
	
	public MovimentoRobo(double frente, double tras, double direita, double esquerda) {
		this.frente = frente;
		this.tras = tras;
		this.direita = direita;
		this.esquerda = esquerda;
	}
}