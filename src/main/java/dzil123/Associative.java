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

    protected static Derivable chain(Class <? extends Associative> type, Derivable term1, Derivable[] terms) {
        return Associative.chain(Associative.getSubclassConstructor(type), term1, terms);
    }

    protected static Derivable chain(Class <? extends Associative> type, Derivable[] terms) {
        return Associative.chain(Associative.getSubclassConstructor(type), terms);
    }

    public static Constructor<?> getSubclassConstructor(Class <? extends Associative> type) {
        try { // shut up java
            // System.out.println(new Object() { });
            // System.out.println(new Object() { }.getClass());
            // System.out.println(new Object() { }.getClass().getEnclosingClass());
            // System.out.println(new Object() { }.getClass().getEnclosingClass().getEnclosingClass());

            // System.out.println(new Object() { }.getClass().getEnclosingClass().getConstructor(Derivable.class, Derivable.class));
            // System.out.println();
            //return new Object() { }.getClass().getEnclosingClass().getConstructor(Derivable.class, Derivable.class);
            // return Product.class.getConstructor(Derivable.class, Derivable.class);\
            return type.getConstructor(Derivable.class, Derivable.class);
        } catch (NoSuchMethodException e) {System.out.println("oops");}
        return null;
    }


    private static Derivable chain(Constructor<?> constuctor, Derivable term1, Derivable[] terms) {
        try { // only neccesarry to get java to shut up about uncaught throwables

            if (terms.length == 0) {
                return term1;
            } else if (terms.length == 1) {   
                return (Derivable) constuctor.newInstance(term1, terms[0]);
            }

            return (Derivable) constuctor.newInstance(term1, Associative.chain(constuctor, terms));

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {e.printStackTrace();}
        //return new Constant(1); // Will never run, just neccesarry to get java to shut up
        return null;
    }

    private static Derivable chain(Constructor<?> constuctor, Derivable[] terms) {
        try { // only neccesarry to get java to shut up about uncaught throwables

            if (terms.length == 0) {
                return new Constant(1);
            } else if (terms.length == 1) {
                return terms[0];
            } else if (terms.length == 2) {
                return (Derivable) constuctor.newInstance(terms[0], terms[1]);
            }

            Derivable firstTwoTerms = (Derivable) constuctor.newInstance(terms[0], terms[1]);
            Derivable[] restOfTerms = Arrays.copyOfRange(terms, 2, terms.length);
            
            return Associative.chain(constuctor, firstTwoTerms, restOfTerms);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {e.printStackTrace();}
        //return new Constant(1); // Will never run, just neccesarry to get java to shut up
        return null;
    }

}
