grammar Slyde;

// Entry point
prog: classDeclaration* EOF;

// Class Definition
classDeclaration: 'class' IDENTIFIER '{' classBody '}' ;

// Class body (fields and methods)
classBody: (fieldDeclaration | methodDeclaration)* ;

// Variable declarations
fieldDeclaration: type IDENTIFIER ';' ;

// Method declarations
methodDeclaration: type IDENTIFIER '(' paramList? ')' block ;

// Parameter List
paramList: type IDENTIFIER (',' type IDENTIFIER)* ;

// Block (code inside `{}`)
block: '{' statement* '}' ;

// Statements
statement: varDecl | assignment | methodCall | returnStmt | block ;

// Variable declaration
varDecl: type IDENTIFIER '=' expr ';' ;

// Assignment
assignment: IDENTIFIER '=' expr ';' ;

// Return statement
returnStmt: 'return' expr ';' ;

// Method Call
methodCall: IDENTIFIER '(' argList? ')' ';' ;

// Arguments
argList: expr (',' expr)* ;

// Expressions
expr: IDENTIFIER | NUMBER | STRING | methodCall | expr ('+' | '-' | '*' | '/') expr ;

// Type Definitions
type: 'int' | 'float' | 'String' | 'boolean';

// Tokens
IDENTIFIER: [a-zA-Z_][a-zA-Z_0-9]* ;
NUMBER: [0-9]+ ;
STRING: '"' .*? '"' ;
WS: [ \t\r\n]+ -> skip ;
