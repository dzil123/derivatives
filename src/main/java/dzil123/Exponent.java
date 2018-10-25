package dzil123;

import java.util.*;

public class Exponent extends Derivable {
    Derivable exponent;
    Derivable base;

    protected Exponent() {

    }

    public Exponent(Derivable base, Derivable exponent) {
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
        Derivable simplification = this.simplify();
        if (simplification instanceof Exponent) {
            throw new RuntimeException(); // TODO a^u
        }
        
        return simplification.derive();
    }

    public Derivable simplify() {
        Derivable simplification = null;
        Derivable exponent = this.exponent.simplify();
        Derivable base = this.base.simplify();
        
        if (exponent.isZero()) {
            simplification = new Constant(1);
        } else if (exponent instanceof Constant) {
            Constant constExponent = (Constant)exponent;
            Derivable[] terms = new Derivable[(int)(constExponent.value)];
            Arrays.fill(terms, this.base.simplify());
            simplification = Product.chain(terms);
        } else if (base instanceof Constant) {
            return this;
        }
        
        return simplification.simplify();
    }

    public List<Variable> deChain() {
        return this.simplify().deChain();
    }
}
