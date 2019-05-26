package GUIs;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import obj.Conector;

public class GUIRegistro extends JDialog
{
    private static final long  	serialVersionUID	= 1L;
    
    private JPanel	       	panel;
    private GridBagConstraints 	gbc;
    private ArrayList<String>  	datos, datatypes, consulta;
    private ArrayList<ArrayList<String>> foreignKeys;
    private String	       	columnPK, primaryKey= null, tabla;
    private JButton	       	guardar, eliminar;

    public GUIRegistro(String tb, String pk)
    {
	tabla = tb;
	primaryKey = pk;
	
	setModalityType(ModalityType.APPLICATION_MODAL);
	
	try
	{
	    columnPK= Conector.getPK(tb);
	}
	catch (SQLException e)
	{
	    e.printStackTrace();
	    System.out.println("No se pudo recuperar la llave principal");
	}
	
	init();
    }
    
    public void init()
    {
	panel 	= new JPanel();
	gbc 	= new GridBagConstraints();
	guardar = new JButton("Guardar");
	eliminar = new JButton("Eliminar");
	
	panel.setLayout(new GridBagLayout());
	panel.setFont(new Font("Calibri light", Font.PLAIN, 15));
	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.PAGE_START;
	gbc.gridwidth = GridBagConstraints.REMAINDER;
	panel.add(new JLabel(firstUpperCase(tabla)), gbc);
	
	try
	{
	    datos = Conector.getHeaders("SELECT * FROM " + tabla);
	    datatypes = Conector.getDatatypes(tabla);
	    foreignKeys = Conector.getFKs(tabla);
	    consulta = Conector.recuperarRegistro(tabla, columnPK, primaryKey);

	   
	    for (int i = 0; i < datos.size(); i++)
	    {
		JLabel lb = new JLabel(firstUpperCase(datos.get(i)));
		lb.setToolTipText(datatypes.get(i));
		
		if (datos.get(i).equals(columnPK))
		    lb.setForeground(Color.red);
		
		if (foreignKeys.get(0).contains(datos.get(i)))
		{
		    lb.setForeground(Color.blue);
		    addComponentes(lb, new JComboBox<String>());
		}
		else
		{
		    addComponentes(lb, new JTextField(20));
		}
	    }
	    
	    if (consulta != null && primaryKey != null)
	    {
		rellenarDatos();
		setFont();
		guardar.addActionListener(accionModificar);
		eliminar.addActionListener(accionEliminar);
	    }
	    else
	    {
		rellenarFK();
		setFont();
		setTitle("Insertar registro");
		eliminar.setText("Limpiar");
		eliminar.addActionListener(accionLimpiar);
		guardar.addActionListener(accionAgregar);
	    }
	}
	catch (SQLException e)
	{
	    JOptionPane.showMessageDialog(null,  "Error al consultar el registro");
	    e.printStackTrace();
	}
	
	JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
	
	botones.add(guardar);
	botones.add(eliminar);
	panel.add(botones, gbc);
	
	add(panel);
	pack();
	
	setSize(450,600);
	setLocationRelativeTo(null);
	setVisible(true);
    }

    private void rellenarDatos() throws SQLException
    {
	int caux= 0;
	
	for (int i = 0, k = 0; k < consulta.size(); i++)
	{
	    if (panel.getComponent(i) instanceof JTextField)
	    {
		((JTextField) panel.getComponent(i)).setText(consulta.get(k));
		k++;
	    }
	    else if (panel.getComponent(i) instanceof JComboBox<?>)
	    {
		JComboBox<String> combo = (JComboBox<String>) panel.getComponent(i);
		
		ArrayList<ArrayList<String>> aux= Conector.getDatos("SELECT " + foreignKeys.get(2).get(caux) + " FROM " + foreignKeys.get(1).get(caux));
		
		caux++;
		
		for(int c= 0; c < aux.get(0).size(); c++)
		{
		    combo.addItem(aux.get(0).get(c));
		}
		
		k++;
	    }
	}
    }
    
    private void rellenarFK() throws SQLException
    {
	int caux= 0;
	
	for (int k = 0; k < panel.getComponentCount(); k++)
	{
	    if (panel.getComponent(k) instanceof JComboBox<?>)
	    {
		JComboBox<String> combo = (JComboBox<String>) panel.getComponent(k);
		
		ArrayList<ArrayList<String>> aux= Conector.getDatos("SELECT " + foreignKeys.get(2).get(caux) + " FROM " + foreignKeys.get(1).get(caux));
		
		caux++;
		
		for(int c= 0; c < aux.get(0).size(); c++)
		{
		    combo.addItem(aux.get(0).get(c));
		}
		
		k++;
	    }
	}
    }

