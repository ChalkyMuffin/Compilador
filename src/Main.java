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

        PilasYCuadruplos pilas = new PilasYCuadruplos();
        MiVisitor visitor = new MiVisitor(pilas);
        visitor.visit(tree);

        // Imprimir tabla de variables después del parseo
        visitor.imprimirVariables();

        // Imprimir tabla de variables después del parseo
        visitor.imprimirFunciones();


        // Imprimir los cuádruplos generados
        System.out.println("\n=== CUÁDRUPLOS ===");
        pilas.imprimirCuadruplos();

    }
}
