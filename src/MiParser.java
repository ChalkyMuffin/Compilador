import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class MiParser extends ExprBaseVisitor<Void> {
   //Tabla de variables
    TablaVariables tabla = new TablaVariables();

    @Override
    public Void visitVars_declGlobal(ExprParser.Vars_declGlobalContext ctx) {
        String tipo = ctx.type().getText();     // "int" o "float"
        String nombre = ctx.ID().getText();     // nombre de variable

        tabla.declararVariableGlobal(nombre, tipo);
        return null;
    }

    @Override
    public Void visitVars_decl(ExprParser.Vars_declContext ctx) {
        String tipo = ctx.type().getText();     // "int" o "float"
        String nombre = ctx.ID().getText();     // nombre de variable

        tabla.declararVariableLocal(nombre, tipo);
        return null;
    }

    @Override
    public Void visitParam(ExprParser.ParamContext ctx) {
        String tipo = ctx.type().getText();     // "int" o "float"
        String nombre = ctx.ID().getText();     // nombre de variable

        tabla.declararVariableLocal(nombre, tipo);
        return null;
    }



    public void imprimirVariables() {
        tabla.imprimirVariables();
    }

    //Directorio de funciones
    TablaVariables tablaVariablesActual = new TablaVariables();
    DirectorioFunciones dirFun = new DirectorioFunciones();


    @Override
    public Void visitFuncs_decl(ExprParser.Funcs_declContext ctx) {
        String nombreFuncion = ctx.ID().getText();
        String tipoRetorno = "void";

        List<String> tiposParametros = new ArrayList<>();
        // Contador de tipos
        Map<String, Integer> contadorTipos = new HashMap<>();

// 1. Contar tipos de parámetros
        if (ctx.params() != null) {
            for (ExprParser.ParamContext paramCtx : ctx.params().param()) {
                String tipo = paramCtx.type().getText();
                contadorTipos.put(tipo, contadorTipos.getOrDefault(tipo, 0) + 1);
//                System.out.println("Contador Tipos 1: " + contadorTipos);
//                System.out.println("Parametro - Tipo: " + tipo + ", Nombre: " + paramCtx.ID().getText());
            }
        }

// 2. Contar tipos de variables locales
        if (ctx.vars_decl() != null) {
            for (ExprParser.Vars_declContext varsDeclCtx : ctx.vars_decl()) {
                String tipo = varsDeclCtx.type().getText();
                String nombre = varsDeclCtx.ID().getText(); // Solo un ID por declaración
                contadorTipos.put(tipo, contadorTipos.getOrDefault(tipo, 0) + 1);
//                System.out.println("Contador Tipos 2: " + contadorTipos);
//                System.out.println("Variable local - Tipo: " + tipo + ", Nombre: " + nombre);
            }
        }



        int direccionInicio = pilas.cuadruplos.size();

        // 1. Cambia la tabla actual de variables
        TablaVariables tablaTemporal = new TablaVariables();
        tablaVariablesActual = tablaTemporal; // Asumimos que tienes un puntero actual

        // 2. Visita hijos primero para llenar la tabla de variables locales
        visitChildren(ctx);

        // 3. Luego cuenta las variables ya insertadas
        Map<String, Integer> conteo = tablaTemporal.contarVariablesPorTipo();

        // 4. Finalmente, registra la función
        dirFun.actualizarFuncion(nombreFuncion, conteo, tablaTemporal);
        dirFun.declararFuncion(nombreFuncion, tipoRetorno, direccionInicio, contadorTipos, tablaTemporal);

        return null;
    }

    public void imprimirFunciones() {
        dirFun.imprimirFunciones();
    }

    TablaConstantes tablaConstantes = new TablaConstantes();


    public void imprimirConstantes() {
        tablaConstantes.imprimirConstantes();
    }



    //Cuadruplos
    PilasYCuadruplos pilas;

    public MiParser(PilasYCuadruplos pilas) {
        this.pilas = pilas;
    }


    @Override
    public Void visitIntConst(ExprParser.IntConstContext ctx) {
        int valor = Integer.parseInt(ctx.INT_NUM().getText());
        int direccion = tablaConstantes.agregarConstante(valor);

        pilas.operandos.push(String.valueOf(direccion));  // Solo dirección virtual
        pilas.tipos.push("int");
        return null;
    }

    @Override
    public Void visitFloatConst(ExprParser.FloatConstContext ctx) {
        float valor = Float.parseFloat(ctx.FLOAT_NUM().getText());
        int direccion = tablaConstantes.agregarConstante(valor);

        pilas.operandos.push(String.valueOf(direccion));  // Solo dirección virtual
        pilas.tipos.push("float");
        return null;
    }


    @Override
    public Void visitFactor(ExprParser.FactorContext ctx) {
        if (ctx.ID() != null) {
            String id = ctx.ID().getText();
            pilas.operandos.push(id);

            // Buscar el tipo real de la variable en la tabla
            TablaVariables.InfoVariable info = tabla.obtenerVariable(id);
            if (info != null) {
                pilas.tipos.push(info.getTipo());
            } else {
                // Si no está en la tabla global, buscar en la tabla actual de funciones
                if (tablaVariablesActual != null) {
                    TablaVariables.InfoVariable infoLocal = tablaVariablesActual.obtenerVariable(id);
                    if (infoLocal != null) {
                        pilas.tipos.push(infoLocal.getTipo());
                    } else {
                        pilas.tipos.push("int"); // Tipo por defecto
                    }
                } else {
                    pilas.tipos.push("int"); // Tipo por defecto
                }
            }
        } else if (ctx.expression() != null) {
            visit(ctx.expression());
        } else if (ctx.cte() != null) {
            visit(ctx.cte());
        } else if (ctx.SUMA() != null || ctx.RESTA() != null) {
            // Manejar operadores unarios + y -
            String operador = ctx.getChild(0).getText();
            if (operador.equals("+") || operador.equals("-")) {
                pilas.operandos.push("0"); // Operando implícito para unario
                pilas.tipos.push("int");
                pilas.operadores.push(operador);
            }
        }
        return null;
    }

    @Override
    public Void visitAritExpr(ExprParser.AritExprContext ctx) {
        visit(ctx.term(0)); // Visita el primer término

        if (ctx.term().size() > 1) {
            // Buscar el operador correctamente
            String operador = null;
            if (ctx.SUMA() != null) {
                operador = "+";
            } else if (ctx.RESTA() != null) {
                operador = "-";
            }

            if (operador != null) {
                pilas.operadores.push(operador);
                visit(ctx.term(1)); // Visita el segundo término
                generarCuadruplo();
            }
        }

        return null;
    }

    @Override
    public Void visitMulExpr(ExprParser.MulExprContext ctx) {
        visit(ctx.term());
        visit(ctx.factor());

        String operador = ctx.getChild(1).getText(); // '*' o '/'
        pilas.operadores.push(operador);

        generarCuadruplo();
        return null;
    }
    @Override
    public Void visitAssign(ExprParser.AssignContext ctx) {
        String variable = ctx.ID().getText();
        visit(ctx.expression());




        String resultado = pilas.operandos.pop();
        pilas.tipos.pop();

        pilas.agregarCuadruplo("=", resultado, null, variable);
        return null;
    }
    @Override
    public Void visitExpression(ExprParser.ExpressionContext ctx) {
        visit(ctx.exp(0)); // Visita el primer término

        if (ctx.exp().size() > 1) {
            visit(ctx.exp(1)); // Visita el segundo término

            String operador = ctx.getChild(1).getText();
            pilas.operadores.push(operador);

            generarCuadruplo();
        }

        return null;
    }

    @Override
    public Void visitCondition(ExprParser.ConditionContext ctx) {
        visit(ctx.expression());

        String condicion = pilas.operandos.pop();
        String tipoCond = pilas.tipos.pop();

//        if (!tipoCond.equals("bool")) {
//            throw new RuntimeException("Condición no booleana en if. Tipo = " + tipoCond);
//        }

        // Cuádruplo GOTOF con salto pendiente
        pilas.agregarCuadruplo("GOTOF", condicion, "_", "pendiente");
        pilas.saltos.push(pilas.listaCuadruplos().size() - 1);

        // Cuerpo del if
        visit(ctx.body(0));

        if (ctx.body().size() > 1) {
            // Hay else -> cuádruplo GOTO con salto pendiente
            pilas.agregarCuadruplo("GOTO", "_", "_", "pendiente");
            int falso = pilas.saltos.pop();
            pilas.actualizarCuadruplo(falso, String.valueOf(pilas.listaCuadruplos().size()));
            pilas.saltos.push(pilas.listaCuadruplos().size() - 1);

            // Cuerpo del else
            visit(ctx.body(1));

            int fin = pilas.saltos.pop();
            pilas.actualizarCuadruplo(fin, String.valueOf(pilas.listaCuadruplos().size() + 1));
        } else {
            int falso = pilas.saltos.pop();
            pilas.actualizarCuadruplo(falso, String.valueOf(pilas.listaCuadruplos().size() + 1));
        }

        return null;
    }

    @Override
    public Void visitCycle(ExprParser.CycleContext ctx) {
        // Paso 1: Marca el inicio del ciclo
        int inicioCiclo = pilas.listaCuadruplos().size();

        // Paso 2: Visita la expresión de la condición
        visit(ctx.expression());

        // Paso 3: Revisa tipo
        String condicion = pilas.operandos.pop();
        String tipoCond = pilas.tipos.pop();
//        if (!tipoCond.equals("bool")) {
//            throw new RuntimeException("Condición no booleana en ciclo while.");
//        }

        // Paso 4: Crea GOTOF con destino pendiente
        pilas.agregarCuadruplo("GOTOF", condicion, "_", "pendiente");
        pilas.saltos.push(pilas.listaCuadruplos().size() - 1);

        // Paso 5: Visita el cuerpo del ciclo
        visit(ctx.body());

        // Paso 6: GOTO de regreso al inicio del ciclo
        pilas.agregarCuadruplo("GOTO", "_", "_", String.valueOf(inicioCiclo));

        // Paso 7: Backpatch del GOTOF
        int falso = pilas.saltos.pop();
        pilas.actualizarCuadruplo(falso, String.valueOf(pilas.listaCuadruplos().size()));

        return null;
    }







    public void generarCuadruplo() {
        if (!pilas.operadores.isEmpty()) {
            String operador = pilas.operadores.pop();
            String der = pilas.operandos.pop();
            String izq = pilas.operandos.pop();
            String tipoDer = pilas.tipos.pop();
            String tipoIzq = pilas.tipos.pop();

            if (!tipoDer.equals(tipoIzq)) {
                System.err.println("Error: Tipos incompatibles entre " + izq + " y " + der);
            }

            String temp = pilas.nuevoTemporal(); // genera t1, t2, ...
            pilas.operandos.push(temp);
            pilas.tipos.push(tipoIzq); // asumir mismo tipo

            pilas.agregarCuadruplo(operador, izq, der, temp);
        }
    }


    @Override
    public Void visitPrintStat(ExprParser.PrintStatContext ctx) {
        // Visita la expresión dentro del print
        visit(ctx.printExpr());
        return null;
    }

    @Override
    public Void visitPrintExpr(ExprParser.PrintExprContext ctx) {
        // Visita la expresión que se va a imprimir
        visit(ctx.expression());

        // Obtén el resultado de la expresión de las pilas
        String argumento = pilas.operandos.pop();
        pilas.tipos.pop(); // También quita el tipo

        // Genera el cuádruplo de PRINT
        pilas.agregarCuadruplo("PRINT", argumento, "_", "_");

        return null;
    }




}