package states;

import tokenizer.OperationToken;
import tokenizer.Token;
import tokenizer.Tokenizer;

public class OperationState extends State {
    public OperationState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        final char symbol = tokenizer.currentSymbol();
        tokenizer.nextSymbol();

        Token.TokenType type = switch (symbol) {
            case '+' -> Token.TokenType.PLUS;
            case '-' -> Token.TokenType.MINUS;
            case '*' -> Token.TokenType.MUL;
            case '/' -> Token.TokenType.DIV;
            default -> throw new IllegalArgumentException("Illegal char '" + symbol);
        };

        return new OperationToken(type);
    }
}