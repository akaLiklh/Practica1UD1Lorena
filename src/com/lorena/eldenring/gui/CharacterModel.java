package com.lorena.eldenring.gui;

import com.lorena.eldenring.base.ClasesPersonaje;
import com.lorena.eldenring.base.ItemInicial;
import com.lorena.eldenring.base.Personaje;
import com.lorena.eldenring.base.PersonajeFemenino;
import com.lorena.eldenring.base.PersonajeMasculino;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * MODELO de la aplicación (MVC).
 * <p>
 * Guarda la lista de personajes en memoria y sabe dar de alta, modificar, borrar,
 * exportar a XML e importar desde XML. No sabe nada de la interfaz gráfica.
 *
 */
public class CharacterModel {

    private final ArrayList<Personaje> personajes;

    /** Crea el modelo con la lista vacía. */
    public CharacterModel() {
        personajes = new ArrayList<>();
    }

    /** @return lista con todos los personajes */
    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    /**
     * Añade un personaje nuevo a la lista.
     *
     * @param personaje personaje a añadir
     */
    public void altaPersonaje(Personaje personaje) {
        personajes.add(personaje);
    }

    /**
     * Sustituye un personaje por otro en la misma posición de la lista.
     *
     * @param antiguo personaje que había
     * @param nuevo   personaje con los datos modificados
     */
    public void modificarPersonaje(Personaje antiguo, Personaje nuevo) {
        int posicion = personajes.indexOf(antiguo);
        if (posicion >= 0) {
            personajes.set(posicion, nuevo);
        } else {
            personajes.add(nuevo);
        }
    }

    /**
     * Quita un personaje de la lista.
     *
     * @param personaje personaje a borrar
     */
    public void eliminarPersonaje(Personaje personaje) {
        personajes.remove(personaje);
    }

