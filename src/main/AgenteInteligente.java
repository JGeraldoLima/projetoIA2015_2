package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AgenteInteligente {
	double enemyX;
	double enemyY;
	boolean podeMovimentar = true;
	double velocidadeMovimentoInimigoX = 0;
	double velocidadeMovimentoInimigoY = 0;
	
	private final int ANGULO_MOVIMENTACAO = 10;
	private final int NUM_ULTIMAS_ACOES = 10;
	public static List<PosicaoFuga> listaPosicoesFuga = new ArrayList<PosicaoFuga>();
	public static UltimaPosicao ultimaPosicao;

	public MovimentoRobo movimentoConstante() {
		MovimentoRobo movimentoConstante = new MovimentoRobo();
		int fatorX = 1;
		int fatorY = 1;

		if (enemyX > 500) {
			fatorX = -1;
		}
		if (enemyY > 300) {
			fatorY = -1;
		}

		if (velocidadeMovimentoInimigoX < 5 && velocidadeMovimentoInimigoX >= 0) {
			movimentoConstante.direita = 0;
		} else if (velocidadeMovimentoInimigoX > 10) {
			movimentoConstante.direita = fatorX * 30;
		} else if (velocidadeMovimentoInimigoX > 30) {
			movimentoConstante.direita = fatorX * 50;
		}else{
			movimentoConstante.frente = fatorX * 70;
		}

		if (velocidadeMovimentoInimigoY < 5 && velocidadeMovimentoInimigoY >= 0) {
			movimentoConstante.frente = 0;
		} else if (velocidadeMovimentoInimigoY > 10) {
			movimentoConstante.frente = fatorY * 30;
		} else if (velocidadeMovimentoInimigoY > 30) {
			movimentoConstante.frente = fatorY * 50;
		}else{
			movimentoConstante.frente = fatorY * 70;
		}
		
		System.out.println("Velocidade: " + velocidadeMovimentoInimigoX);
		return movimentoConstante;
	}

	public void updatePosInimigo(double posX, double posY) {
		
		velocidadeMovimentoInimigoX = enemyX - posX;
		velocidadeMovimentoInimigoY = enemyY - posY;

		enemyX = posX;
		enemyY = posY;

	}

	public TaticaDisparo tiroPerfeito(double distancia, double energia) {
		if (energia < 20) {
			return new TaticaDisparo(1, 1);
		}

		if (distancia < 180) {
			return new TaticaDisparo(3, 3);
		} else if (distancia < 250) {
			return new TaticaDisparo(2, 3);
		} else if (distancia < 300) {
			return new TaticaDisparo(1, 2);
		} else if (distancia < 550) {
			if (velocidadeMovimentoInimigoX < 5) {
				return new TaticaDisparo(2, 3);
			}
			return new TaticaDisparo(2, 1);
		} else if (distancia < 700) {
			if (velocidadeMovimentoInimigoX < 5) {
				return new TaticaDisparo(2, 3);
			}
			return new TaticaDisparo(1, 1);
		} else if (distancia < 900) {
			if (velocidadeMovimentoInimigoX < 5) {
				return new TaticaDisparo(2, 3);
			}
			return new TaticaDisparo(1, 1);
		}
		return new TaticaDisparo(1, 1);
	}

	public double normalRelativeAngle(double angle) {
		if (angle > -180 && angle <= 180) {
			return angle;
		}

		double fixedAngle = angle;
		while (fixedAngle <= -180) {
			fixedAngle += 360;
		}

		while (fixedAngle > 180) {
			fixedAngle -= 360;
		}

		return fixedAngle;
	}

	public double mira(double Adv, double dirTanque, double dirMetralhadora) {
		double anguloTiro = dirTanque + Adv - dirMetralhadora;
		// o ângulo da direcção do nosso robô em relação ao norte, menos o
		// ângulo do nosso radar (é o mesmo do canhão) em relação a direcção
		// para onde estamos virados,
		// mais o ângulo do robô que acabámos de detectar, em relação à direcção
		// para onde o nosso robô está virado.
		
		if (!(anguloTiro > -180 && anguloTiro <= 180)) {
			while (anguloTiro <= -180) {
				anguloTiro += 360;
			}
			while (anguloTiro > 180) {
				anguloTiro -= 360;
			}
		}
		// System.out.println("Posicao da metranca nova: " + getGunHeading());
		// System.out.println("ADV: " + Adv);
		// System.out.println("Hradig: " + getHeading());
		return anguloTiro;
	}

	// feito por Hudson
	public MovimentoRobo foiAtingido(double anguloBala) throws Exception {

		int anguloAtingido = 0;

		if (anguloBala >= 90 && anguloBala < 180) {
			anguloAtingido = 0;
		} else if (anguloBala >= 0 && anguloBala < 90) {
			anguloAtingido = 1;
		} else if (anguloBala < 0 && anguloBala >= -90) {
			anguloAtingido = 2;
		} else if (anguloBala < -90 && anguloBala >= -180) {
			anguloAtingido = 3;
		}
		
		if (listaPosicoesFuga.isEmpty()) {
			try {
				listaPosicoesFuga = ControladorArquivo.lerArquivo();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (listaPosicoesFuga.isEmpty()) {
			
			for (int anguloTiro = 0; anguloTiro < 4; anguloTiro++) {
				for(int ladoFuga = 0; ladoFuga < 4; ladoFuga++){
					for (int h = 0; h < 19; h++) {
						int anguloMovimento = h * ANGULO_MOVIMENTACAO;
						PosicaoFuga posicaoFuga = new PosicaoFuga();
						
						posicaoFuga.setAnguloFuga(anguloMovimento);
						posicaoFuga.setTempoResistencia(new Double(0));
						posicaoFuga.setPosicaoTiro(anguloTiro);
						posicaoFuga.setPosicaoFuga(ladoFuga);
						listaPosicoesFuga.add(posicaoFuga);
						
					}
				}
			}
			ControladorArquivo.gravarArquivo(listaPosicoesFuga);
		}
		
		if (ultimaPosicao != null) {
			ultimaPosicao.setTempoProximoTiro(Calendar.getInstance().getTimeInMillis());
			
			for (PosicaoFuga posicaoFuga : listaPosicoesFuga) {
				if (posicaoFuga.getAnguloFuga() == ultimaPosicao.getAnguloFuga()
						&& posicaoFuga.getPosicaoFuga() == ultimaPosicao.getPosicaoFuga()
						&& posicaoFuga.getPosicaoTiro() == ultimaPosicao.getPosicaoTiro()) {
					Double tempoResistencia = ((((double) (ultimaPosicao.getTempoProximoTiro() - ultimaPosicao.getTempoTiro()) / 1000)) + posicaoFuga.getTempoResistencia()) / 2;
					
					posicaoFuga.setTempoResistencia(tempoResistencia);
					break;
					
				}
			}
		}
	
		return escolheMelhorAngulo(anguloAtingido);

	}

	public MovimentoRobo escolheMelhorAngulo(int posicaoTiro) {


		PosicaoFuga melhorPosicaoFuga= new PosicaoFuga();
		boolean haPosicoesNaoTestadas = false;
		
		if (!listaPosicoesFuga.isEmpty()) {
			for (PosicaoFuga posicaoFuga : listaPosicoesFuga) {
				if (posicaoFuga.getPosicaoTiro() == posicaoTiro) {
					if (posicaoFuga.getTempoResistencia().doubleValue() == 0) {
						melhorPosicaoFuga = posicaoFuga.clone();	
						haPosicoesNaoTestadas = true;	
						ultimaPosicao = new UltimaPosicao(posicaoFuga);
						ultimaPosicao.setTempoTiro(Calendar.getInstance().getTimeInMillis());
						break;
					}
				}
			}
		}
		
		if (!haPosicoesNaoTestadas) {
			melhorPosicaoFuga = getMaiorResistencia(posicaoTiro);
			ultimaPosicao = new UltimaPosicao(melhorPosicaoFuga);
			ultimaPosicao.setTempoTiro(Calendar.getInstance().getTimeInMillis());
		}
		

		switch (melhorPosicaoFuga.getPosicaoFuga()) {
		case 0:
			return new MovimentoRobo(100, 0, 0, melhorPosicaoFuga.getAnguloFuga());
		case 1:
			return new MovimentoRobo(100, 0, melhorPosicaoFuga.getAnguloFuga(), 0);
		case 2:
			return new MovimentoRobo(0, 100, 0, melhorPosicaoFuga.getAnguloFuga());
		case 3:
			return new MovimentoRobo(0, 100, melhorPosicaoFuga.getAnguloFuga(), 0);
		default:
			return new MovimentoRobo(100, 0, 0, 0);
		}

	}
	
	public PosicaoFuga getMaiorResistencia(int posicaoTiro){
		Random random = new Random();
		PosicaoFuga melhorPosicao = null;
		for (PosicaoFuga posicaoFuga : listaPosicoesFuga) {
			if (posicaoFuga.getPosicaoTiro() == posicaoTiro) {
				if (melhorPosicao == null) {
					melhorPosicao = posicaoFuga.clone();
				}
				if (posicaoFuga.getTempoResistencia().doubleValue() > melhorPosicao.getTempoResistencia().doubleValue()) {
					melhorPosicao = posicaoFuga.clone();
					
				}else if (posicaoFuga.getTempoResistencia().doubleValue() ==  melhorPosicao.getTempoResistencia().doubleValue()) {					
					int valor = random.nextInt(2);
					if (valor == 0) {
						melhorPosicao = posicaoFuga.clone();
					}
				}
			}
		}
		
		return melhorPosicao;
		
		
	}

}
