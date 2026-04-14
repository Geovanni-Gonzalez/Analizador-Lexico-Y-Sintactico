# Gramática BNF — Proyecto 1: Análisis Léxico y Sintáctico
**Compiladores e Intérpretes — TEC — Semestre I 2026**
**Versión: Días 1-2 (estructura base, declaraciones, bloques)**

---

## Convenciones

- Los no-terminales se escriben en `snake_case`
- Los terminales (tokens) se escriben en `MAYÚSCULAS`
- `ε` representa la producción vacía (epsilon)
- El símbolo `|` separa alternativas de la misma producción
- Las producciones siguen notación BNF extendida

### Tokens de referencia rápida

| Token | Lexema |
|---|---|
| `LBLOCK` | `\|:` |
| `RBLOCK` | `:\|` |
| `LPAREN` | `<\|` |
| `RPAREN` | `\|>` |
| `LINDEX` | `<<` |
| `RINDEX` | `>>` |
| `TILDE` | `~` |
| `ASSIGN` | `<-` |
| `STMT_END` | `!` |
| `COMMA` | `,` |

---

## 1. Estructura del Programa

```bnf
program
    : proc_list
    ;

proc_list
    : proc_list proc
    | proc
    ;

proc
    : func_proc
    | main_proc
    ;
```

> El programa es una secuencia de uno o más procedimientos/funciones. Exactamente uno de ellos debe ser `__main__` (validación semántica, no léxico-sintáctica).

---

## 2. Procedimiento Principal (main)

```bnf
main_proc
    : MAIN TILDE LPAREN RPAREN block
    ;
```

> Según el enunciado: `__main__` es de tipo empty, no recibe parámetros, y es el único punto de entrada. La separación `~` aplica porque `MAIN` es palabra reservada seguida de `LPAREN` (que en contexto se escribe como `__main__ ~ <| |>`).

---

## 3. Declaración de Funciones

```bnf
func_proc
    : return_type TILDE ID LPAREN param_list RPAREN block
    | return_type TILDE ID LPAREN RPAREN block
    ;

return_type
    : INT_TYPE
    | FLOAT_TYPE
    | BOOL_TYPE
    | CHAR_TYPE
    ;
```

> `string` no es tipo de retorno válido según el enunciado (solo entero, flotante, char y booleano pueden retornarse).
> El separador `~` aparece entre el tipo de retorno (palabra reservada) y el identificador de la función.

---

## 4. Parámetros

```bnf
param_list
    : param_list COMMA param
    | param
    ;

param
    : value_type TILDE ID
    ;
```

> `value_type` es cualquier tipo de variable válido (ver sección 6). El separador `~` aparece entre el tipo y el nombre del parámetro en cada declaración.

---

## 5. Bloque de Código

```bnf
block
    : LBLOCK stmt_list RBLOCK
    | LBLOCK RBLOCK
    ;

stmt_list
    : stmt_list stmt
    | stmt
    ;
```

> Los bloques se delimitan con `|:` y `:|`. Un bloque puede estar vacío.

---

## 6. Tipos de Datos

```bnf
value_type
    : INT_TYPE
    | FLOAT_TYPE
    | BOOL_TYPE
    | CHAR_TYPE
    | STRING_TYPE
    ;

array_base_type
    : INT_TYPE
    | FLOAT_TYPE
    ;

array_type
    : array_base_type LINDEX INT_LIT RINDEX LINDEX INT_LIT RINDEX
    ;
```

> Solo se permiten arreglos de tipo `int` y `float` según el enunciado.
> El tamaño del arreglo se indica con literales enteros entre `<<` y `>>`.

---

## 7. Sentencias (stmt) — Estructura General

```bnf
stmt
    : decl_stmt
    | assign_stmt
    | expr_stmt
    | if_stmt
    | do_while_stmt
    | switch_stmt
    | return_stmt
    | break_stmt
    | cin_stmt
    | cout_stmt
    | block
    ;
```

> Las sentencias que no son estructuras de control terminan con `!` (STMT_END).
> Los bloques `block` pueden aparecer como sentencias independientes.

---

## 8. Sentencias de Declaración

```bnf
decl_stmt
    : value_type TILDE ID STMT_END
    | value_type TILDE ID ASSIGN expr STMT_END
    | array_type TILDE ID STMT_END
    | array_type TILDE ID ASSIGN array_init STMT_END
    ;

array_init
    : LBLOCK expr_list RBLOCK
    | LBLOCK RBLOCK
    ;

expr_list
    : expr_list COMMA expr
    | expr
    ;
```

