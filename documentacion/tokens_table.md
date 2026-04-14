# Tabla de Tokens — Proyecto 1: Análisis Léxico y Sintáctico
**Compiladores e Intérpretes — TEC — Semestre I 2026**

---

## Convenciones de la tabla

| Campo | Descripción |
|---|---|
| **Token ID** | Nombre simbólico usado en JFlex y CUP |
| **Lexema / Patrón** | Cadena literal o regex simplificada |
| **Regex JFlex** | Expresión regular lista para copiar en el archivo `.flex` |
| **Tabla de símbolos** | Tabla donde se almacena (o `—` si no aplica) |
| **Información almacenada** | Campos guardados en esa tabla |

---

## 1. Palabras Reservadas (Keywords)

No se almacenan en tabla de símbolos; se reconocen y se retorna el token directamente.

| Token ID | Lexema | Regex JFlex | Tabla | Info |
|---|---|---|---|---|
| `INT_TYPE` | `int` | `"int"` | — | — |
| `FLOAT_TYPE` | `float` | `"float"` | — | — |
| `BOOL_TYPE` | `bool` | `"bool"` | — | — |
| `CHAR_TYPE` | `char` | `"char"` | — | — |
| `STRING_TYPE` | `string` | `"string"` | — | — |
| `TRUE` | `true` | `"true"` | — | — |
| `FALSE` | `false` | `"false"` | — | — |
| `IF` | `if` | `"if"` | — | — |
| `ELSE` | `else` | `"else"` | — | — |
| `DO` | `do` | `"do"` | — | — |
| `WHILE` | `while` | `"while"` | — | — |
| `SWITCH` | `switch` | `"switch"` | — | — |
| `CASE` | `case` | `"case"` | — | — |
| `DEFAULT` | `default` | `"default"` | — | — |
| `RETURN` | `return` | `"return"` | — | — |
| `BREAK` | `break` | `"break"` | — | — |
| `CIN` | `cin` | `"cin"` | — | — |
| `COUT` | `cout` | `"cout"` | — | — |
| `EQUAL_OP` | `equal` | `"equal"` | — | — |
| `NEQUAL_OP` | `n_equal` | `"n_equal"` | — | — |
| `LESST_OP` | `less_t` | `"less_t"` | — | — |
| `LESSTE_OP` | `less_te` | `"less_te"` | — | — |
| `GREATT_OP` | `greather_t` | `"greather_t"` | — | — |
| `GREATTE_OP` | `greather_te` | `"greather_te"` | — | — |
| `MAIN` | `__main__` | `"__main__"` | — | — |

---

## 2. Identificadores

| Token ID | Patrón | Regex JFlex | Tabla | Info almacenada |
|---|---|---|---|---|
| `ID` | `letra (letra\|dígito\|_)*` | `[a-zA-Z_][a-zA-Z0-9_]*` | **Variables** o **Funciones** (según contexto sintáctico) | nombre, tipo, ámbito, línea de declaración |

> **Nota:** El scanner solo almacena el lexema. El parser determina si el ID pertenece a la tabla de variables o de funciones según el contexto gramatical.

---

## 3. Literales

### 3.1 Numéricos

| Token ID | Descripción | Ejemplo | Regex JFlex | Tabla | Info almacenada |
|---|---|---|---|---|---|
| `INT_LIT` | Entero decimal | `42` | `[0-9]+` | **Literales** | valor (int), línea |
| `FLOAT_LIT` | Flotante | `3.14` | `[0-9]+"."[0-9]+` | **Literales** | valor (float), línea |
| `EXP_LIT` | Entero notación exponencial (+) | `100e10` | `[0-9]+"e"[0-9]+` | **Literales** | valor (long/exp), línea |
| `FRAC_LIT` | Fraccionario (variación flotante) | `5/6` | `[0-9]+"/"[0-9]+` | **Literales** | numerador, denominador, línea |

