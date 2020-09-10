package src;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.sql.Timestamp;

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
	private boolean mutationOption;
	private ArrayList<Integer> idList;
	private ArrayList<Integer> overallist;
	
	public void setMutationOption(boolean mutationOption) {
		this.mutationOption = mutationOption;
	}

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

		for (int i = 1; i < totalNumberRows; i++) {
			for (int j = 0; j < columns; j++) {
				cellReader = sheet.getCell(j, i);// column, row
				reader = cellReader.getContents();
				spreadSheet[i][j] = Integer.parseInt(reader);// column, row
			}
		}

		boolean cond = true;
		counter = 0;	
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		long start = System.nanoTime();
		long end = 0;
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
		end = System.nanoTime() - start;
		JOptionPane.showMessageDialog(null, "Found new threshold in "+counter+" generations\n"+ 
				(end)+" nanoseconds"+"\n"+
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

		// calculating the Pri only now because I need the totalFitness ready calculated
		for (int i = 1; i < totalNumberRows; i++) {
			arrayFitness[i][3] = (arrayFitness[i][2] / totalFitness) * 1000;
			sortedFitness.add(arrayFitness[i][3]);// store in an arrayList to make easier to order them
		}
		pairsSelector();
	}

	public void pairsSelector() {
		Collections.sort(sortedFitness);// puting all elements in ascendent order
		double biggestNumber = sortedFitness.get(sortedFitness.size() - 1);
		int sortedNumber = 0;
		while (selectedPairs.size() < (totalNumberRows / 2)) {
			sortedNumber = random.nextInt((int) biggestNumber);
			
			for (int j = 0; j < sortedFitness.size(); j++) {
				if (j == 0 && sortedNumber <= sortedFitness.get(j)) {
					selectedPairs.add(sortedFitness.get(j));
					sortedFitness.remove(j);
					break;
				} else if (j > 0 && sortedNumber > sortedFitness.get(j - 1) && sortedNumber <= sortedFitness.get(j)) {
					selectedPairs.add(sortedFitness.get(j));
					sortedFitness.remove(j);
					break;
				}
			}
		}
		singlePointCrossover();
	}

	public void singlePointCrossover() {
		int index[] = new int[selectedPairs.size()];
		for (int i = 0; i < selectedPairs.size(); i++) {
			for (int j = 0; j < arrayFitness.length; j++) {// number of lines
				if (selectedPairs.get(i) == arrayFitness[j][3]) {
					index[i] = j;// store the arrays' position
					break;
				}
			}
		}

		double first = 0;
		double second = 0;
		String binary1 = "";
		String binary2 = "";
		int converter = 0;

		for (int i = 0; i < index.length; i++) {
			first = arrayFitness[index[i]][0];// getting the 1st chromosome from the table
			converter = (int) first;
			binary1 = String.valueOf(converter);// making it a string
			second = arrayFitness[index[i + 1]][0];// getting the 2nd chromosome from the table
			converter = (int) second;
			binary2 = String.valueOf(converter);// making it a string
			i += 1;
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
		StringBuilder binaryMutate = new StringBuilder(mutation);
		if(mutationOption) {
			if (mutation.charAt(mutation.length() - 4) == '0') {
				binaryMutate.setCharAt(mutation.length() - 4, '1');
			} else {
				binaryMutate.setCharAt(mutation.length() - 4, '0');
			}
		}else {
			if (mutation.charAt(mutation.length() - 1) == '0') {
				binaryMutate.setCharAt(mutation.length() - 1, '1');
			} else {
				binaryMutate.setCharAt(mutation.length() - 1, '0');
			}
		}
		binariesForMutation.add(sortedMutationIndex, binaryMutate.toString());// replacing the mutated in the list

		replacePopulation();
	}

	public void auxiliarSingleCross(String binary1, String binary2) {
		String partI = binary1.substring(0, binary1.length() / 2);
		String partII = binary2.substring(0, binary2.length() / 2);
		String partIII = binary2.substring(binary2.length() / 2);
		String partIV = binary1.substring(binary1.length() / 2);
		binariesForMutation.add(partI + partIII);
		binariesForMutation.add(partII + partIV);
	}

	public void replacePopulation() {
		ArrayList<Integer> population = new ArrayList<Integer>();
		for (int j = 0; j < totalNumberRows; j++) {
			population.add(spreadSheet[j][currentColumnNumber]);
		}

		ArrayList<Integer> selectedNumbers = new ArrayList<Integer>();
		int lowestIndex = 0;
		int lowestNumber = 1000000;

		for (int x = 0; x < binariesForMutation.size() - 1; x++) {
			for (int i = 0; i < population.size(); i++) {
				if (i < population.size() && population.get(i) != 0 && population.get(i) < lowestNumber) {
					lowestNumber = population.get(i);
					lowestIndex = i;
				}
			}
			selectedNumbers.add(lowestNumber);
			population.remove(lowestIndex);
			lowestNumber = 1000000;
		}

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
		for (int i = 1; i < totalNumberRows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(spreadSheet[i][j] + " | ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
