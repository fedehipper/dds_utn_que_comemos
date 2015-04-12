package grupo4.dds;

public class Pantalon extends Prenda {
	
	private int cmTela;
	
	public double precioBase(){
		return (250+cmTela);
	}

	public Pantalon(TipoDeImportacion tipoDeImportacion, Marca marca, int tela, MacoWins negocio) {
        super(tipoDeImportacion, marca, negocio);
		cmTela = tela;
	}
}
