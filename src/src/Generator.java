package src;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Generator {

	private Workbook workbook;
	private Sheet sheet;
	private Cell cellReader;

	private String reader;
	private int[][] spreadSheet;
	private double arrayFitness[][];
	private ArrayList<Double> sortedFitness = new ArrayList<Double>();
	private ArrayList<Double> selectedPairs = new ArrayList<Double>();
	private Random random = new Random();

	public void sheetReader() {
		try {
			workbook = Workbook.getWorkbook(new File("teste.xls"));
		} catch (BiffException e) {
			System.err.println("Error sheetReader" + e);
		} catch (IOException e) {
			System.err.println("Error sheetReader" + e);
		}
		sheet = workbook.getSheet(0);
		int rows = sheet.getRows();
		int columns = sheet.getColumns();
		spreadSheet = new int[rows][columns];
		arrayFitness = new double[rows][3];

		arraySetter(rows, columns);
		workbook.close();
	}

	public void arraySetter(int rows, int columns) {
		for (int i = 1; i < rows; i++) {
			for (int j = 2; j < columns; j++) {
				cellReader = sheet.getCell(j, i);// coluna, linha
				reader = cellReader.getContents();
				spreadSheet[i][j] = Integer.parseInt(reader);// coluna, linha
//				System.out.print(spreadSheet[i][j] + " | ");
			}
//			System.out.println();
		}
		generateFitness(rows, 2);
	}

	public void generateFitness(int rows, int column) {
		String binary;
		int totalFitness = 0;
		int number;
		int fitnessCalculus;
		for (int i = 1; i < rows; i++) {
			number = spreadSheet[i][column];
			binary = Integer.toBinaryString(number);
			arrayFitness[i][0] = Integer.parseInt(binary);

//			System.out.println("number "+number+ " binary "+binary+ " int converted "+arrayFitness[i][0]);
//			System.out.println(Integer.parseInt("11111",2));  
			
			fitnessCalculus = number * number;
			totalFitness += fitnessCalculus;
			arrayFitness[i][1] = fitnessCalculus;// guardando o fitness
//			System.out.println("number: "+number+" totalfitness: "+totalFitness+" arrayftns: "+arrayFitness [i][0]);
		}
		// guardando o Pri
		for (int i = 1; i < rows; i++) {
			arrayFitness[i][2] = (arrayFitness[i][1] / totalFitness) * 1000;
			sortedFitness.add(arrayFitness[i][2]);
//			System.out.println("arrftns: "+arrayFitness [i][0]+" srtdftnss: "+arrayFitness [i][1]);
		}
		pairsSelector(rows);
	}

	public void pairsSelector(int rows) {
		Collections.sort(sortedFitness);
		double number = sortedFitness.get(sortedFitness.size() - 1);

		while (selectedPairs.size() < (rows / 2)) {
			int sorted = random.nextInt((int) number);
			System.out.println("sorted: " + sorted);
			for (int j = 0; j < sortedFitness.size(); j++) {
				if (j == 0 && sorted <= sortedFitness.get(j)) {
					selectedPairs.add(sortedFitness.get(j));
					System.out.println(sortedFitness.get(j));
					sortedFitness.remove(j);
					break;
				} else if (j > 0 && sorted > sortedFitness.get(j - 1) && sorted <= sortedFitness.get(j)) {
					selectedPairs.add(sortedFitness.get(j));
					System.out.println(sortedFitness.get(j));
					sortedFitness.remove(j);
					break;
				}
			}
		}
	}

	public void singlePointCrossover() {

	}

}
