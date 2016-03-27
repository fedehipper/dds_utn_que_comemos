package grupo4.dds;

class Pantalon extends Prenda {
	
	private int cmTela;
	
	public double precioBase(){
		return 250 + cmTela;
	}

	Pantalon(TipoDeImportacion tipoDeImportacion, Marca marca, int tela, MacoWins negocio) {
        super(tipoDeImportacion, marca, negocio);
		cmTela = tela;
	}
}
