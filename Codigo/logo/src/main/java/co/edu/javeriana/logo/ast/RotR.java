package co.edu.javeriana.logo.ast;

import java.util.Map;

import co.edu.javeriana.logo.Turtle;

public class RotR implements ASTNode {

	private ASTNode rotacion;
	private Turtle turtle;

	public RotR(ASTNode avance, Turtle turtle) {
		super();
		this.rotacion = avance;
		this.turtle = turtle;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		float valor = (Float) rotacion.execute(symbolTable);
		turtle.right(valor);
		return null;
	}

}
