// ANTLR4 lexer definition .g4

lexer grammar WvletLangLexer;

options {
  caseInsensitive=true;
}

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
