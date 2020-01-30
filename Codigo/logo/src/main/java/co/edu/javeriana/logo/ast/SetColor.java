package co.edu.javeriana.logo.ast;

import java.util.Map;

import co.edu.javeriana.logo.Turtle;

public class SetColor implements ASTNode {

	private ASTNode color1;
	private ASTNode color2;
	private ASTNode color3;
	private ASTNode transparencia;
	private Turtle turtle;

	public SetColor(ASTNode color1, ASTNode color2, ASTNode color3, ASTNode transparencia, Turtle turtle) {
		super();
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
		this.transparencia = transparencia;
		this.turtle = turtle;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable) {
		float red = (Float) color1.execute(symbolTable);
		float green = (Float) color2.execute(symbolTable);
		float blue = (Float) color3.execute(symbolTable);
		float alpha = (Float) transparencia.execute(symbolTable);
		turtle.color(red, green, blue, alpha);
		return null;
	}

}
