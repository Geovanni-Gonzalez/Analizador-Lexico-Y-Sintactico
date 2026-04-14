package compiler;

/**
 * Representa un error sintáctico detectado durante el análisis.
 *
 * Entrada:  Línea, token inesperado, token esperado y descripción.
 * Salida:   Objeto con la información estructurada del error.
 * Restricciones: La línea debe ser >= 1.
 * Objetivo: Centralizar el reporte de errores sintácticos para su escritura en errors.txt.
 */
public class SyntaxError {

    /** Número de línea en el archivo fuente (base 1). */
    public final int line;

    /** Token o lexema donde ocurrió el error. */
    public final String found;

    /** Descripción del error o qué se esperaba. */
    public final String message;

    /**
     * Crea un nuevo error sintáctico.
     *
     * Entrada:  line    — número de línea (base 1)
     *           found   — lexema o tipo de token encontrado
     *           message — descripción del error / qué se esperaba
     */
    public SyntaxError(int line, String found, String message) {
        this.line    = line;
        this.found   = found;
        this.message = message;
    }

    /**
     * Representación para escribir en errors.txt.
     * Formato: [SINTACTICO] Línea X: mensaje (encontrado: 'found')
     */
    @Override
    public String toString() {
        return String.format("[SINTACTICO] Línea %d: %s (encontrado: '%s')", line, message, found);
    }
}
