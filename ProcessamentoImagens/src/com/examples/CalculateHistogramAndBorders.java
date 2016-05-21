package com.examples;

import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.util.List;

import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;

import com.util.DisplayGrayHistogram;

public class CalculateHistogramAndBorders {

	private static File[] files = new File[] {
			new File("images/1.jpg"),
			new File("images/01.jpg"),
			new File("images/02.jpg"),
			new File("images/03.jpg"),
			new File("images/04.jpg"),
			new File("images/05.jpg"),
			new File("images/06.jpg"),
			new File("images/07.png"),
			new File("images/08.jpg"),
			new File("images/09.jpg"),
			new File("images/10.jpg")};
	private static File[] filesFesta = new File[] {
			new File("images/festa/01.jpg"),
			new File("images/festa/02.jpg"),
			new File("images/festa/03.jpg"),
			new File("images/festa/04.jpg"),
			new File("images/festa/05.jpg"),
			new File("images/festa/06.jpg"),
			new File("images/festa/07.jpg"),
			new File("images/festa/08.jpg"),
			new File("images/festa/09.jpg"),
			new File("images/festa/10.jpg"),
			new File("images/festa/11.jpg"),
			new File("images/festa/12.jpg"),
			new File("images/festa/13.jpg"),
			new File("images/festa/14.jpg"),
			new File("images/festa/15.jpg"),
			new File("images/festa/16.jpg"),
			new File("images/festa/17.jpg"),
			new File("images/festa/18.jpg"),
			new File("images/festa/19.jpg"),
			new File("images/festa/20.jpg"),
			new File("images/festa/21.jpg"),
			new File("images/festa/22.jpg"),
			new File("images/festa/23.jpg"),
			new File("images/festa/24.jpg"),
			new File("images/festa/25.jpg"),
			new File("images/festa/26.jpg"),
			new File("images/festa/27.jpg"),
			new File("images/festa/28.jpg"),
			new File("images/festa/29.jpg"),
			new File("images/festa/30.jpg"),
			new File("images/festa/31.jpg"),
			new File("images/festa/32.jpg"),
			new File("images/festa/33.jpg"),
			new File("images/festa/34.jpg"),
			new File("images/festa/35.jpg"),
			new File("images/festa/36.jpg"),
			new File("images/festa/37.jpg"),
			new File("images/festa/38.jpg"),
			new File("images/festa/39.jpg"),
			new File("images/festa/40.jpg"),
			new File("images/festa/41.jpg")};
	private static File[] filesNonFesta = new File[] {
			new File("images/non_festa/01.jpg"),
			new File("images/non_festa/02.jpg"),
			new File("images/non_festa/03.jpg"),
			new File("images/non_festa/04.jpg"),
			new File("images/non_festa/05.jpg"),
			new File("images/non_festa/06.jpg"),
			new File("images/non_festa/07.jpg"),
			new File("images/non_festa/08.jpg"),
			new File("images/non_festa/09.JPG"),
			new File("images/non_festa/10.jpg"),
			new File("images/non_festa/11.jpg"),
			new File("images/non_festa/12.jpg"),
			new File("images/non_festa/13.jpg"),
			new File("images/non_festa/14.jpg"),
			new File("images/non_festa/15.jpg"),
			new File("images/non_festa/16.jpg"),
			new File("images/non_festa/17.jpg"),
			new File("images/non_festa/18.jpg"),
			new File("images/non_festa/19.jpg"),
			new File("images/non_festa/20.jpg"),
			new File("images/non_festa/21.jpg"),
			new File("images/non_festa/22.jpg"),
			new File("images/non_festa/23.jpg"),
			new File("images/non_festa/24.jpg"),
			new File("images/non_festa/25.jpg"),
			new File("images/non_festa/26.png"),
			new File("images/non_festa/27.jpg"),
			new File("images/non_festa/28.jpg"),
			new File("images/non_festa/29.jpg"),
			new File("images/non_festa/30.jpg"),
			new File("images/non_festa/31.jpg"),
			new File("images/non_festa/32.jpg"),
			new File("images/non_festa/33.jpg"),
			new File("images/non_festa/34.jpg"),
			new File("images/non_festa/35.jpg"),
			new File("images/non_festa/36.jpg"),
			new File("images/non_festa/37.JPG"),
			new File("images/non_festa/38.jpg"),
			new File("images/non_festa/39.jpg"),
			new File("images/non_festa/40.jpg"),
			new File("images/non_festa/41.jpg")
			};

	public static void main(String[] args) {
		System.out.println("imagens misturadas");
		for (File file : files) {
//			borderHorizontal(file.getPath());
			histogramImage(file.getPath(), 64);
		}
		System.out.println("imagens festa");
		for (File fileFesta : filesFesta) {
			histogramImage(fileFesta.getPath(), 64);
		}
		System.out.println("imagens non_festa");
		for (File fileNonFesta : filesNonFesta) {
			histogramImage(fileNonFesta.getPath(), 64);
		}

	}

	public static void borderHorizontal(String f) {
		PlanarImage imagem = JAI.create("fileload", f);
		float[] kernelMatrix = { -1, -2, -1, 0, 0, 0, 1, 2, 1 };
		KernelJAI kernel = new KernelJAI(3, 3, kernelMatrix);
		PlanarImage bordas = JAI.create("convolve", imagem, kernel);
		JFrame frame = new JFrame("Bordas horizontais");
		frame.add(new DisplayTwoSynchronizedImages(imagem, bordas));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void histogramImage(String file, int bins) {
		PlanarImage image = JAI.create("fileload", file);

		ParameterBlock pb1 = new ParameterBlock();
		pb1.addSource(image);
		pb1.add(null);
		pb1.add(1);
		pb1.add(1);
		pb1.add(new int[] { bins });
		pb1.add(new double[] { 0 });
		pb1.add(new double[] { 256 });

		PlanarImage dummyImage = JAI.create("histogram", pb1);
		Histogram histo = (Histogram) dummyImage.getProperty("histogram");

		JFrame f = new JFrame("Histograma: " + file);
		DisplayGrayHistogram dh1 = new DisplayGrayHistogram(histo, 0, String.valueOf(bins) + " bins");
		dh1.setBinWidth((int) Math.pow(2, (10 - (Math.log(bins) / Math.log(2)))));
		dh1.setHeight(160);
		dh1.setIndexMultiplier(8);
		f.getContentPane().add(dh1);
		//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

}
