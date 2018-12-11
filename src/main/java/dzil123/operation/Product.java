package dzil123.operation;

import java.util.*;

import dzil123.*;
import dzil123.helpers.Associative;
import dzil123.variable.Variable;

public class Product extends Associative {
	public Product(Derivable term1, Derivable term2) {
		super(term1, term2);
	}
	
	public static Derivable[] simplifyTerms(Derivable[] terms) {
		List<Derivable> list = new ArrayList<Derivable>();
		double total = 1;
		
		for (Derivable item : terms) {
			if (item instanceof Constant) {
				Constant constant = (Constant) item;
				total *= constant.value;
			} else {
				list.add(item);
			}
		}
		
		if (!(total == 1)) {
			list.add(0, new Constant(total));
		}
		
		return list.toArray(new Derivable[]{});
	}
	
	public static Derivable chain(Derivable[] terms) {
		//terms = Product.simplifyTerms(terms);
		return chain(Product.class, terms);
	}

	public Derivable derive() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Product)) {
			return simplification.derive();
		}
		
		Derivable term1 = ((Product) simplification).term1;
		Derivable term2 = ((Product) simplification).term2;
		
		// Product rule: (uv)' = (uv') + (vu')
		
		Derivable term1deriv = term1.derive();
		Derivable term2deriv = term2.derive();
		
		// if ( (this.term1.isZero() || term2deriv.isZero()) && (this.term2.isZero() || term1deriv.isZero()) ) {
		//     return new Constant(0);
		// } else if ( (this.term1.isZero() || term2deriv.isZero()) ) {
		//     return new Product(this.term2, term1deriv);
		// } else if ( (this.term2.isZero() || term1deriv.isZero()) ) {
		//     return new Product(this.term1, term2deriv);
		// }
		
		Product left = new Product(term2, term1deriv);
		Product right = new Product(term1, term2deriv);
		Sum sum = new Sum(left, right);

		return sum.simplify();
	}

	private static Derivable simplifyAssociative(Associative associative, Derivable term) {
		Derivable term1 = new Product(term, associative.term1); // a(u+v) = a*u + a*v
		Derivable term2 = new Product(term, associative.term2);
		Derivable result = null;
		
		if (associative instanceof Product) {
			//result = new Product(term1, term2);
			try {
				Derivable[] terms = (new Product(associative, term)).deChain().toArray(new Derivable[]{});
				return Product.chain(Product.simplifyTerms(terms));
				//throw new RuntimeException();
			} catch (RuntimeException e) {
				return new Product(associative, term);
			}
		} else if (associative instanceof Sum) {
			result = new Sum(term1, term2);
		} else {
			throw new RuntimeException();
		}
		return result.simplify();
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
		// } else if (term1.equals(term2)) {
			// return new Exponent(this.term1.simplify(), new Constant(2)); // lol Product.simplify() returns Exponent, Exponent.simplify() returns Product
		} else if (term1 instanceof Associative) {
			return Product.simplifyAssociative((Associative)term1, term2);
		} else if (term2 instanceof Associative) {
			return Product.simplifyAssociative((Associative)term2, term1);
		}

		return new Product(term1, term2);
	}

	public String toString() {
		return "" + this.term1.toString() + "*" + this.term2.toString() + "";
	}

	public boolean isZero() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Product)) {
			return simplification.isZero();
		}
		
		return this.term1.isZero() || this.term2.isZero();
	}

	public boolean isOne() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Product)) {
			return simplification.isOne();
		}
		
		return this.term1.isOne() && this.term2.isOne();
	}

	public List<Variable> deChain() {
		List<Variable> factors = new ArrayList<Variable>();

		factors.addAll(this.term1.deChain());
		factors.addAll(this.term2.deChain());

		return factors;
	}
}
