import java.util.LinkedHashMap;
import java.util.Map;

public class TablaVariables {
    private Map<String, String> variables = new LinkedHashMap<>();

    public void declararVariable(String nombre, String tipo) {
        if (variables.containsKey(nombre)) {
            System.out.println("Error: variable '" + nombre + "' ya fue declarada.");
        } else {
            variables.put(nombre, tipo);
            System.out.println("Var: " + nombre + " " + "tipo: " + tipo);
        }
    }

    public void imprimirVariables() {
        System.out.println("=== Tabla de Variables ===");
        for (Map.Entry<String, String> entrada : variables.entrySet()) {
            System.out.println(entrada.getKey() + " : " + entrada.getValue());
        }
    }
}