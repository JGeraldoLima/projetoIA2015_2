package com.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ReaderBordersTxtToArff {

	private FileReader input;
	private BufferedReader data;
	private FileWriter output;
	private PrintWriter printer;
	
	public ReaderBordersTxtToArff(){}

	public void instatianteFilesInput(String filePath) throws IOException {
		input = new FileReader("data/" + filePath);
		data = new BufferedReader(input);
	}
	
	public void instatiateFilesOutput(String fileName) throws IOException{
		output = new FileWriter("classifier/" + fileName);
		printer = new PrintWriter(output);
	}

	public String readData() {
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
	
	public void instatiateAttributesOnArff(){
		printer.printf("@relation party\n\n");
		printer.printf("@attribute borders numeric\n");
		printer.printf("@attribute isParty {YES, NO}");
		printer.printf("\n\n");
		printer.printf("@data\n");
	}
	
	public void saveDataToArff(String party){
		while (readData() != null) {
			printer.printf(readData() + ", " + party + "\n");
			printer.printf(readData() + ", " + party + "\n");
			printer.printf(readData() + ", " + party + "\n");
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
	    String input1 = ler.nextLine();
	    String input2 = ler.nextLine();
	    
	    System.out.printf("Informe o caminho de arquivo de saída:\n");
	    String output = ler.nextLine();
	    
		ReaderBordersTxtToArff reader = new ReaderBordersTxtToArff();
		reader.instatianteFilesInput(input1);
		reader.instatiateFilesOutput(output);
		reader.instatiateAttributesOnArff();
		reader.saveDataToArff("YES");
		reader.instatianteFilesInput(input2);
		reader.saveDataToArff("NO");
		reader.output.close();
	}
	
}