import java.util.*;

public class TablaVariables {
    // Clase interna para almacenar información de la variable
    public static class InfoVariable {
        private String nombre;
        private String tipo;
        public int direccion;
        private boolean esGlobal;

        public InfoVariable(String nombre, String tipo, int direccion, boolean esGlobal) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.direccion = direccion;
            this.esGlobal = esGlobal;
        }

        // Getters
        public String getNombre() { return nombre; }
        public String getTipo() { return tipo; }
        public int getDireccion() { return direccion; }
        public boolean esGlobal() { return esGlobal; }
    }

    private Map<String, InfoVariable> variables = new LinkedHashMap<>();

    // Contadores para direcciones virtuales
    private int dirGlobalInt = 5000;    // Variables globales int empiezan en 5000
    private int dirGlobalFloat = 8000;  // Variables globales float empiezan en 8000
    private int dirLocalInt = 11000;    // Variables locales int empiezan en 11000
    private int dirLocalFloat = 13000;  // Variables locales float empiezan en 13000

    public void declararVariableGlobal(String nombre, String tipo) {
        declararVariable(nombre, tipo, true);
    }

    public void declararVariableLocal(String nombre, String tipo) {
        declararVariable(nombre, tipo, false);
    }

    private void declararVariable(String nombre, String tipo, boolean esGlobal) {
        if (variables.containsKey(nombre)) {
            System.out.println("Error: variable '" + nombre + "' ya fue declarada.");
        } else {
            int direccion = asignarDireccion( nombre, tipo, esGlobal);
            InfoVariable info = new InfoVariable(nombre, tipo, direccion, esGlobal);
            variables.put(nombre, info);
        }
    }

    public int asignarDireccion(String nombre, String tipo, boolean esGlobal) {
        if (esGlobal) {
            if (tipo.equals("int")) {
                return dirGlobalInt++;
            } else if (tipo.equals("float")) {
                return dirGlobalFloat++;
            }
        } else {
            if (tipo.equals("int")) {
                return dirLocalInt++;
            } else if (tipo.equals("float")) {
                return dirLocalFloat++;
            }
        }
        throw new IllegalArgumentException("Tipo no soportado: " + tipo);
    }

    public Integer obtenerDireccion(String nombre) {
        InfoVariable variable = variables.get(nombre);
        if (variable != null) {
            return variable.getDireccion();
        } else {
            System.out.println("Error: variable '" + nombre + "' no encontrada.");
            return null;
        }
    }

    public void imprimirVariables() {
        System.out.println("=== Tabla de Variables ===");
        for (Map.Entry<String, InfoVariable> entrada : variables.entrySet()) {
            InfoVariable info = entrada.getValue();
            String scope = info.esGlobal() ? "global" : "local";
            System.out.println(entrada.getKey() + " : " + info.getTipo() +
                    " | dirección: " + info.getDireccion() +
                    " | scope: " + scope);
        }
    }

    public Map<String, Integer> contarVariablesPorTipo() {
        Map<String, Integer> conteo = new LinkedHashMap<>();
        for (InfoVariable var : variables.values()) {
            String tipo = var.getTipo();
            if (!var.esGlobal()) { // Solo contar las locales
                conteo.put(tipo, conteo.getOrDefault(tipo, 0) + 1);
            }
        }
        return conteo;
    }

    public Map<String, InfoVariable> getVariablesLocales() {
        Map<String, InfoVariable> locales = new LinkedHashMap<>();
        for (Map.Entry<String, InfoVariable> entry : variables.entrySet()) {
            if (!entry.getValue().esGlobal()) {
                System.out.println("Variables locales2: " + entry.getKey() + " "+ entry.getValue());
                locales.put(entry.getKey(), entry.getValue());
            }
        }
        return locales;
    }

    // Método para obtener información de una variable
    public InfoVariable obtenerVariable(String nombre) {
        return variables.get(nombre);
    }

    // NUEVO: Método para obtener todas las variables (necesario para la MaquinaVirtual)
    public Map<String, InfoVariable> obtenerTodasLasVariables() {
        return new LinkedHashMap<>(variables);
    }
}