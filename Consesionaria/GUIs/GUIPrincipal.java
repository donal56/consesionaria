package GUIs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Otros.GUIGenerator;
import obj.Ventana;

public class GUIPrincipal extends Ventana
{
    ImageIcon[]	iconos;
    JButton  []	botones;
    ImageIcon[] pressed;
    JPanel	    pnlCentral, pnlTitulo;
    JLabel	    lblTitulo,
                lblGif    ;
    Container	contenedorGral;
    
    static int	ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    static int	alto  = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

    public GUIPrincipal()
    {
	super("Ventana Principal", ancho, alto, true);
    }

    public void crear()
    {
	contenedorGral = new Container();
	contenedorGral.setLayout(new BorderLayout());
	this.setContentPane(contenedorGral);
	contenedorGral.setBackground(Color.white);
	
	iconos  = new ImageIcon[7];
	botones = new JButton  [7];
	pressed =new ImageIcon [7];
	
	pnlCentral = new JPanel();
	pnlTitulo  = new JPanel();
	lblTitulo  = new JLabel();
	lblGif     = new JLabel();
	
	lblGif   .setIcon(new ImageIcon(GUIPrincipal.class.getResource("/img/llantaGif.gif")));
	
	lblTitulo.setIcon(new ImageIcon(
		GUIPrincipal.class.getResource("/img/imgTitulo.png")));
	iconos[0] = new ImageIcon(
		GUIPrincipal.class.getResource("/img/icnAutomoviles.png"));
	iconos[1] = new ImageIcon(
		GUIPrincipal.class.getResource("/img/icnCaracteristicas.png"));
	iconos[2] = new ImageIcon(
		GUIPrincipal.class.getResource("/img/icnMarcas.png"));
	iconos[3] = new ImageIcon(
		GUIPrincipal.class.getResource("/img/icnModelo.png"));
	iconos[4] = new ImageIcon(
		GUIPrincipal.class.getResource("/img/icnServicios.png"));
	iconos[5] = new ImageIcon(
		GUIPrincipal.class.getResource("/img/icnVendedores.png"));
	iconos[6] = new ImageIcon(
		GUIPrincipal.class.getResource("/img/icnVentas.png"));
	
	pressed[0]=new ImageIcon(GUIPrincipal.class.getResource("/img/pressAutomoviles.png"    ));
	pressed[1]=new ImageIcon(GUIPrincipal.class.getResource("/img/pressCaracteristicas.png"));
	pressed[2]=new ImageIcon(GUIPrincipal.class.getResource("/img/pressMarcas.png"         ));
	pressed[3]=new ImageIcon(GUIPrincipal.class.getResource("/img/pressModelo.png"         ));
	pressed[4]=new ImageIcon(GUIPrincipal.class.getResource("/img/pressServicios.png"      ));
	pressed[5]=new ImageIcon(GUIPrincipal.class.getResource("/img/pressVendedores.png"     ));
	pressed[6]=new ImageIcon(GUIPrincipal.class.getResource("/img/pressVentas.png"         ));
	
	
	
	rellenarPanel();
	
	pnlTitulo.add(lblTitulo);
	pnlTitulo.setBackground(Color.WHITE);
	pnlTitulo.setBorder(null);
	pnlCentral.setBackground(Color.WHITE);
	contenedorGral.setBackground(Color.WHITE);
	pnlCentral.add(lblGif);
	
	
	
	pnlTitulo.addMouseListener(new MouseListener()
	{
	    public void mouseReleased(MouseEvent e)	{	}
	    
	    public void mousePressed (MouseEvent e)	{	}
	    
        public void mouseExited  (MouseEvent e)	{	}
	    
	    public void mouseEntered (MouseEvent e)	{	}
	    
	    public void mouseClicked (MouseEvent e)
	    {		
			pnlCentral.removeAll();
			pnlCentral.revalidate();
			rellenarPanel();		
			pnlCentral.repaint();
	    }
	});
    }
    private ActionListener generarTabla(String tabla){
		return new ActionListener(){
			public void actionPerformed(ActionEvent arg0) 
			{
				
				pnlCentral.setLayout(new GridLayout(1,1));
				pnlCentral.removeAll();
				pnlCentral.repaint();
				pnlCentral.add(	new GUIGenerator(tabla) );
				pnlCentral.revalidate();
			} 
		};
	}
    private void rellenarPanel()
    {
	String nomTablas[] = {"automoviles","caracteristicas","marcas","modelo","servicios_oficiales","vendedores","ventas"};
	for (int i = 0; i < 7; i++)
	{
	    botones[i] = new JButton();
	    botones[i].setIcon(iconos[i]);
	    botones[i].setBorder(null);
	    botones[i].setOpaque(false);
	    botones[i].setContentAreaFilled(false);
	    botones[i].setSelectedIcon(null);
	    botones[i].addActionListener(generarTabla(nomTablas[i]));
	    botones[i].setPressedIcon(pressed[i]);
	    pnlCentral.add(botones[i]);
	    contenedorGral.add(pnlTitulo ,BorderLayout.NORTH);
	    contenedorGral.add(pnlCentral, BorderLayout.CENTER);
	}
    }
 }
