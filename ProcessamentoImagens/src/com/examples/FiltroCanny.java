package com.examples;
/***********************************************************************************

FiltroCanny.java - Implementa o filtro de Canny para detecção de bordas.

Direitos Autorais Reservados (c) 2008 Embrapa Informática Agropecuária

Este programa é software livre; você pode redistribuí-lo e/ou modificá-lo sob os
termos da Licença Pública Geral GNU conforme publicada pela Free Software
Foundation; tanto a versão 2 da Licença, como (a seu critério) qualquer versão
posterior.

Este programa é distribuído na expectativa de que seja útil, porém, SEM NENHUMA
GARANTIA; nem mesmo a garantia implícita de COMERCIABILIDADE OU ADEQUAÇÃO A UMA
FINALIDADE ESPECÍFICA. Consulte a Licença Pública Geral do GNU para mais detalhes.

Você deve ter recebido uma cópia da Licença Pública Geral do GNU junto com este
programa; se não, escreva para a Free Software Foundation, Inc., no endereço 59
Temple Street, Suite 330, Boston, MA 02111-1307 USA.

**********************************************************************************

Parte integrante do Comunicado Técnico 096/08.

Descrição:

	Implementa o filtro de detecção de bordas de Canny.
 
	A implementação do filtro tem por referência o artigo:

	CANNY, J. A computational approach to edge detection. IEEE Transactions on 
		Patterns Analysis and Machine Intelligence, 8(6): 679-698, 1986.

Parâmetros de entrada:

  1. imagem =>	nome do arquivo imagem.
	2. dp			=>	desvio padrão.
	3. inf		=>	limiar inferior.
	4. sup		=>	limiar superior.

Saída:

  Painel com as bordas da imagem filtrada.

Desenvolvido por:

    José Iguelmar Miranda & João Camargo Neto

Informações do CVS:
       $Source$:
       $Revision$:
       $Date$:

***********************************************************************************/

// pacotes genéricos
import java.io.File;

// pacotes do AWT
import java.awt.image.WritableRaster;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.GridLayout;

// pacote Swing para interface gráfica
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

// leitura em modo imediato - para J2SE 1.4+
import javax.imageio.ImageIO;

public class FiltroCanny extends JFrame {

	// Atributos
	// Escala e magnitude para imagens
	static double ORI_SCALE = 40.0;
	static double MAG_SCALE = 20.0;

	// Tamanho máximo da máscara do filtro
	static int MAX_MASK_SIZE = 20;

	// Fração de pixels que estarão acima do limiar de corte superior
	double ratio = 0.1;
	int LARGURA = 0;

  public static void main(String args[]) {

		// dp		=>	desvio padrão.
		// inf	=>	limiar inferior
		// sup	=>	limiar superior
		// 
		double dp = 1.0;
		int inf = 0, sup = 128; //alteração

		// Certifica a passagem dos parâmetros
//    if (args.length != 4) {
//			String msga = "Uso: java -cp . FiltroCanny <imagem> <dp> <inf> <sup>";
//			String msgb = "\n   dp => desvio padrao.";
//			String msgc = "\n   inf => limiar inferior.";
//			String msgd = "\n   sup => limiar superior.";
//			System.out.println(msga + msgb + msgc + msgd);
//      System.exit(0);
//    }

		// Mostra JFrame decorado pelo Swing
		JFrame.setDefaultLookAndFeelDecorated(true);

//    try {
//        dp = Double.parseDouble(args[1]);
//    } catch (NumberFormatException e) {
//        System.out.println("Valor do parametro <dp>, desvio padrao, invalido");
//        System.exit(0);
//    }
//
//    try {
//        inf = Integer.parseInt(args[2]);
//    } catch (NumberFormatException e) {
//        System.out.println("Valor do parametro <inf>, limiar inferior, invalido");
//        System.exit(0);
//    }
//
//    try {
//        sup = Integer.parseInt(args[3]);
//    } catch (NumberFormatException e) {
//        System.out.println("Valor do parametro <sup>, limiar superior, invalido");
//        System.exit(0);
//    }

    // Chama o construtor passando o nome do arquivo imagem
		if (dp <= 0.0)	
			dp = 1.0;
		if (inf < 0)	
			inf = 0;
		if (sup > 255)	
			sup = 255;

		long eq_time = System.currentTimeMillis();

		FiltroCanny fc = new FiltroCanny("../../../images/1.jpg", dp, inf, sup); //alteração 

    eq_time = System.currentTimeMillis() - eq_time;
		String msg = "Canny: tempo de execucao ";
    System.out.println(msg + eq_time + " milisseg.");

		// Encerra a aplicação clicando no "close"
		fc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fc.setVisible(true);
	}
  
