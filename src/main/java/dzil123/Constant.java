package dzil123;

import dzil123.variable.Variable;

public class Constant extends Variable {
    public final double value;

    public Constant() {
        this(1);
    }

    public Constant(double value) {
        super(Double.toString(value));
        this.value = value;
    }

    public Derivable derive() {
        return new Constant(0);
    }

    // public boolean equals(Constant other) {
    //     return this.value == other.value;
    // }

    // public boolean equals(double other) {
    //     return this.value == other;
    // }

    // public boolean equals(int other) {
    //     return this.value == other;
    // }

    public boolean isZero() {
        return this.value == 0.0;
    }

    public boolean isOne() {
        return this.value == 1.0;
    }

    public Derivable simplify() {
        return this;
    }

    public Boolean equal(Derivable other) {
        if (other instanceof Constant) {
            Constant c = (Constant) other;
            return (this.value == c.value);
        }
        return null;
    }

    public boolean canSimplify(Variable other) {
        if (other instanceof Constant) {
            return true;
        }
        return super.canSimplify(other);
    }
}
