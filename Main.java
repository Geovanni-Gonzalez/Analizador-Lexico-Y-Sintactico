package compiler;

import java.io.*;
import java.util.List;

/**
 * Clase principal del compilador.
 *
 * Entrada:  Ruta al archivo fuente .src como argumento de línea de comandos.
 * Salida:   tokens.txt  — lista de tokens encontrados (TOKEN_ID | LEXEMA | LÍNEA)
 *           errors.txt  — errores léxicos y sintácticos con número de línea
 *           result.txt  — "OK" si el archivo es sintácticamente válido, "FAIL" si no
 * Restricciones: El archivo fuente debe existir y ser legible.
 * Objetivo: Orquestar el pipeline completo: scanner → parser → reporte.
 */
public class Main {

    public static void main(String[] args) {

        // Validar argumento de línea de comandos
        if (args.length < 1) {
            System.err.println("Uso: java -jar compilador.jar <archivo_fuente.src>");
            System.exit(1);
        }

        String sourceFile = args[0];
        File source = new File(sourceFile);

        if (!source.exists() || !source.isFile()) {
            System.err.println("Error: El archivo fuente no existe: " + sourceFile);
            System.exit(1);
        }

        // Definir archivos de salida en el mismo directorio que el fuente
        String baseName   = sourceFile.replaceAll("\\.[^.]+$", "");
        String tokensFile = baseName + "_tokens.txt";
        String errorsFile = baseName + "_errors.txt";
        String resultFile = baseName + "_result.txt";

        System.out.println("Compilando: " + sourceFile);

        try (FileReader reader = new FileReader(source)) {

            // ── FASE 1: Análisis Léxico ──────────────────────────────────────
            // TODO (Día 5-7): Instanciar el scanner generado por JFlex
            // Scanner scanner = new Scanner(reader);

            // ── FASE 2: Análisis Sintáctico ──────────────────────────────────
            // TODO (Día 8-10): Instanciar el parser generado por CUP
            // Parser parser = new Parser(scanner);
            // parser.parse();

            // ── FASE 3: Escritura de resultados ──────────────────────────────
            // TODO (Día 12): Escribir tokens.txt, errors.txt, result.txt

            System.out.println("Tokens escritos en:   " + tokensFile);
            System.out.println("Errores escritos en:  " + errorsFile);
            System.out.println("Resultado escrito en: " + resultFile);

        } catch (IOException e) {
            System.err.println("Error al leer el archivo fuente: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Escribe una lista de líneas en un archivo de texto.
     *
     * Entrada:  path — ruta del archivo de salida
     *           lines — lista de cadenas a escribir (una por línea)
     * Salida:   Archivo creado o sobreescrito en disco
     * Restricciones: El directorio padre debe existir y tener permisos de escritura
     */
    public static void writeFile(String path, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
