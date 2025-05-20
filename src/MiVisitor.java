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
}
