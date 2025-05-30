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

    public int agregarConstante(int valor) {
        String clave = String.valueOf(valor);
        if (constantes.containsKey(clave)) {
            return constantes.get(clave).getDireccion();
        }

        InfoConstante info = new InfoConstante(valor, "int", dirConstanteInt++);
        constantes.put(clave, info);
//        System.out.println("Constante: " + valor + " | tipo: int | dirección: " + info.getDireccion());
        return info.getDireccion();
    }

    public int agregarConstante(float valor) {
        String clave = String.valueOf(valor);
        if (constantes.containsKey(clave)) {
            return constantes.get(clave).getDireccion();
        }

        InfoConstante info = new InfoConstante(valor, "float", dirConstanteFloat++);
        constantes.put(clave, info);
//        System.out.println("Constante: " + valor + " | tipo: float | dirección: " + info.getDireccion());
        return info.getDireccion();
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

    public Object obtenerValorPorDireccion(int direccion) {
        for (InfoConstante info : constantes.values()) {
            if (info.getDireccion() == direccion) {
                return info.getValor();
            }
        }
        return null;
    }
}