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
        private final HashMap conteoTipos;
        String tipoRetorno;
        List<String> tiposParametros;
        int direccionInicio;
        Map<String, Integer> conteoVariables; // por tipo
        TablaVariables tablaVariables;

        public FuncionInfo(String tipoRetorno, List<String> tiposParametros, int direccionInicio,
                           Map<String, Integer> conteoVariables, TablaVariables tablaVariables) {
            this.tipoRetorno = tipoRetorno;
            this.tiposParametros = tiposParametros;
            this.direccionInicio = direccionInicio;
            this.conteoVariables = conteoVariables;
            this.tablaVariables = tablaVariables;
            this.tablaVariablesLocales = tablaVariables; // También guarda como locales
            this.conteoTipos = new HashMap(); // opcional o lo puedes eliminar
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
                    ", Parámetros: " + tiposParametros +
                    ", Dirección Inicio: " + direccionInicio +
                    ", Memoria: " + conteoVariables;
        }
    }

    private static Map<String, FuncionInfo> funciones = new LinkedHashMap<>();

    public void declararFuncion(String nombre, String tipoRetorno, List<String> tiposParametros,
                                int dirInicio, Map<String, Integer> conteoVars, TablaVariables tablaVars) {
        if (funciones.containsKey(nombre)) {
            System.out.println("Error: la función '" + nombre + "' ya fue declarada.");
        } else {
            funciones.put(nombre, new FuncionInfo(tipoRetorno, tiposParametros, dirInicio, conteoVars, tablaVars));

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