	public FiltroCanny(String aFile, double s, int inf, int sup) {
		
		// Define objeto BufferedImage para encapsular a imagem
    BufferedImage imagem = null, imagemMagnitude = null, imagemOriginal = null;
		int w, h, tipo;
		JLabel img1;
      
    // Certifica que a imagem existe
    File file = new File(aFile);
    try {
        imagem = ImageIO.read(file);
    } catch(Exception e) {
        System.out.println("Imagem '" + aFile + "' nao existe.");
        System.exit(0);
    }

		String msga = "\nParametros para Canny:\n";
		String msgb = "==> limiar inferior " + inf + "\n";
		String msgc = "==> limiar superior " + sup + "\n";
		String msgd = "==> desvio padrao   " + s + "\n";
	  System.out.println(msga + msgb + msgc + msgd);

		// Atribui nome ao frame
    setTitle("Canny: " + file.getName());

		// Cria imagem local
		w = imagem.getWidth();
		h = imagem.getHeight();
    tipo = BufferedImage.TYPE_BYTE_GRAY;
    imagemMagnitude = new BufferedImage(w, h, tipo);
    imagemOriginal = new BufferedImage(w, h, tipo);
    WritableRaster imWR = imagem.getRaster();

		// Chama o método do filtro
		canny(s, imagem, imagemMagnitude, imagemOriginal);
    
		// Define pixels de borda usando valores limiares sup e inf
		linhasBordas(sup, inf, imagem, imagemMagnitude, imagemOriginal);

		for (int i = 0; i < LARGURA; i++)
			for (int j = 0; j < w; j++)
				imWR.setSample(j, i, 0, 255);

		for (int i = h - 1; i > h - 1 - LARGURA; i--)
			for (int j = 0; j < w; j++)
				imWR.setSample(j, i, 0, 255);

		for (int i = 0; i < h; i++)
			for (int j = 0; j < LARGURA; j++)
				imWR.setSample(j, i, 0, 255);

		for (int i = 0; i < h; i++)
			for (int j = w - LARGURA - 1; j < w; j++)
				imWR.setSample(j, i, 0, 255);

		// Cria gridLayout de 1 x 1
    getContentPane().setLayout(new GridLayout(1, 1));
		img1 = new JLabel(new ImageIcon(imagem));
		setSize(w, h);
		getContentPane().add(new JScrollPane(img1));
	}

