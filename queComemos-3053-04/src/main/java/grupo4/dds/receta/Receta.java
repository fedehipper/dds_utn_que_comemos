package grupo4.dds.receta;

import grupo4.dds.excepciones.NoSePuedeModificarLaReceta;
import grupo4.dds.persistencia.Persistible;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.usuario.Usuario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import queComemos.entrega3.dominio.Dificultad;

@Entity
@Table(name = "Recetas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_receta")
@DiscriminatorValue("privada")
public class Receta implements Persistible, WithGlobalEntityManager {

    @Id
    @GeneratedValue
    @Column(name = "id_receta")
    private long id;
    @ManyToOne
    protected Usuario creador;

    /* Encabezado de la receta */
    @Embedded
    protected EncabezadoDeReceta encabezado = new EncabezadoDeReceta();

    /* Detalle de la receta */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Recetas_Ingredientes")
    protected List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Recetas_Condimentos")
    protected List<Ingrediente> condimentos = new ArrayList<Ingrediente>();
    @ManyToMany
    @JoinTable(name = "Recetas_Subrecetas")
    protected List<Receta> subrecetas = new ArrayList<Receta>();
    protected String preparacion;
    protected int consultasHombres;
    protected int consultasMujeres;

    /* Servicios */
    public boolean esValida() {
        int totalCalorias = getTotalCalorias();
        return !ingredientes.isEmpty() && 10 <= totalCalorias && totalCalorias <= 5000;
    }

    public boolean tieneIngrediente(String nombreIngrediente) {
        return tiene(getIngredientes(), nombreIngrediente);
    }

    public boolean tieneCondimento(String nombreCondimento) {
        return tiene(getCondimentos(), nombreCondimento);
    }

    public float cantidadCondimento(String nombreCondimento) {
        int index = getCondimentos().indexOf(Ingrediente.nuevaComida(nombreCondimento));
        return index < 0 ? 0 : getCondimentos().get(index).getCantidad();
    }

    public boolean puedeSerVistaPor(Usuario usuario) {
        if (creador == null) {
            return false;
        }
        return creador.equals(usuario);
    }

    public boolean puedeSerAgregadaPor(Usuario usuario) {
        return puedeSerVistaPor(usuario);
    }

    public void modificarReceta(Usuario usuario, EncabezadoDeReceta encabezado, List<Ingrediente> ingredientes,
            List<Ingrediente> condimentos, String preparacion, List<Receta> subrecetas) {

        if (!usuario.puedeModificar(this)) {
            throw new NoSePuedeModificarLaReceta();
        }

        this.encabezado = encabezado;
        this.ingredientes = ingredientes != null ? ingredientes
                : new ArrayList<>();
        this.condimentos = condimentos != null ? condimentos
                : new ArrayList<>();
        this.subrecetas = subrecetas != null ? subrecetas
                : new ArrayList<Receta>();
        this.preparacion = preparacion;

    }

    public void modificarReceta(Usuario usuario, EncabezadoDeReceta encabezado, Ingrediente ingrediente,
            Ingrediente condimento) {

        if (!usuario.puedeModificar(this)) {
            throw new NoSePuedeModificarLaReceta();
        }

        this.encabezado = encabezado;

        if (ingrediente != null) {
            this.ingredientes.add(ingrediente);
        }

        if (condimento != null) {
            this.condimentos.add(condimento);
        }

    }

    public String getPreparacion() {
        if (preparacion == null & subrecetas.isEmpty()) {
            return "";
        }

        String preparacionDeSubrecetas = subrecetas == null ? null : subrecetas
                .stream().map(Receta::getPreparacion)
                .collect(Collectors.joining(""));

        if (preparacionDeSubrecetas == null) {
            return preparacion;
        }

        return String.join("", preparacion, preparacionDeSubrecetas);
    }

    public List<Ingrediente> getIngredientes() {
        return getConSubrecetas((Receta receta) -> {
            return receta.ingredientes;
        }, ingredientes);
    }

    public List<Ingrediente> getCondimentos() {
        return getConSubrecetas((Receta receta) -> {
            return receta.condimentos;
        }, condimentos);
    }

