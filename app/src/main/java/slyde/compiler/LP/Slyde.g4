grammar Slyde;

prog: 
    (classDeclaration | MAIN '(' paramList? ')' block)* EOF;

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

binOp:
    '+' | '-' | '*' | '/';

compareOp:
    '==' | '!=' | '<' | '>' | '<=' | '>=' | '&&' | '||';

// Expressions
expr: 
    expr binOp expr
    | expr compareOp expr
    | '!' expr
    | IDENTIFIER ('[' expr ']')?
    | NUMBER
    | STRING
    | BOOLEAN
    | methodCall
    | '(' expr ')'
    | IDENTIFIER
    | newInstance
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


// Types
type: 
    'int' | 'double' | 'float' | 'String' | 'boolean' | 'void' | IDENTIFIER;

// Arrays
arrayDeclaration: 
    type '[' ']' IDENTIFIER ('=' arrayLiteral)? ';';

arrayLiteral: 
    '[' (expr (',' expr)*)? ']';

newInstance:
    'new' IDENTIFIER '(' argList? ')';

SLC:
    '//' ~[\r\n]* -> skip;

MLC:
    '/*' .*? '*/' -> skip;


CLASS: 'class';
EXTENDS: 'extends';
IF: 'if';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
RETURN: 'return';
CONSTRUCT: 'constructor';
MAIN: 'main';

// Tokens
IDENTIFIER: [a-zA-Z_][a-zA-Z_0-9]*;
NUMBER: [-+]?[0-9]+;
DOUBLE: [-+]?([0-9]*.[0-9]+|[0-9]+.[0-9]*)[eE][-+]?[0-9]+?;
FLOAT: [-+]?([0-9]*.[0-9]+|[0-9]+.[0-9]*)[eE][-+]?[0-9]+?'f';
STRING: '"' .*? '"';
BOOLEAN: 'true' | 'yes' | 'on' | 'false' | 'no' | 'off';

// Whitespace
WS: [ \t\r\n]+ -> skip;
