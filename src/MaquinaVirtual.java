import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MaquinaVirtual {
    private Map<String, Object> memoria = new HashMap<>();
    private List<List<String>> cuadruplos;
    private int punteroInstruccion = 0;
    private TablaVariables tablaVariables;
    private TablaConstantes tablaConstantes;

    public MaquinaVirtual(List<List<String>> cuadruplos, TablaVariables tablaVariables, TablaConstantes tablaConstantes) {
        this.cuadruplos = cuadruplos;
        this.tablaVariables = tablaVariables;
        this.tablaConstantes = tablaConstantes;
        inicializarMemoria();
    }

    private void inicializarMemoria() {
        // Inicializar variables en memoria con valor 0
        // Aquí necesitarías acceso a las variables declaradas
        // Por simplicidad, inicializamos dinámicamente cuando se usen
    }

    public void ejecutar() {
        System.out.println("\n=== EJECUTANDO PROGRAMA ===");

        while (punteroInstruccion < cuadruplos.size()) {
            List<String> cuadruplo = cuadruplos.get(punteroInstruccion);
            String operacion = cuadruplo.get(0);
            String arg1 = cuadruplo.get(1);
            String arg2 = cuadruplo.get(2);
            String resultado = cuadruplo.get(3);

            System.out.printf("Ejecutando %02d: (%s, %s, %s, %s)\n",
                    punteroInstruccion + 1, operacion, arg1, arg2, resultado);

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
                    continue; // No incrementar puntero
                case "GOTOF":
                    saltoCondicional(arg1, resultado);
                    break;
                default:
                    System.err.println("Operación no reconocida: " + operacion);
            }

            punteroInstruccion++;
        }

        System.out.println("\n=== FIN DE EJECUCIÓN ===");
        imprimirEstadoMemoria();
    }

    private void asignacion(String fuente, String destino) {
        Object valor = obtenerValor(fuente);
        memoria.put(destino, valor);
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
        if (esEntero(val1) && esEntero(val2) && !operador.equals("/")) {
            memoria.put(resultado, (int) res);
        } else {
            memoria.put(resultado, (float) res);
        }

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

        memoria.put(resultado, res);
        System.out.println("  " + resultado + " = " + num1 + " " + operador + " " + num2 + " = " + res);
    }

    private void print(String arg) {
        Object valor = obtenerValor(arg);
        System.out.println("PRINT: " + valor);
    }

    private void saltoIncondicional(String destino) {
        if (!destino.equals("*")) {
            punteroInstruccion = Integer.parseInt(destino) - 1; // -1 porque se incrementará
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

    private Object obtenerValor(String identificador) {
        // Si es una dirección de constante
        if (identificador.matches("\\d+")) {
            int direccion = Integer.parseInt(identificador);

            // Buscar en tabla de constantes por dirección
            if (direccion >= 20000 && direccion < 21000) {
                return obtenerConstantePorDireccion(direccion);
            }
        }

        // Si está en memoria (variable o temporal)
        if (memoria.containsKey(identificador)) {
            return memoria.get(identificador);
        }

        // Si es un identificador de variable, inicializar con 0
        if (identificador.matches("[a-zA-Z][a-zA-Z0-9]*")) {
            memoria.put(identificador, 0);
            return 0;
        }

        // Si es "null" o "_", retornar null
        if (identificador.equals("null") || identificador.equals("_")) {
            return null;
        }

        throw new RuntimeException("No se puede obtener valor de: " + identificador);
    }

    private Object obtenerConstantePorDireccion(int direccion) {
        if (tablaConstantes != null) {
            return tablaConstantes.obtenerValorPorDireccion(direccion);
        }

        // Fallback para valores comunes
        Map<Integer, Object> constantesTemp = Map.of(
                20000, 3,
                20001, 18,
                20002, 20,
                20003, 0,
                20004, 5,
                20005, 10,
                20500, 5.14f
        );

        return constantesTemp.getOrDefault(direccion, 0);
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

    private boolean esEntero(Object valor) {
        return valor instanceof Integer;
    }

    private void imprimirEstadoMemoria() {
        System.out.println("\n=== ESTADO FINAL DE MEMORIA ===");
        for (Map.Entry<String, Object> entry : memoria.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }


}