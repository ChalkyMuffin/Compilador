import java.util.LinkedHashMap;
import java.util.Map;

public class TablaConstantes {
    // Clase interna para almacenar información de la constante
    public static class InfoConstante {
        private Object valor;  // Puede ser Integer o Float
        private String tipo;   // "int" o "float"
        private int direccion;

        public InfoConstante(Object valor, String tipo, int direccion) {
            this.valor = valor;
            this.tipo = tipo;
            this.direccion = direccion;
        }

        // Getters
        public Object getValor() { return valor; }
        public String getTipo() { return tipo; }
        public int getDireccion() { return direccion; }
    }

    //InfoConstante: valor, tipo, direccion
    private Map<String, InfoConstante> constantes = new LinkedHashMap<>();

    // Contadores para direcciones virtuales
    private int dirConstanteInt = 20000;    // Constantes int empiezan en 20000
    private int dirConstanteFloat = 20500;  // Constantes float empiezan en 20500

    public int agregarConstante(int valorStr) {
        // Si la constante ya existe, devolver su dirección
        if (constantes.containsKey(valorStr)) {
            return constantes.get(valorStr).getDireccion();
        }

        Object valor;
        String tipo;
        int direccion;

        // Determinar si es int o float
        if (valorStr == (int) valorStr) {
            // Es int
            valor = valorStr;
            tipo = "int";
            direccion = dirConstanteInt++;

        } else {
            // Es float
            valor = valorStr;
            tipo = "float";
            direccion = dirConstanteFloat++;

        }

        InfoConstante info = new InfoConstante(valor, tipo, direccion);
        constantes.put(String.valueOf(valorStr), info);

        System.out.println("Constante: " + valorStr + " | tipo: " + tipo +
                " | dirección: " + direccion);

        return direccion;
    }

    public void imprimirConstantes() {
        System.out.println("=== Tabla de Constantes ===");
        for (Map.Entry<String, InfoConstante> entrada : constantes.entrySet()) {
            InfoConstante info = entrada.getValue();
            System.out.println("Valor: " + entrada.getKey() +
                    " | Tipo: " + info.getTipo() +
                    " | Dirección: " + info.getDireccion());
        }
    }

    // Método para obtener información de una constante
    public InfoConstante obtenerConstante(String valor) {
        return constantes.get(valor);
    }

    // Método para obtener dirección de una constante por su valor
    public Integer obtenerDireccion(String valor) {
        InfoConstante info = constantes.get(valor);
        return info != null ? info.getDireccion() : null;
    }
}