    public boolean contieneAlguna(List<Ingrediente> comidas) {
        return !noContieneNinguna(comidas);
    }

    public boolean noContieneNinguna(List<Ingrediente> comidas) {
        return Collections.disjoint(getIngredientes(), comidas);
    }

    public boolean tieneCarne() {
        return getIngredientes().stream().anyMatch(i -> i.esCarne());
    }

    public boolean esDificil() {
        return Dificultad.DIFICIL.equals(encabezado.dificultad);
    }

    @Override
    public String toString() {
        return getNombreDelPlato();
    }

    /* Servicios privados */
    private boolean tiene(List<Ingrediente> lista, String nombre) {
        return lista.contains(Ingrediente.nuevaComida(nombre));
    }

    private List<Ingrediente> getConSubrecetas(Function<Receta, List<Ingrediente>> f, List<Ingrediente> seed) {

        List<Ingrediente> acum = new ArrayList<Ingrediente>(seed);

        for (Receta elem : subrecetas) {
            acum.addAll(f.apply(elem));
        }

        return acum;
    }

    public void actualizarReceta(String nombreReceta, String dificultad, String temporada, String calorias,
            String preparacion, String favorita, String condimento, String ingrediente, String dosis,
            String ingredienteParaEliminar, String condimentoParaEliminar) {

        EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
        Ingrediente nuevoCondimento = null;
        Ingrediente nuevoIngrediente = null;

        if (!(Objects.isNull(nombreReceta) || nombreReceta.isEmpty())) {
            encabezado.setNombreDelPlato(nombreReceta);
        }

        if (!((Objects.isNull(calorias) || calorias.isEmpty()))) {
            encabezado.setTotalCalorias(Integer.parseInt(calorias));
        }

        if (!(Objects.isNull(dificultad) || dificultad.isEmpty())) {
            encabezado.setDificultad(Dificultad.valueOf(dificultad));
        }

        if (!(Objects.isNull(temporada) || temporada.isEmpty())) {
            encabezado.setTemporada(Temporada.valueOf(temporada));
        }

        if (!(Objects.isNull(preparacion) || preparacion.isEmpty())) {
            this.setPreparacion(preparacion);
        }

        if (!Objects.isNull(favorita)) {
            this.getCreador().marcarFavorita(this);
        } else {
            if (this.getCreador().getHistorial().contains(this)) {
                this.getCreador().getHistorial().remove(this);
            }
        }

        if (!(Objects.isNull(condimento) || condimento.isEmpty())) {
            nuevoCondimento = Ingrediente.nuevoCondimento(condimento, 0);
        }

        if (!((Objects.isNull(ingrediente) || ingrediente.isEmpty())) && !((Objects.isNull(dosis) || dosis.isEmpty()))) {
            nuevoIngrediente = Ingrediente.nuevoIngrediente(ingrediente, Float.parseFloat(dosis));
        }

        if (!(Objects.isNull(ingredienteParaEliminar) || ingredienteParaEliminar.isEmpty())) {
            //this.getIngredientes().remove(paraEliminar);//como carajo puede eliminar un ingrediente
            eliminarElemento(ingredientes, ingredienteParaEliminar);
        }

        if (!(Objects.isNull(condimentoParaEliminar) || condimentoParaEliminar.isEmpty())) {
            //this.getIngredientes().remove(paraEliminar);//como carajo puede eliminar un ingrediente
            eliminarElemento(condimentos, condimentoParaEliminar);
        }

        this.modificarReceta(this.getCreador(), encabezado, nuevoIngrediente, nuevoCondimento);
    }

    public void eliminarElemento(List<Ingrediente> lista, String nombreIngrediente) {
        for (int x = 0; x < lista.size(); x++) {
            if (lista.get(x).getNombre().equals(nombreIngrediente)) {
                lista.remove(lista.get(x));
            }
        }
    }

