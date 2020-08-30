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

import javax.swing.JOptionPane;

public class Generator {

	private Workbook workbook;
	private Sheet sheet;
	private Cell cellReader;

	private int[][] spreadSheet;
	private double arrayFitness[][];
	private ArrayList<Double> sortedFitness;
	private ArrayList<Double> selectedPairs;
	private ArrayList<String> binariesForMutation;
	private Random random = new Random();
	private int currentColumnNumber;
	private int totalNumberRows;
	private int threshold;
	private int counter;
	private ArrayList<Integer> idList;
	private ArrayList<Integer> overallist;
	
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public void sheetReader(String path) {
		try {
			workbook = Workbook.getWorkbook(new File(path));
		} catch (BiffException e) {
			System.err.println("Error sheetReader" + e);
		} catch (IOException e) {
			System.err.println("Error sheetReader" + e);
		}
		sheet = workbook.getSheet(0);
		totalNumberRows = sheet.getRows();
		int columns = sheet.getColumns();
		spreadSheet = new int[totalNumberRows][columns];
		arrayFitness = new double[totalNumberRows][4];

		arraySetter(totalNumberRows, columns);
		workbook.close();
	}

	// It reads the file saving it to an array
	public void arraySetter(int rows, int columns) {
		String reader;

		System.out.println("METHOD ARRAYSETTER: printing the table");
		for (int i = 1; i < totalNumberRows; i++) {
			for (int j = 0; j < columns; j++) {
				cellReader = sheet.getCell(j, i);// column, row
				reader = cellReader.getContents();
				spreadSheet[i][j] = Integer.parseInt(reader);// column, row
				System.out.print(spreadSheet[i][j] + " | ");
			}
			System.out.println();
		}
		System.out.println();

		boolean cond = true;
		counter = 0;
		while (cond) {
			counter++;
			for (int i = 2; i < columns; i++) {
				sortedFitness = new ArrayList<Double>();
				selectedPairs = new ArrayList<Double>();
				binariesForMutation = new ArrayList<String>();
				currentColumnNumber = i;
				generateFitness();// 2 passing only the 1st column pace_acceleration
			}

			//calculating the new overall
			idList = new ArrayList<Integer>();
			overallist = new ArrayList<Integer>();
			for (int i = 1; i < totalNumberRows; i++) {
				int newOverall = 0;
				for (int j = 2; j < columns; j++) {
					newOverall += spreadSheet[i][j];
				}
				spreadSheet[i][1] = newOverall / (columns - 2);
				if (spreadSheet[i][1] >= threshold) {
					idList.add(spreadSheet[i][0]);
					overallist.add(spreadSheet[i][1]);
				}
			}
			if (idList.size() > 0) {
				cond = false;
			}
			newPopulation(columns);
		}
		JOptionPane.showMessageDialog(null, "Found new threshold in "+counter+" generations\n"+ 
				"ids: "+idList.toString()+"\n"+
				"overall: "+overallist.toString());
	}

	public void generateFitness() {
		String binary;
		int totalFitness = 0;
		int number;
		int fitnessCalculus;
		for (int i = 1; i < totalNumberRows; i++) {
			number = spreadSheet[i][currentColumnNumber];
			binary = Integer.toBinaryString(number);
			arrayFitness[i][0] = Integer.parseInt(binary);// storing the binary chromosome
			arrayFitness[i][1] = number;// toring the phenotype

			fitnessCalculus = number * number;
			totalFitness += fitnessCalculus;
			arrayFitness[i][2] = fitnessCalculus;// storing the fitness
		}

//		System.out.println("METHOD GENERATE FITNESS: My array fitness table");
		// calculating the Pri only now because I need the totalFitness ready calculated
		for (int i = 1; i < totalNumberRows; i++) {
			arrayFitness[i][3] = (arrayFitness[i][2] / totalFitness) * 1000;
			sortedFitness.add(arrayFitness[i][3]);// store in an arrayList to make easier to order them
//			System.out.println(arrayFitness[i][0] + " | " + arrayFitness[i][1] + " | " + arrayFitness[i][2] + " | "
//					+ arrayFitness[i][3]);
		}
		pairsSelector();
	}

	public void pairsSelector() {
		Collections.sort(sortedFitness);// puting all elements in ascendent order
		double biggestNumber = sortedFitness.get(sortedFitness.size() - 1);
		int sortedNumber = 0;
		while (selectedPairs.size() < (totalNumberRows / 2)) {
			sortedNumber = random.nextInt((int) biggestNumber);
//			System.out.print("sortedNumber: " + sortedNumber + " ");
			for (int j = 0; j < sortedFitness.size(); j++) {
				if (j == 0 && sortedNumber <= sortedFitness.get(j)) {
					selectedPairs.add(sortedFitness.get(j));
//					System.out.println(" | corresponding table number " + sortedFitness.get(j));
					sortedFitness.remove(j);
					break;
				} else if (j > 0 && sortedNumber > sortedFitness.get(j - 1) && sortedNumber <= sortedFitness.get(j)) {
					selectedPairs.add(sortedFitness.get(j));
//					System.out.println(" | corresponding table number " + sortedFitness.get(j));
					sortedFitness.remove(j);
					break;
				}
			}
		}
		singlePointCrossover();
	}

