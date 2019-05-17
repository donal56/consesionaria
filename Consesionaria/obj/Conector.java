package obj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Conector
{
    private static Connection con	  = null;
    private static Statement  declaracion = null;
    private static PreparedStatement preparado = null;
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
		declaracion= null;
		resultado= null;
		preparado= null;
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
		for (int i = 0; i < resultado.getMetaData().getColumnCount() ; i++)
		{
			if (datos.size() <= i) {
				datos.add(i,new ArrayList<String>());
			}
	    	datos.get(i).add(resultado.getString(i+1));
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
    
    public static ArrayList<String> getDatatypes(String stm) throws SQLException
    {
	crearConexion();
	
	ArrayList<String> datatypes = new ArrayList<String>();
	declaracion = con.createStatement();
	resultado = declaracion.executeQuery(stm);
	
	for (int i = 0; i < resultado.getMetaData().getColumnCount(); i++)
	{
	    datatypes.add(resultado.getMetaData().getColumnTypeName(i + 1));
	}
	
	cerrarConexion();
	
	return datatypes;
    }
    
    public static String getPK(String table) throws SQLException
    {
	crearConexion();
	
	String pk = null;
	resultado = con.getMetaData().getPrimaryKeys(null, null, table);
	
	while(resultado.next())
	{
	    pk = resultado.getString(1);
	}
	
	cerrarConexion();
	
	return pk;
    }
    
    public static ArrayList<String> getFKs(String table) throws SQLException
    {
	crearConexion();
	
	ArrayList<String> fks = new ArrayList<String>();
	resultado = con.getMetaData().getExportedKeys(null, null, table);
	
	while(resultado.next())
	{
	    fks.add(resultado.getString(1));
	}
	
	cerrarConexion();
	
	return fks;
    }

    public static String setQuery(String query) throws SQLException
    {
	crearConexion();
	
	declaracion = con.createStatement();
	int mod= declaracion.executeUpdate(query);
	
	cerrarConexion();	
    
	return (mod + " elemento(s) alterado(s)");
    }
    
    public static ArrayList<String> recuperarRegistro(String tabla, String columnPK, String primaryKey) throws SQLException
    {
	crearConexion();
	
	ArrayList<String> datos = new ArrayList<String>();
	declaracion = con.createStatement();
	resultado = declaracion.executeQuery("SELECT * FROM " + tabla + " WHERE " + columnPK + "= " + primaryKey);
	
	for (int i = 0; i < resultado.getMetaData().getColumnCount(); i++)
	{
	    datos.add(resultado.getString(i + 1));
	}
	
	cerrarConexion();
	
	return datos;
    }
}