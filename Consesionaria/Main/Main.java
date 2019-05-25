package Main;

import GUIs.GUIPrincipal;
import GUIs.GUIRegistro;

public class Main 
{
	public static void main(String[] args) 
	{
		//new GUIPrincipal();
	    	//Nuevo registro en tabla
		new GUIRegistro("vendedores", null);
		//Modificar o eliminar un registro existente
		//new GUIRegistro("vendedores", "RAGC980622BP3");
	}
}