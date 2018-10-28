package dzil123.helpers;

import java.lang.reflect.*;

import dzil123.*;

public abstract class Associative extends Derivable {
	public final Derivable term1;
	public final Derivable term2;

	public Associative(Derivable term1, Derivable term2) {
		this.term1 = term1;
		this.term2 = term2;
	}

	protected static Derivable chain(Class <? extends Associative> type, Derivable[] terms) {
		Constructor<? extends Associative> constructor = null;
		
		try {
			constructor = type.getConstructor(Derivable.class, Derivable.class);
		} catch (NoSuchMethodException e) {e.printStackTrace();}
		
		
		if (terms.length == 0) {
			return new Constant(1);
		} else if (terms.length == 1) {
			return terms[0];
		}
		
		
		try {

			Derivable result = (Derivable)constructor.newInstance(terms[0], terms[1]);

			for (int index = 2; index < terms.length; index++) {
				result = (Derivable)constructor.newInstance(result, terms[index]); // (((a*b)*c)*d)
			}

			return result;
		
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {e.printStackTrace();}
		
		return null; // Won't run
	}
	
	public Boolean equal(Derivable other) {
		if (other instanceof Associative) {
			Associative product = (Associative) other;
			
			Derivable t1 = this.term1;
			Derivable t2 = this.term2;
			Derivable o1 = product.term1;
			Derivable o2 = product.term2;
			
			boolean p1 = t1.equals(o1) && t2.equals(o2);
			boolean p2 = t1.equals(o2) && t2.equals(o1);
			
			if (p1 || p2) {
				return true;
			}
		}
		return null;
	}
}