	public void singlePointCrossover() {
//		System.out.println();
//		System.out.println("Corresponding position in the array");
		int index[] = new int[selectedPairs.size()];
		for (int i = 0; i < selectedPairs.size(); i++) {
			for (int j = 0; j < arrayFitness.length; j++) {// number of lines
				if (selectedPairs.get(i) == arrayFitness[j][3]) {
					index[i] = j;// store the arrays' position
					break;
				}
			}
//			System.out.print(index[i] + " | ");
		}

		double first = 0;
		double second = 0;
		String binary1 = "";
		String binary2 = "";
		int converter = 0;
//		System.out.println();
//		System.out.println();
		for (int i = 0; i < index.length; i++) {
			first = arrayFitness[index[i]][0];// getting the 1st chromosome from the table
			converter = (int) first;
			binary1 = String.valueOf(converter);// making it a string
			second = arrayFitness[index[i + 1]][0];// getting the 2nd chromosome from the table
			converter = (int) second;
			binary2 = String.valueOf(converter);// making it a string
			i += 1;
//			System.out.print("Binaries sent for crossing: " + binary1 + " , " + binary2);
			auxiliarSingleCross(binary1, binary2);
		}

		// check if any crossed binary exceeded 100
		for (int i = 0; i < binariesForMutation.size(); i++) {
			int tempNumber = Integer.parseInt(binariesForMutation.get(i));
			if (tempNumber > 1100100) {
				binariesForMutation.set(i, "1100100");// setting 100 as the biggest number
			}
		}

		int sortedMutationIndex = random.nextInt(binariesForMutation.size());
		String mutation = binariesForMutation.get(sortedMutationIndex);
//		System.out.println();
//		System.out.print("Index sorted for mutation: " + sortedMutationIndex + " , corresponding binary: " + mutation);

		StringBuilder binaryMutate = new StringBuilder(mutation);
		if (mutation.charAt(mutation.length() - 1) == '0') {
			binaryMutate.setCharAt(mutation.length() - 1, '1');
		} else {
			binaryMutate.setCharAt(mutation.length() - 1, '0');
		}

		binariesForMutation.add(sortedMutationIndex, binaryMutate.toString());// replacing the mutated in the list
//		System.out.println(" , binary mutated: " + binariesForMutation.get(sortedMutationIndex));

		replacePopulation();
	}

	public void auxiliarSingleCross(String binary1, String binary2) {
		String partI = binary1.substring(0, binary1.length() / 2);
		String partII = binary2.substring(0, binary2.length() / 2);
		String partIII = binary2.substring(binary2.length() / 2);
		String partIV = binary1.substring(binary1.length() / 2);
		binariesForMutation.add(partI + partIII);
		binariesForMutation.add(partII + partIV);
//		System.out.print(" | binaries crossed: " + binariesForMutation.toString());
//		System.out.println();
	}

	public void replacePopulation() {
//		System.out.println();
//		System.out.println("Population from the current column 6");
		ArrayList<Integer> population = new ArrayList<Integer>();
		for (int j = 0; j < totalNumberRows; j++) {
			population.add(spreadSheet[j][currentColumnNumber]);
//			System.out.print(spreadSheet[j][currentColumnNumber] + " ");
		}

		ArrayList<Integer> selectedNumbers = new ArrayList<Integer>();
		int lowestIndex = 0;
		int lowestNumber = 1000000;
//		System.out.println();
//		System.out.println();

		for (int x = 0; x < binariesForMutation.size() - 1; x++) {
			for (int i = 0; i < population.size(); i++) {
				if (i < population.size() && population.get(i) != 0 && population.get(i) < lowestNumber) {
					lowestNumber = population.get(i);
					lowestIndex = i;
				}
			}
//			System.out.println("Lowest index picked: " + lowestIndex + ", corresponding lowest number: " + lowestNumber);
			selectedNumbers.add(lowestNumber);
			population.remove(lowestIndex);
			lowestNumber = 1000000;
//			System.out.println("Population after element removed: " + population.toString());
		}
//		System.out.println("Lowest numbers going for mutation: " + selectedNumbers.toString());

		int newNumber = 0;
		for (int i = 0; i < selectedNumbers.size(); i++) {
			for (int j = 0; j < totalNumberRows; j++) {
				if (selectedNumbers.get(i) == spreadSheet[j][currentColumnNumber]) {
					newNumber = Integer.parseInt(binariesForMutation.get(i), 2);// converting the binary to decimal
					spreadSheet[j][currentColumnNumber] = newNumber;
					break;
				}
			}
		}
	}

	public void newPopulation(int columns) {
		System.out.println("New population after mutation");
		for (int i = 1; i < totalNumberRows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(spreadSheet[i][j] + " | ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
