package com.lorena.eldenring.base;

/**
 * Objeto (item) inicial que el personaje recibe al empezar la partida.
 * <p>
 * El orden de las constantes es el mismo que el del desplegable de items del
 * formulario {@code CharacterInformation.form}: la posición del elemento elegido
 * se corresponde con {@link #ordinal()}.
 *
 */
public enum ItemInicial {
    /** Medallón de ámbar. */
    MEDALLON_AMBAR("Medallón de ámbar"),
    /** Semilla dorada. */
    SEMILLA_DORADA("Semilla dorada"),
    /** Llave de espada pétrea. */
    LLAVE_ESPADA("Llave de espada pétrea"),
    /** Rama hechizante. */
    RAMA_HECHIZANTE("Rama hechizante"),
    /** Gambas cocidas. */
    GAMBAS_COCIDAS("Gambas cocidas"),
    /** Cenizas de diablillo con colmillos. */
    CENIZAS_DIABLILLO("Cenizas de diablillo con colmillos"),
    /** Tarro agrietado. */
    TARRO_AGRIETADO("Tarro agrietado"),
    /** Runa de las Tierras Intermedias. */
    RUNA_TIERRAS("Runa de las Tierras Intermedias"),
    /** Pesar de Shabriri. */
    PESAR_SHABRIRI("Pesar de Shabriri"),
    /** Sin objeto inicial. */
    NINGUNO("Ninguno");

    private final String nombre;

    /**
     * @param nombre nombre legible del objeto
     */
    ItemInicial(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return nombre legible del objeto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return el nombre legible del objeto
     */
    @Override
    public String toString() {
        return nombre;
    }
}
