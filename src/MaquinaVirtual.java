import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Stack;

public class MaquinaVirtual {
    // Memoria dividida por tipos y rangos
    private Map<Integer, Object> memoriaGlobalInt = new HashMap<>();     // 5000-7999
    private Map<Integer, Object> memoriaGlobalFloat = new HashMap<>();   // 8000-10999
    private Map<Integer, Object> memoriaLocalInt = new HashMap<>();      // 11000-12999
    private Map<Integer, Object> memoriaLocalFloat = new HashMap<>();    // 13000-19999
    private Map<String, Object> memoriaTemporales = new HashMap<>();     // t1, t2, etc.

    private List<List<String>> cuadruplos;
    private int punteroInstruccion = 0;
    private TablaVariables tablaVariables;
    private TablaConstantes tablaConstantes;
    private OutputManager outputManager; // AGREGADO
    private Cuadruplos Cuadruplos; // AGREGADO

    private Stack<Integer> pilaRetorno = new Stack<>();
    private Stack<Map<Integer, Object>> pilaMemoriaLocal = new Stack<>();
    private Map<String, Object> parametrosActuales = new HashMap<>();


    public MaquinaVirtual(List<List<String>> cuadruplos, TablaVariables tablaVariables,
                          TablaConstantes tablaConstantes, OutputManager outputManager, Cuadruplos Cuadruplos) {
        this.cuadruplos = cuadruplos;
        this.tablaVariables = tablaVariables;
        this.tablaConstantes = tablaConstantes;
        this.outputManager = outputManager;
        this.Cuadruplos = Cuadruplos;
        inicializarMemoria();
    }

    private void inicializarMemoria() {
        // Inicializar todas las variables declaradas con 0
        if (tablaVariables != null) {
            for (Map.Entry<String, TablaVariables.InfoVariable> entry :
                    tablaVariables.obtenerTodasLasVariables().entrySet()) {

                TablaVariables.InfoVariable info = entry.getValue();
                int direccion = info.getDireccion();

                // Inicializar según el tipo y rango de dirección
                if (direccion >= 5000 && direccion < 8000) {
                    memoriaGlobalInt.put(direccion, 0);
                } else if (direccion >= 8000 && direccion < 11000) {
                    memoriaGlobalFloat.put(direccion, 0.0f);
                } else if (direccion >= 11000 && direccion < 13000) {
                    memoriaLocalInt.put(direccion, 0);
                } else if (direccion >= 13000 && direccion < 20000) {
                    memoriaLocalFloat.put(direccion, 0);
                }
            }
        }
    }

    private void era(String nombreFuncion, int instruccionPuntero) {
        System.out.println("  ERA: Preparando espacio para función " + nombreFuncion);

        // Guardar estado actual de memoria local
        Map<Integer, Object> memoriaActual = new HashMap<>(memoriaLocalInt);
        memoriaActual.putAll(memoriaLocalFloat);
        pilaMemoriaLocal.push(memoriaActual);
        System.out.println("Memoria local" + pilaMemoriaLocal.peek());
//        inicializarParametros(instruccionPuntero + 2);

        // Limpiar memoria local para nueva función
        memoriaLocalInt.clear();
        memoriaLocalFloat.clear();
        parametrosActuales.clear();
    }

    private void param(String valor, String indiceParam, int instruccionPuntero) {
        Object valorParametro = obtenerValor(valor);
        System.out.println("Valor: " + obtenerValor(valor));
        System.out.println("  PARAM " + indiceParam + ": " + valorParametro);
//        inicializarParametros(instruccionPuntero + 1);
        parametrosActuales.put("param_" + indiceParam, valorParametro);
    }





    private void gosub(String nombreFuncion, int instruccionPuntero) {
        System.out.println("  GOSUB: Llamando a función " + nombreFuncion);

        // Guardar dirección de retorno
        pilaRetorno.push(punteroInstruccion + 1);

        // Buscar dirección de inicio de la función
        DirectorioFunciones dirFun = new DirectorioFunciones();
        int direccionFuncion = dirFun.getDireccionInicio(nombreFuncion);

        if (direccionFuncion != -1) {
            // Inicializar parámetros en memoria local
            inicializarParametros(instruccionPuntero);
            punteroInstruccion = direccionFuncion - 1;
        } else {
            System.err.println("Error: Función " + nombreFuncion + " no encontrada");
        }
    }

