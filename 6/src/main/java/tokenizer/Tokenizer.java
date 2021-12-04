package tokenizer;

import states.*;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final List<Token> tokens;
    private final String expression;
    private int index;
    private State state;

    private static final char expressionEnd = '#';

    public Tokenizer(String expression) {
        this.expression = expression + expressionEnd;
        this.index = 0;
        this.tokens = new ArrayList<>();
        this.state = new StartState(this);
    }

    public List<Token> parse() {
        while (index < expression.length() && expression.charAt(index) != '#') {
            char symbol = expression.charAt(index);
            if (Character.isWhitespace(symbol)) {
                index++;
                continue;
            } else if (Character.isDigit(symbol)) {
                state = new NumberState(this);
            } else if (symbol == '(' || symbol == ')') {
                state = new BracketState(this);
            } else if (symbol == '/' || symbol == '*' || symbol == '-' || symbol == '+') {
                state = new OperationState(this);
            }
            tokens.add(state.createToken());
        }
        return tokens;
    }

    public char currentSymbol() {
        assert index < expression.length() : new IndexOutOfBoundsException(index);

        return expression.charAt(index);
    }

    public char nextSymbol() {
        index++;
        return currentSymbol();
    }
}