> **Importante para JFlex:** `FRAC_LIT` debe declararse **antes** que `INT_LIT` y antes de la regla del operador `/` para que el longest match lo reconozca correctamente. `EXP_LIT` debe declararse antes que `INT_LIT`.

### 3.2 Otros literales

| Token ID | Descripción | Ejemplo | Regex JFlex | Tabla | Info almacenada |
|---|---|---|---|---|---|
| `CHAR_LIT` | Carácter entre comillas simples | `'a'` | `\'[^\'\n]\'` | **Literales** | valor (char), línea |
| `STRING_LIT` | Cadena entre comillas dobles | `"hola"` | `\"[^\"]*\"` | **Literales** | valor (string), longitud, línea |

> `TRUE` y `FALSE` se tratan como keywords (sección 1), no como literales en tabla.

---

## 4. Operadores Aritméticos

| Token ID | Lexema | Regex JFlex | Tabla | Info |
|---|---|---|---|---|
| `PLUS` | `+` | `"+"` | — | — |
| `MINUS` | `-` | `"-"` | — | — |
| `MULT` | `*` | `"*"` | — | — |
| `DIV` | `/` | `"/"` | — | — |
| `MOD` | `%` | `"%"` | — | — |
| `POW` | `^` | `"^"` | — | — |
| `INC` | `++` | `"++"` | — | — |
| `DEC` | `--` | `"--"` | — | — |

> `INC` y `DEC` deben declararse **antes** que `PLUS` y `MINUS` para que el longest match funcione.

---

## 5. Operadores Lógicos

| Token ID | Lexema | Descripción | Regex JFlex | Tabla | Info |
|---|---|---|---|---|---|
| `AND_OP` | `@` | Conjunción | `"@"` | — | — |
| `OR_OP` | `#` | Disyunción | `"#"` | — | — |
| `NOT_OP` | `$` | Negación (tipo carácter) | `"$"` | — | — |

---

## 6. Delimitadores y Separadores Especiales

| Token ID | Lexema | Descripción | Regex JFlex | Tabla | Info |
|---|---|---|---|---|---|
| `LBLOCK` | `\|:` | Inicio bloque de código | `"\\|:"` | — | — |
| `RBLOCK` | `:\|` | Fin bloque de código | `":\\|"` | — | — |
| `LINDEX` | `<<` | Inicio índice arreglo | `"<<"` | — | — |
| `RINDEX` | `>>` | Fin índice arreglo | `">>"` | — | — |
| `LPAREN` | `<\|` | Paréntesis apertura (exprs/funciones) | `"<\\|"` | — | — |
| `RPAREN` | `\|>` | Paréntesis cierre (exprs/funciones) | `"\\|>"` | — | — |
| `TILDE` | `~` | Separador tipo-identificador / params | `"~"` | — | — |
| `ASSIGN` | `<-` | Operador de asignación | `"<-"` | — | — |
| `STMT_END` | `!` | Delimitador fin de sentencia | `"!"` | — | — |
| `COMMA` | `,` | Separador de parámetros | `","` | — | — |

> **Orden crítico en JFlex:**
> - `LBLOCK` (`|:`) debe ir antes que `OR_OP` (`|` suelto, si existiera) y antes que `RPAREN` (`|>`)
> - `ASSIGN` (`<-`) debe ir antes que `LINDEX` (`<<`) y antes que cualquier `<` suelto
> - `RPAREN` (`|>`) debe ir antes que `OR_OP` o cualquier `|` suelto
> - `LPAREN` (`<|`) debe ir antes que `LINDEX` (`<<`)

---

## 7. Comentarios

| Token ID | Lexema inicio | Lexema fin | Regex JFlex | Tabla | Acción |
|---|---|---|---|---|---|
| `COMMENT_LINE` | `¡¡` | fin de línea | `"¡¡"[^\n]*` | — | Ignorar (no retornar token) |
| `COMMENT_BLOCK` | `{-` | `-}` | Estado JFlex `COMMENT` | — | Ignorar (no retornar token) |

