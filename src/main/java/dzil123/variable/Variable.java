package dzil123.variable;

import java.util.*;

import dzil123.*;

public class Variable extends Derivable{
	public final boolean simplest = true;
	public final String repr;

	public Variable() {
		this.repr = "?";
	}

	public Variable(String repr) {
		this.repr = repr;
	}

	public Derivable derive() {
		throw new RuntimeException();
		//return new Constant(1);
	}

	public String toString() {
		//System.out.println(this.repr);
		return this.repr;
	}

	public Derivable simplify() {
		return this;
	}

	public boolean isZero() {
		return false;
	}

	public boolean isOne() {
		return false;
	}

	public Boolean equal(Derivable other) {
		if (other instanceof Variable) {
			return this.repr.equals(((Variable)other).repr);
		}
		return null;
	}

	public boolean canSimplify(Variable other) {
		return this.repr.equals(other.repr);
	}

	public List<Variable> deChain() {
		return Arrays.asList(this);
	}
}
