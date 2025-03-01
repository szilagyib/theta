grammar XstsDsl;

xsts:
    typeDeclarations+=typeDeclaration*
    variableDeclarations+=variableDeclaration (variableDeclarations+=variableDeclaration)*
    tran=tranSet
    init=initSet
    env=envSet
    PROP LCURLY prop=ctlExpr RCURLY;

// C T L
ctlExpr
    :  stateFormula
    ;

stateFormula
    :   (oper=CTLNOT) LPAREN op=stateFormula RPAREN
    |   LPAREN leftOp=stateFormula RPAREN (oper=CTLAND) LPAREN rightOp=stateFormula RPAREN
    |   LPAREN leftOp=stateFormula RPAREN (oper=CTLOR) LPAREN rightOp=stateFormula RPAREN
    |   LPAREN leftOp=stateFormula RPAREN (oper=CTLIMPLY) LPAREN rightOp=stateFormula RPAREN
    |   LPAREN leftOp=stateFormula RPAREN (oper=CTLEQ) LPAREN rightOp=stateFormula RPAREN
    |   (oper=EXISTS) LPAREN pform=pathFormula RPAREN
    |   (oper=FORALL) LPAREN pform=pathFormula RPAREN
    |   expr
    ;

pathFormula
    :   (oper=GLOBALLY) LPAREN op=stateFormula RPAREN
    |   (oper=FINALLY) LPAREN op=stateFormula RPAREN
    |   (oper=NEXT) LPAREN op=stateFormula RPAREN
    |   LPAREN leftOp=stateFormula RPAREN (oper=UNTIL) LPAREN rightOp=stateFormula RPAREN
    |   LPAREN leftOp=stateFormula RPAREN (oper=RELEASE) LPAREN rightOp=stateFormula RPAREN
    ;

// Boolean operators
CTLNOT      : 'Not';
CTLAND	    : 'And';
CTLOR		: 'Or';
CTLIMPLY	: 'Imply';
CTLEQ       : 'Eq';

//State operators
GLOBALLY	: 'G';
FINALLY	    : 'F';
NEXT		: 'X';
UNTIL		: 'U';
RELEASE		: 'R';

//Path quantifiers
EXISTS    : 'E';
FORALL    : 'A';

// D E C L A R A T I O N S

variableDeclaration
    :   CTRL? VAR name=ID DP ttype=type  (EQUALS initValue=ctlExpr)?
    ;

typeDeclaration
    :   TYPE name=ID DP LCURLY literals+=typeLiteral (COMMA literals+=typeLiteral)* RCURLY
    ;

typeLiteral
    :   name=ID
    ;

CTRL
    :   'ctrl'
    ;

VAR
    :   'var'
    ;

TYPE
    :   'type'
    ;

// T Y P E S

type:	boolType
	|	intType
	|	arrayType
	|   customType
	;

boolType
	:	BOOLTYPE
	;

intType
	:	INTTYPE
	;

arrayType
	:	LBRACK indexType=type RBRACK RARROW elemType=type
	;

customType
    :   name=ID
    ;

BOOLTYPE
	:	'boolean'
	;

INTTYPE
	:	'integer'
	;

// E X P R E S S I O N S

expr:   iteExpr
    ;

iteExpr
    :   iffExpr
    |   IF cond=expr THEN then=expr ELSE elze=expr
    ;

iffExpr
	:	leftOp=implyExpr (IFF rightOp=iffExpr)?
	;

implyExpr
	:	leftOp=orExpr (IMPLY rightOp=implyExpr)?
	;

orExpr
	:	ops+=xorExpr (OR ops+=xorExpr)*
	;

xorExpr
	:	leftOp=andExpr (XOR rightOp=xorExpr)?
	;

andExpr
	:	ops+=notExpr (AND ops+=notExpr)*
	;

notExpr
	:	equalityExpr
	|	NOT op=equalityExpr
	;

equalityExpr
	:	leftOp=relationExpr (oper=(EQ | NEQ) rightOp=relationExpr)?
	;

relationExpr
	:	leftOp=additiveExpr (oper=(LT | LEQ | GT | GEQ) rightOp=additiveExpr)?
	;

additiveExpr
	:	ops+=multiplicativeExpr (opers+=(PLUS | MINUS) ops+=multiplicativeExpr)*
	;

multiplicativeExpr
	:	ops+=unaryExpr (opers+=(MUL | DIV | MOD | REM) ops+=unaryExpr)*
	;

unaryExpr
	:	accessorExpr
	|	oper=(PLUS | MINUS) op=unaryExpr
	;

accessorExpr
	:	op=primaryExpr (accesses+=access)*
	;

access
    :	readIndex=arrayReadAccess
    |	writeIndex=arrayWriteAccess
    ;

