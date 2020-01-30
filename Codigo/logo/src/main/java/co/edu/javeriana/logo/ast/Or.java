package co.edu.javeriana.logo.ast;

import java.util.Map;

public class Or implements ASTNode {
	
	private ASTNode comp1;
	private ASTNode comp2;
	
	/**
	 * @param comp1
	 * @param comp2
	 */
	public Or(ASTNode comp1, ASTNode comp2) {
		super();
		this.comp1 = comp1;
		this.comp2 = comp2;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		Boolean valor1 = (Boolean)comp1.execute(symbolTable);
		Boolean valor2 = (Boolean)comp2.execute(symbolTable);
		return (valor1 || valor2);
	}

}
