package dzil123;

public class VariableTerm extends Exponent{
	// Variable base;
	// Constant exponent;

	public VariableTerm(Variable base, Constant exponent) {
		// this.base = base;
		// this.exponent = exponent;
		super(base, exponent);
	}

	private void changeExponent(int change) {
		this.exponent = new Constant((int) ( ((Constant)(this.exponent)) .value + change));
	}

	public boolean canSimplify(Variable other) {
		//System.out.println("variableTerm: " + this.base.toString() + " " + other.repr.toString());
		return ((Variable)this.base).repr.equals(other.repr) || (this.base instanceof Constant && other instanceof Constant);
	}

	public void multiplyWith(Variable other) {
		if (!this.canSimplify(other)) {
			return;
		}

		if (other instanceof Constant) {
			this.base = new Constant( ((Constant)this.base).value * ((Constant)other).value);
			return;
		}
		
		this.changeExponent(1);
	}

	public String toString() {
		return "(" + ((Variable)this.base).toString() + ")^(" + this.exponent + ")";
	}
}