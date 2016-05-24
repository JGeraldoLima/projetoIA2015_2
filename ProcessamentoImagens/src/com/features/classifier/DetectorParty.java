package com.features.classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.features.training.TrainingBorder;
import com.features.training.TrainingHistogramGray;
import com.features.training.TrainingHistogramRGB;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.BinarySparseInstance;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.SparseInstance;
import weka.core.converters.ArffLoader;

public class DetectorParty {

	private static DetectorParty instance = null;
	private Classifier cls = null;
	private static final String MODELO = "results/bins+borders/rgb3_P_H/models/rgb3_P_H_RandomForest.model"; // colocar o melhor modelo
	private static final int bins = 27; 
	private String file = null;
	private FileReader input;
	private BufferedReader dataBuffer;

	public DetectorParty() throws Exception {
		setCls((Classifier) SerializationHelper.read(MODELO));
	}

	public static DetectorParty getInstance() {
		if (instance == null) {
			try {
				return (instance = new DetectorParty());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public void setImage(String file) {
		this.file = file;
	}

	public String getImage() {
		return file;
	}
	
	public String readData() {
		String line = null;
		try {
			line = dataBuffer.readLine();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo", e.getMessage());
		} catch (NullPointerException e1){
			System.err.printf("Não existem mais dados", e1.getMessage());
		}
		return line;
	}

	public String classify(String file, String party) throws Exception {
		String fileName = "test";

		TrainingHistogramRGB.trainingImages(new File[]{new File(this.file)}, 3, fileName);
		TrainingBorder.trainingImages(new File[]{new File(this.file)}, fileName);
		
		ArffLoader loader= new ArffLoader();
        loader.setSource(new File("classifier/dadosRGB27_P_H.arff"));
		Instances D = loader.getDataSet();
		if (D.classIndex() == -1)
			D.setClassIndex(D.numAttributes() - 1);
		Instance newInst = new SparseInstance(bins + 2);
		newInst.setDataset(D);
		
		
		input = new FileReader("data/" + fileName +  "27.txt");
		dataBuffer = new BufferedReader(input);
		//read do arquivo, split por vírgula
		String dados = readData();
		String[] values = dados.split(", ");
		//converter os bins para double
		int i;
		for (i = 0; i < values.length; i++) {
			newInst.setValue(i,Double.parseDouble(values[i]));
		}
		//converter as bordas para inteiro
		newInst.setValue(i,Integer.parseInt(readData()));
		newInst.setValue(bins+1, party);
		//System.out.println(newInst);
		
		double pred = cls.classifyInstance(newInst);
		Attribute a = D.attribute(bins+1);
		String predClass = a.value((int) pred);
		return "Predição: " + predClass;
	}

	public Classifier getCls() {
		return cls;
	}

	public void setCls(Classifier cls) {
		this.cls = cls;
	}
	
	public static void main(String[] args) throws Exception {
		DetectorParty c = DetectorParty.getInstance();
//		c.setImage("images/test/festa/01.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/02.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/03.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/04.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/05.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/06.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/07.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/08.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/09.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/10.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/11.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/12.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/13.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/14.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/15.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/16.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/17.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/18.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/19.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/20.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/21.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/22.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/23.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/24.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/25.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/26.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/27.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/28.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/29.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/30.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/31.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/32.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/33.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/34.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/35.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/36.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/37.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/38.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/39.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/festa/40.jpg");
//		System.out.println(c.classify(c.getImage(), "YES"));
//		c.setImage("images/test/non_festa/01.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/02.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/03.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/04.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/05.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/06.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/07.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/08.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/09.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/10.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/11.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/12.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/13.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/14.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/15.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/16.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/17.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/18.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/19.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/20.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/21.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/22.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/23.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/24.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/25.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/26.png");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/27.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/28.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/29.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/30.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/31.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/32.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/33.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/34.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/35.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/36.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/37.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/38.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/39.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));
//		c.setImage("images/test/non_festa/40.jpg");
//		System.out.println(c.classify(c.getImage(), "NO"));

	}

}
