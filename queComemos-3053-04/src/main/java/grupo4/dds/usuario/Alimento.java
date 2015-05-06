package grupo4.dds.usuario;

public enum Alimento {

	CHORI("chori"), FRUTAS("frutas"), MARISCOS("mariscos"), MELON("melon"), MONDONGO("mondongo"), 
	CARNE("carne"), PESCADO("pescado"), CHIVITO("chivito"), POLLO("pollo");

	private String valor;

	private Alimento(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

}
