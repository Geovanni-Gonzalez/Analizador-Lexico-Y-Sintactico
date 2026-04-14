package compiler;

/**
 * Representa un error léxico detectado durante el análisis.
 *
 * Entrada:  Línea, lexema no reconocido y descripción del error.
 * Salida:   Objeto con la información estructurada del error.
 * Restricciones: La línea debe ser >= 1.
 * Objetivo: Centralizar el reporte de errores léxicos para su escritura en errors.txt.
 */
public class LexicalError {

    /** Número de línea en el archivo fuente (base 1). */
    public final int line;

    /** Lexema que causó el error. */
    public final String lexeme;

    /** Descripción del problema. */
    public final String message;

    /**
     * Crea un nuevo error léxico.
     *
     * Entrada:  line    — número de línea (base 1)
     *           lexeme  — cadena no reconocida por el scanner
     *           message — descripción del error
     */
    public LexicalError(int line, String lexeme, String message) {
        this.line    = line;
        this.lexeme  = lexeme;
        this.message = message;
    }

    /**
     * Representación para escribir en errors.txt.
     * Formato: [LEXICO] Línea X: mensaje ('lexema')
     */
    @Override
    public String toString() {
        return String.format("[LEXICO]  Línea %d: %s ('%s')", line, message, lexeme);
    }
}
