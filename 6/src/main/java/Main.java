import tokenizer.Token;
import tokenizer.Tokenizer;
import visitors.CalcVisitor;
import visitors.ParserVisitor;
import visitors.PrintVisitor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            assert args.length != 1 : new IllegalArgumentException("You need to input one argument: the expression");
            String expression = args[0];

            Tokenizer tokenizer = new Tokenizer(expression);
            List<Token> tokens = tokenizer.parse();
            ParserVisitor parserVisitor = new ParserVisitor();
            tokens = parserVisitor.parse(tokens);

            System.out.println("Expression in polish notation: " + new PrintVisitor().walk(tokens));
            System.out.println("Calculated value: " + new CalcVisitor().calculate(tokens));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}