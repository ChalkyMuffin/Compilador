import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String input = Files.readString(Path.of("factorial.txt"));

        CharStream cs = CharStreams.fromString(input);
        ExprLexer lexer = new ExprLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);

        ParseTree tree = parser.prog();

        PilasYCuadruplos pilas = new PilasYCuadruplos();
        MiParser visitor = new MiParser(pilas);
        visitor.visit(tree);

        visitor.imprimirVariables();
        visitor.imprimirConstantes();
        visitor.imprimirFunciones();

        // Imprimir los cuádruplos generados
        System.out.println("\n=== CUÁDRUPLOS ===");
        pilas.imprimirCuadruplos();

        // OPCIÓN 1: Ejecutar con debug completo (como antes)
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EJECUCIÓN CON INFORMACIÓN DE DEBUG:");
        System.out.println("=".repeat(60));

        OutputManager outputManagerDebug = new OutputManager(true); // con debug
        MaquinaVirtual mvDebug = new MaquinaVirtual(
                pilas.listaCuadruplos(),
                visitor.tabla,
                visitor.tablaConstantes,
                outputManagerDebug,visitor.pilas
        );
        mvDebug.ejecutar();

        // OPCIÓN 2: Ejecutar solo mostrando la salida del programa
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EJECUCIÓN LIMPIA (SOLO SALIDA DEL PROGRAMA):");
        System.out.println("=".repeat(60));

        OutputManager outputManagerClean = new OutputManager(false); // sin debug
        MaquinaVirtual mvClean = new MaquinaVirtual(
                pilas.listaCuadruplos(),
                visitor.tabla,
                visitor.tablaConstantes,
                outputManagerClean, visitor.pilas
        );
        mvClean.ejecutar();

        // OPCIÓN 3: Obtener la salida programáticamente
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SALIDA OBTENIDA PROGRAMÁTICAMENTE:");
        System.out.println("=".repeat(60));

        for (String linea : mvClean.getProgramOutput()) {
            System.out.println("Salida: " + linea);
        }
    }
}