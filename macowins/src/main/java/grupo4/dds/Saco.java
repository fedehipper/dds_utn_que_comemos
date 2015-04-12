package grupo4.dds;

public class Saco extends Prenda {
	
	private int botones;
	
	public double precioBase(){
		return (300 + 10*botones);
	}

	public Saco(TipoDeImportacion tipoDeImportacion, Marca marca, int cantBotones, MacoWins negocio) {
		super(tipoDeImportacion, marca, negocio);
		botones = cantBotones;
	}
}
