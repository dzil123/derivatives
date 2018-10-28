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
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Exponent)) {
			return simplification.isZero();
		}
		
		return this.base.isZero() && !(this.exponent.isZero());
	}

	public boolean isOne() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Exponent)) {
			return simplification.isOne();
		}
		
		return this.base.isOne() || this.exponent.isZero();
	}

	public Derivable derive() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Exponent)) {
			return simplification.derive();
		}
		
		Derivable exponent = ((Exponent) simplification).exponent;
		Derivable base = ((Exponent) simplification).base;
		
		if (exponent instanceof Constant) { // (u^n)' = n * u^(n-1)* u'
			Constant constExponent = (Constant) exponent;
			Exponent term2 = new Exponent(base, new Constant(constExponent.value - 1));
			Derivable[] terms = {constExponent, term2.simplify(), base.derive()};
			
			return Product.chain(terms).simplify();
		} else if (base instanceof Constant) { // TODO (a^u)' = ln(a) * a^u * u'
			throw new RuntimeException();
		} else { // TODO x^x or other u^v
			throw new RuntimeException();
		}
	}

	public Derivable simplify() {
		Derivable exponent = this.exponent.simplify();
		Derivable base = this.base.simplify();
		
		if (exponent.isZero()) {
			return new Constant(1);
		} else if (exponent.isOne()) {
			return base.simplify();
		} else if (base.isZero()) {
			return new Constant(0);
		} else if (base.isOne()) {
			return new Constant(1);
		} else if (exponent instanceof Constant) {
			Constant constExponent = (Constant)exponent;
			if (base instanceof Constant) {
				Constant constBase = (Constant)base;
				return new Constant(Math.pow(constBase.value, constExponent.value));
			}
			if (constExponent.value == Math.rint(constExponent.value)) { // Test if double == int; equivalent to x == Math.floor(x)
				Derivable[] terms = new Derivable[(int)(constExponent.value)];
				Arrays.fill(terms, this.base.simplify());
				return Product.chain(terms).simplify();
			}
		}
		
		return new Exponent(base, exponent);
	}

	public List<Variable> deChain() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Exponent)) {
			return simplification.deChain();
		}
		
		throw new RuntimeException(); // TODO
	}
	
	public Boolean equal(Derivable other) {
		if (other instanceof Exponent) {
			Exponent exp = (Exponent) other;
			
			boolean baseE = this.base.equals(exp.base);
			boolean exponentE =  this.exponent.equals(exp.exponent);
			
			if (baseE && exponentE) {
				return true;
			}
			
		}
		
		return null;
	}
}
