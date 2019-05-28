package Otros;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import GUIs.GUIRegistro;
import obj.Conector;

public class GUIGenerator extends JPanel {
	private String nomTabla;
	private String stm;
	private TableRowSorter tbfiltro;	
	private JButton btnAdd, btnAtras;
	private JPanel topbar, panelTable;
	private JScrollPane scroll;
	private JTable table;
	private JTextField txtFilter;
	private boolean activateDialog;
	
	public GUIGenerator(String tabla) {
		this(tabla,true);
	}
	
	public GUIGenerator(String tabla,boolean dialog) 
	{
		nomTabla = tabla;
		stm= "SELECT * FROM "+nomTabla;
		activateDialog=dialog;
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		topbar = new JPanel();
		panelTable = new JPanel();
		table = new JTable();
		scroll = new JScrollPane();
		
		panelTable.setBackground(Color.white);
		topbar.setBackground(Color.white);

		topbar.setLayout(new BoxLayout(topbar, BoxLayout.X_AXIS));
		topbar.setBorder(BorderFactory.createEmptyBorder(20, 10, 40, 10));
		
		txtFilter = new JTextField("Filtrar por clave primaria", 70);
		txtFilter.setFont(new Font("Calibri light", Font.PLAIN, 15));
		txtFilter.setMaximumSize( new Dimension(70, 20) );
		
		btnAdd = crearBoton("agregar.png", "agregarHover.png", "agregarPressed.png", "Agregar un registro a la tabla");
		btnAtras = crearBoton("atras.png", "atrasHover.png", "atrasPressed.png", "Volver al menu principal");
		
			
		topbar.add(txtFilter);
		topbar.add(Box.createRigidArea(new Dimension(40, 10)));
		
		if(activateDialog) topbar.add(btnAdd);
	
		topbar.add(Box.createRigidArea(new Dimension(20, 10)));
		topbar.add(btnAtras);
		topbar.add(Box.createHorizontalGlue());

		add(topbar);
		drawTable();

		txtFilter.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				txtFilter.setText("Filtrar por clave primaria");
			}

			public void focusGained(FocusEvent e) {
				txtFilter.setText("");
			}
		});

		txtFilter.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				tbfiltro = new TableRowSorter(table.getModel());
				table.setRowSorter(tbfiltro);
				tbfiltro.setRowFilter(RowFilter.regexFilter("(?i)" + txtFilter.getText(), 0));
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GUIRegistro(nomTabla, null);
				remove(panelTable);
				drawTable();
			}

		});

		btnAtras.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				//Cerrar
			}

		});
	}

	// methods
	private JButton crearBoton(String ruta0, String ruta1, String ruta2, String string) {
		JButton boton = new JButton(new ImageIcon(getClass().getResource("/img/" + ruta0)));
		boton.setContentAreaFilled(false);
		boton.setBorder(null);

		if (string != null) {
			boton.setToolTipText(string);
			boton.setPressedIcon(new ImageIcon(getClass().getResource("/img/" + ruta2)));
		}

		boton.setRolloverIcon(new ImageIcon(getClass().getResource("/img/" + ruta1)));

		return boton;
	}

	public void drawTable() {
		
		DefaultTableModel model= new DefaultTableModel() {
			public boolean isCellEditable(int row,int column){return false;}
		}; 

		ArrayList<ArrayList<String>> data = null;
		ArrayList<String> column = null;
		table = new JTable(model);

		try 
		{
			data = Conector.getDatos(stm);
			column = Conector.getHeaders(stm);
			panelTable.remove(scroll);
		} 
		catch (SQLException e) 
		{
			model.addColumn("TABLA NO ENCONTRADA");
		}



		for (int i = 0; i < data.size(); i++) 
		{
			model.addColumn(column.get(i),data.get(i).toArray());	
		}


		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		
		if(activateDialog) table.addMouseListener(modificarRegistro);
		
		table.setFont(new Font("Calibri light", Font.PLAIN, 14));

		scroll = new JScrollPane(table);
		panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.Y_AXIS));
		panelTable.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		panelTable.add(scroll);

		setPreferredSize(getSize());
		add(panelTable);
		validate();

	}
	
	MouseAdapter modificarRegistro = new MouseAdapter() 
	{
		public void mouseClicked(MouseEvent e) 
		{
			if (e.getClickCount() == 2) 
			{
				new GUIRegistro(nomTabla,table.getValueAt(table.getSelectedRow(),0).toString());
				remove(panelTable);
				drawTable();
			}
		};
	};
}
