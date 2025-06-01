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
        Map<String, Integer> contadorTipos = new HashMap<>();

        // 1. Contar tipos de parámetros
        if (ctx.params() != null) {
            for (ExprParser.ParamContext paramCtx : ctx.params().param()) {
                String tipo = paramCtx.type().getText();
                contadorTipos.put(tipo, contadorTipos.getOrDefault(tipo, 0) + 1);
            }
        }

        // 2. Contar tipos de variables locales
        if (ctx.vars_decl() != null) {
            for (ExprParser.Vars_declContext varsDeclCtx : ctx.vars_decl()) {
                String tipo = varsDeclCtx.type().getText();
                String nombre = varsDeclCtx.ID().getText();
                contadorTipos.put(tipo, contadorTipos.getOrDefault(tipo, 0) + 1);
            }
        }

        int direccionInicio = pilas.cuadruplos.size() + 1; // +1 porque será la siguiente instrucción

        // Cambiar tabla actual de variables
        TablaVariables tablaTemporal = new TablaVariables();
        tablaVariablesActual = tablaTemporal;

        // Visitar hijos para llenar tabla de variables locales
        visitChildren(ctx);

        // AGREGAR ENDFUNC al final de la función
        pilas.agregarCuadruplo("ENDFUNC", "_", "_", "_");

        // Contar variables ya insertadas y registrar función
        Map<String, Integer> conteo = tablaTemporal.contarVariablesPorTipo();
        dirFun.actualizarFuncion(nombreFuncion, conteo, tablaTemporal);
        dirFun.declararFuncion(nombreFuncion, tipoRetorno, direccionInicio, contadorTipos, tablaTemporal);

        return null;
    }



    // MODIFICAR el método visitF_call para implementar ERA, PARAM y GOSUB
    @Override
    public Void visitF_call(ExprParser.F_callContext ctx) {
        String nombreFuncion = ctx.ID().getText();

        // 1. Generar ERA (Espacio en Registro de Activación)
        pilas.agregarCuadruplo("ERA", nombreFuncion, "_", "_");
//        System.out.println( "Cuadruplo size: " + pilas.cuadruplos.size());
        dirFun.actualizardirInicio(nombreFuncion, pilas.cuadruplos.size());


        // 2. Evaluar parámetros y generar PARAM
        if (ctx.expression() != null) {
            List<ExprParser.ExpressionContext> parametros = ctx.expression();
            for (int i = 0; i < parametros.size(); i++) {
                visit(parametros.get(i));  // Evaluar expresión del parámetro

                String valorParametro = pilas.operandos.pop();
                pilas.tipos.pop();

                // Generar cuádruplo PARAM
                pilas.agregarCuadruplo("PARAM", valorParametro, "_", "p"+String.valueOf(i+1));
            }
        }

        // 3. Generar GOSUB
        pilas.agregarCuadruplo("GOSUB", nombreFuncion, "_", "_");

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

    //Suma y resta
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

    // Multiplicacion y division
    @Override
    public Void visitMulExpr(ExprParser.MulExprContext ctx) {

        visit(ctx.factor(0)); // Visita el primer término

        if (ctx.factor().size() > 1) {
            // Buscar el operador correctamente
            String operador = null;
            if (ctx.MULT() != null) {
                operador = "*";
            } else if (ctx.DIVIDE() != null) {
                operador = "/";
            }

            if (operador != null) {
                pilas.operadores.push(operador);
                visit(ctx.factor(1)); // Visita el segundo término
                generarCuadruplo();
            }
        }

        return null;

    }

    @Override
    public Void visitAssign(ExprParser.AssignContext ctx) {
        String variable = ctx.ID().getText();
        //Expresion a cuyo resultado se va a igualar
        visit(ctx.expression());




        String resultado = pilas.operandos.pop();
        pilas.tipos.pop();

        pilas.agregarCuadruplo("=", resultado, null, variable);
        return null;
    }



    @Override
    public Void visitExpression(ExprParser.ExpressionContext ctx) {
        visit(ctx.exp(0));

        //Checa si hay mas de una expression
        if (ctx.exp().size() > 1) {
            visit(ctx.exp(1));

            String operador = ctx.getChild(1).getText();
            pilas.operadores.push(operador);

            generarCuadruplo();
        }

        return null;
    }

    //If
    @Override
    public Void visitCondition(ExprParser.ConditionContext ctx) {
        visit(ctx.expression());

        String condicional = pilas.operandos.pop();


        // Cuádruplo GOTOF con salto pendiente
        pilas.agregarCuadruplo("GOTOF", condicional, "_", "pendiente");
        pilas.saltos.push(pilas.listaCuadruplos().size() - 1);

        // Cuerpo del if
        visit(ctx.body(0));

        // Checa si hay un else checando si hay mas de un body
        if (ctx.body().size() > 1) {
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

    //While
    @Override
    public Void visitCycle(ExprParser.CycleContext ctx) {
        // Marca el numero del cuadruplo en el que inicia para darselo al GOTO del final
        int inicioCiclo = pilas.listaCuadruplos().size();

        visit(ctx.expression());

        String condicional = pilas.operandos.pop();

        // Cuádruplo GOTOF con salto pendiente
        pilas.agregarCuadruplo("GOTOF", condicional, "_", "pendiente");
        pilas.saltos.push(pilas.listaCuadruplos().size() - 1);

        //Cuerpo del while
        visit(ctx.body());

        // GOTOF de regreso al inicio del ciclo
        pilas.agregarCuadruplo("GOTO", "_", "_", String.valueOf(inicioCiclo));

        // Backpatch del GOTOF
        int falso = pilas.saltos.pop();
        pilas.actualizarCuadruplo(falso, String.valueOf(pilas.listaCuadruplos().size()));

        return null;
    }







    public void generarCuadruplo() {
        if (!pilas.operadores.isEmpty()) {
            String operador = pilas.operadores.pop();
            String der = pilas.operandos.pop();
            String izq = pilas.operandos.pop();
            String tipoIzq = pilas.tipos.pop();
//          String tipoDer = pilas.tipos.pop();
//
//            if (!tipoDer.equals(tipoIzq)) {
//                System.err.println("Error: Tipos incompatibles entre " + izq + " y " + der);
//            }

            //Temporales
            String temp = pilas.nuevoTemporal();
            pilas.operandos.push(temp);
            pilas.tipos.push(tipoIzq); // asumir mismo tipo

            pilas.agregarCuadruplo(operador, izq, der, temp);
        }
    }



    //Print
    @Override
    public Void visitPrintExpr(ExprParser.PrintExprContext ctx) {
        visit(ctx.expression());

//        String resultado = pilas.operandos.peek();
//        System.out.println("Resultado check: " + resultado);


        // Obtén el resultado de operacion en los cuadruplos
        String argumento = pilas.operandos.pop();
        pilas.tipos.pop(); // También quita el tipo

        // Genera el cuádruplo de PRINT
        pilas.agregarCuadruplo("PRINT", argumento, "_", "_");

        return null;
    }




}