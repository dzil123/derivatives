package dzil123.operation;

import java.util.*;

import dzil123.*;
import dzil123.helpers.Associative;
import dzil123.variable.Variable;

public class Product extends Associative {
	public Product(Derivable term1, Derivable term2) {
		super(term1, term2);
	}

	public static Derivable chain(Derivable[] terms) {
		return chain(Product.class, terms);
	}

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
		Derivable term1 = this.term1.simplify();
		Derivable term2 = this.term2.simplify();
		
		if (term1.isZero() || term2.isZero()) {
			return new Constant(0);
		} else if (term1.isOne()) {
			return term2;
		} else if (term2.isOne()) {
			return term1;
		} else if ( (term1 instanceof Constant) && (term2 instanceof Constant)) {
			return new Constant( ((Constant)term1).value * ((Constant)term2).value );
		} else if (term1.equals(term2)) {
			// return new Exponent(this.term1.simplify(), new Constant(2)); // lol Product.simplify() returns Exponent, Exponent.simplify() returns Product
		} else if (term1 instanceof Sum) {
			return Product.simplifySum((Sum)term1, term2);
		} else if (term2 instanceof Sum) {
			return Product.simplifySum((Sum)term2, term1);
		}

		return new Product(term1, term2);
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
