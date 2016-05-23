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
	
	public void calculateBorders(int dimension, float[] kernelMatrix, String f){
		PlanarImage input = JAI.create("fileload", f);
		PlanarImage combined = bandCombination(input);
		PlanarImage borders  = detectBorder(combined, 3, kernelMatrix);
		PlanarImage binarized = binarizeImage(borders);
		JFrame frame = new JFrame("Detecção de bordas");
		frame.add(new DisplayFourSynchronizedImages(input, combined, borders, binarized));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Create a gray-level image with the weighted average of the three bands.
	 * @param image
	 * @return
	 */
	public PlanarImage bandCombination(PlanarImage image){
		double[][] matrix = {{ 0.114, 0.587, 0.299, 0 }};
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(image);
		pb.add(matrix);
		PlanarImage combined = JAI.create("bandcombine", pb, null);
		return combined;
	}

	/**
	 * 
	 * @param image
	 * @param dimension
	 * @param kernelMatrix
	 * @return
	 */
	public PlanarImage detectBorder(PlanarImage image, int dimension, float[] kernelMatrix) {
		KernelJAI kernel = new KernelJAI(dimension, dimension, kernelMatrix);
		PlanarImage bordas = JAI.create("convolve", image, kernel);
//		TrainingBorder.writeLine(bordas.toString());
		return bordas;
	}
	
	/**
	 * 
	 * @param image
	 * @return
	 */
	public PlanarImage binarizeImage(PlanarImage image) {
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(image);
		pb.add(255.0D);
		PlanarImage binarizada = JAI.create("binarize", pb);
		return binarizada;
	}
}