	private void canny(double s, BufferedImage imagem, 
			BufferedImage imagemMag, BufferedImage imagemOrig) {
		
		int width = 0, k, n, nc, nr;
		double[][]	componenteX,	// Componente x da imagem original convolvida
															// com a função Gaussiana
								componenteY, 	// Componente y da imagem original convolvida
															// com a função Gaussiana
								derivadaX,		// Componente x da imagem convolvida 
															// (componenteX) com derivada da Gaussiana
								derivadaY;		// Componente y da imagem convolvida 
															// (componenteY) com derivada da Gaussiana
		double	funcGauss[], 			// Valores da função Gaussiana
						derivadaGauss[],	// Valores da primeira derivada da Gaussiana
						z;

		funcGauss = new double[MAX_MASK_SIZE];
		derivadaGauss = new double[MAX_MASK_SIZE];
		nc = imagem.getWidth();
		nr = imagem.getHeight();

		// Cria uma máscara do filtro Gaussiano e de sua derivada
		for (int i = 0; i < MAX_MASK_SIZE; i++) {
			funcGauss[i] = mediaGauss((double)i, s);
			if (funcGauss[i] < 0.005) {
				width = i;
				break;
			}
			derivadaGauss[i] = dGauss((double)i, s);
		}

		n = width + width + 1;
		LARGURA = (int)width/2;
		System.out.println("Suavizando com uma Gaussiana (largura = " +
												n + ") ...\n");

		componenteX = new double[nc][nr];
		componenteY = new double[nc][nr];

		// Convolução da imagem original com a máscara Gaussiana nas 
		// direções x e y.
		convolveImagemXY(imagem, funcGauss, width, componenteX, componenteY);

		// Convolve imagem suavizada com a derivada
		System.out.println("Convolucao com a derivada da Gaussiana...\n");
		derivadaX = convolveDerivadaXY(componenteX, nr, nc, derivadaGauss, width, 
																	 1);
		derivadaY = convolveDerivadaXY(componenteY, nr, nc, derivadaGauss, width, 
																	 0);

    WritableRaster magWR = imagemMag.getRaster();
		// Cria a imagem magnitude das derivadas de x e y (gradiente)
		for (int i = 0; i < nr; i++)
			for (int j = 0; j < nc; j++) {
	      z = norma(derivadaX[j][i], derivadaY[j][i]);
				magWR.setSample(j, i, 0, (int)z*MAG_SCALE);
			}

		// Suprime falsos máximos - pixels de borda deveriam ser máximo local.
		// Detalhes desta função no texto do Comunicado Técnico.
		removeFalsoMax(derivadaX, derivadaY, nr, nc, imagemMag, imagemOrig);
	}

	// Calcula média da função Gauss
	private double mediaGauss(double x, double sigma) {
		double z;

		z = (gauss(x,sigma)+gauss(x+0.5,sigma)+gauss(x-0.5,sigma))/3.0;
		z = z/(Math.PI*2.0*sigma*sigma);
		return z;
	}

	// Calcula valor da função Gaussiana
	private double gauss(double x, double sigma) {
    double expoente;

    if (sigma == 0)
			return 0.0;
    expoente = Math.exp(((-x*x)/(2*sigma*sigma)));
    return expoente;
	}

	// Calcula valor da primeira derivada da função Gaussiana
	private double dGauss(double x, double sigma) {
		return (-x/(sigma*sigma)*gauss(x, sigma));
	}

	// Realiza a convolução separadamente nas componentes x e y da imagem.
	private void convolveImagemXY(
		BufferedImage imagem, double[] funcGauss, int width, double[][] compX, 
		double[][] compY) {

		int i1, i2, nr, nc;
		double x, y;

		nc = imagem.getWidth();
		nr = imagem.getHeight();

    Raster imR = imagem.getRaster(); 
		for (int i = 0; i < nr; i++)
			for (int j = 0; j < nc; j++) {
				x = funcGauss[0]*imR.getSample(j, i, 0);
				y = funcGauss[0]*imR.getSample(j, i, 0);
				for (int k = 1; k < width; k++) {
					i1 = (i+k)%nr; 
					i2 = (i-k+nr)%nr;
		      y += funcGauss[k]*imR.getSample(j, i1, 0) + 
							 funcGauss[k]*imR.getSample(j, i2, 0);
		      i1 = (j+k)%nc; 
					i2 = (j-k+nc)%nc;
		      x += funcGauss[k]*imR.getSample(i1, i, 0) + 
							 funcGauss[k]*imR.getSample(i2, i, 0);
				}
				compX[j][i] = x; 
				compY[j][i] = y;
			}
	}

