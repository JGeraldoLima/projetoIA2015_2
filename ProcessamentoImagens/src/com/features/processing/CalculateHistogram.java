package com.features.processing;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;

import com.util.DisplayGrayHistogram;
import com.util.DisplayRGBHistogram;

public class CalculateHistogram {

	public CalculateHistogram() {
	}

	public void histogramGrayImage(String file, int bins) {
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
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

	public void histogramsRGBImage(String file, int bins) {
		PlanarImage image = JAI.create("fileload", file);
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(image);
		pb.add(null); // The ROI.
		pb.add(1); // Samplings.
		pb.add(1);
		pb.add(new int[] { bins }); // Num. bins.
		pb.add(new double[] { 0 }); // Min value to be considered.
		pb.add(new double[] { 256 }); // Max value to be considered.
		// Creates the histogram.
		PlanarImage temp = JAI.create("histogram", pb);
		Histogram h = (Histogram) temp.getProperty("histogram");
		// Creates the GUI to display three histogram components.
		JFrame frame = new JFrame("R,G,B Histograms");
		Container cp = frame.getContentPane();
		cp.setLayout(new GridLayout(3, 1));
		DisplayGrayHistogram cRed = new DisplayGrayHistogram(h, 0, "Red");
		DisplayGrayHistogram cGreen = new DisplayGrayHistogram(h, 1, "Green");
		DisplayGrayHistogram cBlue = new DisplayGrayHistogram(h, 2, "Blue");
		cRed.setBarColor(Color.RED);
		cRed.setMarksColor(Color.WHITE);
		cGreen.setBarColor(Color.GREEN);
		cGreen.setMarksColor(Color.WHITE);
		cBlue.setBarColor(Color.BLUE);
		cBlue.setMarksColor(Color.WHITE);
		// Set the width auto of histograms.
		cRed.setBinWidth((int) Math.pow(2, (10 - (Math.log(bins) / Math.log(2)))));
		cGreen.setBinWidth((int) Math.pow(2, (10 - (Math.log(bins) / Math.log(2)))));
		cBlue.setBinWidth((int) Math.pow(2, (10 - (Math.log(bins) / Math.log(2)))));
		// Use the same scale on the y-axis.
		int max = Math.max(cRed.getMaxCount(), Math.max(cGreen.getMaxCount(), cBlue.getMaxCount()));
		cRed.setMaxCount(max);
		cGreen.setMaxCount(max);
		cBlue.setMaxCount(max);
		cp.add(cRed);
		cp.add(cGreen);
		cp.add(cBlue);
		// Set the closing operation so the application is finished.
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); // Adjust the frame size using preferred dimensions.
		frame.setVisible(true); // Show the frame.
	}
	
	public void histogramRGBImage(String file, int bins) {
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

		JFrame f = new JFrame("Histograma RGB: " + file);
		DisplayRGBHistogram dh1 = new DisplayRGBHistogram(histo, String.valueOf((int)Math.pow(bins, 3)) + " bins");
		dh1.setBinWidth((int) Math.pow(2, (10 - (Math.log(bins) / Math.log(2)))));
		dh1.setHeight(160);
		dh1.setIndexMultiplier(8);
		f.getContentPane().add(dh1);
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

}
