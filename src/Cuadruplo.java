public class Cuadruplo {
    String operador;
    String operandoIzq;
    String operandoDer;
    String resultado;

    public Cuadruplo(String operador, String operandoIzq, String operandoDer, String resultado) {
        this.operador = operador;
        this.operandoIzq = operandoIzq;
        this.operandoDer = operandoDer;
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "(" + operador + ", " + operandoIzq + ", " + operandoDer + ", " + resultado + ")";
    }
}
