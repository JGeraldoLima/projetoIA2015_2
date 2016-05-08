package com.util;

import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.util.List;

import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;

public class CalculateHistogram {

	public CalculateHistogram(){}
	
	public void histogramImage(String file, int bins) {
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
