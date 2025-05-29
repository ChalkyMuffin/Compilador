import java.util.*;

public class PilasYCuadruplos {
    public Stack<String> operandos = new Stack<>();
    public Stack<String> operadores = new Stack<>();
    public Stack<String> tipos = new Stack<>();

    private int contadorTemp = 1;

    // Fila para almacenar cuádruplos
    public Queue<List<String>> cuadruplos = new LinkedList<>();

    public void agregarCuadruplo(String op, String arg1, String arg2, String res) {
        cuadruplos.add(Arrays.asList(op, arg1, arg2, res));
    }

    public void imprimirCuadruplos() {

        int i = 1;
        for (List<String> cuad : cuadruplos) {
            System.out.printf("%02d: (%s, %s, %s, %s)%n", i++, cuad.get(0), cuad.get(1), cuad.get(2), cuad.get(3));
        }

    }

    public String nuevoTemporal() {
        return "t" + (contadorTemp++);
    }
    public Stack<Integer> saltos = new Stack<>();

    // Permite acceso a un cuádruplo por índice (para hacer backpatch)
    public List<List<String>> listaCuadruplos() {
        return new ArrayList<>(cuadruplos);
    }

    // Sobrescribe un cuádruplo en cierto índice
    public void actualizarCuadruplo(int index, String nuevoRes) {
        List<List<String>> lista = new ArrayList<>(cuadruplos);
        List<String> original = lista.get(index);
        // Reemplaza el resultado
        lista.set(index, Arrays.asList(original.get(0), original.get(1), original.get(2), nuevoRes));
        cuadruplos = new LinkedList<>(lista); // Vuelve a guardar la cola
    }

}
