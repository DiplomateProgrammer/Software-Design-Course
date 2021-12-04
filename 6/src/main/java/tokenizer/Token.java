package tokenizer;

import visitors.TokenVisitor;

public interface Token {
    public enum TokenType {
        LEFT_BR,
        RIGHT_BR,
        PLUS,
        MINUS,
        DIV,
        MUL,
        NUMBER;

        public int getPriority() {
            return switch (this) {
                case LEFT_BR, RIGHT_BR -> 0;
                case PLUS, MINUS -> 1;
                case DIV, MUL -> 2;
                default -> -1;
            };
        }
    }

    void accept(TokenVisitor visitor);

    TokenType getTokenType();

    String toString();
}