    private void endfunc() {
        System.out.println("  ENDFUNC: Retornando de función");

        // AGREGAR ESTA VERIFICACIÓN
        if (pilaRetorno.isEmpty()) {
            System.out.println("  ENDFUNC ignorado - no hay función activa");
            return; // Simplemente continuar sin hacer nada
        }

        // Restaurar memoria local anterior
        if (!pilaMemoriaLocal.isEmpty()) {
            Map<Integer, Object> memoriaAnterior = pilaMemoriaLocal.pop();
            memoriaLocalInt.clear();
            memoriaLocalFloat.clear();

            for (Map.Entry<Integer, Object> entrada : memoriaAnterior.entrySet()) {
                int direccion = entrada.getKey();
                Object valor = entrada.getValue();

                if (direccion >= 11000 && direccion < 13000) {
                    memoriaLocalInt.put(direccion, valor);
                } else if (direccion >= 13000 && direccion < 20000) {
                    memoriaLocalFloat.put(direccion, valor);
                }
            }
        }

        // Retornar a la dirección guardada
        punteroInstruccion = pilaRetorno.pop();
    }



    private void inicializarParametros(int instruccionPuntero) {
        // Inicializar parámetros en memoria local
//        String parametros = pilasYCuadruplos.imprimirParametroCuadruplo(instruccionPuntero, 1);
//        int direccionParam = tablaVariables.obtenerDireccion(parametros); // Empezar en direcciones locales
          int direccionParam = 11000;



        for (Map.Entry<String, Object> param : parametrosActuales.entrySet()) {
            Object valor = param.getValue();

//            System.out.println("*****************************");
//            System.out.println("Parametro: " + parametros);
//            System.out.println("Direccion: " + direccionParam);
//            System.out.println("Valor: " + valor);
//            System.out.println("Memoria local int: " + memoriaLocalInt.values());
//            System.out.println("*****************************");

            if (valor instanceof Integer) {
                memoriaLocalInt.put(direccionParam , valor);
            } else {
                direccionParam = 13000;
                memoriaLocalFloat.put(direccionParam, valor);
            }
            direccionParam++;
        }
    }

    public void ejecutar() {
        System.out.println("\n=== EJECUTANDO PROGRAMA ===");

        while (punteroInstruccion < cuadruplos.size()) {
            List<String> cuadruplo = cuadruplos.get(punteroInstruccion);
            String operacion = cuadruplo.get(0);
            String arg1 = cuadruplo.get(1);
            String arg2 = cuadruplo.get(2);
            String resultado = cuadruplo.get(3);

            outputManager.debugPrintf("Ejecutando %02d: (%s, %s, %s, %s)\n",
                    punteroInstruccion + 1, operacion, arg1, arg2, resultado);

//            outputManager.debugPrintf("Puntero instruccion: ", "%02d:", punteroInstruccion +1);

            switch (operacion) {
                case "=":
                    asignacion(arg1, resultado);
                    break;
                case "+":
                    operacionAritmetica(arg1, arg2, resultado, "+");
                    break;
                case "-":
                    operacionAritmetica(arg1, arg2, resultado, "-");
                    break;
                case "*":
                    operacionAritmetica(arg1, arg2, resultado, "*");
                    break;
                case "/":
                    operacionAritmetica(arg1, arg2, resultado, "/");
                    break;
                case ">":
                    operacionRelacional(arg1, arg2, resultado, ">");
                    break;
                case "<":
                    operacionRelacional(arg1, arg2, resultado, "<");
                    break;
                case "==":
                    operacionRelacional(arg1, arg2, resultado, "==");
                    break;
                case "!=":
                    operacionRelacional(arg1, arg2, resultado, "!=");
                    break;
                case "PRINT":
                    print(arg1);
                    break;
                case "GOTO":
                    saltoIncondicional(resultado);
                    continue;
                case "GOTOF":
                    saltoCondicional(arg1, resultado);
                    break;
                case "ERA":
                    era(arg1, punteroInstruccion);
                    break;
                case "PARAM":
                    param(arg1, resultado, punteroInstruccion);
                    break;
                case "GOSUB":
                    gosub(arg1, punteroInstruccion);
                    continue;
                case "ENDFUNC":
                    endfunc();
                    continue;
                default:
                    System.err.println("Operación no reconocida: " + operacion);
            }

            punteroInstruccion++;
        }

        System.out.println("\n=== FIN DE EJECUCIÓN ===");
        imprimirEstadoMemoria();

        // MOSTRAR SALIDA DEL PROGRAMA
        outputManager.showProgramOutput();
    }

