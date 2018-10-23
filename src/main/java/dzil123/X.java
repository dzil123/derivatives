package dzil123;


public class X extends Variable {
    public X() {
        this.repr = "x";
    }

    public Derivable derive() {
        return new Constant(1);
    }
    
    public Boolean equal(Derivable other) {
        if (other instanceof X) {
            return true;
        }
        return null;
    }
}
