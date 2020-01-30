package co.edu.javeriana.logo.ast;

import java.util.Map;

public class Referencia implements ASTNode {

	private String name;

	public Referencia(String name) {
		super();
		this.name = name;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable) {
		return symbolTable.get(name);
	}

}
