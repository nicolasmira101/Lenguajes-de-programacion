package co.edu.javeriana.logo.ast;

import java.util.Map;

public class Negacion implements ASTNode {
	
	private ASTNode node;
	
	/**
	 * @param node
	 */
	public Negacion(ASTNode node) {
		super();
		this.node = node;
	}



	@Override
	public Object execute(Map<String, Object> symbolTable) {
		Boolean valor = (Boolean)node.execute(symbolTable);
		return !valor;
	}

}
