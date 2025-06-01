import java.util.ArrayList;
import java.util.List;

/**
 * Clase para gestionar y separar las salidas del programa compilado
 * de la información de debug del compilador
 */
public class OutputManager {
    private List<String> programOutput;
    private boolean showDebugInfo;

    public OutputManager(boolean showDebugInfo) {
        this.programOutput = new ArrayList<>();
        this.showDebugInfo = showDebugInfo;
    }

    /**
     * Agrega una salida del programa (resultado de PRINT)
     */
    public void addProgramOutput(String output) {
        programOutput.add(output);
    }

    /**
     * Imprime información de debug solo si está habilitada
     */
    public void debugPrint(String message) {
        if (showDebugInfo) {
            System.out.println(message);
        }
    }

    /**
     * Imprime información de debug con formato solo si está habilitada
     */
    public void debugPrintf(String format, Object... args) {
        if (showDebugInfo) {
            System.out.printf(format, args);
        }
    }

    /**
     * Muestra todas las salidas del programa de manera limpia
     */
    public void showProgramOutput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           SALIDA DEL PROGRAMA");
        System.out.println("=".repeat(50));

        if (programOutput.isEmpty()) {
            System.out.println("(No hay salidas del programa)");
        } else {
            for (int i = 0; i < programOutput.size(); i++) {
                System.out.println(programOutput.get(i));
            }
        }

        System.out.println("=".repeat(50));
    }

    /**
     * Obtiene todas las salidas como una lista
     */
    public List<String> getProgramOutput() {
        return new ArrayList<>(programOutput);
    }

    /**
     * Limpia todas las salidas almacenadas
     */
    public void clearOutput() {
        programOutput.clear();
    }

    /**
     * Obtiene el número de salidas del programa
     */
    public int getOutputCount() {
        return programOutput.size();
    }
}