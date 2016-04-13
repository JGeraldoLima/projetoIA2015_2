package main;

import java.io.IOException;
import java.util.Calendar;

//import java.awt.Color;
import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.RobotStatus;
import robocode.RoundEndedEvent;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Mario - a robot by (your name here)
 */

public class Mario extends AdvancedRobot {

	boolean podeAtualizarPos = false;
	private double enemyLife = 0;
	int tempoParaAtualizarPosicao = 0;
	private RobotStatus robotStatus;

	AgenteInteligente agenteInteligente = new AgenteInteligente();

	/**
	 * run: MegaMan's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop
		while (true) {
			tempoParaAtualizarPosicao++;
			if (tempoParaAtualizarPosicao >= 5) {
				tempoParaAtualizarPosicao = 0;
				podeAtualizarPos = true;
			}
			// Replace the next 4 lines with any behavior you would like
			movimento();

		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */

	public void onStatus(StatusEvent e) {
		this.robotStatus = e.getStatus();
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		disparar(e);

		if (podeAtualizarPos) {
			double angleToEnemy = e.getBearing();

			// Calculate the angle to the scanned robot
			double angle = Math
					.toRadians((robotStatus.getHeading() + angleToEnemy % 360));

			// Calculate the coordinates of the robot
			double posX = (robotStatus.getX() + Math.sin(angle)
					* e.getDistance());
			double posY = (robotStatus.getY() + Math.cos(angle)
					* e.getDistance());
			agenteInteligente.updatePosInimigo(posX, posY);
			podeAtualizarPos = false;
		}
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		
		MovimentoRobo movimento;
		try {
			agenteInteligente.podeMovimentar = false;
			movimento = agenteInteligente.foiAtingido(e.getBearing());
			turnRight(movimento.direita);
			turnLeft(movimento.esquerda);
			back(movimento.tras);
			ahead(movimento.frente);
			agenteInteligente.podeMovimentar = true;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			agenteInteligente.podeMovimentar = true;
			e1.printStackTrace();
		}

	}

	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		bateuParede(e);
	}

	public void movimento() { 

		System.out.println("Ppode movimentar: " + agenteInteligente.podeMovimentar);
		if (agenteInteligente.podeMovimentar) {
			MovimentoRobo movimento = agenteInteligente.movimentoConstante();
			
			turnRight(movimento.direita);
			turnLeft(movimento.esquerda);
			back(movimento.tras);
			ahead(movimento.frente);
		}

		turnGunLeft(360);

	}

	public void disparar(ScannedRobotEvent e) { 
		enemyLife = e.getEnergy();
		turnGunRight(agenteInteligente.mira(e.getBearing(), getHeading(), getGunHeading()));
		TaticaDisparo tatica = agenteInteligente.tiroPerfeito(e.getDistance(),
				getEnergy());
		for (int i = 0; i < tatica.quantidade; i++) {
			fire(tatica.potencia);
		}

	}

	public void bateuParede(HitWallEvent e) { 
		// back(500);
		// turnRight(90);
		// ahead(100);
	}
	
	
	public void onRoundEnded(RoundEndedEvent event){
	
		if (AgenteInteligente.ultimaPosicao != null) {
			AgenteInteligente.ultimaPosicao.setTempoProximoTiro(Calendar.getInstance().getTimeInMillis());
			
			for (PosicaoFuga posicaoFuga : AgenteInteligente.listaPosicoesFuga) {
				if (posicaoFuga.getAnguloFuga() == AgenteInteligente.ultimaPosicao.getAnguloFuga()
						&& posicaoFuga.getPosicaoFuga() == AgenteInteligente.ultimaPosicao.getPosicaoFuga()
						&& posicaoFuga.getPosicaoTiro() == AgenteInteligente.ultimaPosicao.getPosicaoTiro()) {
					Double tempoResistencia = ((((double) (AgenteInteligente.ultimaPosicao.getTempoProximoTiro() - AgenteInteligente.ultimaPosicao.getTempoTiro()) / 1000)) + posicaoFuga.getTempoResistencia()) / 2;
					
					posicaoFuga.setTempoResistencia(tempoResistencia);
					break;
					
				}
			}
		}
		
		try {
			ControladorArquivo.gravarArquivo(AgenteInteligente.listaPosicoesFuga);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}   

	

}
