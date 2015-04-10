package macoWins;

import macoWins.MacoWins;

public class Prenda {
	private int precioBase;
	private String tipoDeImportacion;

	
	
	public int valorFijoDelNegocio(MacoWins maco) {
		return maco.getValorFijoDelNegocio();
	}
	
	public double precioFinal(MacoWins negocio) {
		return ((precioBase + negocio.getValorFijoDelNegocio())* (this.tasaDeImportacion()));
	}
	
	public double tasaDeImportacion() {
		if (this.esImportada())
			return 1.3;
		else 
			return 1;
	}
	
	public boolean esImportada() {
		return (tipoDeImportacion.equals("importada") );
		
	}
	
	public Prenda(int valorPrecioBase, String valorTipoDeImportacion) {
		precioBase = valorPrecioBase;
		tipoDeImportacion = valorTipoDeImportacion;
	}
	
}
