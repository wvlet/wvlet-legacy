grammar WvletLang;

options {
  caseInsensitive=true;
}


singleStatement
    : statement EOF
    ;

statement
    : query
    ;

query
    : FOR forClause
      (WHERE expression)?
      (GROUP BY expression)?
      returnClause?
    ;

forClause
    : forItem (COMMA? forItem)*
    ;

forItem
    : identifier IN expression
    ;

returnClause
    : RETURN expression
    ;

returnExpr
    : expression (COMMA expression)*                  # singleLineExpr
    | expression NEWLINE (INDENT expression NEWLINE)* # multiLineExpr
    ;

qualifiedName
    : identifier (DOT identifier)*
    ;

expression
    : qualifiedName
    | functionCall
    ;

functionCall
    : qualifiedName LEFT_PAREN (functionArg (COMMA  functionArg)*)? RIGHT_PAREN
    ;

functionArg
    : expression
    | namedExpression
    ;

namedExpression
    :  identifier COLON expression
    ;

nonReserved
    : FOR
    | IN
    ;



SEMICOLON: ';';

LEFT_PAREN: '(';
RIGHT_PAREN: ')';
COMMA: ',';
DOT: '.';
COLON: ':';
LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';

// logical operators
IF: 'if';
THEN: 'then';
ELSE: 'else';
AND: 'and';
OR: 'or';
NOT: 'not';
NULL: 'null';

// column rename
AS: 'AS';

DEF: 'def';
IMPORT: 'import';
SET: 'set';

// FLOWER
FOR: 'for';
IN: 'in';
FROM: 'from';
WHERE: 'where';
GROUP: 'group';
BY: 'BY';
JOIN: 'join';
LEFT: 'left';
RIGHT: 'right';
LIMIT: 'limit';
END: 'end';
ORDER: 'order';
RETURN: 'return';

EXPORT: 'export';
TO: 'to';
// boolean expressions
TRUE: 'true';
FALSE: 'false';

EQ  : '=';
NEQ : '!=';
LT  : '<';
LTE : '<=';
GT  : '>';
GTE : '>=';

PLUS: '+';
MINUS: '-';
ASTERISK: '*';

AT: '@';

identifier
    : IDENTIFIER             #unquotedIdentifier
    | QUOTED_IDENTIFIER      #quotedIdentifier
    | nonReserved            #unquotedIdentifier
    ;

IDENTIFIER
    : (LETTER | '_') (LETTER | DIGIT | '_' | '@' | ':')*
    ;

QUOTED_IDENTIFIER
    : '"' ( ~'"' | '""' )* '"'
    ;


fragment DIGIT
    : [0-9]
    ;

fragment LETTER
    : [A-Z]
    ;

ANNOTATION_COMMENT
    : '--' AT ~[\r\n]* '\r'? '\n'?
    ;

SIMPLE_COMMENT
    : '--' ~[\r\n]* '\r'? '\n'? -> channel(HIDDEN)
    ;

BRACKETED_COMMENT
    : '"""' .*? '"""' -> channel(HIDDEN)
    ;

INDENT
    : '  '
    ;

NEWLINE
    : [\r\n]
    ;

WS
    : [ \r\n\t]+ -> channel(HIDDEN)
    ;

// Catch-all for anything we can't recognize.
// We use this to be able to ignore and recover all the text
// when splitting statements with DelimiterLexer
UNRECOGNIZED
    : .
    ;

