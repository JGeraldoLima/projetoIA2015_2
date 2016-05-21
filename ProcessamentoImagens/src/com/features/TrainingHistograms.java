package com.features;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.util.CalculateHistogram;

public class TrainingHistograms {
	
	private static File[] filesFesta = new File[] {
			new File("images/training/festa/01.jpg"),
			new File("images/training/festa/02.jpg"),
			new File("images/training/festa/03.jpg"),
			new File("images/training/festa/04.jpg"),
			new File("images/training/festa/05.jpg"),
			new File("images/training/festa/06.jpg"),
			new File("images/training/festa/07.jpg"),
			new File("images/training/festa/08.jpg"),
			new File("images/training/festa/09.jpg"),
			new File("images/training/festa/10.jpg"),
			new File("images/training/festa/11.jpg"),
			new File("images/training/festa/12.jpg"),
			new File("images/training/festa/13.jpg"),
			new File("images/training/festa/14.jpg"),
			new File("images/training/festa/15.jpg"),
			new File("images/training/festa/16.jpg"),
			new File("images/training/festa/17.jpg"),
			new File("images/training/festa/18.jpg"),
			new File("images/training/festa/19.jpg"),
			new File("images/training/festa/20.jpg"),
			new File("images/training/festa/21.jpg"),
			new File("images/training/festa/22.jpg"),
			new File("images/training/festa/23.jpg"),
			new File("images/training/festa/24.jpg"),
			new File("images/training/festa/25.jpg"),
			new File("images/training/festa/26.jpg"),
			new File("images/training/festa/27.jpg"),
			new File("images/training/festa/28.jpg"),
			new File("images/training/festa/29.jpg"),
			new File("images/training/festa/30.jpg"),
			new File("images/training/festa/31.jpg"),
			new File("images/training/festa/32.jpg"),
			new File("images/training/festa/33.jpg"),
			new File("images/training/festa/34.jpg"),
			new File("images/training/festa/35.jpg"),
			new File("images/training/festa/36.jpg"),
			new File("images/training/festa/37.jpg"),
			new File("images/training/festa/38.jpg"),
			new File("images/training/festa/39.jpg"),
			new File("images/training/festa/40.jpg")
			};
	private static File[] filesNonFesta = new File[] {
			new File("images/training/non_festa/01.jpg"),
			new File("images/training/non_festa/02.jpg"),
			new File("images/training/non_festa/03.jpg"),
			new File("images/training/non_festa/04.jpg"),
			new File("images/training/non_festa/05.jpg"),
			new File("images/training/non_festa/06.jpg"),
			new File("images/training/non_festa/07.jpg"),
			new File("images/training/non_festa/08.jpg"),
			new File("images/training/non_festa/09.JPG"),
			new File("images/training/non_festa/10.jpg"),
			new File("images/training/non_festa/11.jpg"),
			new File("images/training/non_festa/12.jpg"),
			new File("images/training/non_festa/13.jpg"),
			new File("images/training/non_festa/14.jpg"),
			new File("images/training/non_festa/15.jpg"),
			new File("images/training/non_festa/16.jpg"),
			new File("images/training/non_festa/17.jpg"),
			new File("images/training/non_festa/18.jpg"),
			new File("images/training/non_festa/19.jpg"),
			new File("images/training/non_festa/20.jpg"),
			new File("images/training/non_festa/21.jpg"),
			new File("images/training/non_festa/22.jpg"),
			new File("images/training/non_festa/23.jpg"),
			new File("images/training/non_festa/24.jpg"),
			new File("images/training/non_festa/25.jpg"),
			new File("images/training/non_festa/26.png"),
			new File("images/training/non_festa/27.jpg"),
			new File("images/training/non_festa/28.jpg"),
			new File("images/training/non_festa/29.jpg"),
			new File("images/training/non_festa/30.jpg"),
			new File("images/training/non_festa/31.jpg"),
			new File("images/training/non_festa/32.jpg"),
			new File("images/training/non_festa/33.jpg"),
			new File("images/training/non_festa/34.jpg"),
			new File("images/training/non_festa/35.jpg"),
			new File("images/training/non_festa/36.jpg"),
			new File("images/training/non_festa/37.JPG"),
			new File("images/training/non_festa/38.jpg"),
			new File("images/training/non_festa/39.jpg"),
			new File("images/training/non_festa/40.jpg")};
	private static CalculateHistogram histogram = new CalculateHistogram();

	private static FileWriter fw;
	private static String filename;
	
	public static void main(String[] args) throws IOException {
//		trainingImages(filesFesta, "gray", 32, "festa");
//		trainingImages(filesFesta, "gray", 64, "festa");
//		trainingImages(filesFesta, "gray", 128, "festa");
//		trainingImages(filesFesta, "gray", 256, "festa");
		trainingImages(filesFesta, "gray", 32, "nonfesta");
		trainingImages(filesFesta, "gray", 64, "nonfesta");
		trainingImages(filesFesta, "gray", 128, "nonfesta");
		trainingImages(filesFesta, "gray", 256, "nonfesta");
	}
	
	public static void trainingImages(File[] array, String color, int bins, String fileName) throws IOException{
		filename = String.format("./data/%s%s.txt", fileName, bins);
		if (color.equals("gray")){
			for (File fileFesta : array) {
				histogram.histogramGrayImage(fileFesta.getPath(), bins);
			}

		} else if (color.equals("color")){
			for (File fileFesta : array) {
				histogram.histogramRGBImage(fileFesta.getPath(), bins);
			}
		}
	}
	
	public static void writeLine(String line) {
		try {
			FileWriter fw = new FileWriter(filename, true);// em modo de append!!
		    fw.write(line);
		    fw.close();
		} catch (Exception e) {
			System.out.println(String.format("Erro ao gerar o arquivo: %s", e.getMessage()));
		}
	}
}