    private void asignacion(String fuente, String destino) {
        Object valor = obtenerValor(fuente);
        asignarValor(destino, valor);
        System.out.println("  " + destino + " = " + valor);
    }

    private void operacionAritmetica(String arg1, String arg2, String resultado, String operador) {
        Object val1 = obtenerValor(arg1);
        Object val2 = obtenerValor(arg2);

        double num1 = convertirANumero(val1);
        double num2 = convertirANumero(val2);
        double res = 0;

        switch (operador) {
            case "+": res = num1 + num2; break;
            case "-": res = num1 - num2; break;
            case "*": res = num1 * num2; break;
            case "/":
                if (num2 == 0) {
                    throw new RuntimeException("División por cero");
                }
                res = num1 / num2;
                break;
        }

        // Mantener tipo apropiado
        Object valorFinal;
        if (esEntero(val1) && esEntero(val2) && !operador.equals("/")) {
            valorFinal = (int) res;
        } else {
            valorFinal = (float) res;
        }

        asignarValor(resultado, valorFinal);
        System.out.println("  " + resultado + " = " + num1 + " " + operador + " " + num2 + " = " + res);
    }

    private void operacionRelacional(String arg1, String arg2, String resultado, String operador) {
        Object val1 = obtenerValor(arg1);
        Object val2 = obtenerValor(arg2);

        double num1 = convertirANumero(val1);
        double num2 = convertirANumero(val2);
        boolean res = false;

        switch (operador) {
            case ">": res = num1 > num2; break;
            case "<": res = num1 < num2; break;
            case "==": res = num1 == num2; break;
            case "!=": res = num1 != num2; break;
        }

        asignarValor(resultado, res);
        System.out.println("  " + resultado + " = " + num1 + " " + operador + " " + num2 + " = " + res);
    }


    private void print(String arg) {
        Object valor = obtenerValor(arg);

        // Agregar a la salida del programa (lo que ve el usuario final)
        outputManager.addProgramOutput(String.valueOf(valor));

        // Mensaje de debug (solo si está habilitado)
        System.out.println("PRINT: " + valor);
    }

    private void saltoIncondicional(String destino) {
        if (!destino.equals("*") && !destino.equals("_")) {
            punteroInstruccion = Integer.parseInt(destino) - 1;
            System.out.println("  Saltando a línea " + destino);
        }
    }

    private void saltoCondicional(String condicion, String destino) {
        Object valor = obtenerValor(condicion);
        boolean cond = (Boolean) valor;

        if (!cond && !destino.equals("_")) {
            punteroInstruccion = Integer.parseInt(destino) - 1;
            System.out.println("  Condición falsa, saltando a línea " + destino);
        } else {
            System.out.println("  Condición verdadera, continuando");
        }
    }

    // Obtener solo la salida del programa
    public List<String> getProgramOutput() {
        return outputManager.getProgramOutput();
    }



    private Object obtenerValor(String identificador) {
        if (identificador.matches("\\d+")) {
            int direccion = Integer.parseInt(identificador);
            if (direccion >= 20000) {
                return obtenerConstantePorDireccion(direccion);
            }
        }

        if (identificador.matches("t\\d+")) {
            return memoriaTemporales.getOrDefault(identificador, 0);
        }

        TablaVariables.InfoVariable info = tablaVariables.obtenerVariable(identificador);
        if (info != null) {
            int direccion = info.getDireccion();
            return obtenerValorPorDireccion(direccion);
        }

        if (identificador.equals("null") || identificador.equals("_")) {
            return null;
        }

        try {
            if (identificador.contains(".")) {
                return Float.parseFloat(identificador);
            } else {
                return Integer.parseInt(identificador);
            }
        } catch (NumberFormatException e) {
            // No es un número
        }

        System.err.println("Advertencia: No se puede obtener valor de: " + identificador);
        return 0;
    }

    private Object obtenerValorPorDireccion(int direccion) {
        if (direccion >= 5000 && direccion < 8000) {
            return memoriaGlobalInt.getOrDefault(direccion, 0);
        } else if (direccion >= 8000 && direccion < 11000) {
            return memoriaGlobalFloat.getOrDefault(direccion, 0.0f);
        } else if (direccion >= 11000 && direccion < 13000) {
            return memoriaLocalInt.getOrDefault(direccion, 0);
        } else if (direccion >= 13000 && direccion < 20000) {
            return memoriaLocalFloat.getOrDefault(direccion, 0.0f);
        }
        return 0;
    }

