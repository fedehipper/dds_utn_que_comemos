package macoWins;

import macoWins.MacoWins;

public class Prenda {
	private int precioBase;
	private String tipoDeImportacion;

	
	/*------------------------------------------------------------------------------------*/
	
	public double precioFinal(int valorFijo) {
		return ((precioBase + valorFijo)* (this.tasaDeImportacion()));
	}
	
	public double tasaDeImportacion() {
		if (this.esImportada())
			return 1.3;
		else 
			return 1;
	}
	
	public boolean esImportada() {
		return (tipoDeImportacion == "importada");
		
	}
	
	public Prenda(int valorPrecioBase, String valorTipoDeImportacion) {
		precioBase = valorPrecioBase;
		tipoDeImportacion = valorTipoDeImportacion;
	}
	
}