> La asignación usa `<-` (ASSIGN). El separador `~` va entre el tipo y el identificador.
> La inicialización de arreglos usa `|:` y `:|` según el enunciado.

---

## 9. Sentencias de Asignación

```bnf
assign_stmt
    : ID ASSIGN expr STMT_END
    | array_access ASSIGN expr STMT_END
    ;

array_access
    : ID LINDEX expr RINDEX LINDEX expr RINDEX
    ;
```

> El acceso a elementos de arreglo usa `<<` y `>>` para cada dimensión.

---

## 10. Sentencia de Expresión

```bnf
expr_stmt
    : expr STMT_END
    ;
```

> Permite expresiones sin asignación como sentencias independientes (e.g., llamadas a función).

---

## 11. Estructuras de Control (placeholder — Día 4)

```bnf
if_stmt
    : IF TILDE LPAREN expr RPAREN block
    | IF TILDE LPAREN expr RPAREN block ELSE TILDE block
    ;

do_while_stmt
    : DO TILDE block WHILE TILDE LPAREN expr RPAREN STMT_END
    ;

switch_stmt
    : SWITCH TILDE LPAREN expr RPAREN LBLOCK case_list RBLOCK
    | SWITCH TILDE LPAREN expr RPAREN LBLOCK case_list default_case RBLOCK
    ;

case_list
    : case_list case_item
    | case_item
    ;

case_item
    : CASE TILDE expr TILDE block
    ;

default_case
    : DEFAULT TILDE block
    ;
```

> El separador `~` aparece entre la palabra reservada (`if`, `while`, `do`, `switch`) y el paréntesis de apertura `<|`.

---

## 12. Return y Break

```bnf
return_stmt
    : RETURN TILDE expr STMT_END
    ;

break_stmt
    : BREAK STMT_END
    ;
```

> `return` usa el separador `~` entre la palabra reservada y la expresión retornada.

---

## 13. Entrada/Salida

```bnf
cin_stmt
    : CIN TILDE ID STMT_END
    ;

cout_stmt
    : COUT TILDE cout_arg STMT_END
    ;

cout_arg
    : STRING_LIT
    | INT_LIT
    | FLOAT_LIT
    | TRUE
    | FALSE
    | ID
    ;
```

---

## 14. Llamada a Función

```bnf
func_call
    : ID TILDE LPAREN arg_list RPAREN
    | ID TILDE LPAREN RPAREN
    ;

arg_list
    : arg_list COMMA expr
    | expr
    ;
```

> Las llamadas a función también usan `~` entre el nombre y `<|`.

---

## 15. Expresiones — Jerarquía de Precedencia (placeholder — Día 3)

> **Esta sección se completa en el Día 3 del cronograma.**
> La gramática de expresiones usa una jerarquía de no-terminales para evitar las formas prohibidas.
> La estructura tentativa (de menor a mayor precedencia):

```
expr
    → logical_or_expr

logical_or_expr
    → logical_or_expr OR_OP logical_and_expr
    | logical_and_expr

logical_and_expr
    → logical_and_expr AND_OP not_expr
    | not_expr

not_expr
    → NOT_OP not_expr
    | relational_expr
    | arithmetic_expr

relational_expr
    → EQUAL_OP  LPAREN arithmetic_expr COMMA arithmetic_expr RPAREN
    | NEQUAL_OP LPAREN arithmetic_expr COMMA arithmetic_expr RPAREN
    | LESST_OP  LPAREN arithmetic_expr COMMA arithmetic_expr RPAREN
    | ...

arithmetic_expr
    → additive_expr

additive_expr
    → additive_expr PLUS  multiplicative_expr
    | additive_expr MINUS multiplicative_expr
    | multiplicative_expr

multiplicative_expr
    → multiplicative_expr MULT  power_expr
    | multiplicative_expr DIV   power_expr
    | multiplicative_expr MOD   power_expr
    | power_expr

power_expr
    → unary_expr POW power_expr
    | unary_expr

unary_expr
    → MINUS   primary_expr
    | INC     primary_expr
    | DEC     primary_expr
    | primary_expr

primary_expr
    → INT_LIT | FLOAT_LIT | EXP_LIT | FRAC_LIT
    | CHAR_LIT | STRING_LIT | TRUE | FALSE
    | ID
    | array_access
    | func_call
    | LPAREN expr RPAREN
```

