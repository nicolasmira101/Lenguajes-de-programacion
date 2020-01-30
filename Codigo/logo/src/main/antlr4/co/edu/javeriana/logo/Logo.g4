grammar Logo;

@parser::header{
import java.util.Map;
import java.util.HashMap;
import co.edu.javeriana.logo.ast.*;
}

@parser::members {

	private Turtle turtle;

	public LogoParser(TokenStream input, Turtle turtle) {
    	this(input);
    	this.turtle = turtle;
	}
	
	Map<String, Object> symbolTable = new HashMap<String, Object>();

}


//------------------SINTAXIS Y GRAMÁTICAS--------------------------------------------------------------

program: 
	{
		List<ASTNode> body =  new ArrayList<ASTNode>();
		Map<String, Object> symbolTable = new HashMap<String, Object>();
		Map<String, Object> funcTable = new HashMap<String, Object>();
	}
	(sentence{body.add($sentence.node);})*
	{
		for(ASTNode n : body)
		{
			n.execute(symbolTable);
		}
	};

sentence returns [ASTNode node]:  
	imprimir {$node = $imprimir.node;}
	|leer {$node = $leer.node;} 
	|condicion {$node = $condicion.node;}
	|declaracion {$node = $declaracion.node;}
	|asignacion {$node = $asignacion.node;}
	|mientras {$node = $mientras.node;}
	|ejecutarFuncion {$node = $ejecutarFuncion.node;}
	|comandos {$node = $comandos.node;}
	|funcion {$node = $funcion.node;}
	;

comandos returns [ASTNode node]:
	(c1 = move_forw {$node = $c1.node;})
	|(c2 = move_back {$node = $c2.node;})
	|(c3 = rot_l {$node = $c3.node;})
	|(c4 = rot_r {$node = $c4.node;})
	|(c5 = set_color {$node = $c5.node;});
	
imprimir returns [ASTNode node]: PRINTLN expression
			{$node = new Println($expression.node);};

leer returns [ASTNode node] : READ ID
			{$node = new Read($ID.text);};
			
mientras returns [ASTNode node]:
{
	List<ASTNode> body = new ArrayList<ASTNode>();
}
WHILE expression DO (s1 = sentence {body.add($s1.node);})*
END_WHILE 
{
	$node = new While($expression.node, body);
};

condicion returns [ASTNode node]: 
			{
				List<ASTNode> body = new ArrayList<ASTNode>();
				List<ASTNode> elseBody = new ArrayList<ASTNode>();
			}
			IF expression THEN (s1 = sentence { body.add($s1.node);})*
			(ELSE (s2 = sentence { elseBody.add($s2.node);})* )? END_IF
			{
				$node = new If( $expression.node, body, elseBody);	
			};

move_forw returns [ASTNode node]:  MOVE_FORW expression
	{
		$node = new MoveForw($expression.node, this.turtle);
	};

move_back returns [ASTNode node]:  MOVE_BACK expression
	{
		$node = new MoveBack($expression.node, this.turtle);
	};

rot_l returns [ASTNode node]:  ROT_L expression
	{
		$node = new RotL($expression.node, this.turtle);
	};
	
rot_r returns [ASTNode node]:  ROT_R expression
	{
		$node = new RotR($expression.node, this.turtle);
	};
	
set_color returns [ASTNode node]:   SET_COLOR e1 = expression COMA e2 = expression COMA e3 = expression COMA e4 = expression
	{
		$node = new SetColor($e1.node, $e2.node, $e3.node, $e4.node, this.turtle);
	};	

//------------------SEMÁNTICA--------------------------------------------------------------------------

declaracion returns [ASTNode node]:
	LET ID {$node = new Declaracion($ID.text);}
	(ASSING expression {$node = new Asignacion($ID.text, $expression.node);})?;

asignacion returns [ASTNode node]:
	ID ASSING expression {$node = new Asignacion($ID.text, $expression.node);};

expression returns [ASTNode node]:
	t1 = andOr{$node = $t1.node;}
	|NOT t2 = andOr{$node = new Negacion($t2.node);};
	
andOr returns [ASTNode node]:
	t1 = comparison{$node = $t1.node;}
	(AND t2 = comparison{$node = new And($node, $t2.node);}
	|OR t3 = comparison{$node = new Or($node, $t3.node);}		
	)*;
	
