package co.edu.javeriana.logo.ast;

import java.util.Map;

public class Constante implements ASTNode {

	private Object value;
	
	public Constante(Object value) {
		super();
		this.value = value;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		return value;
	}

}