> **NOTA CRÍTICA:** Esta forma `additive_expr → additive_expr PLUS multiplicative_expr` es la forma **correcta** usando no-terminales distintos (no la forma prohibida `expr → expr PLUS expr`). La gramática del enunciado prohíbe la forma donde el mismo no-terminal aparece en ambos lados del operador.

---

## 16. Resumen de No-Terminales

| No-terminal | Descripción | Estado |
|---|---|---|
| `program` | Raíz de la gramática | ✅ Día 2 |
| `proc_list` | Lista de procedimientos | ✅ Día 2 |
| `proc` | Procedimiento o main | ✅ Día 2 |
| `main_proc` | Procedimiento principal | ✅ Día 2 |
| `func_proc` | Función con tipo de retorno | ✅ Día 2 |
| `return_type` | Tipos válidos de retorno | ✅ Día 2 |
| `param_list` | Lista de parámetros | ✅ Día 2 |
| `param` | Un parámetro | ✅ Día 2 |
| `block` | Bloque |: :| | ✅ Día 2 |
| `stmt_list` | Lista de sentencias | ✅ Día 2 |
| `stmt` | Sentencia | ✅ Día 2 |
| `value_type` | Tipos de variable | ✅ Día 2 |
| `array_type` | Tipo arreglo 2D | ✅ Día 2 |
| `decl_stmt` | Declaración de variable | ✅ Día 2 |
| `assign_stmt` | Asignación | ✅ Día 2 |
| `array_access` | Acceso a arreglo | ✅ Día 2 |
| `array_init` | Inicialización de arreglo | ✅ Día 2 |
| `expr_stmt` | Expresión como sentencia | ✅ Día 2 |
| `if_stmt` | Condicional if-else | ✅ Día 2 (placeholder) |
| `do_while_stmt` | Ciclo do-while | ✅ Día 2 (placeholder) |
| `switch_stmt` | Switch | ✅ Día 2 (placeholder) |
| `case_list` / `case_item` | Cases de switch | ✅ Día 2 (placeholder) |
| `return_stmt` | Return | ✅ Día 2 |
| `break_stmt` | Break | ✅ Día 2 |
| `cin_stmt` | Lectura | ✅ Día 2 |
| `cout_stmt` | Escritura | ✅ Día 2 |
| `func_call` | Llamada a función | ✅ Día 2 |
| `arg_list` | Argumentos de llamada | ✅ Día 2 |
| `expr` | Expresión (raíz) | 🔄 Día 3 |
| `logical_or_expr` ... `primary_expr` | Jerarquía de expresiones | 🔄 Día 3 |

---

## 17. Programa de Ejemplo Válido

El siguiente programa debe poder ser generado por esta gramática:

```
¡¡ Función que suma dos enteros
int ~ suma <| int ~ a , int ~ b |> |:
    int ~ resultado <-  a + b !
    return ~ resultado !
:|

¡¡ Programa principal
__main__ ~ <| |> |:
    int ~ x <- 10 !
    float ~ y <- 3.14 !
    int ~ res !
    res <- suma ~ <| x , 5 |> !
    cout ~ res !
:|
```

---

## 18. Decisiones de Diseño

1. **Separador `~`**: Se usa consistentemente entre toda secuencia de palabra-reservada → identificador o paréntesis, tal como indica el enunciado (sección s del Anexo I).

2. **Arreglos bidimensionales**: Se declaran con el tipo, las dos dimensiones entre `<<` y `>>`, el separador `~` y el identificador. El acceso también usa `<<` y `>>`.

3. **Funciones vs variables**: La distinción entre tabla de variables y tabla de funciones la hace el parser, no el scanner, basándose en el contexto gramatical (si el ID va después de un tipo en posición de proc, es función; si va dentro de un bloque, es variable).

4. **Precedencia de operadores**: Se implementa mediante jerarquía de no-terminales, no con declaraciones de precedencia de CUP (aunque CUP soporta `precedence`, la jerarquía explícita es más portable y pedagógicamente correcta para el curso).

5. **Recuperación de errores**: Modo Pánico — al encontrar un error, el parser descarta tokens hasta encontrar `!` (STMT_END) y continúa desde ahí.

---

*Documento generado: Días 1-2 del cronograma — 13-14 abril 2026*
*Estado: Estructura base completa. Pendiente: expresiones completas (Día 3).*
