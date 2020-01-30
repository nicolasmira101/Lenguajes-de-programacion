package co.edu.javeriana.logo.ast;

import java.util.List;
import java.util.Map;


public class Funcion implements ASTNode {

	private String nombre;
	private List<String> parametros;
	private List<ASTNode> sentencias;
	private Boolean retorno;
	private ASTNode llaveRetorno;

	public Funcion(String nombre, List<String> parametros, List<ASTNode> sentencias,
			Boolean retorno, ASTNode llaveRetorno) {
		super();
		this.nombre = nombre;
		this.parametros = parametros;
		this.sentencias = sentencias;
		this.retorno = retorno;
		this.llaveRetorno = llaveRetorno;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable) {
		symbolTable.put(nombre, this);
		return null;
	}


	public String getNombre() {
		return nombre;
	}


	public List<String> getParametros() {
		List<String> re = this.parametros;
		return re;
	}


	public List<ASTNode> getSentencias() {
		List<ASTNode> se = this.sentencias;
		return se;
	}


	public Boolean getRetorno() {
		return retorno;
	}


	public ASTNode getLlaveRetorno() {
		return llaveRetorno;
	}

	

}
