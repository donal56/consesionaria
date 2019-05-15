package obj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Conector
{
    private static Connection con	  = null;
    private static Statement  declaracion = null;
    private static ResultSet  resultado	  = null;

    private static void crearConexion()
    {
	try
	{
	    if (con == null)
	    {
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection(
			"jdbc:postgresql://64.62.211.134:5432/sia2019_agencia",
			"sia2019", "intellideskSIA");
	    }
	}
	catch (Exception e)
	{
	    System.out.println("Conexión fallida");
	    e.printStackTrace();
	}
    }

    private static void cerrarConexion()
    {
	try
	{
	    if (con != null)
	    {
		con.close();
		declaracion.close();
		resultado.close();
		con= null;
	    }
	}
	catch (Exception e)
	{
	    System.out.println("Desconexión fallida");
	}
    }

    public static void test()
    {
	crearConexion();
	cerrarConexion();
    }

    public static ArrayList<ArrayList<String>> getDatos(String stm) throws SQLException
    {
	crearConexion();
	
	ArrayList<ArrayList<String>> datos = new ArrayList<ArrayList<String>>();
	declaracion = con.createStatement();
	resultado = declaracion.executeQuery(stm);
	
	while (resultado.next())
	{
	    for (int i = 0; i < resultado.getMetaData().getColumnCount(); i++)
	    {
		ArrayList<String> rows = new ArrayList<String>();
		rows.add(resultado.getString(i + 1));
		datos.add(rows);
	    }
	}
	
	cerrarConexion();
	
	return datos;
    }
    
    public static ArrayList<String> getHeaders(String stm) throws SQLException
    {
	crearConexion();
	
	ArrayList<String> headers = new ArrayList<String>();
	declaracion = con.createStatement();
	resultado = declaracion.executeQuery(stm);
	
	for (int i = 0; i < resultado.getMetaData().getColumnCount(); i++)
	{
	    headers.add(resultado.getMetaData().getColumnLabel(i + 1));
	}
	
	cerrarConexion();
	
	return headers;
    }

    public static String setQuery(String query) throws SQLException
    {
	crearConexion();
	
	declaracion = con.createStatement();
	int mod= declaracion.executeUpdate(query);
	
	cerrarConexion();	
    
	return (mod + " elemento(s) alterado(s)");
    }
}
