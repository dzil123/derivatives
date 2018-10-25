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
}
