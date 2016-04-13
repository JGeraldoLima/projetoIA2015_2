package main;

public class PosicaoFuga {
	
	public int posicaoDisparo;
	public int posicaoFuga;
	public int anguloFuga;
	public Double tempoResistencia;
	
	
	public Double getTempoResistencia() {
		return tempoResistencia;
	}
	public void setTempoResistencia(Double tempoResistencia) {
		this.tempoResistencia = tempoResistencia;
	}
	public int getPosicaoTiro() {
		return posicaoDisparo;
	}
	public void setPosicaoTiro(int posicaoTiro) {
		this.posicaoDisparo = posicaoTiro;
	}
	public int getPosicaoFuga() {
		return posicaoFuga;
	}
	public void setPosicaoFuga(int posicaoFuga) {
		this.posicaoFuga = posicaoFuga;
	}
	public int getAnguloFuga() {
		return anguloFuga;
	}
	public void setAnguloFuga(int anguloFuga) {
		this.anguloFuga = anguloFuga;
	}
	
	public String toString(){
		
		String retorno = posicaoDisparo+";"+posicaoFuga+";"+anguloFuga+";"+tempoResistencia+"|";
		
		return retorno;
	}
	
	
	public PosicaoFuga clone(){
		PosicaoFuga posicaoFugaClone = new PosicaoFuga();
		posicaoFugaClone.setAnguloFuga(this.getAnguloFuga());
		posicaoFugaClone.setPosicaoFuga(this.getPosicaoFuga());
		posicaoFugaClone.setPosicaoTiro(this.getPosicaoTiro());
		posicaoFugaClone.setTempoResistencia(this.getTempoResistencia());
		
		return posicaoFugaClone;
	}
	
	

}
