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
		String input = "x 3 ^ ;";

		testParser(input);
		
		// Constant a = new Constant(3);
		// Constant b = new Constant(2);
		// Constant c = new Constant(1);
		// Constant d = new Constant(1);
		// X x = new X();

		// Exponent term1 = new Exponent(x, a);
		// Exponent term2 = new Exponent(x, b);
		// Exponent term3 = new Exponent(x, c);
		// Constant term4 = d;

		// Derivable equation = Sum.chain(new Derivable[]{term1, term2, term3, term4});

		// derive(equation);

	}
}
