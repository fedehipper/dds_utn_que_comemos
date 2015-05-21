package grupo4.dds.receta;

import static org.junit.Assert.assertEquals;
import grupo4.dds.excepciones.NoSePuedeModificarLaReceta;
import grupo4.dds.usuario.Ingrediente;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Receta {

	protected Usuario creador;

	/* Encabezado de la receta */
	protected EncabezadoDeReceta encabezado = new EncabezadoDeReceta();

	/* Detalle de la receta */
	protected List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
	protected List<Ingrediente> condimentos = new ArrayList<Ingrediente>();
	protected List<Receta> subrecetas = new ArrayList<Receta>();
	protected String preparacion;

	/* Constructores */
	public Receta(){}

	public Receta(Usuario creador) {
		this.creador = creador;
	}// Creado para testear por ahora
	
	//TODO: lanzar excepción cuando no se especifica el creador
	public Receta(Usuario creador, EncabezadoDeReceta encabezado, String preparacion) {
		this.creador = creador;
		this.encabezado = encabezado != null ? encabezado : new EncabezadoDeReceta();
		this.preparacion = preparacion;
	}
	
	protected Receta(Usuario creador, EncabezadoDeReceta encabezado, List<Ingrediente> ingredientes,
			List<Ingrediente> condimentos, List<Receta> subrecetas, String preparacion) {
		
		this(creador, encabezado, preparacion);
		this.ingredientes = ingredientes != null ? ingredientes : new ArrayList<>();
		this.condimentos = condimentos != null ? condimentos : new ArrayList<>();
		this.subrecetas = subrecetas != null ? subrecetas : new ArrayList<Receta>();
	}

	/* Servicios */
	public boolean esValida() {
		int totalCalorias = getTotalCalorias();
		return !ingredientes.isEmpty() && 10 <= totalCalorias && totalCalorias <= 5000;
	}

	public boolean tieneIngrediente(String unIngrediente) {
		return this.ingredientes.stream().anyMatch(i -> i.getNombre() == unIngrediente);
	}

	public boolean tieneCondimento(String condimento) {
		return this.condimentos.stream().anyMatch(c -> c.getNombre() == condimento);
	}

	public Float cantidadCondimento(String nombreCondimento) {
		 return this.condimentos.stream().filter(c -> c.getNombre() == nombreCondimento).findFirst().get().getCantidad();  					
	}
	
	public boolean puedeSerVistaPor(Usuario usuario) {
		return creador.equals(usuario);
	}

	public boolean puedeSerModificadaPor(Usuario usuario) {
		return puedeSerVistaPor(usuario);
	}
	
	public boolean puedeSerAgregadaPor(Usuario usuario) {
		return puedeSerVistaPor(usuario);
	}

	public void modificarReceta(Usuario usuario, EncabezadoDeReceta encabezado,
			List<Ingrediente> ingredientes,
			List<Ingrediente> condimentos, String preparacion,
			List<Receta> subrecetas) {

		if (!puedeSerModificadaPor(usuario))
			throw new NoSePuedeModificarLaReceta();

		this.encabezado = encabezado;
		this.ingredientes = ingredientes != null ? ingredientes : new ArrayList<>();
		this.condimentos = condimentos != null ? condimentos : new ArrayList<>();
		this.subrecetas = subrecetas != null ? subrecetas : new ArrayList<Receta>();
		this.preparacion = preparacion;
		
	}

	public String getPreparacion() {
		if(preparacion == null && subrecetas.isEmpty())
			return "";
		
		String preparacionDeSubrecetas = subrecetas == null ? null : subrecetas.stream()
				.map(Receta::getPreparacion).collect(Collectors.joining(""));
		
		if(preparacionDeSubrecetas == null)
			return preparacion;
		
		return String.join("", preparacion, preparacionDeSubrecetas);
	}
		
	public List<Ingrediente> getIngredientes() {
		return this.ingredientes;
	}
	
	public List<Ingrediente> getCondimentos() {
		return this.condimentos;
	}
	
	// testear, necesito comparar por ingredientes totales, osea de la receta y sus subrecetas
	// en lugar de meter el this.ingredientes, hay que hacer un metodo que devuelva todos
	// el mismo que piden los dos test que pinchan
	public boolean compartenPalabrasClave(List<Ingrediente> palabrasClaveGrupo) {
		return this.ingredientes.stream().anyMatch(i-> palabrasClaveGrupo.contains(i));
	}
	
		
	/* Accessors and Mutators */
	public int getTotalCalorias() {
		return encabezado.getTotalCalorias();
	}

	public void setTotalCalorias(int totalCalorias) {
		encabezado.setTotalCalorias(totalCalorias);
	}

	public void agregarIngrediente(Ingrediente unIngrediente) {
		ingredientes.add(unIngrediente);
	}

	public void agregarCondimento(Ingrediente unCondimento) {
		condimentos.add(unCondimento);
	}
	
	public void agregarSubreceta(Receta subreceta) {
		subrecetas.add(subreceta);
	}

	public void agregarIngredientes(List<Ingrediente> ingredientes) {
		ingredientes.addAll(ingredientes);
	}

	public void agregarCondimentos(List<Ingrediente> condimentos) {
		condimentos.addAll(condimentos);
	}
	
	public void agregarSubrecetas(List<Receta> subrecetas) {
		subrecetas.addAll(subrecetas);
	}
	
	public String getNombreDelPlato() {
		return encabezado.getNombreDelPlato();
	}

	public Temporada getTemporada() {
		return encabezado.getTemporada();
	}

	public String getDificultad() {
		return encabezado.getDificultad();
	}
	
	public List<Receta> getSubRecetas() {
		return this.subrecetas;
	}
	
	/* retorna el total de ingredientes de la receta mas los de las subrecetas */
	public List<Ingrediente> getIngredientesRecetaYSubReceta() {		
				
		List<Ingrediente> totalIngredientes = new ArrayList<>();
		ListIterator<Receta> r =  subrecetas.listIterator(); 
		
		while(r.hasNext())
			totalIngredientes.addAll(r.next().getIngredientes());
	
		totalIngredientes.addAll(ingredientes);
		return totalIngredientes;  
	}
	
	/* retorna el total de condimentos de la receta mas los de las subrecetas */
	public List<Ingrediente> getCondimentosRecetaYSubReceta() {		
		
		List<Ingrediente> totalCondimentos = new ArrayList<>();
		ListIterator<Receta> r =  subrecetas.listIterator(); 
		
		while(r.hasNext())
			totalCondimentos.addAll(r.next().getCondimentos());
	
		totalCondimentos.addAll(condimentos);
		return totalCondimentos;  
	}
	
		
}