	// Realiza a convolução nas derivadas das componentes x e y 
	// da imagem convolvida.
	private double[][] convolveDerivadaXY(double[][] imagem, int nr, 
		int nc, double[] funcGauss, int width, int compXY) {

		int i1, i2;
		double x;
		double[][] componente = new double[nc][nr];

		for (int i = 0; i < nr; i++)
			for (int j = 0; j < nc; j++) {
				x = 0.0;
				for (int k = 1; k < width; k++) {
					switch (compXY){
						case 0:						// componente y
							i1 = (i+k)%nr;
							i2 = (i-k+nr)%nr;
							x += -funcGauss[k]*imagem[j][i1] + funcGauss[k]*imagem[j][i2];
							break;
						case 1:						// componente x
							i1 = (j+k)%nc; 
							i2 = (j-k+nc)%nc;
							x += -funcGauss[k]*imagem[i1][i] + funcGauss[k]*imagem[i2][i];
							break;
					}
				}
				componente[j][i] = x;
			}
		return componente;
	}

	// Suprime falsos máximos - pixels de borda deveriam ser máximo local
	// Detalhes desta função no texto do Comunicado Técnico
	private void removeFalsoMax(double[][] derivX, double[][] derivY, 
		int nr, int nc, BufferedImage imagemMag, BufferedImage imagemOrig) {

		int k, n, m, top, bottom, left, right;
		double xx, yy, grad1, grad2, grad3, grad4, gradiente, compX, compY;

		nc = imagemMag.getWidth();
		nr = imagemMag.getHeight();

    WritableRaster magWR = imagemMag.getRaster();
    WritableRaster oriWR = imagemOrig.getRaster();

		for (int i = 1; i < nr - 1; i++) {
			for (int j = 1; j < nc - 1; j++) {
				magWR.setSample(j, i, 0, 0);

				// Derivadas de x e y são componentes de um vetor (gradiente)
				compX = derivX[j][i];
				compY = derivY[j][i];
				if (Math.abs(compX) < 0.01 && Math.abs(compY) < 0.01) 
					continue;
		    gradiente  = norma(compX, compY);

				// Segue a direção do gradiente, vetor (compX, compY).
				// Mantém pixels da borda (máximo local).
				if (Math.abs(compY) > Math.abs(compX)) {
					// Primeiro caso: componente y é maior. Direção do gradiente é
					//								para cima ou para baixo.
					xx = Math.abs(compX)/Math.abs(compY);
					yy = 1.0;
					grad2 = norma(derivX[j][i-1], derivY[j][i-1]);
					grad4 = norma(derivX[j][i+1], derivY[j][i+1]);
					if (compX*compY > 0.0) {
						grad1 = norma(derivX[j-1][i-1], derivY[j-1][i-1]);
						grad3 = norma(derivX[j+1][i+1], derivY[j+1][i+1]);
					} else {
						grad1 = norma(derivX[j+1][i-1], derivY[j+1][i-1]);
						grad3 = norma(derivX[j-1][i+1], derivY[j-1][i+1]);
					}
				} else {
					// Segundo caso: componente x é maior. Direção do gradiente é
					//							 para esquerda ou direita.
					xx = Math.abs(compY)/Math.abs(compX);
					yy = 1.0;
					grad2 = norma(derivX[j+1][i], derivY[j+1][i]);
					grad4 = norma(derivX[j-1][i], derivY[j-1][i]);
					if (compX*compY > 0.0) {
						grad1 = norma(derivX[j+1][i+1], derivY[j+1][i+1]);
						grad3 = norma(derivX[j-1][i-1], derivY[j-1][i-1]);
					} else {
						grad1 = norma(derivX[j+1][i-1], derivY[j+1][i-1]);
						grad3 = norma(derivX[j-1][i+1], derivY[j-1][i+1]);
					}
				}

				// Calcula o valor interpolado da magnitude do gradiente.
				if ((gradiente > (xx*grad1 + (yy-xx)*grad2)) &&
					  (gradiente > (xx*grad3 + (yy-xx)*grad4))) {
					if (gradiente*MAG_SCALE <= 255)
						magWR.setSample(j, i, 0, (int)gradiente*MAG_SCALE);
					else
						magWR.setSample(j, i, 0, 255);
					oriWR.setSample(j, i, 0, (int)Math.atan2(compY, compX)*ORI_SCALE);
				} else {
					magWR.setSample(j, i, 0, 0);
					oriWR.setSample(j, i, 0, 0);
				}
			}
		}
	}