arrayReadAccess
	:	LBRACK index=expr RBRACK
	;

arrayWriteAccess
	:	LBRACK index=expr LARROW elem=expr RBRACK
	;

primaryExpr
	:	trueExpr
	|	falseExpr
	|	intLitExpr
	|	arrLitExpr
	|	idExpr
	|	parenExpr
	;

trueExpr
	:	TRUE
	;

falseExpr
	:	FALSE
	;

intLitExpr
	:	value=INTLIT
	;

arrLitExpr
    :   LBRACK (indexExpr+=expr LARROW valueExpr+=expr COMMA)+ (LT indexType=type GT)? DEFAULT LARROW elseExpr=expr RBRACK
    |   LBRACK LT indexType=type GT DEFAULT LARROW elseExpr=expr RBRACK
    ;

idExpr
	:	id=ID
	;

parenExpr
	:	LPAREN op=expr RPAREN
	;


////

THEN:	'then'
	;

IFF	:	'iff'
	;

IMPLY
	:	'=>'
	;

OR	:	'||'
	;

AND	:	'&&'
	;

XOR	:	'xor'
	;

NOT	:	'!'
	;

EQ	:	'=='
	;

NEQ	:	'!='
	;

LT	:	'<'
	;

LEQ	:	'<='
	;

GT	:	'>'
	;

GEQ	:	'>='
	;

PLUS:	'+'
	;

MINUS
	:	'-'
	;

MUL	:	'*'
	;

DIV	:	'/'
	;

MOD	:	'%'
	;

REM	:	'rem'
	;

TRUE:	'true'
	;

FALSE
	:	'false'
	;

DEFAULT
    :   'default'
    ;


// S T A T E M E N T S

stmt:	localVarDeclStmt
    |   assignArrayWriteSugar
    |   assignStmt
	|	havocStmt
	|	assumeStmt
	|   nonDetStmt
	|   ifStmt
	|   blockStmt
	|   loopStmt
	;

nonDetStmt
    :   CHOICE blocks+=stmt (NONDET_OR blocks+=stmt)*
    ;

blockStmt
    :   LCURLY (stmts+=stmt)* RCURLY
    ;

ifStmt
    :   IF LPAREN cond=ctlExpr RPAREN then=stmt (ELSE elze=stmt)?
    ;

loopStmt
    :   FOR loopVar=ID FROM from=ctlExpr TO to=ctlExpr DO subStmt=stmt
    ;

localVarDeclStmt
    :   LOCAL VAR name=ID DP ttype=type (EQUALS initValue=ctlExpr)? SEMICOLON
    ;

assignArrayWriteSugar
    :   array=ID LBRACK index=ctlExpr RBRACK ASSIGN value=ctlExpr SEMICOLON
    ;

assignStmt
	:	lhs=ID ASSIGN value=ctlExpr SEMICOLON
	;

havocStmt
	:	HAVOC lhs=ID SEMICOLON
	;

assumeStmt
	:	ASSUME cond=ctlExpr SEMICOLON
	;

//

IF	:	'if'
	;

ELSE:	'else'
	;

ASSIGN
	:	':='
	;

HAVOC
	:	'havoc'
	;

ASSUME
	:	'assume'
	;

CHOICE
    :   'choice'
    ;

FOR
    :   'for'
    ;

FROM
    :   'from'
    ;

TO
    :   'to'
    ;

DO
    :   'do'
    ;

NONDET_OR
    :   'or'
    ;

LCURLY
    :   '{'
    ;

RCURLY
    :   '}'
    ;

LOCAL
    :   'local'
    ;

// T R A N S I T I O N S

tranSet
    :   TRAN transitionSet
    ;

envSet
    :   ENV transitionSet
    ;

initSet
    :   INIT transitionSet
    ;

transitionSet
    :   stmts+=stmt (NONDET_OR stmts+=stmt)*
    ;

TRAN
    :   'trans'
    ;

INIT
    :   'init'
    ;

ENV
    :   'env'
    ;


// B A S I C   T O K E N S

DP
    :   ':'
    ;

EQUALS
    :   '='
    ;

PROP
    :   'prop'
    ;

SEMICOLON
    :   ';'
    ;

LARROW
    :   '<-'
    ;

RARROW
    :   '->'
    ;

LPAREN
    :   '('
    ;

RPAREN
    :   ')'
    ;

PRIME
    :   '\''
    ;

COMMA
    :   ','
    ;

LBRACK
    :   '['
    ;

RBRACK
    :   ']'
    ;

INTLIT
    :   [0-9]+
    ;

ID
    :   [a-zA-Z_][a-zA-Z0-9_]*
    ;

WS
    :   (' '| '\t' | '\n' | '\r') -> skip
    ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;
