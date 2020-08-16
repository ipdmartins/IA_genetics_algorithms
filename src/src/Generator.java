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
	private ArrayList<String> binariesForMutation = new ArrayList<String>();
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
		arrayFitness = new double[rows][4];

		arraySetter(rows, columns);
		workbook.close();
	}

	public void arraySetter(int rows, int columns) {
		for (int i = 1; i < rows; i++) {
			for (int j = 2; j < columns; j++) {
				cellReader = sheet.getCell(j, i);// coluna, linha
				reader = cellReader.getContents();
				spreadSheet[i][j] = Integer.parseInt(reader);// coluna, linha
				System.out.print(spreadSheet[i][j] + " | ");
			}
			System.out.println();
		}
		generateFitness(rows, 2);// 2 passando a 1st coluna pace_acceleration
	}

	public void generateFitness(int rows, int column) {
		String binary;
		int totalFitness = 0;
		int number;
		int fitnessCalculus;
		for (int i = 1; i < rows; i++) {
			number = spreadSheet[i][column];
			binary = Integer.toBinaryString(number);
			arrayFitness[i][0] = Integer.parseInt(binary);// guardando o cromossomo binario
			arrayFitness[i][1] = number;// guardando o fenótipo

//			System.out.println("number "+number+ " binary "+binary+ " int converted "+arrayFitness[i][0]);
//			System.out.println(Integer.parseInt("11111",2));  

			fitnessCalculus = number * number;
			totalFitness += fitnessCalculus;
			arrayFitness[i][2] = fitnessCalculus;// guardando o fitness
//			System.out.println("number: "+number+" totalfitness: "+totalFitness+" arrayftns: "+arrayFitness [i][0]);
		}
		// guardando o Pri
		for (int i = 1; i < rows; i++) {
			arrayFitness[i][3] = (arrayFitness[i][2] / totalFitness) * 1000;
			sortedFitness.add(arrayFitness[i][3]);
//			System.out.println("fitness: "+arrayFitness [i][2]+" srtdftnss: "+arrayFitness [i][3]);
		}
		pairsSelector(rows);
	}

	public void pairsSelector(int rows) {
		Collections.sort(sortedFitness);
		double number = sortedFitness.get(sortedFitness.size() - 1);
		int sorted = 0;
		while (selectedPairs.size() < (rows / 2)) {
			sorted = random.nextInt((int) number);
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
		singlePointCrossover(rows);
	}

	public void singlePointCrossover(int rows) {

		for (int i = 0; i < arrayFitness.length; i++) {
			for (int j = 0; j < 4; j++) {// nro colunas
				System.out.print(arrayFitness[i][j] + " ");
			}
			System.out.println();
		}

		int index[] = new int[selectedPairs.size()];
		for (int i = 0; i < selectedPairs.size(); i++) {
			for (int j = 0; j < arrayFitness.length; j++) {// nro de linhas
				if (selectedPairs.get(i) == arrayFitness[j][3]) {
					index[i] = j;// guarda a posição da matriz
					break;
				}
			}
			System.out.println(index[i]);
		}

		double first = 0;
		double second = 0;
		String binary1 = "";
		String binary2 = "";
		int converter = 0;
		for (int i = 0; i < index.length; i++) {
			first = arrayFitness[index[i]][0];
			converter = (int) first;
			binary1 = String.valueOf(converter);
			second = arrayFitness[index[i + 1]][0];
			converter = (int) second;
			binary2 = String.valueOf(converter);
			i += 1;

			System.out.println(first + " ," + second + " ," + binary1 + " ," + binary2);
			auxiliarSingleCross(binary1, binary2);
		}

		int sortedMutationIndex = random.nextInt(binariesForMutation.size());
		String mutation = binariesForMutation.get(sortedMutationIndex);
		System.out.println("index: " + sortedMutationIndex + " , mutation: " + mutation);
		StringBuilder binaryMutate = new StringBuilder(mutation);
		if (mutation.charAt(mutation.length() - 1) == '0') {
			binaryMutate.setCharAt(mutation.length() - 1, '1');
		} else {
			binaryMutate.setCharAt(mutation.length() - 1, '0');
		}

		binariesForMutation.add(sortedMutationIndex, binaryMutate.toString());
		System.out.println(binariesForMutation.get(sortedMutationIndex));
		replacePopulation(rows);
	}

	public void auxiliarSingleCross(String binary1, String binary2) {
		String partI = binary1.substring(0, binary1.length() / 2);
		String partII = binary2.substring(0, binary2.length() / 2);
		String partIII = binary2.substring(binary2.length() / 2);
		String partIV = binary1.substring(binary1.length() / 2);
		binariesForMutation.add(partI + partIII);
		binariesForMutation.add(partII + partIV);
		System.out.println(binariesForMutation.toString());
	}

	public void replacePopulation(int rows) {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (int j = 0; j < rows; j++) {
			numbers.add(spreadSheet[j][3]);
			System.out.print(spreadSheet[j][3] + " ");
		}

		ArrayList<Integer> selectedNumbers = new ArrayList<Integer>();
		int lowestIndex = 0;
		int lowestNumber = 1000000;
		for (int x = 0; x < binariesForMutation.size()-1; x++) {
			for (int i = 0; i < numbers.size(); i++) {
				if (i < numbers.size() - 1 && numbers.get(i) != 0 && numbers.get(i) < lowestNumber) {
					lowestNumber = numbers.get(i);
					lowestIndex = i;
				} 
			}
			System.out.println("index: "+ lowestIndex+ " ");
			selectedNumbers.add(lowestNumber);
			numbers.remove(lowestIndex);
			lowestNumber = 1000000;
//			System.out.println(numbers.toString());
		}
		System.out.println(selectedNumbers.toString());
		
		int newNumber = 0;
		for (int i = 0; i < selectedNumbers.size(); i++) {
			for (int j = 0; j < rows; j++) {
				if(selectedNumbers.get(i) == spreadSheet[j][3]) {
					newNumber = Integer.parseInt(binariesForMutation.get(i), 2);     
					spreadSheet[j][3] = newNumber;
					break;
				}
			}
		}
		
		for (int j = 0; j < rows; j++) {
			System.out.print(spreadSheet[j][3] +" ,");
		}
		
	}

}
