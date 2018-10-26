package dzil123.operation;

import java.util.*;

import dzil123.*;
import dzil123.variable.Variable;

public class Exponent extends Derivable {
    public final Derivable exponent;
    public final Derivable base;

    protected Exponent() {
        this(new Constant(0), new Constant(0));
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
