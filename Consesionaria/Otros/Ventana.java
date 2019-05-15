package Otros;
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
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		else
		{
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		crear();
		this.setVisible(true);
	}
	public abstract void crear();
}
