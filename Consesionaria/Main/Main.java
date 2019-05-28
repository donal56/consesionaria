package Main;

import java.util.Properties;

import javax.swing.UIManager;

import com.jtattoo.plaf.smart.SmartLookAndFeel;

import GUIs.GUIPrincipal;
import obj.Conector;

public class Main 
{
	public static void main(String[] args) 
	{
		Properties props = new Properties();
		 props.put("focusColor", "62 135 189");
		 props.put("windowTitleColorLight" , "255 255 255");
		 props.put("windowTitleColorDark"  , "255 255 255");
		 props.put("windowBorderColor"     , "255 255 255");
		 props.put("menuBackgroundColor"   , "255 255 255");
		 props.put("menuOpaque"            , "255 255 255");
		SmartLookAndFeel.setCurrentTheme(props);
		//new GUIAviones();
		 
		 try{
	          UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
			 //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	     } catch(Exception ex) {
	    	 ex.printStackTrace();
	    	
	     }
		
		try {
			Conector.crearConexion();
			new GUIPrincipal();
		} catch (Exception e) {
			System.err.println(e);
			Conector.cerrarConexion();
		}
		
	    //Nuevo registro en tabla
		//new GUIRegistro("vendedores", null);
		//Modificar o eliminar un registro existente
		//new GUIRegistro("vendedores", "RAGC980622BP3");
	}
}