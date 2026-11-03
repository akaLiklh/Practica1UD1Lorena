package com.lorena.eldenring.base;

import java.time.LocalDate;

/**
 * Personaje de género masculino. Hereda de personaje.
 *
 */
public class PersonajeMasculino extends Personaje {
    private static final long serialVersionUID = 1L;

    /**
     * @param nombre      nombre del personaje
     * @param fecha       fecha de nacimiento
     * @param clase       clase inicial
     * @param itemInicial objeto inicial
     */
    public PersonajeMasculino(String nombre, LocalDate fecha, ClasesPersonaje clase,
                              ItemInicial itemInicial) {
        super(nombre, fecha, clase, itemInicial);
    }

    /** @return "Masculino" */
    @Override
    public String getGenero() {
        return "Masculino";
    }

    /** @return nombre de la clase en masculino */
    @Override
    public String getNombreClase() {
        return getClase().getNombreMasculino();
    }
}
