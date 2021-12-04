package states;

import tokenizer.BracketToken;
import tokenizer.Token;
import tokenizer.Tokenizer;

public class BracketState extends State {
    public BracketState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        char symbol = tokenizer.currentSymbol();
        tokenizer.nextSymbol();

        Token.TokenType type = switch (symbol) {
            case '(' -> Token.TokenType.LEFT_BR;
            case ')' -> Token.TokenType.RIGHT_BR;
            default -> throw new IllegalArgumentException("Illegal char:" + symbol);
        };

        return new BracketToken(type);
    }
}