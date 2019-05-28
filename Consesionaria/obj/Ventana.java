package obj;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public abstract class Ventana extends JFrame
{
	public Ventana()
	{
		this("Nueva ventana");
	}
	public Ventana(String pT�tulo)
	{
		this(pT�tulo,600,600);
	}
	public Ventana(String pT�tulo,Integer pAncho,Integer pLargo)
	{
		this(pT�tulo,pAncho,pLargo,false);
	}
	public Ventana(String pT�tulo,Integer pAncho,Integer pLargo,Boolean pPrincipal)
	{
		this(pT�tulo,pAncho,pLargo,pPrincipal,-1,-1);
	}
	public Ventana(String pT�tulo,Integer pAncho,Integer pLargo,Boolean pPrincipal,Integer pX,Integer pY)
	{
		this.setTitle(pT�tulo);
		this.setSize(pAncho,pLargo);
		if(pX==-1 && pY==-1)
		{
			this.setLocationRelativeTo(null);
		}
		else
		{
			this.setLocation(pX,pY);
		}
		if(pPrincipal)
		{
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.addWindowListener(exitListener);
		}
		else
		{
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		crear();
		this.setVisible(true);
		this.setExtendedState(MAXIMIZED_BOTH);
	}
	
	public abstract void crear();
	

WindowListener exitListener = new WindowAdapter() {

    @Override
    public void windowClosing(WindowEvent e) {
    	Conector.cerrarConexion();
        System.exit(0);
       
    }
};


}
