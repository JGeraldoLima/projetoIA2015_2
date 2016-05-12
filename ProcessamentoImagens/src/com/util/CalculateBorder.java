package com.util;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;

public class CalculateBorder {
	
	public CalculateBorder() {}

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
	
	public static void borderVertical(String f) {
		PlanarImage imagem = JAI.create("fileload", f);
		float[] kernelMatrix = { -1, 0, 1, 2, 0, -2, -1, 0, 1 };
		KernelJAI kernel = new KernelJAI(3, 3, kernelMatrix);
		PlanarImage bordas = JAI.create("convolve", imagem, kernel);
		JFrame frame = new JFrame("Bordas verticais");
		frame.add(new DisplayTwoSynchronizedImages(imagem, bordas));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
