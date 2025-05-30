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
        MiParser visitor = new MiParser(pilas);
        visitor.visit(tree);

        visitor.imprimirVariables();
        visitor.imprimirConstantes();
        visitor.imprimirFunciones();

        // Imprimir los cuádruplos generados
        System.out.println("\n=== CUÁDRUPLOS ===");
        pilas.imprimirCuadruplos();

        // NUEVA PARTE: Ejecutar los cuádruplos
        MaquinaVirtual mv = new MaquinaVirtual(
                pilas.listaCuadruplos(),
                visitor.tabla,
                visitor.tablaConstantes
        );
        mv.ejecutar();
    }
}