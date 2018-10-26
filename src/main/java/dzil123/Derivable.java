package dzil123;

import java.util.List;

import dzil123.variable.Variable;

public abstract class Derivable {
	public final boolean simplest = false;

	public Derivable derive() { return new Constant(1); }
	public String toString() { return " "; }
	public boolean isZero() { return false; }
	public boolean isOne() { return false; }
	public Derivable simplify() { return this; }
	public Boolean equal(Derivable other) { return null; }
	public List<Variable> deChain() { return null; }


	public boolean equals(Derivable other) {
		Derivable thisSimple = this.simplify();
		Derivable otherSimple = other.simplify();
		
		Boolean test1 = thisSimple.equal(otherSimple);
		if (test1 != null) {
			return test1;
		}

		Boolean test2 = otherSimple.equal(thisSimple);
		if (test2 != null) {
			return test2;
		}

		return false;
	}

}
