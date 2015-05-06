package grupo4.dds.usuario;

import java.util.Collection;
import java.util.stream.Collectors;

public enum Alimento {

	CHORI("chori"), FRUTAS("frutas"), MARISCOS("mariscos"), MELON("melon"), MONDONGO("mondongo"), 
	CARNE("carne"), PESCADO("pescado"), CHIVITO("chivito"), POLLO("pollo");

	private String ingrediente;

	private Alimento(String valor) {
		this.ingrediente = valor;
	}

	public String getValor() {
		return ingrediente;
	}
	
	static public Collection<String> mapIngrediente(Collection<Alimento> c) {
		return c.stream().map(Alimento::getValor).collect(Collectors.toList());
	}

}
