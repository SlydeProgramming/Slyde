grammar Slyde;

prog: 
    classDeclaration* EOF;

// Class Definition
classDeclaration: 
    CLASS IDENTIFIER ('extends' IDENTIFIER)? '{' classBody '}';

classBody: 
    (varDecl | methodDeclaration | constructor)*;

// Methods (Function Overloading Supported)
methodDeclaration: 
    type IDENTIFIER '(' paramList? ')' block;

// Parameters
paramList: 
    type IDENTIFIER (',' type IDENTIFIER)*;

// Statements
block: '{' statement* '}';

constructor:
    CONSTRUCT '(' paramList? ')' block;

statement: 
    varDecl 
    | assignment 
    | methodCall 
    | returnStmt 
    | block 
    | ifStmt 
    | whileStmt 
    | forStmt 
    | printStmt 
    | inputStmt 
    | expr ';';

// Variable Declaration
varDecl: 
    type IDENTIFIER ('=' expr)? ';';

// Assignments
assignment: 
    IDENTIFIER ('[' expr ']')? '=' expr ';';

// Method Calls
methodCall: 
    IDENTIFIER '(' argList? ')' ';';

// Arguments
argList: expr (',' expr)*;

// Expressions
expr: 
    expr ('+' | '-' | '*' | '/') expr
    | expr ('==' | '!=' | '<' | '>' | '<=' | '>=') expr
    | expr ('&&' | '||') expr
    | '!' expr
    | IDENTIFIER ('[' expr ']')?
    | NUMBER
    | STRING
    | BOOLEAN
    | methodCall
    | '(' expr ')'
    ;

// Control Flow
ifStmt: 
    IF '(' expr ')' block (ELSE block)?;

whileStmt: 
    WHILE '(' expr ')' block;

forStmt: 
    FOR '(' varDecl? ';' expr? ';' assignment? ')' block;


returnStmt:
    RETURN expr ';';


// I/O
printStmt: PRINT '(' expr ')' ';';
inputStmt: INPUT '(' STRING ')' ';';

// Types
type: 
    'int' | 'float' | 'String' | 'boolean' | 'void';

// Arrays
arrayDeclaration: 
    type '[' ']' IDENTIFIER ('=' arrayLiteral)? ';';

arrayLiteral: 
    '[' (expr (',' expr)*)? ']';



TRUE: 'true';
FALSE: 'false';
YES: 'yes';
NO: 'no';
CLASS: 'class';
EXTENDS: 'extends';
IF: 'if';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
RETURN: 'return';
PRINT: 'print';
INPUT: 'input';
CONSTRUCT: 'constructor';

// Tokens
IDENTIFIER: [a-zA-Z_][a-zA-Z_0-9]*;
NUMBER: [0-9]+;
STRING: '"' .*? '"';
BOOLEAN: TRUE | FALSE | YES | NO;

// Whitespace
WS: [ \t\r\n]+ -> skip;
