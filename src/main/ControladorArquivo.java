package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import robocode.RobocodeFileOutputStream;

public class ControladorArquivo {
	
	
	public ControladorArquivo(){
		
	}
	
	
	public static List<PosicaoFuga> lerArquivo() throws Exception{
		
		File arquivo = new File("../main/posicoesDeFuga.txt");
		List<PosicaoFuga> posicoesFuga = new ArrayList<PosicaoFuga>();
		if (arquivo.exists()) {
			Scanner s = new Scanner(arquivo);
			Pattern pattern = Pattern.compile("\\d+;\\d+;\\d+;\\d+\\.\\d+\\|");
			while(s.hasNext()){
				String posicao = s.next(pattern);
				String[] posicoes = posicao.split(";");
				PosicaoFuga posicaoFuga = new PosicaoFuga();
				posicaoFuga.setPosicaoTiro(Integer.parseInt(posicoes[0]));
				posicaoFuga.setPosicaoFuga(Integer.parseInt(posicoes[1]));
				posicaoFuga.setAnguloFuga(Integer.parseInt(posicoes[2]));
				posicaoFuga.setTempoResistencia(Double.parseDouble(posicoes[3].substring(0, posicoes[3].length()-1)));
				posicoesFuga.add(posicaoFuga);
			}
			s.close();
		}

		return posicoesFuga;
		
	}
	
	
	public static void gravarArquivo(List<PosicaoFuga> posicoesFuga) throws IOException{
		
		File arquivo = new File("../main/posicoesDeFuga.txt");
		RobocodeFileOutputStream fos = null;
		try {
			fos = new RobocodeFileOutputStream(arquivo);
			for (PosicaoFuga posicaoFuga : posicoesFuga) {
				fos.write(posicaoFuga.toString().getBytes());
				//fos.write("\n".getBytes());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fos.close();
		
		
	}
	

}
