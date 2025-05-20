import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.nio.file.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String input = Files.readString(Path.of("input.txt"));

        CharStream cs = CharStreams.fromString(input);
        ExprLexer lexer = new ExprLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);

        ParseTree tree = parser.prog(); // o la regla raíz que tengas

        MiVisitor visitor = new MiVisitor();
        visitor.visit(tree);

        // Imprimir tabla de variables después del parseo
        visitor.imprimirVariables();

        visitor.visit(tree);
        visitor.imprimirFunciones();

    }
}
