package co.edu.javeriana.logo.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EjecutarFuncion implements ASTNode {

	private String nombre;
	private List<ASTNode> argumentos;

	public EjecutarFuncion(String nombre, List<ASTNode> argumentos) {
		super();
		this.nombre = nombre;
		this.argumentos = argumentos;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable) {
		Funcion func = (Funcion)symbolTable.get(nombre);
		Map<String, Object> funcTable = new HashMap<String, Object>();
		List<String> parametros = func.getParametros();
		List<ASTNode> setencias = func.getSentencias();
		
		if( parametros.size() != argumentos.size() )
		{
			System.out.println("Numero de argumentos invalidos");
		}
		else
		{
			int cont = 0;
			
			for( ASTNode n : argumentos)
			{
				funcTable.put(parametros.get(cont), n.execute(symbolTable));
				cont++;
			}
			
			for( ASTNode n : setencias)
			{
				n.execute(funcTable);
			}
		}
		if( func.getRetorno() )
		{
			return func.getLlaveRetorno().execute(funcTable);
		}
		return null;
	}

}
