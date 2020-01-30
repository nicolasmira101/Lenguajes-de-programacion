package co.edu.javeriana.logo.ast;

import java.util.List;
import java.util.Map;

public class If implements ASTNode {
	
	private ASTNode condicion;
	private List<ASTNode> body;
	private List<ASTNode> elseBody;

	
	public If(ASTNode condicion, List<ASTNode> body, List<ASTNode> elseBody) {
		super();
		this.condicion = condicion;
		this.body = body;
		this.elseBody = elseBody;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable) {
		if( (boolean)condicion.execute(symbolTable) ) 
		{
			for( ASTNode n : body ) 
			{
				n.execute(symbolTable);
			}
		} 
		else 
		{
			for( ASTNode n : elseBody )
			{
				n.execute(symbolTable); 
			}
		}
		return null;
	}

}
