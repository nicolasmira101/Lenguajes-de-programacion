package co.edu.javeriana.logo.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class While implements ASTNode {

	private ASTNode condicion;
	private List<ASTNode> body;

	public While(ASTNode condicion, List<ASTNode> body) {
		super();
		this.condicion = condicion;
		this.body = body;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable) {
		Map<String, Object> auxiliar = new HashMap<String, Object>();
		
		for( Map.Entry<String, Object> entry : symbolTable.entrySet())
		{
			auxiliar.put(entry.getKey(), symbolTable.get(entry.getKey()));
		}
		
		while( (Boolean)condicion.execute(auxiliar) )
		{
			for(ASTNode n : body)
			{
				n.execute(auxiliar);
			}
		}
		
		for( Map.Entry<String, Object> entry : symbolTable.entrySet())
		{
			symbolTable.put(entry.getKey(), auxiliar.get(entry.getKey()));
		}
		
		return null;
	}

}
