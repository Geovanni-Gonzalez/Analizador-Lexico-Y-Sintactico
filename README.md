# Proyecto 1 — Análisis Léxico y Sintáctico
**Compiladores e Intérpretes — TEC — Semestre I 2026**

## Descripción

Implementación de un analizador léxico (scanner) con JFlex y un analizador sintáctico (parser) con CUP para un lenguaje imperativo ligero orientado a configuración de chips embebidos.

## Estructura del Repositorio

```
PP1_Nombre1_Nombre2/
│
├── programa/
│   ├── pom.xml                        ← Build Maven con plugins JFlex y CUP
│   ├── src/
│   │   └── main/
│   │       ├── jflex/
│   │       │   └── scanner.flex       ← Analizador léxico JFlex
│   │       ├── cup/
│   │       │   └── parser.cup         ← Analizador sintáctico CUP
│   │       └── java/
│   │           └── compiler/
│   │               ├── Main.java              ← Punto de entrada
│   │               ├── LexicalError.java      ← Clase para errores léxicos
│   │               ├── SyntaxError.java       ← Clase para errores sintácticos
│   │               ├── SymbolTable.java        ← Tabla de variables
│   │               ├── FunctionTable.java      ← Tabla de funciones
│   │               └── LiteralTable.java       ← Tabla de literales
│   └── tests/
│       ├── test_valid_01.src           ← Programa completo válido
│       ├── test_valid_02.src           ← Funciones y arreglos
│       ├── test_lex_error_01.src       ← Errores léxicos
│       ├── test_syn_error_01.src       ← Errores sintácticos
│       └── test_mixed_errors.src       ← Errores mixtos
│
└── documentacion/
    ├── Requerimientos.zip              ← Entrega 15 abril (puntos extra)
    ├── tokens_table.md                 ← Especificación de tokens (este repo)
    ├── grammar_v1.md                   ← Gramática BNF
    └── informe_final.pdf               ← Documentación externa completa
```

## Requisitos

- Java 17 o superior
- Maven 3.8+
- JFlex 1.9.1 (manejado por Maven)
- CUP v0.11b (manejado por Maven)

## Compilación y Ejecución

```bash
# Compilar (genera scanner y parser automáticamente)
mvn clean package

# Ejecutar con un archivo fuente
java -jar target/compilador.jar archivo_fuente.src

# Salida generada
#   tokens.txt       ← Todos los tokens encontrados
#   errors.txt       ← Errores léxicos y sintácticos con número de línea
#   result.txt       ← OK / FAIL según análisis sintáctico
```

## Estado del Proyecto

| Fase | Estado |
|---|---|
| Diseño de gramática | 🔄 En progreso |
| Scanner JFlex | ⏳ Pendiente |
| Parser CUP | ⏳ Pendiente |
| Integración Java | ⏳ Pendiente |
| Pruebas | ⏳ Pendiente |
| Documentación externa | ⏳ Pendiente |

## Fechas Clave

| Fecha | Hito |
|---|---|
| 15 abril 2026 (11:55 PM) | Documento de Requerimientos (+2.5 pts extra) |
| 27 abril 2026 (11:55 PM) | Entrega final (+5 pts por hora) |

---

*Profesor: Allan Rodríguez Dávila — COMPILADORES E INTÉRPRETES GR 60*
