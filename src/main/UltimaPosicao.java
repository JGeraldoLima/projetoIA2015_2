package main;

public class UltimaPosicao extends PosicaoFuga {
	
	Long tempoTiro = new Long(0);
	Long tempoProximoTiro = new Long(0);
	
	public UltimaPosicao(PosicaoFuga posicaoFuga){
		this.anguloFuga = posicaoFuga.getAnguloFuga();
		this.posicaoFuga = posicaoFuga.getPosicaoFuga();
		this.posicaoDisparo = posicaoFuga.getPosicaoTiro();
		this.tempoResistencia = posicaoFuga.getTempoResistencia();
	}
	
	public Long getTempoTiro() {
		return tempoTiro;
	}
	public void setTempoTiro(Long tempoTiro) {
		this.tempoTiro = tempoTiro;
	}
	public Long getTempoProximoTiro() {
		return tempoProximoTiro;
	}
	public void setTempoProximoTiro(Long tempoProximoTiro) {
		this.tempoProximoTiro = tempoProximoTiro;
	}
	
	
	@Override
	public String toString(){

		String retorno = posicaoDisparo+";"+posicaoFuga+";"+anguloFuga+";"+tempoResistencia+";"+tempoTiro+";"+tempoProximoTiro+"|";
			
		return retorno;
		
	}
	
	

}
