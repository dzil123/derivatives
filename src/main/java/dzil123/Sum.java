package dzil123;

import java.util.*;

public class Sum extends Associative {
    public Sum(Derivable term1, Derivable term2) {
        super(term1, term2);
    }

    public static Derivable chain(Derivable term1, Derivable[] terms) {
        return chain(Sum.class, term1, terms);
    }

    public static Derivable chain(Derivable[] terms) {
        return chain(Sum.class, terms);
    }

    public Derivable derive() {
        // Product rule: (uv)' = (uv') + (vu')
        return new Sum(this.term1.derive(), this.term2.derive()).simplify();
    }

    public String toString() {
        return "(" + this.term1.toString() + "+" + this.term2.toString() + ")";
    }

    public Derivable simplify() {
        if (this.term1.isZero()) {
            return this.term2.simplify();
        } else if (this.term2.isZero()) {
            return this.term1.simplify();
        } else if (this.term1.equals(this.term2)) {
            return new Product(new Constant(2), this.term1.simplify());
        }

        return new Sum(this.term1.simplify(), this.term2.simplify());
    }

    public boolean isZero() {
        return this.term1.isZero() && this.term1.isZero(); // TODO more complex analysis
    }

    public boolean isOne() {
        return ( (this.term1.isOne() && this.term2.isZero()) || (this.term2.isOne() && this.term1.isZero()) );
    }

    public List<Variable> deChain() {
        Derivable simple = this.simplify();
        if (!(simple instanceof Sum)) {
            return simple.deChain();
        }

        System.out.println(term1);

        Monomial term1 = new Monomial (this.term1);
        Monomial term2 = new Monomial (this.term2);

        System.out.println(term1.simplify());
        System.out.println(term2.simplify());

        throw new RuntimeException();
    }
}
