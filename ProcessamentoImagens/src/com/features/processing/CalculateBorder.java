package com.features.processing;

import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.IOException;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;

import com.features.training.TrainingBorder;
import com.util.DisplayFourSynchronizedImages;

public class CalculateBorder {
	
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
	
	public CalculateBorder() {}
	
	public void calculateBorders(int dimension, float[] kernelMatrix, String f) throws IOException{
		PlanarImage input = JAI.create("fileload", f);
		PlanarImage combined = bandCombination(input);
		PlanarImage borders  = detectBorder(combined, 3, kernelMatrix);
		PlanarImage binarized = binarizeImage(borders);
		TrainingBorder.writeLine(valuesPixelsWithJAI(binarized));
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
	 * Detect borders by convolve operation, using kernelMatrix mask.
	 * @param image
	 * @param dimension
	 * @param kernelMatrix
	 * @return
	 */
	public PlanarImage detectBorder(PlanarImage image, int dimension, float[] kernelMatrix) {
		KernelJAI kernel = new KernelJAI(dimension, dimension, kernelMatrix);
		PlanarImage bordas = JAI.create("convolve", image, kernel);
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
		pb.add(255.0);
		PlanarImage binarizada = JAI.create("binarize", pb);
		return binarizada;
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	public String valuesPixelsWithJAI(PlanarImage input) {
		BufferedImage image = input.getAsBufferedImage();
		int[] pixel = new int[3];
		int brancos = 0;
		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				pixel = getPixelData(image, w, h);
				if ((pixel[0] == 255.0D) && (pixel[1] == 255.0D) && (pixel[2] == 255.0D))
					brancos++;
			}
		}
		return String.valueOf(brancos);
	}
	
	/**
	 * 
	 * @param img
	 * @param x
	 * @param y
	 * @return
	 */
	private static int[] getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);

		int rgb[] = new int[] { (argb >> 16) & 0xff, // red
				(argb >> 8) & 0xff, // green
				(argb) & 0xff // blue
		};
		return rgb;
	}
}
