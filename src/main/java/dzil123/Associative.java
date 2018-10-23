package dzil123;

import java.lang.reflect.*;
import java.util.Arrays;

public abstract class Associative extends Derivable {
    Derivable term1;
    Derivable term2;

    public Associative(Derivable term1, Derivable term2) {
        this.term1 = term1;
        this.term2 = term2;
    }

    protected static Derivable chain(Class <? extends Associative> type, Derivable[] terms) {
        return Associative.chain(Associative.getSubclassConstructor(type), terms);
    }

    public static Constructor<?> getSubclassConstructor(Class <? extends Associative> type) {
        try { // shut up java
            // return new Object() { }.getClass().getEnclosingClass().getConstructor(Derivable.class, Derivable.class);
            // return Product.class.getConstructor(Derivable.class, Derivable.class);
            return type.getConstructor(Derivable.class, Derivable.class);
        } catch (NoSuchMethodException e) {e.printStackTrace(); System.exit(1);}
        return null; // Will never run, just neccesarry to get java to shut up
    }

    private static Derivable chain(Constructor<?> constuctor, Derivable[] terms) {
        try { // shut up java

            if (terms.length == 0) {
                return new Constant(1);
            } else if (terms.length == 1) {
                return terms[0];
            } else if (terms.length == 2) {
                return (Derivable) constuctor.newInstance(terms[0], terms[1]);
            }

            Derivable firstTerm = terms[0];
            Derivable[] restOfTerms = Arrays.copyOfRange(terms, 1, terms.length);
            
            return (Derivable) constuctor.newInstance(firstTerm, Associative.chain(constuctor, restOfTerms));

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {e.printStackTrace();}
        return null; // Will never run, just neccesarry to get java to shut up
    }
}
