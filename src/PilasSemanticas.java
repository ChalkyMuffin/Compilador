import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

public class PilasSemanticas {
    // Pila de operandos (IDs, temporales, constantes)
    public Stack<String> operandos = new Stack<>();

    // Pila de operadores: +, -, *, /, <, >
    public Stack<String> operadores = new Stack<>();

    // Pila de tipos: int, float, etc.
    public Stack<String> tipos = new Stack<>();

    // Fila de cuádruplos generados
    public List<Cuadruplo> cuadruplos = new ArrayList<>();

    private int contadorTemporales = 0;

    // Genera un nombre de temporal (t1, t2, ...)
    public String nuevoTemporal() {
        contadorTemporales++;
        return "t" + contadorTemporales;
    }

    // Agrega un cuádruplo a la lista
    public void agregarCuadruplo(String operador, String operandoIzq, String operandoDer, String resultado) {
        cuadruplos.add(new Cuadruplo(operador, operandoIzq, operandoDer, resultado));
    }

    // Imprimir los cuádruplos generados
    public void imprimirCuadruplos() {
        System.out.println("=== Cuádruplos Generados ===");
        for (int i = 0; i < cuadruplos.size(); i++) {
            System.out.println(i + ": " + cuadruplos.get(i));
        }
    }
}
