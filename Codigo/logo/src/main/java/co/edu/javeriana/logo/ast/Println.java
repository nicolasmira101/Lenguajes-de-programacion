package co.edu.javeriana.logo.ast;

import java.util.Map;

public class Println implements ASTNode {
	
	private ASTNode data;
	
	/**
	 * @param data
	 */
	public Println(ASTNode data) {
		super();
		this.data = data;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		System.out.println(data.execute(symbolTable));
		return null;
	}

}
