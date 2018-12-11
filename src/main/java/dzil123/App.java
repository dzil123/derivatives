package dzil123;

import java.util.Scanner;

import dzil123.input.*;
import dzil123.operation.*;

public class App {

	public static void derive(Derivable derive) {
		System.out.println(derive);
		System.out.println(derive.simplify());
		System.out.println(derive.derive());
		System.out.println(derive.derive().simplify());
		System.out.println();
	}
	
	public static void testParser(String input) {
		Polish parser = new Polish();
		Scanner reader = new Scanner(input);

		parser.parseTokenLoop(reader);

		derive(parser.getResult());
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		// while (true) {
		// 	System.out.print("> ");
		// 	String userInput = input.nextLine();
		// 	testParser(userInput);
		// }
		
		testParser("4 x * -1 1 + ^");
		
	}
}
