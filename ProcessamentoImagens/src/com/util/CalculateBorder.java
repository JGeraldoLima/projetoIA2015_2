package com.util;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;

import com.features.training.TrainingBorder;

public class CalculateBorder {
	
	public static final float[] ROBERT_PRINCIPAL = { 1, 0, 0, 
													 0, 1, 0, 
													 0, 0, 1 };
	public static final float[] ROBERT_INVERSE = { 0, 0, 1, 
												   0, 1, 0, 
												   1, 0, 0 };
	public static final float[] PREWITT_HORIZONTAL = { -1, -1, -1, 
														0, 0, 0, 
														1, 1, 1 };
	public static final float[] PREWITT_VERTICAL = { -1, 0, 1, 
													  1, 0, -1, 
													 -1, 0, 1 };
	public static final float[] SOBEL_HORIZONTAL = { -1, -2, -1, 
													  0, 0, 0, 
													  1, 2, 1 };
	public static final float[] SOBEL_VERTICAL = { -1, 0, 1, 
													2, 0, -2, 
													-1, 0, 1 };
//	private int borderHorizontal = 0;
//	private int borderVertical = 0;
	
	public CalculateBorder() {}

	public void detectBorder(float[] kernelMatrix, String f) {
		PlanarImage imagem = JAI.create("fileload", f);
		KernelJAI kernel = new KernelJAI(3, 3, kernelMatrix);
		PlanarImage bordas = JAI.create("convolve", imagem, kernel);
		TrainingBorder.writeLine(bordas.toString());
		JFrame frame = new JFrame("Bordas horizontais");
		frame.add(new DisplayTwoSynchronizedImages(imagem, bordas));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
