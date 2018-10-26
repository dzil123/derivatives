package dzil123;

import java.util.*;

import dzil123.operation.*;
import dzil123.variable.*;

public class Monomial extends Derivable {
    public List<Variable> terms;
    
    public Monomial(Variable[] terms) {
        this(Arrays.asList(terms));
    }

    public Monomial(List<Variable> terms) {
        this.terms = terms;
    }

    public Monomial(Derivable term) {
        this(term.deChain());
    }

    public Derivable toProduct() {
        return Product.chain(this.terms.toArray(new Derivable[]{}));
    }

    public Derivable simplify() {
        return Product.chain(this.simplifyMonomial()).simplify();
    }

    public VariableTerm[] simplifyMonomial() {
        List<VariableTerm> variableTerms = new ArrayList<VariableTerm>();

        for (Variable term : this.terms) {
            boolean foundExisting = false;
            for (VariableTerm variableTerm : variableTerms) {
                //System.out.println(term.toString() + " " + variableTerm.toString());
                if (variableTerm.canSimplify(term)) {
                    //variableTerm.changeExponent(1);
                    variableTerm.multiplyWith(term);
                    //System.out.println("found");
                    foundExisting = true;
                    break;
                }
            }

            if (!foundExisting) {
                //System.out.println("not found");
                variableTerms.add(new VariableTerm(term, new Constant(1)));
            }
        }

        return variableTerms.toArray(new VariableTerm[]{});
    }
    
    public Derivable derive() {
        return this.simplify().derive();
    }

    public String toString() {
        return this.toProduct().toString();
    }

    public List<Variable> deChain() {
        return this.terms;
    }
}