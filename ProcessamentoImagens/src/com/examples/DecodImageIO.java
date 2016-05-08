package com.examples;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.Histogram;
import javax.media.jai.InterpolationBilinear;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;
import com.util.DisplayHistogram;
import com.util.DisplayTwoSynchronizedImages;

public class DecodImageIO {

	private static File[] files = new File[] { new File("images/1.jpg"), new File("images/01.jpg"),
			new File("images/02.jpg"), new File("images/03.jpg"), new File("images/04.jpg"), new File("images/05.jpg"),
			new File("images/06.jpg"), new File("images/07.jpg"), new File("images/08.jpg"), new File("images/09.jpg"),
			new File("images/10.jpg") };

	public static void main(String[] args) throws IOException {
		// System.out.println(dimensionsImage(f));
		// System.out.println(valuesPixelsWithoutJAI(f));
		// System.out.println(valuesPixelsWithJAI(f));
		// displayImageWithoutJAI(f);
		// displayImageWithJAI(f);
		// invertImage(f.getPath());
		// binarizeImage(f.getPath()); //BUG!
		// suavizeImage(f.getPath());
		//borderHorizontal(files[1].getPath());
		//histogramImage(files[1].getPath(), 128);
		// dilataImage(f.getPath());
		// erodeImage(f.getPath());
		// rotateImage(f.getPath());
		// scaleImage(f.getPath());
	}

	public static String dimensionsImage(File f) throws IOException {
		BufferedImage image = ImageIO.read(f);
		return "Dimensões: " + image.getWidth() + "x" + image.getHeight() + " pixels";
	}

	public static String valuesPixelsWithoutJAI(File f) throws IOException {
		String msg = "";
		BufferedImage imagem = ImageIO.read(f);
		Raster raster = imagem.getRaster();
		int[] pixel = new int[3];
		int brancos = 0;
		for (int h = 0; h < imagem.getHeight(); h++) {
			for (int w = 0; w < imagem.getWidth(); w++) {
				raster.getPixel(w, h, pixel);
				if ((pixel[0] == 255) && (pixel[1] == 255) && (pixel[2] == 255))
					brancos++;
			}
			msg += brancos + " pixels brancos\n";
		}
		return msg;
	}

	public static String valuesPixelsWithJAI(File f) throws IOException {
		String msg = "";
		BufferedImage imagem = ImageIO.read(f);
		RandomIter iterator = RandomIterFactory.create(imagem, null);
		int[] pixel = new int[3];
		int brancos = 0;
		for (int h = 0; h < imagem.getHeight(); h++) {
			for (int w = 0; w < imagem.getWidth(); w++) {
				iterator.getPixel(w, h, pixel);
				if ((pixel[0] == 255) && (pixel[1] == 255) && (pixel[2] == 255))
					brancos++;
			}
			msg += brancos + "pixels brancos\n";
		}
		return msg;
	}

	public static void displayImageWithoutJAI(File f) throws IOException {
		BufferedImage image = ImageIO.read(f);
		JFrame frame = new JFrame("Display Image: " + f);
		ImageIcon icon = new ImageIcon(image);
		JLabel imageLabel = new JLabel(icon);
		frame.getContentPane().add(new JScrollPane(imageLabel));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 300);
		frame.setVisible(true);
	}

	public static void displayImageWithJAI(File f) throws IOException {
		BufferedImage image = ImageIO.read(f);
		JFrame frame = new JFrame("Display Image: " + f);
		DisplayJAI display = new DisplayJAI(image);
		frame.getContentPane().add(new JScrollPane(display));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 300);
		frame.setVisible(true);
	}

	public static void invertImage(String f) {
		PlanarImage input = JAI.create("fileload", f);
		PlanarImage output = JAI.create("invert", input);
		JFrame frame = new JFrame();
		frame.setTitle("Invert image " + f);
		frame.getContentPane().add(new DisplayTwoSynchronizedImages(input, output));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void binarizeImage(String f) {
		PlanarImage imagem = JAI.create("fileload", f);
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(imagem);
		pb.add(255);
		PlanarImage binarizada = JAI.create("binarize", pb);
		JFrame frame = new JFrame("Imagem binarizada");
		frame.add(new DisplayTwoSynchronizedImages(imagem, binarizada));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void suavizeImage(String f) {
		PlanarImage imagem = JAI.create("fileload", f);
		float[] kernelMatrix = { 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f,
				1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f,
				1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f };
		KernelJAI kernel = new KernelJAI(5, 5, kernelMatrix);
		PlanarImage bordas = JAI.create("convolve", imagem, kernel);
		JFrame frame = new JFrame("Suavização da imagem");
		frame.add(new DisplayTwoSynchronizedImages(imagem, bordas));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
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

	public static void dilataImage(String f) {
		PlanarImage imagem = JAI.create("fileload", f);
		float[] estrutura = { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1,
				1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 };
		KernelJAI kernel = new KernelJAI(7, 7, estrutura);
		ParameterBlock p = new ParameterBlock();
		p.addSource(imagem);
		p.add(kernel);
		PlanarImage dilatada = JAI.create("dilate", p);
		JFrame frame = new JFrame("Imagem dilatada");
		frame.add(new DisplayTwoSynchronizedImages(imagem, dilatada));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void erodeImage(String f) {
		PlanarImage imagem = JAI.create("fileload", f);
		float[] estrutura = { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1,
				1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 };
		KernelJAI kernel = new KernelJAI(7, 7, estrutura);
		ParameterBlock p = new ParameterBlock();
		p.addSource(imagem);
		p.add(kernel);
		PlanarImage erodida = JAI.create("erode", p);
		JFrame frame = new JFrame("Imagem dilatada");
		frame.add(new DisplayTwoSynchronizedImages(imagem, erodida));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void rotateImage(String f) {
		PlanarImage imagem = JAI.create("fileload", f);
		float angle = (float) Math.toRadians(10);
		// Usamos o centro da imagem para rotação
		float centerX = imagem.getWidth() / 2f;
		float centerY = imagem.getHeight() / 2f;
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(imagem);
		pb.add(centerX);
		pb.add(centerY);
		pb.add(angle);
		pb.add(new InterpolationBilinear());
		PlanarImage rotacionada = JAI.create("rotate", pb);
		JFrame frame = new JFrame("Imagem rotacionada");
		frame.add(new DisplayTwoSynchronizedImages(imagem, rotacionada));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void scaleImage(String f) {
		PlanarImage imagem = JAI.create("fileload", f);
		float scale = 0.3f;
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(imagem);
		pb.add(scale);
		pb.add(scale);
		pb.add(0.0F);
		pb.add(0.0F);
		pb.add(new InterpolationNearest());
		PlanarImage reescalada = JAI.create("scale", pb);
		JFrame frame = new JFrame("Imagem reescalada");
		frame.add(new DisplayTwoSynchronizedImages(imagem, reescalada));
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
