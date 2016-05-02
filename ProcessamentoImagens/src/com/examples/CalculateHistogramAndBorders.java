package com.examples;

import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.util.List;

import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;

import com.util.DisplayHistogram;

public class CalculateHistogramAndBorders {

	private static File[] files = new File[] { new File("images/1.jpg"), new File("images/01.jpg"),
			new File("images/02.jpg"), new File("images/03.jpg"), new File("images/04.jpg"), new File("images/05.jpg"),
			new File("images/06.jpg"), new File("images/07.jpg"), new File("images/08.jpg"), new File("images/09.jpg"),
			new File("images/10.jpg") };

	public static void main(String[] args) {
		borderHorizontal(files[2].getPath());
		histogramImage(files[2].getPath(), 32);
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
		DisplayHistogram dh1 = new DisplayHistogram(histo, String.valueOf(bins) + " bins");
		dh1.setBinWidth((int) Math.pow(2, (10 - (Math.log(bins) / Math.log(2)))));
		dh1.setHeight(160);
		dh1.setIndexMultiplier(8);
		f.getContentPane().add(dh1);
		//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

}