    private void setFont()
    {
	for (int i = 0; i < panel.getComponentCount(); i++)
	{
	    if (panel.getComponent(i) instanceof JLabel)
	    {
		panel.getComponent(i).setFont(null);
	    }
	}
    }

    private void addComponentes(JLabel lb, Component comp)
    {
	gbc.gridwidth = 1;
	panel.add(lb, gbc);
	gbc.gridwidth = GridBagConstraints.REMAINDER;
	panel.add(comp, gbc);
    }

    private String firstUpperCase(String word)
    {
	return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static String insert(String word)
    {
	return ("'" + word + "'").trim();
    }

    ActionListener accionAgregar   = new ActionListener()
    {
	public void actionPerformed( ActionEvent arg0)
	{
	    String query = "";
	    int i = 0, k = 0;
	
	    while (i < panel.getComponentCount())
	    {
		if (panel.getComponent(i) instanceof JTextField)
		{
		    if (k != 0)
			query += " , ";
		    
		    query += insert(((JTextField) panel.getComponent(i)).getText());
		    k++;
		}
		else if (panel.getComponent(i) instanceof JComboBox<?>)
		{
		    if (k != 0)
			query += " , ";
		    
		    query += insert(((JComboBox<String>) panel.getComponent(i)).getSelectedItem().toString());
		    k++;
		}
		i++;
	    }
	    
	    try
	    {
		Conector.setQuery("Insert Into " + tabla + " Values(" + query + " )");
		JOptionPane.showMessageDialog(null, "Registro agregado exitosamente.");
		
		//Actualizar tabla
		
		dispose();
	    }
	    catch (SQLException e2)
	    {
		JOptionPane.showMessageDialog( null, "Error de guardado: " + "\n" + e2);
	    }
	}
};
    
    ActionListener accionModificar = new ActionListener()
    {
        public void actionPerformed( ActionEvent arg0)
        {
    	String query = "";
    	int i = 0, k = 0;
        
    	while (k < consulta.size())
    	{
    	    if (panel.getComponent(i) instanceof JTextField || panel.getComponent(i) instanceof JComboBox<?>)
    	    {
    		if (k != 0)
    		    query += " , ";
    		
    		if(panel.getComponent(i) instanceof JTextField)
    		    query += ((JLabel) panel.getComponent(i - 1)).getText() + " = " + 
    				insert(((JTextField) panel.getComponent(i)).getText());
    		
    		if(panel.getComponent(i) instanceof JComboBox<?>)
    		    query += ((JLabel) panel.getComponent(i - 1)).getText() + " = " + 
    				insert(((JComboBox<String>) panel.getComponent(i)).getSelectedItem().toString());
    		
    		k++;
    	    }
    	    i++;
    	}
    	
    	try
    	{
        	    Conector.setQuery("UPDATE " + tabla + " SET " + query + " WHERE " + columnPK + " = " + primaryKey);
        	    JOptionPane.showMessageDialog(null, "Registro modificado exitosamente.");
        	    dispose();
    	}
    	catch (SQLException e)
    	{
    	    JOptionPane.showMessageDialog(null, "Error de guardado: " + "\n" + "Otros registros dependen de este.", "Error", 
        		    					JOptionPane.ERROR_MESSAGE);
    	}
        }
    };
    
    ActionListener accionLimpiar   = new ActionListener()
    {
        public void actionPerformed(ActionEvent arg0)
        {
    	for (int i = 0; i < panel.getComponentCount(); i++)
    	{
    	    if (panel.getComponent(i) instanceof JTextField)
    	    {
    		((JTextField) panel.getComponent(i)).setText("");
    	    }
    	}
        }
    };
    
    ActionListener accionEliminar  = new ActionListener()
    {
        public void actionPerformed(ActionEvent arg0)
        {
    	try
    	{
    	    Conector.setQuery("DELETE FROM " + tabla + " WHERE " + columnPK + " = '" + primaryKey + "'");
    	    JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente.");
    	    dispose();
    	}
    	catch (SQLException e4)
    	{
    	    JOptionPane.showMessageDialog( null, "Error:\n" + e4, "Error", JOptionPane.ERROR_MESSAGE);
    	}
        }
    };
}