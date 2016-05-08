package com.features;

import java.io.File;

import com.util.CalculateHistogram;

public class TrainingHistogram64 {
	
	private static File[] filesFesta = new File[] {
			new File("images/festa/01.jpg"),
			new File("images/festa/02.jpg"),
			new File("images/festa/03.jpg"),
			new File("images/festa/04.jpg"),
			new File("images/festa/05.jpg"),
			new File("images/festa/06.jpg"),
			new File("images/festa/07.jpg"),
			new File("images/festa/08.jpg"),
			new File("images/festa/09.jpg"),
			new File("images/festa/10.jpg"),
			new File("images/festa/11.jpg"),
			new File("images/festa/12.jpg"),
			new File("images/festa/13.jpg"),
			new File("images/festa/14.jpg"),
			new File("images/festa/15.jpg"),
			new File("images/festa/16.jpg"),
			new File("images/festa/17.jpg"),
			new File("images/festa/18.jpg"),
			new File("images/festa/19.jpg"),
			new File("images/festa/20.jpg"),
			new File("images/festa/21.jpg"),
			new File("images/festa/22.jpg"),
			new File("images/festa/23.jpg"),
			new File("images/festa/24.jpg"),
			new File("images/festa/25.jpg"),
			new File("images/festa/26.jpg"),
			new File("images/festa/27.jpg"),
			new File("images/festa/28.jpg"),
			new File("images/festa/29.jpg"),
			new File("images/festa/30.jpg"),
			new File("images/festa/31.jpg"),
			new File("images/festa/32.jpg"),
			new File("images/festa/33.jpg"),
			new File("images/festa/34.jpg"),
			new File("images/festa/35.jpg"),
			new File("images/festa/36.jpg"),
			new File("images/festa/37.jpg"),
			new File("images/festa/38.jpg"),
			new File("images/festa/39.jpg"),
			new File("images/festa/40.jpg")};
	private static File[] filesNonFesta = new File[] {
			new File("images/non_festa/01.jpg"),
			new File("images/non_festa/02.jpg"),
			new File("images/non_festa/03.jpg"),
			new File("images/non_festa/04.jpg"),
			new File("images/non_festa/05.jpg"),
			new File("images/non_festa/06.jpg"),
			new File("images/non_festa/07.jpg"),
			new File("images/non_festa/08.jpg"),
			new File("images/non_festa/09.JPG"),
			new File("images/non_festa/10.jpg"),
			new File("images/non_festa/11.jpg"),
			new File("images/non_festa/12.jpg"),
			new File("images/non_festa/13.jpg"),
			new File("images/non_festa/14.jpg"),
			new File("images/non_festa/15.jpg"),
			new File("images/non_festa/16.jpg"),
			new File("images/non_festa/17.jpg"),
			new File("images/non_festa/18.jpg"),
			new File("images/non_festa/19.jpg"),
			new File("images/non_festa/20.jpg"),
			new File("images/non_festa/21.jpg"),
			new File("images/non_festa/22.jpg"),
			new File("images/non_festa/23.jpg"),
			new File("images/non_festa/24.jpg"),
			new File("images/non_festa/25.jpg"),
			new File("images/non_festa/26.png"),
			new File("images/non_festa/27.jpg"),
			new File("images/non_festa/28.jpg"),
			new File("images/non_festa/29.jpg"),
			new File("images/non_festa/30.jpg"),
			new File("images/non_festa/31.jpg"),
			new File("images/non_festa/32.jpg"),
			new File("images/non_festa/33.jpg"),
			new File("images/non_festa/34.jpg"),
			new File("images/non_festa/35.jpg"),
			new File("images/non_festa/36.jpg"),
			new File("images/non_festa/37.JPG"),
			new File("images/non_festa/38.jpg"),
			new File("images/non_festa/39.jpg"),
			new File("images/non_festa/40.jpg")};
	private static CalculateHistogram histogram = new CalculateHistogram();

	public static void main(String[] args) {
		trainingImagesFesta();
		trainingImagesNonFesta();
	}
	
	public static void trainingImagesFesta(){
		System.out.println("imagens festa");
		for (File fileFesta : filesFesta) {
			histogram.histogramImage(fileFesta.getPath(), 64);
		}
	}
	
	public static void trainingImagesNonFesta(){
		System.out.println("imagens non_festa");
		for (File fileNonFesta : filesNonFesta) {
			histogram.histogramImage(fileNonFesta.getPath(), 64);
		}
	}

}
