package textExcel;

import java.util.Scanner; 

//import java.io.FileNotFoundException;
import java.util.Scanner;

// Update this file with your own code.

public class TextExcel
{

	public static void main(String[] args)
	{
	    // Add your command loop here
		
		Spreadsheet a = new Spreadsheet();
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a command: ");
		String line = scan.nextLine();
		while(!line.equals("quit")){
			String output = a.processCommand(line);
			System.out.println(output);
			System.out.println("Enter a command: ");
			line = scan.nextLine();
		}

	}
}
