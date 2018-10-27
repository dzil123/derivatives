package dzil123.operation;

import java.util.List;

import dzil123.*;
import dzil123.variable.Variable;

public class Quotient extends Derivable {
	public final Derivable top;
	public final Derivable bottom;
	
	public Quotient(Derivable top, Derivable bottom) {
		if (bottom.isZero()) {
			throw new ArithmeticException("bottom cannot be zero");
		}
		
		this.top = top;
		this.bottom = bottom;
	}
	
	public String toString() {
		return "(" + this.top.toString() + ")/(" + this.bottom.toString() + ")";
	}
	
	public boolean isZero() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Quotient)) {
			return simplification.isZero();
		}
		
		return this.top.isZero();
	}
	
	public boolean isOne() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Quotient)) {
			return simplification.isOne();
		}
		
		return this.top.equals(this.bottom);
	}
	
	public Derivable simplify() {
		Derivable top = this.top.simplify();
		Derivable bottom = this.bottom.simplify();
		
		if (bottom.isZero()) {
			throw new ArithmeticException("bottom cannot be zero");
		} else if (top.isZero()) {
			return new Constant(0);
		} else if (top.equals(bottom)) {
			return new Constant(1);
		} else if ( (top instanceof Constant) && (bottom instanceof Constant) ) {
			return new Constant( ((Constant)(top)).value / ((Constant)(bottom)).value );
		}
		
		return new Quotient(top, bottom);
	}
	
	public Boolean equal(Derivable other) {
		if (other instanceof Quotient) {
			Quotient quo = (Quotient) other;
			boolean topE = this.top.equals(quo.top);
			boolean bottomE = this.bottom.equals(quo.bottom);
			boolean result = topE && bottomE;
			
			if (!result) {
				// TODO
			}
			return result;
		}
		
		return null;
	}
	
	public Derivable derive() {
		Derivable simplification = this.simplify();
		if (!(simplification instanceof Quotient)) {
			return simplification.derive();
		}
		
		// (uv'-vu')/(v^2)
		Derivable top = ((Quotient) simplification).top;
		Derivable bottom = ((Quotient) simplification).bottom;
		
		Derivable left = new Product(bottom, top.derive());
		Derivable right = Product.chain(new Derivable[] {new Constant(-1), top, bottom.derive()});
		Derivable newTop = new Sum(left, right);
		Derivable newBottom = new Exponent(bottom, new Constant(2));
		Derivable result = new Quotient(newTop, newBottom);
		return result;
	}
	
	public List<Variable> deChain() {
		return null;
	}
}
