package dzil123.input;

import java.util.*;

import dzil123.*;
import dzil123.operation.*;
import dzil123.variable.X;

public class Polish {
    private Stack<Derivable> tokens;

    public Polish() {
        this.tokens = new Stack<Derivable>();
    }

    public Derivable getResult() {
        return tokens.peek();
    }

    public void parseTokenLoop(Scanner input) {
        while (true) {
            //System.out.print("Enter> ");
            String token = input.next();
            if (token.charAt(0) == ';') {
                break;
            }

            this.parseToken(token);
        }
    }

    public void parseToken(String token) {
        Derivable token1;
        Derivable token2;

        try {
            double value = Double.parseDouble(token);
            this.addConstantToken(value);
        } catch (NumberFormatException e) {
            switch (token.charAt(0)) {
                case 'x':
                    this.addToken(new X());
                    break;
                case '*':
                    token2 = this.getToken();
                    token1 = this.getToken();
                    this.addToken(new Product(token1, token2));
                    break;
                case '+':
                    token2 = this.getToken();
                    token1 = this.getToken();
                    this.addToken(new Sum(token1, token2));
                    break;
                case '^':
                    token2 = this.getToken();
                    token1 = this.getToken();
                    this.addToken(new Exponent(token1, token2));
                    break;
                case ' ':
                    break;
                default:
                    throw new RuntimeException();
            }
        }
    }

    public void addConstantToken(double value) {
        Constant token = new Constant(value);
        this.tokens.push(token);
    }

    public void addToken(Derivable token) {
        this.tokens.push(token);
    }

    public Derivable getToken() {
        return this.tokens.pop();
        //this.tokens.
    }

}
