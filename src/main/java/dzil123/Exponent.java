package dzil123;

import java.util.*;

public class Exponent extends Derivable {
    Constant exponent;
    Derivable base;

    protected Exponent() {

    }

    public Exponent(Derivable base, Constant exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    public String toString() {
        return "(" + this.base.toString() + ")^(" + this.exponent + ")";
    }

    public boolean isZero() {
        return this.base.isZero() && !(this.exponent.isZero());
    }

    public boolean isOne() {
        return this.exponent.isZero();
    }

    public Derivable derive() {
        // Derivable[] terms = new Derivable[(int)this.exponent.value];
        // Arrays.fill(terms, this.base);

        // return Product.chain(terms).derive().simplify();
        return this.simplify().derive();
    }

    public Derivable simplify() {
        Derivable[] terms = new Derivable[(int)this.exponent.value];
        Arrays.fill(terms, this.base.simplify());

        return Product.chain(terms).simplify();
    }

    public List<Variable> deChain() {
        return this.simplify().deChain();
    }
}
