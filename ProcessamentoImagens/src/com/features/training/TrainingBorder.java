package com.features.training;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.util.CalculateBorder;
import com.util.CalculateLines;

public class TrainingBorder {
	
	private static File[] filesFesta = new File[] {
//			new File("images/training/festa/01.jpg"),
			new File("images/training/festa/02.jpg")
//			,
//			new File("images/training/festa/03.jpg"),
//			new File("images/training/festa/04.jpg"),
//			new File("images/training/festa/05.jpg"),
//			new File("images/training/festa/06.jpg"),
//			new File("images/training/festa/07.jpg"),
//			new File("images/training/festa/08.jpg"),
//			new File("images/training/festa/09.jpg"),
//			new File("images/training/festa/10.jpg"),
//			new File("images/training/festa/11.jpg"),
//			new File("images/training/festa/12.jpg"),
//			new File("images/training/festa/13.jpg"),
//			new File("images/training/festa/14.jpg"),
//			new File("images/training/festa/15.jpg"),
//			new File("images/training/festa/16.jpg"),
//			new File("images/training/festa/17.jpg"),
//			new File("images/training/festa/18.jpg"),
//			new File("images/training/festa/19.jpg"),
//			new File("images/training/festa/20.jpg"),
//			new File("images/training/festa/21.jpg"),
//			new File("images/training/festa/22.jpg"),
//			new File("images/training/festa/23.jpg"),
//			new File("images/training/festa/24.jpg"),
//			new File("images/training/festa/25.jpg"),
//			new File("images/training/festa/26.jpg"),
//			new File("images/training/festa/27.jpg"),
//			new File("images/training/festa/28.jpg"),
//			new File("images/training/festa/29.jpg"),
//			new File("images/training/festa/30.jpg"),
//			new File("images/training/festa/31.jpg"),
//			new File("images/training/festa/32.jpg"),
//			new File("images/training/festa/33.jpg"),
//			new File("images/training/festa/34.jpg"),
//			new File("images/training/festa/35.jpg"),
//			new File("images/training/festa/36.jpg"),
//			new File("images/training/festa/37.jpg"),
//			new File("images/training/festa/38.jpg"),
//			new File("images/training/festa/39.jpg"),
//			new File("images/training/festa/40.jpg"),
//			new File("images/training/festa/41.jpg"),
//			new File("images/training/festa/42.jpg"),
//			new File("images/training/festa/43.jpg"),
//			new File("images/training/festa/44.jpg"),
//			new File("images/training/festa/45.jpg"),
//			new File("images/training/festa/46.jpg"),
//			new File("images/training/festa/47.jpg"),
//			new File("images/training/festa/48.jpg"),
//			new File("images/training/festa/49.jpg"),
//			new File("images/training/festa/50.jpg"),
//			new File("images/training/festa/51.jpg"),
//			new File("images/training/festa/52.jpg"),
//			new File("images/training/festa/53.jpg"),
//			new File("images/training/festa/54.jpg"),
//			new File("images/training/festa/55.jpg"),
//			new File("images/training/festa/56.jpg"),
//			new File("images/training/festa/57.jpg"),
//			new File("images/training/festa/58.jpg"),
//			new File("images/training/festa/59.jpg"),
//			new File("images/training/festa/60.jpg"),
//			new File("images/training/festa/61.jpg"),
//			new File("images/training/festa/62.jpg"),
//			new File("images/training/festa/63.jpg"),
//			new File("images/training/festa/64.jpg"),
//			new File("images/training/festa/65.jpg"),
//			new File("images/training/festa/66.jpg"),
//			new File("images/training/festa/67.jpg"),
//			new File("images/training/festa/68.jpg"),
//			new File("images/training/festa/69.jpg"),
//			new File("images/training/festa/70.jpg"),
//			new File("images/training/festa/71.jpg"),
//			new File("images/training/festa/72.jpg"),
//			new File("images/training/festa/73.jpg"),
//			new File("images/training/festa/74.jpg"),
//			new File("images/training/festa/75.jpg"),
//			new File("images/training/festa/76.jpg"),
//			new File("images/training/festa/77.jpg"),
//			new File("images/training/festa/78.jpg"),
//			new File("images/training/festa/79.jpg"),
//			new File("images/training/festa/80.jpg"),
//			new File("images/training/festa/81.jpg"),
//			new File("images/training/festa/82.jpg"),
//			new File("images/training/festa/83.jpg"),
//			new File("images/training/festa/84.jpg"),
//			new File("images/training/festa/85.jpg"),
//			new File("images/training/festa/86.jpg"),
//			new File("images/training/festa/87.jpg"),
//			new File("images/training/festa/88.jpg"),
//			new File("images/training/festa/89.jpg"),
//			new File("images/training/festa/90.jpg"),
//			new File("images/training/festa/91.jpg"),
//			new File("images/training/festa/92.jpg"),
//			new File("images/training/festa/93.jpg"),
//			new File("images/training/festa/94.jpg"),
//			new File("images/training/festa/95.jpg"),
//			new File("images/training/festa/96.jpg"),
//			new File("images/training/festa/97.jpg"),
//			new File("images/training/festa/98.jpg"),
//			new File("images/training/festa/99.jpg"),
//			new File("images/training/festa/100.jpg")
			};
	@SuppressWarnings("unused")
	private static File[] filesNonFesta = new File[] {
			new File("images/training/non_festa/001.jpg"),
			new File("images/training/non_festa/002.jpg"),
			new File("images/training/non_festa/003.jpg"),
			new File("images/training/non_festa/004.jpg"),
			new File("images/training/non_festa/005.jpg"),
			new File("images/training/non_festa/006.jpg"),
			new File("images/training/non_festa/007.jpg"),
			new File("images/training/non_festa/008.jpg"),
			new File("images/training/non_festa/009.jpg"),
			new File("images/training/non_festa/010.jpg"),
			new File("images/training/non_festa/011.jpg"),
			new File("images/training/non_festa/012.jpg"),
			new File("images/training/non_festa/013.jpg"),
			new File("images/training/non_festa/014.jpg"),
			new File("images/training/non_festa/015.jpg"),
			new File("images/training/non_festa/016.jpg"),
			new File("images/training/non_festa/017.jpg"),
			new File("images/training/non_festa/018.jpg"),
			new File("images/training/non_festa/019.jpg"),
			new File("images/training/non_festa/020.jpg"),
			new File("images/training/non_festa/021.jpg"),
			new File("images/training/non_festa/022.jpg"),
			new File("images/training/non_festa/023.jpg"),
			new File("images/training/non_festa/024.jpg"),
			new File("images/training/non_festa/025.jpg"),
			new File("images/training/non_festa/026.jpg"),
			new File("images/training/non_festa/027.jpg"),
			new File("images/training/non_festa/028.jpg"),
			new File("images/training/non_festa/029.jpg"),
			new File("images/training/non_festa/030.jpg"),
			new File("images/training/non_festa/031.jpg"),
			new File("images/training/non_festa/032.jpg"),
			new File("images/training/non_festa/033.jpg"),
			new File("images/training/non_festa/034.jpg"),
			new File("images/training/non_festa/035.jpg"),
			new File("images/training/non_festa/036.jpg"),
			new File("images/training/non_festa/037.jpg"),
			new File("images/training/non_festa/038.jpg"),
			new File("images/training/non_festa/039.jpg"),
			new File("images/training/non_festa/040.jpg"),
			new File("images/training/non_festa/041.jpg"),
			new File("images/training/non_festa/042.jpg"),
			new File("images/training/non_festa/043.jpg"),
			new File("images/training/non_festa/044.jpg"),
			new File("images/training/non_festa/045.jpg"),
			new File("images/training/non_festa/046.jpg"),
			new File("images/training/non_festa/047.jpg"),
			new File("images/training/non_festa/048.jpg"),
			new File("images/training/non_festa/049.jpg"),
			new File("images/training/non_festa/050.jpg"),
			new File("images/training/non_festa/051.jpg"),
			new File("images/training/non_festa/052.jpg"),
			new File("images/training/non_festa/053.jpg"),
			new File("images/training/non_festa/054.jpg"),
			new File("images/training/non_festa/055.jpg"),
			new File("images/training/non_festa/056.jpg"),
			new File("images/training/non_festa/057.jpg"),
			new File("images/training/non_festa/058.jpg"),
			new File("images/training/non_festa/059.jpg"),
			new File("images/training/non_festa/060.jpg"),
			new File("images/training/non_festa/061.jpg"),
			new File("images/training/non_festa/062.jpg"),
			new File("images/training/non_festa/063.jpg"),
			new File("images/training/non_festa/064.jpg"),
			new File("images/training/non_festa/065.jpg"),
			new File("images/training/non_festa/066.jpg"),
			new File("images/training/non_festa/067.jpg"),
			new File("images/training/non_festa/068.jpg"),
			new File("images/training/non_festa/069.jpg"),
			new File("images/training/non_festa/070.jpg"),
			new File("images/training/non_festa/071.jpg"),
			new File("images/training/non_festa/072.jpg"),
			new File("images/training/non_festa/073.jpg"),
			new File("images/training/non_festa/074.jpg"),
			new File("images/training/non_festa/075.jpg"),
			new File("images/training/non_festa/076.jpg"),
			new File("images/training/non_festa/077.jpg"),
			new File("images/training/non_festa/078.jpg"),
			new File("images/training/non_festa/079.jpg"),
			new File("images/training/non_festa/080.jpg"),
			new File("images/training/non_festa/081.jpg"),
			new File("images/training/non_festa/082.jpg"),
			new File("images/training/non_festa/083.jpg"),
			new File("images/training/non_festa/084.jpg"),
			new File("images/training/non_festa/085.jpg"),
			new File("images/training/non_festa/086.jpg"),
			new File("images/training/non_festa/087.jpg"),
			new File("images/training/non_festa/088.jpg"),
			new File("images/training/non_festa/089.jpg"),
			new File("images/training/non_festa/090.jpg"),
			new File("images/training/non_festa/091.jpg"),
			new File("images/training/non_festa/092.jpg"),
			new File("images/training/non_festa/093.jpg"),
			new File("images/training/non_festa/094.jpg"),
			new File("images/training/non_festa/095.jpg"),
			new File("images/training/non_festa/096.jpg"),
			new File("images/training/non_festa/097.jpg"),
			new File("images/training/non_festa/098.jpg"),
			new File("images/training/non_festa/099.jpg"),
			new File("images/training/non_festa/100.jpg")
			};
	private static CalculateBorder border = new CalculateBorder();
	private static CalculateLines lines = new CalculateLines();

