import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DirectorioFunciones {
    public TablaVariables getTablaVariablesLocales(String nombreFuncion) {
        FuncionInfo info = funciones.get(nombreFuncion);
        if (info != null) {
            return info.tablaVariablesLocales;
        }
        return null;
    }

    public static class FuncionInfo {
        private final TablaVariables tablaVariablesLocales;
        String tipoRetorno;
        int direccionInicio;
        int direccionLlamada;
        Map<String, Integer> conteoVariables; // por tipo
        TablaVariables tablaVariables;

        public FuncionInfo(String tipoRetorno,  int direccionInicio,  int direccionLlamada,
                           Map<String, Integer> conteoVariables, TablaVariables tablaVariables) {
            this.tipoRetorno = tipoRetorno;
            this.direccionInicio = direccionInicio;
            this.direccionLlamada = direccionLlamada;
            this.conteoVariables = conteoVariables;
            this.tablaVariables = tablaVariables;
            this.tablaVariablesLocales = tablaVariables; // También guarda como locales
        }

        public TablaVariables getTablaVariablesLocales(String nombreFuncion) {
            FuncionInfo info = funciones.get(nombreFuncion);
            if (info != null) {
                return info.tablaVariablesLocales;
            }
            return null;
        }

        @Override
        public String toString() {
            return "Tipo: " + tipoRetorno +
                    ", Dirección Inicio: " + direccionInicio +
                    ", Dirección Llamada: " + direccionLlamada +
                    ", Memoria: " + conteoVariables;
        }
    }

    private static Map<String, FuncionInfo> funciones = new LinkedHashMap<>();

    public void declararFuncion(String nombre, String tipoRetorno,
                                int dirInicio, int dirDeclaracion, Map<String, Integer> conteoVars, TablaVariables tablaVars) {
        if (funciones.containsKey(nombre)) {
            System.out.println("Error: la función '" + nombre + "' ya fue declarada.");
        } else {
            funciones.put(nombre, new FuncionInfo(tipoRetorno, dirInicio, dirDeclaracion, conteoVars, tablaVars));

        }
    }

    public void actualizardirLlamada(String nombre, int nuevaDirLlamada) {
        FuncionInfo info = funciones.get(nombre);
        if (info != null) {
            info.direccionLlamada = nuevaDirLlamada;
        }
    }

    public void imprimirFunciones() {
        System.out.println("=== Directorio de Funciones ===");
        for (Map.Entry<String, FuncionInfo> entrada : funciones.entrySet()) {
            System.out.println(entrada.getKey() + " -> " + entrada.getValue());
        }
    }

    public void actualizarFuncion(String nombre, Map<String, Integer> conteoVars, TablaVariables tablaVars) {
        FuncionInfo info = funciones.get(nombre);
        if (info != null) {
            info.conteoVariables = conteoVars;
            info.tablaVariables = tablaVars;
        }
    }


}
