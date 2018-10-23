package dzil123;

import java.util.*;

public class Product extends Associative {
    public Product(Derivable term1, Derivable term2) {
        super(term1, term2);
    }

    public static Derivable chain(Derivable term1, Derivable[] terms) {
        return chain(Product.class, term1, terms);
    }

    public static Derivable chain(Derivable[] terms) {
        return chain(Product.class, terms);
    }
    
    // public static Derivable Product(Derivable term1, Derivable[] terms) {
    //     if (terms.length == 0) {
    //         return term1;
    //     } else if (terms.length == 1) {
    //         return new Product(term1, terms[0]);
    //     }

    //     return new Product(term1, Product.Product(terms));

    // }

    // public static Derivable Product(Derivable[] terms) {
    //     if (terms.length == 0) {
    //         return new Constant(1);
    //     } else if (terms.length == 1) {
    //         return terms[0];
    //     } else if (terms.length == 2) {
    //         return new Product(terms[0], terms[1]);
    //     }

    //     Product firstTwoTerms = new Product(terms[0], terms[1]);
    //     Derivable[] restOfTerms = Arrays.copyOfRange(terms, 2, terms.length);

    //     return Product.Product(firstTwoTerms, restOfTerms);
    // }

    public Derivable derive() {
        // Product rule: (uv)' = (uv') + (vu')
        
        Derivable term1deriv = this.term1.derive();
        Derivable term2deriv = this.term2.derive();
        
        // if ( (this.term1.isZero() || term2deriv.isZero()) && (this.term2.isZero() || term1deriv.isZero()) ) {
        //     return new Constant(0);
        // } else if ( (this.term1.isZero() || term2deriv.isZero()) ) {
        //     return new Product(this.term2, term1deriv);
        // } else if ( (this.term2.isZero() || term1deriv.isZero()) ) {
        //     return new Product(this.term1, term2deriv);
        // }

        return new Sum(new Product(this.term2, term1deriv), new Product(this.term1, term2deriv)).simplify();
    }

    private static Derivable simplifySum(Sum sum, Derivable term) {
        return new Product(sum.simplify(), term.simplify()); // TODO do the a(u+v) = a*u + a*v
    }

    public Derivable simplify() {
        if (this.term1.isZero() || this.term2.isZero()) {
            return new Constant(0);
        } else if (this.term1.isOne()) {
            return this.term2.simplify();
        } else if (this.term2.isOne()) {
            return this.term1.simplify();
        } else if (this.term1.equals(this.term2)) {
            // return new Exponent(this.term1.simplify(), new Constant(2)); // lol Product.simplify() returns Exponent, Exponent.simplify() returns Product
        } else if (this.term1 instanceof Sum) {
            return Product.simplifySum((Sum)term1, term2);
        } else if (this.term2 instanceof Sum) {
            return Product.simplifySum((Sum)term2, term1);
        }

        return new Product(this.term1.simplify(), this.term2.simplify());
    }

    public String toString() {
        return "(" + this.term1.toString() + "*" + this.term2.toString() + ")";
    }

    public boolean isZero() {
        return this.term1.isZero() || this.term2.isZero();
    }

    public boolean isOne() {
        return this.term1.isOne() && this.term2.isOne();
    }

    public List<Variable> deChain() {
        List<Variable> factors = new ArrayList<Variable>();

        factors.addAll(this.term1.deChain());
        factors.addAll(this.term2.deChain());

        return factors;
    }

}