> `¡¡` es el carácter Unicode U+00A1 (¡) repetido dos veces. En JFlex con soporte Unicode activado: `%unicode` en el encabezado del `.flex`.

### Implementación de comentario multilinea en JFlex (estado):
```
%state COMMENT

<YYINITIAL> "{-"    { yybegin(COMMENT); }
<COMMENT>   "-}"    { yybegin(YYINITIAL); }
<COMMENT>   \n      { /* incrementar contador de líneas */ }
<COMMENT>   .       { /* ignorar contenido */ }
```

---

## 8. Espacios en Blanco y Saltos de Línea

| Token ID | Descripción | Regex JFlex | Acción |
|---|---|---|---|
| *(ignorar)* | Espacios, tabs | `[ \t\r]+` | Ignorar, no retornar token |
| *(contador)* | Salto de línea | `\n` | Incrementar `yyline`, ignorar |

---

## 9. Manejo de Errores Léxicos

| Caso | Regex JFlex | Acción |
|---|---|---|
| Carácter no reconocido | `.` (regla catch-all al final) | Reportar error con `yyline`, `yytext()`, continuar |
| String no cerrada | `\"[^\"\n]*\n` | Reportar error, continuar |
| Char inválido | `\'[^\']{2,}\'` | Reportar error, continuar |

```java
// Clase de error sugerida
public class LexicalError {
    public int line;
    public String lexeme;
    public String message;
    // constructor, toString...
}
```

---

## 10. Diseño de Tablas de Símbolos

### Tabla de Variables

| Campo | Tipo Java | Descripción |
|---|---|---|
| `name` | String | Nombre del identificador |
| `dataType` | String | Tipo del lenguaje: int, float, bool, char, string |
| `scope` | String | Función/procedimiento donde se declara |
| `isArray` | boolean | Si es arreglo bidimensional |
| `dimensions` | int[2] | Tamaño de dimensiones si es arreglo |
| `declarationLine` | int | Línea en el fuente |

### Tabla de Funciones

| Campo | Tipo Java | Descripción |
|---|---|---|
| `name` | String | Nombre de la función |
| `returnType` | String | Tipo de retorno: int, float, char, bool |
| `parameters` | List<ParamEntry> | Lista de parámetros (tipo + nombre) |
| `declarationLine` | int | Línea en el fuente |

### Tabla de Literales / Constantes

| Campo | Tipo Java | Descripción |
|---|---|---|
| `value` | String | Valor literal como cadena |
| `dataType` | String | int, float, exp, frac, char, string |
| `line` | int | Línea donde aparece |

### Resumen: ¿Qué va en qué tabla?

| Token | Tabla |
|---|---|
| `ID` (declaración de variable) | Tabla de Variables |
| `ID` (declaración de función) | Tabla de Funciones |
| `ID` (uso, referencia) | Búsqueda en tablas existentes |
| `INT_LIT`, `FLOAT_LIT`, `EXP_LIT`, `FRAC_LIT` | Tabla de Literales |
| `CHAR_LIT`, `STRING_LIT` | Tabla de Literales |
| `TRUE`, `FALSE` | — (keywords, no van a tabla) |
| Keywords, operadores, delimitadores | — (no van a tabla) |

---

## 11. Resumen de Cantidad de Tokens

| Categoría | Cantidad |
|---|---|
| Palabras reservadas (tipos, control, I/O, operadores relacionales, main) | 25 |
| Identificadores | 1 |
| Literales (numéricas + char + string) | 6 |
| Operadores aritméticos (binarios + unarios) | 8 |
| Operadores lógicos | 3 |
| Delimitadores y separadores especiales | 10 |
| Comentarios | 2 |
| **Total de tipos de token** | **~55** |

---

*Documento generado: Día 1 del cronograma — 13 abril 2026*
