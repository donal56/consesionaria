package GUIs;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Otros.Ventana;

public class GUIPrincipal extends Ventana
{
	ImageIcon[] iconos    ;
	JButton  [] botones   ;
	JPanel      pnlCentral,
	            pnlTitulo ;
	JLabel      lblTitulo ;
	
	Container contenedorGral;
	
	static int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width ;
	static int alto  = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public GUIPrincipal()
	{
		super("Ventana Principal",ancho,alto,true);
	}
	
	public void crear()
	{
		contenedorGral=new Container ()                  ;
		contenedorGral.setLayout     (new BorderLayout());
		this          .setContentPane(contenedorGral)    ;
		contenedorGral.setBackground (Color.white)       ;
		
		
		iconos    =new ImageIcon[7];
		botones   =new JButton  [7];
		pnlCentral=new JPanel();
		pnlTitulo =new JPanel();
		lblTitulo =new JLabel();
		
		lblTitulo.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/img/imgTitulo.png")));
		
		iconos[0]=new ImageIcon(GUIPrincipal.class.getResource("/img/icnAutomoviles.png"    ));
		iconos[1]=new ImageIcon(GUIPrincipal.class.getResource("/img/icnCaracteristicas.png"));
		iconos[2]=new ImageIcon(GUIPrincipal.class.getResource("/img/icnMarcas.png"         ));
		iconos[3]=new ImageIcon(GUIPrincipal.class.getResource("/img/icnModelo.png"         ));
		iconos[4]=new ImageIcon(GUIPrincipal.class.getResource("/img/icnServicios.png"      ));
		iconos[5]=new ImageIcon(GUIPrincipal.class.getResource("/img/icnVendedores.png"     ));
		iconos[6]=new ImageIcon(GUIPrincipal.class.getResource("/img/icnVentas.png"         ));
		
		for (int i = 0; i < 7; i++) 
		{
			botones[i]=new JButton();
			botones[i].setIcon(iconos[i]);
			botones[i].setBorder           (null );
			botones[i].setOpaque           (false);
			botones[i].setContentAreaFilled(false);
			botones[i].setSelectedIcon     (null );
			pnlCentral.add(botones[i]);
		}
		
		pnlTitulo.add(lblTitulo);
		pnlTitulo.setBackground(Color.WHITE);
		pnlTitulo.setBorder(null);
		pnlCentral.setBackground(Color.WHITE);
		contenedorGral.setBackground(Color.WHITE);
		
		contenedorGral.add(pnlTitulo ,BorderLayout.NORTH );
		contenedorGral.add(pnlCentral,BorderLayout.CENTER);
	}
}
