package com.util;

import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;

import com.features.training.TrainingBorder;

public class CalculateBorder {
	
	public static final float[] ROBERT_PRINCIPAL = { 0, 1, 0, 
													 0, 0, -1,
													 0, 0, 0};
	public static final float[] ROBERT_INVERSE = { 0, 1, 0, 
												   0, -1, 0,
												   0, 0, 0};
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
	public static final float[] FREICHEN_HORIZONTAL = { 1,   0,   -1,
            										1.414F, 0,   -1.414F,
            										1,   0,   -1};
	public static final float[] FREICHEN_VERTICAL = {-1,  -1.414F, -1,
            										  0,   0,    0,
            										  1,   1.414F,  1};
//	private int borderHorizontal = 0;
//	private int borderVertical = 0;
	
	public CalculateBorder() {}

	public void detectBorder(int dimension, float[] kernelMatrix, String f) {
		PlanarImage imagem = JAI.create("fileload", f);
		PlanarImage input = bandCombination(imagem);
		KernelJAI kernel = new KernelJAI(dimension, dimension, kernelMatrix);
		PlanarImage bordas = JAI.create("convolve", input, kernel);
		TrainingBorder.writeLine(bordas.toString());
		JFrame frame = new JFrame("Detecção de bordas");
		frame.add(new DisplayThreeSynchronizedImages(imagem, input, bordas));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public PlanarImage bandCombination(PlanarImage image){
		// Create a gray-level image with the weighted average of the three bands.
		double[][] matrix = {{ 0.114, 0.587, 0.299, 0 }};
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(image);
		pb.add(matrix);
		PlanarImage input = JAI.create("bandcombine", pb, null);
		return input;
	}
}
