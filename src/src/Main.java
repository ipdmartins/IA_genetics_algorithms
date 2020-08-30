package src;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws BiffException, IOException {
		
		Generator gen = new Generator();
//		gen.sheetReader();
		
		View view = new View();
		
		int num [][] = new int [2][5];
		int a = 1;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 5; j++) {
				num [i][j] = a;
				//System.out.print(num [i][j]);
			}
			a++;
			//System.out.println();
		}

	}

}
