package co.edu.javeriana.logo.ast;

import java.util.Map;

import co.edu.javeriana.logo.Turtle;

public class MoveBack implements ASTNode {

	private ASTNode avance;
	private Turtle turtle;

	public MoveBack(ASTNode avance, Turtle turtle) {
		super();
		this.avance = avance;
		this.turtle = turtle;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		float valor = (Float) avance.execute(symbolTable);
		turtle.backwards(valor);
		return null;
	}

}
