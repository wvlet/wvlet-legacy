parser grammar WvletLangParser;

options {
  tokenVocab=WvletLangLexer;
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
    : forItem (COMMA? forItem)*         #singleLineFor
    | forItem NEWLINE (INDENT forItem NEWLINE)* #multiLineFor
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


identifier
    : IDENTIFIER             #unquotedIdentifier
    | QUOTED_IDENTIFIER      #quotedIdentifier
    | nonReserved            #unquotedIdentifier
    ;


