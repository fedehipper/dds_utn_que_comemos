package tpMacoWins;

import tpMacoWins.MacoWins;
import static tpMacoWins.TipoDeImportacion.*;

public abstract class Prenda {
	private double tasaDeImportacion;
	private Marca marca;
	private MacoWins negocio;
	
	
	public Prenda(TipoDeImportacion tipoDeImportacion, Marca marca, MacoWins negocio) {
		
		this.marca = marca;
		this.negocio = negocio;
		tasaDeImportacion= tipoDeImportacion.equals(IMPORTADA) ? 1.3 : 1 ;
		
	}

	public double precioFinal() {
		return marca.precioFinal(this);
	}
	
	public double precioOriginal() {
		return (precioBase() + negocio.getValorFijoDelNegocio())* tasaDeImportacion;
	}


	protected abstract double precioBase();

	public Marca getMarca() {
		return marca;
	}

}