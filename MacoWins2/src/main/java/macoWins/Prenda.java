package macoWins;

public class Prenda {
	
	private int precioBase;
	private float tasaDeImportacion;
	
	public Prenda(int precioBase, String origen) {
		this.precioBase = precioBase;
		
		if (esImportada(origen))
			tasaDeImportacion = 1.3f;
		else 
			tasaDeImportacion = 1f;
	}

	public double precioFinal() {
		return (precioBase + MacoWins.getValorFijo()) * tasaDeImportacion;
	}
	
	public boolean esImportada(String origen) {
		return origen.equals("importada");	
	}
	
}
