package com.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ReaderBinsAndBordersTxtToArff {

	private FileReader input1;
	private FileReader input2;
	private BufferedReader data1;
	private BufferedReader data2;
	private FileWriter output;
	private PrintWriter printer;
	
	public ReaderBinsAndBordersTxtToArff(){}

	public void instatianteFilesInput(String filePath1, String filePath2) throws IOException {
		input1 = new FileReader("data/" + filePath1);
		data1 = new BufferedReader(input1);
		input2 = new FileReader("data/" + filePath2);
		data2 = new BufferedReader(input2);
	}
	
	public void instatiateFilesOutput(String fileName) throws IOException{
		output = new FileWriter("classifier/" + fileName);
		printer = new PrintWriter(output);
	}

	public String readData(BufferedReader data) {
		String line = null;
		try {
			line = data.readLine();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo", e.getMessage());
		} catch (NullPointerException e1){
			System.err.printf("Não existem mais dados", e1.getMessage());
		}
		return line;
	}
	
	public void instatiateAttributesOnArff(int bins){
		printer.printf("@relation party\n\n");
		for (int i = 1; i <= bins; i++) {
			printer.printf("@attribute bin" + i + " numeric\n");
		}
		printer.printf("@attribute borders numeric\n");
		printer.printf("@attribute isParty {YES, NO}");
		printer.printf("\n\n");
		printer.printf("@data\n");
	}
	
	public void saveDataToArff(String party){
		while (readData(data1) != null && readData(data2) != null) {
			printer.printf(readData(data1) + readData(data2) + ", " + party + "\n");
		}
	}
	
	/**
	 * Construct the .arff file using filePath of file input, fileName of file output and number of bins
	 * @param args  
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Scanner ler = new Scanner(System.in);
		
		System.out.printf("Informe o caminho dos arquivos de entrada:\n");
	    String input1 = ler.nextLine(); //Festa
	    String input2 = ler.nextLine(); //Não Festa
	    String input3 = ler.nextLine(); //Bordas
	    
	    System.out.printf("Informe o caminho de arquivo de saída:\n");
	    String output = ler.nextLine();
	    
	    System.out.printf("Informe a quantidade de bins:\n");
	    String bins = ler.nextLine();
	    
		ReaderBinsAndBordersTxtToArff reader = new ReaderBinsAndBordersTxtToArff();
		reader.instatianteFilesInput(input1, input3);
		reader.instatiateFilesOutput(output);
		reader.instatiateAttributesOnArff(Integer.parseInt(bins));
		reader.saveDataToArff("YES");
		reader.instatianteFilesInput(input2, input3);
		reader.saveDataToArff("NO");
		reader.output.close();
	}
	
}