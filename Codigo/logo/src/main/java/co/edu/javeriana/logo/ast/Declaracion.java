package co.edu.javeriana.logo.ast;

import java.util.Map;

public class Declaracion implements ASTNode {

	private String name;
	
	public Declaracion(String name) {
		super();
		this.name = name;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable) {
		symbolTable.put(name, new Object());
		return null;
	}

}
