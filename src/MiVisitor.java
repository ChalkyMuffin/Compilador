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
}