    /**
     * Comprueba si ya hay otro personaje con el mismo nombre (sin distinguir mayúsculas).
     *
     * @param nombre  nombre a comprobar
     * @param ignorar personaje que no se tiene en cuenta (el que se está editando), puede ser null
     * @return true si el nombre ya está usado por otro personaje
     */
    public boolean existeNombre(String nombre, Personaje ignorar) {
        for (Personaje p : personajes) {
            if (p != ignorar && p.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Exporta todos los personajes a un fichero XML usando DOM.
     * <pre>
     * &lt;Personajes&gt;
     *   &lt;PersonajeMasculino&gt;
     *     &lt;nombre&gt;...&lt;/nombre&gt;
     *     &lt;fecha-nacimiento&gt;2000-05-14&lt;/fecha-nacimiento&gt;
     *     &lt;clase&gt;GUERRERO&lt;/clase&gt;
     *     &lt;nivel&gt;8&lt;/nivel&gt;
     *     &lt;item-inicial&gt;SEMILLA_DORADA&lt;/item-inicial&gt;
     *   &lt;/PersonajeMasculino&gt;
     * &lt;/Personajes&gt;
     * </pre>
     *
     * @param fichero fichero de destino
     * @throws ParserConfigurationException si no se puede crear el documento XML
     * @throws TransformerException         si no se puede escribir el fichero
     */
    public void exportarXML(File fichero) throws ParserConfigurationException, TransformerException {
        // 1. Crear un documento XML vacío en memoria.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.newDocument();

        // 2. Etiqueta raíz <Personajes>. Un XML solo puede tener una raíz.
        Element raiz = documento.createElement("Personajes");
        documento.appendChild(raiz);

        // 3. Un nodo por personaje, con un hijo por cada dato.
        for (Personaje p : personajes) {
            // La etiqueta es el nombre de la clase hija (PersonajeMasculino o PersonajeFemenino):
            // así al importar se sabe qué clase hay que crear.
            Element nodoPersonaje = documento.createElement(p.getClass().getSimpleName());
            raiz.appendChild(nodoPersonaje);

            anadirDato(documento, nodoPersonaje, "nombre", p.getNombre());
            // LocalDate.toString() escribe la fecha como yyyy-MM-dd.
            anadirDato(documento, nodoPersonaje, "fecha-nacimiento", p.getFechaNacimiento().toString());
            // name() da el nombre de la constante del enum (GUERRERO).
            anadirDato(documento, nodoPersonaje, "clase", p.getClase().name());
            anadirDato(documento, nodoPersonaje, "nivel", String.valueOf(p.getNivel()));
            anadirDato(documento, nodoPersonaje, "item-inicial", p.getItemInicial().name());
        }

        // 4. Pasar el documento de la memoria al fichero con un Transformer.
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new DOMSource(documento), new StreamResult(fichero));
    }

    /**
     * Crea una etiqueta hija con un texto dentro: {@code <etiqueta>valor</etiqueta>}.
     */
    private void anadirDato(Document documento, Element padre, String etiqueta, String valor) {
        Element dato = documento.createElement(etiqueta);
        dato.appendChild(documento.createTextNode(valor));
        padre.appendChild(dato);
    }

    /**
     * Importa los personajes de un fichero XML (el mismo formato que {@link #exportarXML(File)}).
     * Sustituye la lista actual solo si todo el fichero se ha leído bien.
     *
     * @param fichero fichero de origen
     * @throws ParserConfigurationException si no se puede crear el lector XML
     * @throws IOException                  si no se puede leer el fichero
     * @throws SAXException                 si el fichero no es un XML válido
     * @throws IllegalArgumentException     si algún dato no es válido (clase, fecha, ...)
     */
    public void importarXML(File fichero) throws ParserConfigurationException, IOException, SAXException {
        // 1. Leer el fichero y cargarlo en memoria como un árbol DOM.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(fichero);

        if (!documento.getDocumentElement().getTagName().equals("Personajes")) {
            throw new IllegalArgumentException("la etiqueta raíz debe ser <Personajes>");
        }

        // 2. Los hijos de la raíz <Personajes> son los personajes.
        NodeList nodos = documento.getDocumentElement().getChildNodes();

        // Se leen en una lista aparte: si el fichero falla a mitad, la lista actual no se pierde.
        ArrayList<Personaje> leidos = new ArrayList<>();

        for (int i = 0; i < nodos.getLength(); i++) {
            Node nodo = nodos.item(i);
            // Los saltos de línea entre etiquetas también son nodos (de texto): se saltan.
            if (nodo.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element elemento = (Element) nodo;

            // 3. Pasar cada dato de texto a su tipo.
            String nombre = leerDato(elemento, "nombre");
            LocalDate fecha = LocalDate.parse(leerDato(elemento, "fecha-nacimiento"));
            ClasesPersonaje clase = ClasesPersonaje.valueOf(leerDato(elemento, "clase"));
            ItemInicial item = ItemInicial.valueOf(leerDato(elemento, "item-inicial"));
            // El nivel no se lee: se calcula a partir de la clase.

            // 4. Según la etiqueta se crea una clase hija u otra (herencia).
            Personaje personaje;
            if (elemento.getTagName().equals("PersonajeMasculino")) {
                personaje = new PersonajeMasculino(nombre, fecha, clase, item);
            } else if (elemento.getTagName().equals("PersonajeFemenino")) {
                personaje = new PersonajeFemenino(nombre, fecha, clase, item);
            } else {
                throw new IllegalArgumentException("etiqueta desconocida <" + elemento.getTagName() + ">");
            }
            leidos.add(personaje);
        }

        // 5. Todo se ha leído bien: ahora sí se sustituye la lista.
        personajes.clear();
        personajes.addAll(leidos);
    }

    /**
     * Devuelve el texto de la primera etiqueta hija con ese nombre.
     */
    private String leerDato(Element padre, String etiqueta) {
        NodeList lista = padre.getElementsByTagName(etiqueta);
        if (lista.getLength() == 0) {
            throw new IllegalArgumentException("falta la etiqueta <" + etiqueta + ">");
        }
        return lista.item(0).getTextContent().trim();
    }
}