    private void asignarValor(String identificador, Object valor) {
        if (identificador.matches("t\\d+")) {
            memoriaTemporales.put(identificador, valor);
            return;
        }

        TablaVariables.InfoVariable info = tablaVariables.obtenerVariable(identificador);
        if (info != null) {
            int direccion = info.getDireccion();
            asignarValorPorDireccion(direccion, valor);
            return;
        }

        System.err.println("Advertencia: No se puede asignar valor a: " + identificador);
    }

    private void asignarValorPorDireccion(int direccion, Object valor) {
        if (direccion >= 5000 && direccion < 8000) {
            memoriaGlobalInt.put(direccion, convertirAEntero(valor));
        } else if (direccion >= 8000 && direccion < 11000) {
            memoriaGlobalFloat.put(direccion, convertirAFloat(valor));
        } else if (direccion >= 11000 && direccion < 13000) {
            memoriaLocalInt.put(direccion, convertirAEntero(valor));
        } else if (direccion >= 13000 && direccion < 20000) {
            memoriaLocalFloat.put(direccion, convertirAFloat(valor));
        }
    }

    private Object obtenerConstantePorDireccion(int direccion) {
        if (tablaConstantes != null) {
            return tablaConstantes.obtenerValorPorDireccion(direccion);
        }
        return null;
    }

    private double convertirANumero(Object valor) {
        if (valor instanceof Integer) {
            return ((Integer) valor).doubleValue();
        } else if (valor instanceof Float) {
            return ((Float) valor).doubleValue();
        } else if (valor instanceof Double) {
            return (Double) valor;
        }
        return 0.0;
    }

    private int convertirAEntero(Object valor) {
        if (valor instanceof Integer) {
            return (Integer) valor;
        } else if (valor instanceof Float) {
            return ((Float) valor).intValue();
        } else if (valor instanceof Double) {
            return ((Double) valor).intValue();
        }
        return 0;
    }

    private float convertirAFloat(Object valor) {
        if (valor instanceof Float) {
            return (Float) valor;
        } else if (valor instanceof Integer) {
            return ((Integer) valor).floatValue();
        } else if (valor instanceof Double) {
            return ((Double) valor).floatValue();
        }
        return 0.0f;
    }

    private boolean esEntero(Object valor) {
        return valor instanceof Integer;
    }

    private void imprimirEstadoMemoria() {
        System.out.println("\n=== ESTADO FINAL DE MEMORIA ===");

        System.out.println("Variables Globales Int:");
        for (Map.Entry<Integer, Object> entry : memoriaGlobalInt.entrySet()) {
            String nombre = obtenerNombreVariable(entry.getKey());
            System.out.println("  " + nombre + " [" + entry.getKey() + "] = " + entry.getValue());
        }

        System.out.println("Variables Globales Float:");
        for (Map.Entry<Integer, Object> entry : memoriaGlobalFloat.entrySet()) {
            String nombre = obtenerNombreVariable(entry.getKey());
            System.out.println("  " + nombre + " [" + entry.getKey() + "] = " + entry.getValue());
        }

        System.out.println("Variables Locales Int:");
        for (Map.Entry<Integer, Object> entry : memoriaLocalInt.entrySet()) {
            String nombre = obtenerNombreVariable(entry.getKey());
            System.out.println("  " + nombre + " [" + entry.getKey() + "] = " + entry.getValue());
        }

        System.out.println("Variables Locales Float:");
        for (Map.Entry<Integer, Object> entry : memoriaLocalFloat.entrySet()) {
            String nombre = obtenerNombreVariable(entry.getKey());
            System.out.println("  " + nombre + " [" + entry.getKey() + "] = " + entry.getValue());
        }

        if (!memoriaTemporales.isEmpty()) {
            System.out.println("Temporales:");
            for (Map.Entry<String, Object> entry : memoriaTemporales.entrySet()) {
                System.out.println("  " + entry.getKey() + " = " + entry.getValue());
            }
        }
    }

    private String obtenerNombreVariable(int direccion) {
        for (Map.Entry<String, TablaVariables.InfoVariable> entry :
                tablaVariables.obtenerTodasLasVariables().entrySet()) {
            if (entry.getValue().getDireccion() == direccion) {
                return entry.getKey();
            }
        }
        return "dir_" + direccion;
    }
}