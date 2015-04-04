package grupo4.dds;

public abstract class Prenda {
	
	protected static float valorFijoNegocio;
	protected float precioBase;
	protected float tasaImportacion;
	
	public float precioFinal() {
		
		return (valorFijoNegocio + precioBase) * tasaImportacion;
		
	}
	
	protected void fijatTasaImportacion(boolean esImportada) {
		
		tasaImportacion = esImportada ? 1.3f : 1.0f;
		
	}

	public Prenda(boolean esImportada, float precioBase) {
		
		this.precioBase = precioBase;
		fijatTasaImportacion(esImportada);
		
	}
	
	public static void fijarValorDeNegocio(float valorFijoNegocio) {
		
		Prenda.valorFijoNegocio = valorFijoNegocio;
		
	}

}