	private static String filename;
	
	public static void main(String[] args) throws IOException {
		trainingImages(filesFesta, "LinesTest");
	}
	
	public static void trainingImages(File[] array, String fileName) throws IOException{
		filename = String.format("./data/%s.txt", fileName);
		for (File fileFesta : array) {
//			border.detectBorder(2, CalculateBorder.ROBERT_PRINCIPAL, fileFesta.getPath());
//			border.detectBorder(2, CalculateBorder.ROBERT_INVERSE, fileFesta.getPath());
//			border.detectBorder(3, CalculateBorder.PREWITT_HORIZONTAL, fileFesta.getPath());
//			border.detectBorder(3, CalculateBorder.PREWITT_VERTICAL, fileFesta.getPath());
//			border.detectBorder(3, CalculateBorder.SOBEL_HORIZONTAL, fileFesta.getPath());
//			border.detectBorder(3, CalculateBorder.SOBEL_VERTICAL, fileFesta.getPath());
//			border.detectBorder(3, CalculateBorder.FREICHEN_HORIZONTAL, fileFesta.getPath());
//			border.detectBorder(3, CalculateBorder.FREICHEN_VERTICAL, fileFesta.getPath());
//			lines.detectLines(CalculateLines.VERTICAL_LINES, fileFesta.getPath());
		}
	}
	
	public static void writeLine(String line) {
		try {
			FileWriter fw = new FileWriter(filename, true);// em modo de append!!
		    fw.write(line + "\n");
		    fw.close();
		} catch (Exception e) {
			System.out.println(String.format("Erro ao gerar o arquivo: %s", e.getMessage()));
		}
	}
}
