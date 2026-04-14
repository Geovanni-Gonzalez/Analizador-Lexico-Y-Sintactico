package compiler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Tabla de literales (constantes) encontrados en el código fuente.
 *
 * Entrada:  Valores literales con tipo y línea donde aparecen.
 * Salida:   Tabla de literales únicos con su tipo.
 * Restricciones: Se almacena una sola entrada por valor+tipo (sin duplicados).
 * Objetivo: Registrar todos los literales del programa para referencia del compilador.
 */
public class LiteralTable {

    /**
     * Tipos de literal reconocidos por el lenguaje.
     */
    public enum LiteralType {
        INTEGER,        // INT_LIT    — ej. 42
        FLOAT,          // FLOAT_LIT  — ej. 3.14
        EXPONENTIAL,    // EXP_LIT    — ej. 100e10
        FRACTIONAL,     // FRAC_LIT   — ej. 5/6
        CHARACTER,      // CHAR_LIT   — ej. 'a'
        STRING          // STRING_LIT — ej. "hola"
    }

    /**
     * Entrada individual en la tabla de literales.
     */
    public static class LiteralEntry {
        public String value;            // Valor como cadena (ej. "3.14", "'a'")
        public LiteralType type;
        public int firstLine;           // Primera línea donde aparece

        /**
         * Entrada:  value, type, firstLine
         */
        public LiteralEntry(String value, LiteralType type, int firstLine) {
            this.value     = value;
            this.type      = type;
            this.firstLine = firstLine;
        }

        @Override
        public String toString() {
            return String.format("%-20s %-15s Línea: %d", value, type, firstLine);
        }
    }

    // Clave = type::value para evitar duplicados
    private final Map<String, LiteralEntry> table = new LinkedHashMap<>();

    /**
     * Agrega un literal a la tabla (ignora duplicados).
     *
     * Entrada:  value, type, line
     * Salida:   La entrada existente si ya estaba, la nueva si se insertó
     */
    public LiteralEntry add(String value, LiteralType type, int line) {
        String key = type.name() + "::" + value;
        return table.computeIfAbsent(key, k -> new LiteralEntry(value, type, line));
    }

    /**
     * Retorna todas las entradas para impresión/reporte.
     */
    public List<LiteralEntry> getAll() {
        return new ArrayList<>(table.values());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-15s %s%n", "VALOR", "TIPO", "LÍNEA"));
        sb.append("-".repeat(50)).append("\n");
        for (LiteralEntry e : table.values()) {
            sb.append(e).append("\n");
        }
        return sb.toString();
    }
}