    public Receta crearReceta(String nombreReceta, String dificultad, String temporada, String calorias, String preparacion,
            String favorita, Usuario usuario, String condimento, String nombreIngrediente, String dosis,
            List<Ingrediente> ingredientes, List<Ingrediente> condimentos, String ingredienteParaEliminar, String condimentoParaEliminar) {

        BuilderReceta builder = new BuilderReceta();

        if (!(Objects.isNull(nombreReceta) || nombreReceta.isEmpty())) {
            builder.nombre(nombreReceta);
        }

        if (!((Objects.isNull(calorias) || calorias.isEmpty()))) {
            builder.calorias(Integer.parseInt(calorias));
        }

        if (!(Objects.isNull(dificultad) || dificultad.isEmpty())) {
            builder.dificultad(Dificultad.valueOf(dificultad));
        }

        if (!(Objects.isNull(temporada) || temporada.isEmpty())) {
            builder.temporada(Temporada.valueOf(temporada));
        }

        if (!(Objects.isNull(preparacion) || preparacion.isEmpty())) {
            builder.preparacion(preparacion);
        }

        if (!(Objects.isNull(condimento) || condimento.isEmpty())) {
            builder.condimento(Ingrediente.nuevoCondimento(condimento, 0));
        }

        if (!Objects.isNull(favorita)) {
            usuario.marcarFavorita(this);
        } else {
            if (usuario.getHistorial().contains(this)) {
                usuario.getHistorial().remove(this);
            }
        }

        if (!(Objects.isNull(nombreIngrediente) || nombreIngrediente.isEmpty()) && !(Objects.isNull(dosis) || dosis.isEmpty())) {
            builder.ingrediente(Ingrediente.nuevoIngrediente(nombreIngrediente, Float.parseFloat(dosis)));
        }

        Receta nuevaReceta = builder.ingredientes(ingredientes).condimentos(condimentos).creador(usuario).build();

        if (!(Objects.isNull(ingredienteParaEliminar) || ingredienteParaEliminar.isEmpty())) {
            //this.getIngredientes().remove(paraEliminar);//como carajo puede eliminar un ingrediente
            eliminarElemento(nuevaReceta.getIngredientes(), ingredienteParaEliminar);
        }

        if (!(Objects.isNull(condimentoParaEliminar) || condimentoParaEliminar.isEmpty())) {
            //this.getIngredientes().remove(paraEliminar);//como carajo puede eliminar un ingrediente
            eliminarElemento(nuevaReceta.getCondimentos(), condimentoParaEliminar);
        }

        return nuevaReceta;
    }

    /* Accessors and Mutators */
    public int getTotalCalorias() {
        return encabezado.getTotalCalorias();
    }

    public String getNombreDelPlato() {
        return encabezado.getNombreDelPlato();
    }

    public Temporada getTemporada() {
        return encabezado.getTemporada();
    }

    public EncabezadoDeReceta getEncabezado() {
        return encabezado;
    }

    public Usuario getCreador() {
        return creador;
    }

    public List<Receta> getSubrecetas() {
        return subrecetas;
    }

    public Dificultad getDificultad() {
        return encabezado.getDificultad();
    }

    public String getOrigen() {
        return "Privada";
    }

    public long getId() {
        return id;
    }

    public int consultasHombres() {
        return consultasHombres;
    }

    public int consultasMujeres() {
        return consultasMujeres;
    }

    public int totalConsultas() {
        return consultasMujeres + consultasHombres;
    }

    public void setTotalCalorias(int totalCalorias) {
        encabezado.setTotalCalorias(totalCalorias);
    }

    public void agregarIngrediente(Ingrediente unIngrediente) {
        ingredientes.add(unIngrediente);
    }

    public void agregarIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes.addAll(ingredientes);
    }

    public void agregarCondimento(Ingrediente unCondimento) {
        condimentos.add(unCondimento);
    }

    public void agregarCondimentos(List<Ingrediente> condimentos) {
        this.condimentos.addAll(condimentos);
    }

    public void agregarSubreceta(Receta subreceta) {
        subrecetas.add(subreceta);
    }

    public void agregarSubrecetas(List<Receta> subrecetas) {
        this.subrecetas.addAll(subrecetas);
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public void setEncabezado(EncabezadoDeReceta encabezado) {
        this.encabezado = encabezado;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void consultoHombre() {
        consultasHombres++;
    }

    public void consultoMujer() {
        consultasMujeres++;
    }

    public void resetContadorConsultas() {
        consultasMujeres = consultasHombres = 0;
    }
}
