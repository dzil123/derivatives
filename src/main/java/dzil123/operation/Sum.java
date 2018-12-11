package dzil123.operation;

import java.util.*;

import dzil123.*;
import dzil123.helpers.Associative;
import dzil123.variable.Variable;

public class Sum extends Associative {
	public Sum(Derivable term1, Derivable term2) {
		super(term1, term2);
	}

	public static Derivable chain(Derivable[] terms) {
		return chain(Sum.class, terms);
	}

	public Derivable derive() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Sum)) {
			return simplification.derive();
		}
		
		// Sum rule: (u+v)' = u' + v'
		
		Derivable term1 = ((Sum) simplification).term1;
		Derivable term2 = ((Sum) simplification).term2;
		Sum sum = new Sum(term1.derive(), term2.derive());
		
		return sum.simplify();
		
	}

	public String toString() {
		return "(" + this.term1.toString() + ")+(" + this.term2.toString() + ")";
	}

	public Derivable simplify() {
		Derivable term1 = this.term1.simplify();
		Derivable term2 = this.term2.simplify();
		
		if (term1.isZero()) {
			return term2;
		} else if (term2.isZero()) {
			return term1;
		} else if ( (term1 instanceof Constant) && (term2 instanceof Constant)) {
			return new Constant( ((Constant)term1).value + ((Constant)term2).value );
		} else if (term1.equals(term2)) {
			return new Product(new Constant(2), term1);
		}

		return new Sum(term1, term2);
	}

	public boolean isZero() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Sum)) {
			return simplification.isZero();
		}
		
		return this.term1.isZero() && this.term1.isZero();
	}

	public boolean isOne() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Sum)) {
			return simplification.isOne();
		}
		
		return ( (this.term1.isOne() && this.term2.isZero()) || (this.term2.isOne() && this.term1.isZero()) );
	}

	public List<Variable> deChain() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Sum)) {
			return simplification.deChain();
		}

		throw new RuntimeException(); // TODO
	}
}
