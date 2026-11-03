package com.lorena.eldenring.base;

/**
 * Clases iniciales que puede elegir un personaje de Elden Ring.
 * <p>
 * Cada clase guarda su nombre en masculino y en femenino (el formulario muestra
 * uno u otro según el género elegido) y el nivel con el que empieza el personaje,
 * que es el dato numérico de la aplicación.
 * <p>
 * El orden de las constantes es el mismo que el de los desplegables de clases del
 * formulario {@code CharacterInformation.form}: la posición del elemento elegido en el
 * desplegable se corresponde con {@link #ordinal()}.
 *
 * @author Lorena
 */
public enum ClasesPersonaje {
    /** Guerrero / Guerrera. */
    GUERRERO("Guerrero", "Guerrera", 8),
    /** Héroe. */
    HEROE("Héroe", "Héroe", 7),
    /** Bandido / Bandida. */
    BANDIDO("Bandido", "Bandida", 5),
    /** Profeta. */
    PROFETA("Profeta", "Profeta", 7),
    /** Astrólogo / Astróloga. */
    ASTROLOGO("Astrólogo", "Astróloga", 6),
    /** Prisionero / Prisionera. */
    PRISIONERO("Prisionero", "Prisionera", 9),
    /** Samurái. */
    SAMURAI("Samurái", "Samurái", 9),
    /** Miserable. */
    MISERABLE("Miserable", "Miserable", 1),
    /** Confesor / Confesora. */
    CONFESOR("Confesor", "Confesora", 10);

    private final String nombreMasculino;
    private final String nombreFemenino;
    private final int nivelInicial;

    /**
     * Constructor de cada constante del enum.
     *
     * @param nombreMasculino nombre de la clase en masculino
     * @param nombreFemenino  nombre de la clase en femenino
     * @param nivelInicial    nivel con el que empieza un personaje de esta clase
     */
    ClasesPersonaje(String nombreMasculino, String nombreFemenino, int nivelInicial) {
        this.nombreMasculino = nombreMasculino;
        this.nombreFemenino = nombreFemenino;
        this.nivelInicial = nivelInicial;
    }

    /**
     * @return nombre de la clase en masculino (por ejemplo "Guerrero")
     */
    public String getNombreMasculino() {
        return nombreMasculino;
    }

    /**
     * @return nombre de la clase en femenino (por ejemplo "Guerrera")
     */
    public String getNombreFemenino() {
        return nombreFemenino;
    }

    /**
     * @return nivel inicial de la clase
     */
    public int getNivelInicial() {
        return nivelInicial;
    }

    /**
     * Devuelve el nombre de la clase con las dos formas si son distintas.
     *
     * @return por ejemplo "Guerrero / Guerrera" o "Héroe"
     */
    @Override
    public String toString() {
        return nombreMasculino.equals(nombreFemenino)
                ? nombreMasculino
                : nombreMasculino + " / " + nombreFemenino;
    }
}
