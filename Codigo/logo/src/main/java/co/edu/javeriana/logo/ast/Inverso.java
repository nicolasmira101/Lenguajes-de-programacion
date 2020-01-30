package co.edu.javeriana.logo.ast;

import java.util.Map;

public class Inverso implements ASTNode {

	private Object numero;

	public Inverso(Object numero) {
		super();
		this.numero = numero;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		Float valor = (Float)numero; 
		return valor * (-1);
	}

}
