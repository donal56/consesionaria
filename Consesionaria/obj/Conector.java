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
		declaracion= null;
		resultado= null;
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
			if (datos.size() <= i) 
			{
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
    
    public static ArrayList<String> getDatatypes(String table) throws SQLException
    {
	crearConexion();
	
	ArrayList<String> datatypes = new ArrayList<String>();
	String stm= "SELECT data_type FROM information_schema.columns WHERE table_name = '" + table + "'";
	
	declaracion= con.createStatement();
	resultado= declaracion.executeQuery(stm);
	
	while(resultado.next())
	{
	    datatypes.add(resultado.getString(1));
	}
	
	cerrarConexion();
	
	return datatypes;
    }
    
    public static String getPK(String table) throws SQLException
    {
	crearConexion();
	
	String pk = null;
	String stm= "SELECT c.column_name FROM information_schema.table_constraints tc \n" + 
		"JOIN information_schema.constraint_column_usage AS ccu USING (constraint_schema, constraint_name) \n" + 
		"JOIN information_schema.columns AS c ON c.table_schema = tc.constraint_schema\n" + 
		"  AND tc.table_name = c.table_name AND ccu.column_name = c.column_name\n" + 
		"WHERE constraint_type = 'PRIMARY KEY' and tc.table_name = '" + table + "'";
	
	declaracion= con.createStatement();
	resultado= declaracion.executeQuery(stm);
	
	while(resultado.next())
	{
	    pk = resultado.getString(1);
	}
	
	cerrarConexion();
	
	return pk;
    }
    
    public static ArrayList<ArrayList<String>> getFKs(String table) throws SQLException
    {
	crearConexion();
	
	ArrayList<ArrayList<String>> fks = new ArrayList<ArrayList<String>>();
	fks.add(0,new ArrayList<String>());
	fks.add(1,new ArrayList<String>());
	fks.add(2,new ArrayList<String>());
	    
	String stm= "SELECT kcu.column_name, ccu.table_name, ccu.column_name FROM information_schema.table_constraints AS tc " + 
			"JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name " + 
			"AND tc.table_schema = kcu.table_schema JOIN information_schema.constraint_column_usage AS ccu " + 
			"ON ccu.constraint_name = tc.constraint_name AND ccu.table_schema = tc.table_schema " + 
			"WHERE tc.constraint_type = 'FOREIGN KEY' AND tc.table_name= '" + table + "';";
	
	declaracion= con.createStatement();
	resultado= declaracion.executeQuery(stm);
	
	while(resultado.next())
	{
	    fks.get(0).add(resultado.getString(1));
	    fks.get(1).add(resultado.getString(2));
	    fks.get(2).add(resultado.getString(3));
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
	
	try
	{
	    resultado = declaracion.executeQuery("SELECT * FROM " + tabla + " WHERE " + columnPK + "= '" + primaryKey + "'");
	}
	catch(SQLException e)
	{
	    resultado = declaracion.executeQuery("SELECT * FROM " + tabla + " WHERE " + columnPK + "= " + primaryKey);
	}
	
	while(resultado.next())
	{
        	for (int i = 0; i < resultado.getMetaData().getColumnCount(); i++)
        	{
        	    datos.add(resultado.getString(i + 1));
        	}
	}
	
	cerrarConexion();
	
	return datos;
    }
}