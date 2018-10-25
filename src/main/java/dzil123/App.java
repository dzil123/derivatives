package dzil123;

import dzil123.input.*;
import java.util.Scanner;

public class App {

    public static void derive(Derivable derive) {
        System.out.println(derive);
        System.out.println(derive.simplify());
        System.out.println(derive.derive());
        System.out.println(derive.derive().simplify());
        System.out.println();
    }

    public static void main(String[] args) {

        Polish parser = new Polish();
        //Scanner input = new Scanner(System.in);
        //Scanner input = new Scanner("x 1 ^ 4 * 5 x * + ; ");
        Scanner input = new Scanner("1 1 + 2 + 3 1 1 + 7 - 15 / * - ;");

        parser.parseTokenLoop(input);

        derive(parser.getResult());
        
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