	// Define pixels de borda.
	private void linhasBordas(int sup, int inf, BufferedImage imagem, 
		BufferedImage imagemMag, BufferedImage imagemOrig) {

		int nr, nc;

		nc = imagem.getWidth();
		nr = imagem.getHeight();
    WritableRaster imWR = imagem.getRaster();
    Raster imR = imagem.getRaster(); 
    Raster magR = imagemMag.getRaster(); 
	
		System.out.println("Iniciando corte com limiares...\n");
		for (int i = 0; i < nr; i++)
			for (int j = 0; j < nc; j++)
				imWR.setSample(j, i, 0, 0);

		if (sup < inf) {
			estimaLimiar(imagemMag, sup, inf);
			String str = "Limiar de corte (da imagem): SUPERIOR ";
			System.out.println(str + sup +  " INFERIOR\n" + inf);
		}

		// Para cada borda com magnitude acima do limiar superior, desenha
		// pixels de borda que estão acima do limiar inferior.
		for (int i = 0; i < nr; i++)
			for (int j = 0; j < nc; j++)
				if (magR.getSample(j, i, 0) >= sup)
					trace(i, j, inf, imagem, imagemMag, imagemOrig);

		// Torna a borda em preto
		for (int i = 0; i < nr; i++)
			for (int j = 0; j < nc; j++)
				if (imR.getSample(j, i, 0) == 0)
					imWR.setSample(j, i, 0, 255);
				else
					imWR.setSample(j, i, 0, 0);
	}

	// Estima o limiar superior
	private void estimaLimiar(BufferedImage imagemMag, int hi, int inf) {

		int histograma[], count, nr, nc, i, j, k;

		nc = imagemMag.getWidth();
		nr = imagemMag.getHeight();
		histograma = new int[256];
    Raster magR = imagemMag.getRaster(); 

		// Histograma da imagem
		for (k = 0; k < 256; k++)
			histograma[k] = 0;

		for (i = LARGURA; i < nr - LARGURA; i++)
			for (j = LARGURA; j < nc - LARGURA; j++)
				histograma[magR.getSample(j, i, 0)]++;

		// O limiar superior deveria ser maior que 80 ou 90% dos pixels 
		j = nr;
		if (j < nc)
			j = nc;
		j = (int)(0.9*j);
		k = 255;

		count = histograma[255];
		while (count < j) {
		  k--;
		  if (k < 0)
				break;
			count += histograma[k];
		}

		hi = k;
		i = 0;
		while (histograma[i] == 0)
			i++;
		inf = (int)(hi+i)/2;
	}

	// Traça, recursivamente, os pixels da borda.
	private int trace(int i, int j, int inf, BufferedImage imagem,
		BufferedImage imagemMag, BufferedImage imagemOrig) {

		int n, m;
		int flag = 0;

    Raster magR = imagemMag.getRaster();
    Raster imR = imagem.getRaster();
    WritableRaster imWR = imagem.getRaster();

		if (imR.getSample(j, i, 0) == 0) {
			imWR.setSample(j, i, 0, 255);
		  flag = 0;
		  for (n = -1; n <= 1; n++) {
		    for(m = -1; m <= 1; m++) {
					if (i == 0 && m == 0)
						continue;
					if ((range(imagemMag, i+n, j+m) == 1) && 
							(magR.getSample(j+m, i+n, 0)) >= inf)
						if (trace(i+n, j+m, inf, imagem, imagemMag, imagemOrig) == 1)	{
							flag = 1;
							break;
						}
				}
				if (flag == 1)
					break;
			}
			return 1;
		}
		return 0;
	}

	// Certifica que um pixel pertence à imagem
	private int range(BufferedImage imagem, int i, int j) {
		int nc, nr;

		nc = imagem.getWidth();
		nr = imagem.getHeight();
		if ((i < 0) || (i >= nr))
			return 0;
		if ((j < 0) || (j >= nc))
			return 0;
		return 1;
	}

	// Calcula a norma ou magnitude, do gradiente.
	private double norma(double x, double y) {
		return Math.sqrt(x*x + y*y);	
	}

}
