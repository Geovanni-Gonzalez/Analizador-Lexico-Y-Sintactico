package compiler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Tabla de funciones del lenguaje.
 *
 * Entrada:  Entradas de funciones (nombre, tipo de retorno, parámetros, línea).
 * Salida:   Tabla consultable por nombre.
 * Restricciones: No se permiten dos funciones con el mismo nombre.
 * Objetivo: Almacenar y dar acceso a la información de funciones declaradas.
 */
public class FunctionTable {

    /**
     * Representa un parámetro de una función.
     */
    public static class ParamEntry {
        public String type;
        public String name;

        /**
         * Entrada:  type — tipo del parámetro, name — nombre del parámetro
         */
        public ParamEntry(String type, String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public String toString() {
            return type + " ~ " + name;
        }
    }

    /**
     * Entrada individual en la tabla de funciones.
     */
    public static class FunctionEntry {
        public String name;
        public String returnType;           // int, float, bool, char
        public List<ParamEntry> parameters;
        public int declarationLine;

        /**
         * Constructor principal.
         *
         * Entrada:  name, returnType, parameters, declarationLine
         */
        public FunctionEntry(String name, String returnType,
                             List<ParamEntry> parameters, int declarationLine) {
            this.name            = name;
            this.returnType      = returnType;
            this.parameters      = parameters != null ? parameters : new ArrayList<>();
            this.declarationLine = declarationLine;
        }

        /** Retorna la firma de la función como cadena. */
        public String signature() {
            StringBuilder sb = new StringBuilder();
            sb.append(returnType).append(" ~ ").append(name).append(" <| ");
            for (int i = 0; i < parameters.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(parameters.get(i));
            }
            sb.append(" |>");
            return sb.toString();
        }

        @Override
        public String toString() {
            return String.format("%-20s %-10s %-30s Línea: %d",
                name, returnType, parameters.toString(), declarationLine);
        }
    }

    // Almacenamiento: clave = nombre de la función
    private final Map<String, FunctionEntry> table = new LinkedHashMap<>();

    /**
     * Agrega una función a la tabla.
     *
     * Entrada:  entry — entrada de función ya construida
     * Salida:   true si se agregó, false si ya existía
     */
    public boolean add(FunctionEntry entry) {
        if (table.containsKey(entry.name)) return false;
        table.put(entry.name, entry);
        return true;
    }

    /**
     * Busca una función por nombre.
     *
     * Entrada:  name — nombre de la función
     * Salida:   FunctionEntry o null si no existe
     */
    public FunctionEntry lookup(String name) {
        return table.getOrDefault(name, null);
    }

    /**
     * Retorna todas las entradas para impresión/reporte.
     */
    public List<FunctionEntry> getAll() {
        return new ArrayList<>(table.values());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-10s %-30s %s%n",
            "NOMBRE", "RETORNO", "PARÁMETROS", "LÍNEA"));
        sb.append("-".repeat(70)).append("\n");
        for (FunctionEntry e : table.values()) {
            sb.append(e).append("\n");
        }
        return sb.toString();
    }
}
