package grupo4.dds.usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import grupo4.dds.excepciones.NoSePuedeAgregarLaReceta;
import grupo4.dds.excepciones.NoSePuedeGuardarLaRecetaEnElHistorial;
import grupo4.dds.persistencia.Persistible;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.repositorios.RepositorioDeRecetas;
import grupo4.dds.usuario.condicion.Condicion;
import grupo4.dds.usuario.condicion.Vegano;

@Entity
@Table(name = "Usuarios")
public class Usuario implements Persistible, WithGlobalEntityManager {

    @Id
    @GeneratedValue
    @Column(name = "id_usuario")
    private long id;

    /* Datos basicos */
    protected String nombre;
    protected Sexo sexo;
    protected LocalDate fechaNacimiento;

    /* Datos de la complexion */
    protected float peso;
    protected float altura;

    /* Otros datos */
    @Enumerated
    protected Rutina rutina;
    protected String mail;
    protected boolean marcaFavorita;

    @OneToMany(mappedBy = "creador")
    protected List<Receta> recetas = new ArrayList<>();
    @ManyToMany(mappedBy = "usuarios")
    protected Set<GrupoUsuarios> grupos = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Usuarios_Comidas_Preferidas")
    protected List<Ingrediente> preferenciasAlimenticias = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Usuarios_Comidas_Disgustadas")
    protected List<Ingrediente> comidasQueLeDisgustan = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Usuarios_Condiciones")
    protected List<Condicion> condiciones = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "Usuarios_Recetas_Consultadas")
    protected List<Receta> historial = new ArrayList<>();
    @Transient
    protected Set<Receta> favoritas = new HashSet<>();

    /* Servicios */
    public float indiceDeMasaCorporal() {
        return peso / (altura * altura);
    }

    public boolean sigueRutinaSaludable() {
        float imc = indiceDeMasaCorporal();
        return 18 < imc && imc < 30 && subsanaTodasLasCondiciones();
    }

    public void agregarReceta(Receta receta) {
        if (esAdecuada(receta) && receta.puedeSerAgregadaPor(this)) {
            recetas.add(receta);
        } else {
            throw new NoSePuedeAgregarLaReceta();
        }
    }

    public boolean esAdecuada(Receta receta) {
        return receta.esValida() && todasLasCondicionesCumplen(condicion -> condicion.esRecomendable(receta));
    }

    public void modificarReceta(Receta receta, EncabezadoDeReceta encabezado, List<Ingrediente> ingredientes,
            List<Ingrediente> condimentos, String preparacion, List<Receta> subRecetas) {
        receta.modificarReceta(this, encabezado, ingredientes, condimentos, preparacion, subRecetas);
        //TODO: hacer algo con excepción NoSePuedeModificarLaReceta
    }

    public boolean puedeSugerirse(Receta receta) {
        return leGusta(receta) && esAdecuada(receta);
    }

    public List<Receta> recetasQuePuedeVer() {
        return RepositorioDeRecetas.instance().listarRecetasPara(this, null, null);
    }

    public List<Receta> recetasQuePuedeVer(List<Filtro> filtros, PostProcesamiento postProcesamiento) {
        return RepositorioDeRecetas.instance().listarRecetasPara(this, filtros, postProcesamiento);
    }

    public boolean puedeVer(Receta receta) {
        return receta.puedeSerVistaPor(this) || algunGrupoPuedeVer(receta);
    }

    public boolean cumpleTodasLasCondicionesDe(Usuario usuario) {
        return usuario.noTieneCondiciones() ? true : this.getCondiciones().containsAll(usuario.getCondiciones());
    }

    /* Servicios secundarios */
    public boolean esValido() {
        return tieneCamposObligatorios() && nombre.length() > 4 && tieneCondicionesValidas() && fechaNacimiento.isBefore(LocalDate.now());
    }

    public boolean tienePreferenciasAlimenticias() {
        return !preferenciasAlimenticias.isEmpty();
    }

    public boolean noTieneCondiciones() {
        return condiciones.isEmpty();
    }

    public boolean leGusta(String nombreComida) {
        return preferenciasAlimenticias.contains(Ingrediente.nuevaComida(nombreComida));
    }

    public boolean leGusta(Ingrediente comida) {
        return leGusta(comida.getNombre());
    }

    public boolean leGusta(Receta receta) {
        return receta.noContieneNinguna(comidasQueLeDisgustan);
    }

    public boolean leGustaLaCarne() {
        return preferenciasAlimenticias.stream().anyMatch(a -> a.esCarne());
    }

    public boolean tieneRutina(Rutina rutina) {
        return this.rutina == null ? false : this.rutina.equals(rutina);
    }

    public boolean tieneReceta(Receta receta) {
        return recetas.contains(receta);
    }

    public boolean puedeModificar(Receta receta) {
        return puedeVer(receta);
    }

    public Receta recetaMasReciente() {
        return recetas.get(recetas.size() - 1);
    }

    public boolean perteneceA(GrupoUsuarios grupo) {
        return grupos.contains(grupo);
    }

    public String toString() {
        return nombre;
    }

    public boolean esVegano() {
        return this.condiciones.contains(Vegano.instance());
    }

    public boolean esHombre() {
        return Sexo.MASCULINO.equals(sexo);
    }

    public boolean esMujer() {
        return Sexo.FEMENINO.equals(sexo);
    }

    public void marcarFavorita(Receta receta) {

        if (!puedeVer(receta)) {
            throw new NoSePuedeGuardarLaRecetaEnElHistorial();
        }

        favoritas.add(receta);
        historial.add(receta);
    }

    public void marcarFavoritas(List<Receta> consulta) {
        consulta.forEach(r -> marcarFavorita(r));
    }

    public void consulto(Receta receta) {
        if (sexo.equals(Sexo.MASCULINO)) {
            receta.consultoHombre();
        } else {
            receta.consultoMujer();
        }
    }

    public void consulto(List<Receta> recetas) {
        historial.addAll(recetas);
    }

    /* Servicios internos */
    private boolean tieneCamposObligatorios() {
        return this.nombre != null && this.peso != 0 && this.altura != 0 && this.fechaNacimiento != null && this.rutina != null;
    }

    private boolean todasLasCondicionesCumplen(Predicate<Condicion> predicado) {
        return condiciones.isEmpty() ? true : condiciones.stream().allMatch(predicado);
    }

    private boolean tieneCondicionesValidas() {
        return todasLasCondicionesCumplen(unaCondicion -> unaCondicion.esValidaCon(this));
    }

    private boolean subsanaTodasLasCondiciones() {
        return todasLasCondicionesCumplen(unaCondicion -> unaCondicion.subsanaCondicion(this));
    }

    private boolean algunGrupoPuedeVer(Receta receta) {
        return grupos.stream().anyMatch(g -> g.puedeVer(receta));
    }

    public List<Condicion> getCondiciones() {
        return condiciones;
    }

    /* Accessors and Mutators */
    public String getNombre() {
        return nombre;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public float getAltura() {
        return altura;
    }

    public float getPeso() {
        return peso;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public String getMail() {
        return mail;
    }

    public List<Receta> getHistorial() {
        return historial;
    }

    public Set<Receta> getFavoritas() {
        return Collections.unmodifiableSet(favoritas);
    }

    public boolean getMarcaFavorita() {
        return marcaFavorita;
    }

    public long getId() {
        return id;
    }

    public void setMarcaFavorita(boolean bool) {
        marcaFavorita = bool;
    }

    public Usuario setRutina(Rutina rutina) {
        this.rutina = rutina;
        return this;
    }

    public Usuario agregarCondicion(Condicion condicion) {
        this.condiciones.add(condicion);
        return this;
    }

    public Usuario agregarCondiciones(List<Condicion> condiciones) {
        this.condiciones.addAll(condiciones);
        return this;
    }

    public Usuario agregarPreferenciaAlimenticia(Ingrediente comida) {
        this.preferenciasAlimenticias.add(comida);
        return this;
    }

    public Usuario agregarPreferenciasAlimenticias(List<Ingrediente> comidas) {
        this.preferenciasAlimenticias.addAll(comidas);
        return this;
    }

    public Usuario agregarComidaQueLeDisgusta(Ingrediente comida) {
        this.comidasQueLeDisgustan.add(comida);
        return this;
    }

    public Usuario agregarComidasQueLeDisgustan(List<Ingrediente> comidas) {
        this.comidasQueLeDisgustan.addAll(comidas);
        return this;
    }

    public List<Receta> getRecetas() {
        return this.recetas;
    }

    public Usuario agregarGrupo(GrupoUsuarios grupo) {
        grupos.add(grupo);

        if (!grupo.esMiembro(this)) {
            grupo.agregarUsuario(this);
        }

        return this;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Ingrediente> getPreferenciasAlimenticias() {
        return preferenciasAlimenticias;
    }

    public List<Ingrediente> getComidasQueLeDisgustan() {
        return comidasQueLeDisgustan;
    }

}
