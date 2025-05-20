import java.util.*;

public class DirectorioFunciones {
    public static class FuncionInfo {
        String tipoRetorno;
        List<String> tiposParametros;
        int direccionInicio;

        public FuncionInfo(String tipoRetorno, List<String> tiposParametros, int direccionInicio) {
            this.tipoRetorno = tipoRetorno;
            this.tiposParametros = tiposParametros;
            this.direccionInicio = direccionInicio;
        }

        @Override
        public String toString() {
            return "Tipo: " + tipoRetorno +
                    ", Parámetros: " + tiposParametros +
                    ", Dirección Inicio: " + direccionInicio;
        }
    }

    private Map<String, FuncionInfo> funciones = new LinkedHashMap<>();

    public void declararFuncion(String nombre, String tipoRetorno, List<String> tiposParametros, int dirInicio) {
        if (funciones.containsKey(nombre)) {
            System.out.println("Error: la función '" + nombre + "' ya fue declarada.");
        } else {
            funciones.put(nombre, new FuncionInfo(tipoRetorno, tiposParametros, dirInicio));
        }
    }

    public void imprimirFunciones() {
        System.out.println("=== Directorio de Funciones ===");
        for (Map.Entry<String, FuncionInfo> entrada : funciones.entrySet()) {
            System.out.println(entrada.getKey() + " -> " + entrada.getValue());
        }
    }
}