comparison returns [ASTNode node]:
	t1 = addition{$node = $t1.node;}
	(EQ t2 = addition {$node = new Igualdad($node, $t2.node);}
	|GT t3 = addition {$node = new MayorQue($node, $t3.node);}
	|LT t4 = addition {$node = new MenorQue($node, $t4.node);}
	|GEQ t5 = addition {$node = new MayorIgual($node, $t5.node);}
	|LEQ t6 = addition {$node = new MenorIgual($node, $t6.node);}
	|NEQ t7 = addition {$node = new Diferente($node, $t7.node);}		
	)*;

addition returns [ASTNode node]:
	t1 = factor{$node = $t1.node;} 
	(PLUS t2 = factor {$node = new Addition($node, $t2.node);}
	|MINUS t2 = factor {$node = new Substraction($node, $t2.node);}
	)*;

factor returns [ASTNode node]:
	t1 = term{$node = $t1.node;}
	(MULT t2 = term {$node = new Multiplication($node, $t2.node);}
	|DIV t2 = term {$node = new Division($node, $t2.node);}
	|MODUL t2 = term {$node = new Module($node, $t2.node);}
	)*;

term returns [ASTNode node]:
	NUMBER { $node = new Constante(Float.parseFloat($NUMBER.text));}
	|MINUS NUMBER { $node = new Inverso(Float.parseFloat($NUMBER.text));}
	|BOOLEAN { $node = new Constante(Boolean.parseBoolean($BOOLEAN.text));}
	|ID {$node = new Referencia($ID.text);}
	|PAR_OPEN expression{$node = $expression.node;} PAR_CLOSE
	|STRING{
		String cadena = $STRING.text;
		$node = new Constante(cadena.substring(1, cadena.length()-1));
	}
	|ejecutarFuncion {$node = $ejecutarFuncion.node;}
	;

funcion returns [ASTNode node]:
	{
		Boolean retorna = false;
		ASTNode retorno = new Constante("NA");
	}
	DEF t1 = ID PAR_OPEN
	{
		List<String> parametros = new ArrayList<String>();
		List<ASTNode> body = new ArrayList<ASTNode>();
	}
	(t2 = ID{parametros.add($t2.text);})?
	(COMA t3 = ID{parametros.add($t3.text);})*
	PAR_CLOSE DOUBLE_POINT (s1 = sentence{body.add($s1.node);})*
	(RETURN re = andOr{
		retorna = true;
		retorno = $re.node;
	})?
	END_DEF
	{
		$node = new Funcion($t1.text, parametros, body, retorna, retorno);
	};

ejecutarFuncion returns [ASTNode node]:
	{
		List<ASTNode> argumentos = new ArrayList<ASTNode>();	
	}
	ID PAR_OPEN (p1 = expression{argumentos.add($p1.node);})?
	(COMA p2 = expression{argumentos.add($p2.node);})*
	PAR_CLOSE
	{
		$node = new EjecutarFuncion($ID.text, argumentos);
	};

//------------------COMANDOS TORTUGA-------------------------------------------------------------------

MOVE_FORW:'move_forw';
MOVE_BACK:'move_back';
ROT_L:'rot_l';
ROT_R:'rot_r';
SET_COLOR:'set_color';

//------------------PALABRAS RESERVADAS----------------------------------------------------------------

LET:'let';
DEF:'def';
END_DEF:'end_def';
WHILE:'while';
END_WHILE:'end_while';
PRINTLN:'println';
IF:'if';
ELSE:'else';
END_IF:'end_if';
THEN:'then';
DO:'do';
READ:'read';
BOOLEAN:'true'|'false';
RETURN: 'return';

//------------------EXPRESIONES ARITMETICAS-----------------------------------------------------------

PLUS:'+';
MINUS:'-';
MULT:'*';
DIV:'/';
MODUL:'%';

//------------------EXPRESIONES LOGICAS---------------------------------------------------------------

AND:'&&';
OR:'||';
NOT:'!';

//------------------EXPRESIONES COMPARACION------------------------------------------------------------

GT:'>';
LT:'<';
GEQ:'>=';
LEQ:'<=';
EQ:'==';
NEQ:'<>';
ASSING:'=';

//--------------------AGRUPADORES----------------------------------------------------------------------

PAR_OPEN:'(';
PAR_CLOSE:')';

DOUBLE_POINT:':';
COMA:',';

//---------------------IDENTIFICADORES------------------------------------------------------------------

ID:[a-zA-Z_][a-zA-Z0-9]*;
NUMBER: [0-9]+('.'[0-9]+)?;
STRING:'"' ( '\\"' | . )*? '"';


WS:[ \t\r\n]+ -> skip;