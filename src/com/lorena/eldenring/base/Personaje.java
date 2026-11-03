package com.lorena.eldenring.base;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Clase padre (abstracta) de todos los personajes.
 * <p>
 * Guarda los datos comunes: nombre (String), fecha de nacimiento (LocalDate),
 * clase (enum), objeto inicial (enum) y nivel (int). No se pueden crear objetos
 * de esta clase directamente: hay que crear un {@link PersonajeMasculino} o un
 * {@link PersonajeFemenino}, que son las clases hijas (herencia).
 *
 */
public abstract class Personaje implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Formato con el que se muestran las fechas en pantalla. */
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final String nombre;
    private final LocalDate fechaNacimiento;
    private final ClasesPersonaje clase;
    private final ItemInicial itemInicial;
    private final int nivel;

    /**
     * Constructor que usan las clases hijas mediante {@code super(...)}.
     * El nivel no se pide: es el nivel inicial de la clase elegida.
     *
     * @param nombre          nombre del personaje
     * @param fechaNacimiento fecha de nacimiento
     * @param clase           clase inicial
     * @param itemInicial     objeto inicial
     */
    protected Personaje(String nombre, LocalDate fechaNacimiento, ClasesPersonaje clase,
                        ItemInicial itemInicial) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.clase = clase;
        this.itemInicial = itemInicial;
        this.nivel = clase.getNivelInicial();
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public ClasesPersonaje getClase() {
        return clase;
    }

    public ItemInicial getItemInicial() {
        return itemInicial;
    }

    public int getNivel() {
        return nivel;
    }

    /**
     * Calcula la edad en años a partir de la fecha de nacimiento y la fecha de hoy.
     *
     */
    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    /**
     * Cada clase hija dice su género.
     */
    public abstract String getGenero();

    /**
     * Cada clase hija devuelve el nombre de la clase en su género
     */
    public abstract String getNombreClase();

    /**
     * Texto con todos los datos del personaje, para mostrarlo en el área de texto
     * de la pantalla de gestión.
     *
     */
    public String getFicha() {
        return "Nombre: " + nombre + "\n"
                + "Género: " + getGenero() + "\n"
                + "Clase: " + getNombreClase() + "\n"
                + "Nivel: " + nivel + "\n"
                + "Fecha de nacimiento: " + fechaNacimiento.format(FORMATO_FECHA)
                + " (" + getEdad() + " años)\n"
                + "Objeto inicial: " + itemInicial.getNombre();
    }

    /**
     * Texto que se muestra en la lista de personajes.
     *
     */
    @Override
    public String toString() {
        return nombre + " | " + getGenero() + " | " + getNombreClase() + " | Nivel " + nivel;
    }
}
