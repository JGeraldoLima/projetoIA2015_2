package com.util;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;

import com.features.training.TrainingBorder;

public class CalculateLines {
	
	public static final float[] HORIZONTAL_LINES = { -1, -1, -1, 
													2, 2, 2, 
													-1, -1, -1 };
	public static final float[] POSITIVE_DIAGONAL_LINES = { -1, -1, 2, 
															-1, 2, -1, 
															2, -1, -1 };
	public static final float[] VERTICAL_LINES = { -1, 2, -1, 
													-1, 2, -1, 
													-1, 2, -1 };
	public static final float[] NEGATIVE_DIAGONAL_LINES = { 2, -1, -1, 
															-1, 2, -1, 
															-1, -1, 2 };
//	private int borderHorizontal = 0;
//	private int borderVertical = 0;
	
	public CalculateLines() {}

	public void detectLines(float[] kernelMatrix, String f) {
		PlanarImage imagem = JAI.create("fileload", f);
		KernelJAI kernel = new KernelJAI(3, 3, kernelMatrix);
		PlanarImage linhas = JAI.create("convolve", imagem, kernel);
		TrainingBorder.writeLine(linhas.toString());
		JFrame frame = new JFrame("Detecção de linhas");
		frame.add(new DisplayTwoSynchronizedImages(imagem, linhas));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
