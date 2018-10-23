package dzil123;

import java.util.List;

public abstract class Derivable {
    public boolean simplest = false;

    public Derivable derive() { return new Constant(1); }
    public String toString() { return " "; }
    public boolean isZero() { return false; }
    public boolean isOne() { return false; }
    public Derivable simplify() { return this; }
    public Boolean equal(Derivable other) { return null; }
    public List<Variable> deChain() { return null; }


    public boolean equals(Derivable other) {
        Boolean test1 = this.equal(other);
        if (test1 != null) {
            return test1;
        }

        Boolean test2 = other.equal(this);
        if (test2 != null) {
            return test2;
        }

        return false;
    }

}
