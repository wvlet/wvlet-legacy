grammar WvletLang;

options { caseInsensitive=true; }

tokens {
    DELIMITER
}

singleStatement
    : statement EOF
    ;

statement
    : query
    ;

qualifiedName
    : identifier ('.' identifier)*
    ;

identifier
    : IDENTIFIER             #unquotedIdentifier
    | QUOTED_IDENTIFIER      #quotedIdentifier
//    | nonReserved            #unquotedIdentifier
    ;

IDENTIFIER
    : (LETTER | '_') (LETTER | DIGIT | '_' | '@' | ':')*
    ;

QUOTED_IDENTIFIER
    : '"' ( ~'"' | '""' )* '"'
    ;

query
    : FOR identifier IN expression
      (WHERE expression)?
      (GROUP BY expression)?
      (RETURN expression)?
    ;

expression
    : qualifiedName
    | functionCall
    ;

functionCall
    : qualifiedName '(' (functionArg (',' functionArg)*)? ')'
    ;

functionArg
    : expression
    | namedExpression
    ;

namedExpression
    :  identifier COLON expression
    ;


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


COLON: ':';
SEMICOLON: ';';

fragment DIGIT
    : [0-9]
    ;

fragment LETTER
    : [A-Z]
    ;


SIMPLE_COMMENT
    : '--' ~[\r\n]* '\r'? '\n'? -> channel(HIDDEN)
    ;

BRACKETED_COMMENT
    : '"""' .*? '"""' -> channel(HIDDEN)
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
