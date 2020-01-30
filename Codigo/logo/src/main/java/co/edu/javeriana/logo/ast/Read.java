package co.edu.javeriana.logo.ast;

import java.util.Map;
import java.util.Scanner;

public class Read implements ASTNode {

	private String nombre;
	private Scanner entrada;
	
	public Read(String nombre) {
		super();
		entrada = new Scanner(System.in);
		this.nombre = nombre;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		String palabra = entrada.nextLine();
		symbolTable.put(nombre, palabra);
		
		return null;
	}

}
