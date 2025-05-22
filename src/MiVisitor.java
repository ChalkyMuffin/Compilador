import java.util.ArrayList;
import java.util.List;

public class MiVisitor extends ExprBaseVisitor<Void> {
    TablaVariables tabla = new TablaVariables();

    @Override
    public Void visitVars_decl(ExprParser.Vars_declContext ctx) {
        String tipo = ctx.type().getText();     // "int" o "float"
        String nombre = ctx.ID().toString();     // nombre de variable

        tabla.declararVariable(nombre, tipo);
        return null;
    }

    public void imprimirVariables() {
        tabla.imprimirVariables();
    }

    DirectorioFunciones dirFun = new DirectorioFunciones();

    @Override
    public Void visitFuncs_decl(ExprParser.Funcs_declContext ctx) {
        String nombreFuncion = ctx.ID().getText();
        String tipoRetorno = "void";  // Según tu gramática

        List<String> tiposParametros = new ArrayList<>();
        if (ctx.params() != null) {
            for (ExprParser.ParamContext paramCtx : ctx.params().param()) {
                tiposParametros.add(paramCtx.type().getText());
            }
        }

        int direccionInicio = 1000;  // Ejemplo fijo por ahora

        dirFun.declararFuncion(nombreFuncion, tipoRetorno, tiposParametros, direccionInicio);

        return visitChildren(ctx);


    }
    public void imprimirFunciones() {
        dirFun.imprimirFunciones();
    }

    PilasSemanticas pilas = new PilasSemanticas();

    @Override
    public Void visitIntConst(ExprParser.IntConstContext ctx) {
        pilas.operandos.push(ctx.INT_NUM().getText());
        pilas.tipos.push("int");
        return null;
    }

    @Override
    public Void visitFloatConst(ExprParser.FloatConstContext ctx) {
        pilas.operandos.push(ctx.FLOAT_NUM().getText());
        pilas.tipos.push("float");
        return null;
    }


    @Override
    public Void visitFactor(ExprParser.FactorContext ctx) {
        if (ctx.ID() != null) {
            String id = ctx.ID().getText();
            pilas.operandos.push(id);
            pilas.tipos.push("int"); // Asume tipo, o consulta tabla de variables
        } else if (ctx.expression() != null) {
            visit(ctx.expression());
        } else if (ctx.cte() != null) {
            visit(ctx.cte());
        }
        return null;
    }

    @Override
    public Void visitAritExpr(ExprParser.AritExprContext ctx) {
        visit(ctx.expression());  // lado izquierdo
        visit(ctx.term());        // lado derecho

        String operador = ctx.getChild(1).getText(); // '+' o '-'
        pilas.operadores.push(operador);

        generarCuadruplo();
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
    public Void visitRelExpr(ExprParser.RelExprContext ctx) {
        visit(ctx.expression(0));
        visit(ctx.expression(1));

        String operador = ctx.getChild(1).getText(); // '<', '>', '==', '!='
        pilas.operadores.push(operador);

        generarCuadruplo();
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




}
