package dzil123;

public class App {

    public static void derive(Derivable derive) {
        System.out.println(derive);
        System.out.println(derive.simplify());
        System.out.println(derive.derive());
        System.out.println(derive.derive().simplify());
        System.out.println();
    }


    public static void main(String[] args) {
        //derive(Product.chain(new Derivable[]{new Product(new X(), new X()), new X(), new Constant(2)}));

        // Constant o = new Constant(1);
        // Constant t = new Constant(2);
        // Constant th = new Constant(3);
        // X x = new X();

        // Monomial a = new Monomial(new Variable[]{t,x,x,t,th,x});
        // System.out.println(a);
        // System.out.println(a.simplify());
        // Derivable b = a.simplify().derive();
        // System.out.println(b);
        // Monomial c = new Monomial(b);
        // System.out.println(c.simplify());

        Constant a = new Constant(6);
        Constant b = new Constant(-9);
        Constant c = new Constant(4);
        Constant t = new Constant(3);
        X x = new X();

        Exponent term2 = new Exponent(x, t);
        Product term = new Product(a, term2);
        Monomial first = new Monomial(term);
        //System.out.println(first);
        //System.out.println(first.simplify());

        Monomial second = new Monomial(new Product(b, x));
        Monomial third = new Monomial(c);

        Derivable answer = Sum.chain(new Derivable[]{first, second, third});

        System.out.println(answer);
        //System.out.println(answer.derive());
        System.out.println(answer.derive().simplify());

    }
}
