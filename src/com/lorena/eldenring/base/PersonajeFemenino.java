package com.lorena.eldenring.base;

import java.time.LocalDate;

/**
 * Personaje de género femenino. Hereda de personaje
 *
 */
public class PersonajeFemenino extends Personaje {
    private static final long serialVersionUID = 1L;

    /**
     * @param nombre      nombre del personaje
     * @param fecha       fecha de nacimiento
     * @param clase       clase inicial
     * @param itemInicial objeto inicial
     */
    public PersonajeFemenino(String nombre, LocalDate fecha, ClasesPersonaje clase,
                             ItemInicial itemInicial) {
        super(nombre, fecha, clase, itemInicial);
    }

    /** @return "Femenino" */
    @Override
    public String getGenero() {
        return "Femenino";
    }

    /** @return nombre de la clase en femenino */
    @Override
    public String getNombreClase() {
        return getClase().getNombreFemenino();
    }
}
