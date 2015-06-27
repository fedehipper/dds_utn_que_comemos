package grupo4.dds.receta.builder;

import grupo4.dds.receta.RecetaPublica;

public class BuilderRecetaPublica extends BuilderReceta{

	private RecetaPublica receta;
	private BuilderEncabezado encabezado;
	
	
	public BuilderRecetaPublica(){
		this.receta = new RecetaPublica();
	}

	public RecetaPublica build()
	{	
		super.setEncabezado(encabezado.build());
		
		if (!receta.esValida()){
			throw new RuntimeException();
		}
		if (receta.getCreador() != null){
			throw new RuntimeException();
		}
		
		return receta;
	}

}
