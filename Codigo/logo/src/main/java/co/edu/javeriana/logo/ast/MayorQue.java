package co.edu.javeriana.logo.ast;

import java.util.Map;

public class MayorQue implements ASTNode {
	
	private ASTNode comp1;
	private ASTNode comp2;

	/**
	 * @param comp1
	 * @param comp2
	 */
	public MayorQue(ASTNode comp1, ASTNode comp2) {
		super();
		this.comp1 = comp1;
		this.comp2 = comp2;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		if((float)comp1.execute(symbolTable) > (float)comp2.execute(symbolTable)) {
			return true;
		}
		return false;
	}

}
