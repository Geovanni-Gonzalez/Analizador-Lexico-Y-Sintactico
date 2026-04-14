package compiler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Tabla de símbolos para variables del lenguaje.
 *
 * Entrada:  Entradas de variables (nombre, tipo, ámbito, línea).
 * Salida:   Tabla consultable por nombre y ámbito.
 * Restricciones: No se permiten dos variables con el mismo nombre en el mismo ámbito.
 * Objetivo: Almacenar y dar acceso a la información de variables declaradas.
 */
public class SymbolTable {

    /**
     * Entrada individual en la tabla de variables.
     */
    public static class VariableEntry {
        public String name;
        public String dataType;        // int, float, bool, char, string
        public String scope;           // nombre de la función/procedimiento
        public boolean isArray;
        public int[] dimensions;       // {filas, columnas} si isArray = true
        public int declarationLine;

        /**
         * Constructor para variable simple.
         *
         * Entrada:  name, dataType, scope, declarationLine
         */
        public VariableEntry(String name, String dataType, String scope, int declarationLine) {
            this.name            = name;
            this.dataType        = dataType;
            this.scope           = scope;
            this.isArray         = false;
            this.dimensions      = null;
            this.declarationLine = declarationLine;
        }

        /**
         * Constructor para arreglo bidimensional.
         *
         * Entrada:  name, baseType (int/float), scope, rows, cols, declarationLine
         */
        public VariableEntry(String name, String baseType, String scope,
                             int rows, int cols, int declarationLine) {
            this.name            = name;
            this.dataType        = baseType + "[][]";
            this.scope           = scope;
            this.isArray         = true;
            this.dimensions      = new int[]{rows, cols};
            this.declarationLine = declarationLine;
        }

        @Override
        public String toString() {
            String arrInfo = isArray
                ? String.format(" [%d][%d]", dimensions[0], dimensions[1])
                : "";
            return String.format("%-20s %-12s %-15s Línea: %d%s",
                name, dataType, scope, declarationLine, arrInfo);
        }
    }

    // Almacenamiento: clave = "scope::name"
    private final Map<String, VariableEntry> table = new LinkedHashMap<>();

    /**
     * Agrega una variable a la tabla.
     *
     * Entrada:  entry — entrada de variable ya construida
     * Salida:   true si se agregó, false si ya existía en ese ámbito
     */
    public boolean add(VariableEntry entry) {
        String key = entry.scope + "::" + entry.name;
        if (table.containsKey(key)) return false;
        table.put(key, entry);
        return true;
    }

    /**
     * Busca una variable por nombre y ámbito.
     *
     * Entrada:  name — nombre del identificador
     *           scope — ámbito donde se busca
     * Salida:   VariableEntry o null si no existe
     */
    public VariableEntry lookup(String name, String scope) {
        String key = scope + "::" + name;
        return table.getOrDefault(key, null);
    }

    /**
     * Retorna todas las entradas para impresión/reporte.
     */
    public List<VariableEntry> getAll() {
        return new ArrayList<>(table.values());
    }

    /**
     * Representación para depuración.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-12s %-15s %s%n", "NOMBRE", "TIPO", "ÁMBITO", "LÍNEA"));
        sb.append("-".repeat(60)).append("\n");
        for (VariableEntry e : table.values()) {
            sb.append(e).append("\n");
        }
        return sb.toString();
    }
}
