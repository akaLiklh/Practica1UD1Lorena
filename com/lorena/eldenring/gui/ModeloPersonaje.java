package com.lorena.eldenring.gui;

import com.lorena.eldenring.base.Enemigo;
import com.lorena.eldenring.base.Jugador;
import com.lorena.eldenring.base.NPC;
import com.lorena.eldenring.base.Personaje;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

public class ModeloPersonaje {
    private ArrayList<Personaje> personajes;
    public ModeloPersonaje() {
        personajes = new ArrayList<Personaje>();
    }
    public ArrayList<Personaje> obtenerPersonajes() {
        return personajes;
    }

    public void altaPersonaje(Personaje personaje) {
        personajes.add(personaje);
    }

    public void modificarPersonaje(int posicion, Personaje personaje) {
        personajes.set(posicion, personaje);
    }

    public void eliminarPersonaje(int posicion) {
        personajes.remove(posicion);
    }

    public boolean existeNombre(String nombre) {
        for (Personaje personaje : personajes) {
            if (personaje.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    public void exportarXML(File fichero) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.newDocument();

        Element raiz = documento.createElement("Personajes");
        documento.appendChild(raiz);

        for (Personaje personaje : personajes) {
            Element nodoPersonaje;

            if (personaje instanceof Jugador) {
                nodoPersonaje = documento.createElement("Jugador");
            } else if (personaje instanceof NPC) {
                nodoPersonaje = documento.createElement("NPC");
            } else {
                nodoPersonaje = documento.createElement("Enemigo");
            }
            raiz.appendChild(nodoPersonaje);

            anadirNodo(documento, nodoPersonaje, "nombre", personaje.getNombre());
            anadirNodo(documento, nodoPersonaje, "fecha-nacimiento", personaje.getFechaNacimiento().toString());
            anadirNodo(documento, nodoPersonaje, "nivel", String.valueOf(personaje.getNivel()));
            anadirNodo(documento, nodoPersonaje, "vida", String.valueOf(personaje.getVida()));
            anadirNodo(documento, nodoPersonaje, "descripcion", personaje.getDescripcion());

            if (personaje instanceof Jugador) {
                Jugador jugador = (Jugador) personaje;
                anadirNodo(documento, nodoPersonaje, "arma", jugador.getArma());
                anadirNodo(documento, nodoPersonaje, "mana", String.valueOf(jugador.getMana()));
            } else if (personaje instanceof NPC) {
                NPC npc = (NPC) personaje;
                anadirNodo(documento, nodoPersonaje, "ubicacion", npc.getUbicacion());
                anadirNodo(documento, nodoPersonaje, "misiones", String.valueOf(npc.getMisiones()));
            } else {
                Enemigo enemigo = (Enemigo) personaje;
                anadirNodo(documento, nodoPersonaje, "tipo-ataque", enemigo.getTipoAtaque());
                anadirNodo(documento, nodoPersonaje, "recompensa-runas", String.valueOf(enemigo.getRecompensaRunas()));
            }
        }

        DOMSource origen = new DOMSource(documento);
        StreamResult resultado = new StreamResult(fichero);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(origen, resultado);
    }

    private void anadirNodo(Document documento, Element padre, String etiqueta, String texto) {
        Element nodo = documento.createElement(etiqueta);
        nodo.appendChild(documento.createTextNode(texto));
        padre.appendChild(nodo);
    }
    public void importarXML(File fichero) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(fichero);

        ArrayList<Personaje> leidos = new ArrayList<Personaje>();

        NodeList jugadores = documento.getElementsByTagName("Jugador");
        for (int i = 0; i < jugadores.getLength(); i++) {
            Element nodo = (Element) jugadores.item(i);
            leidos.add(new Jugador(
                    leerTexto(nodo, "nombre"),
                    LocalDate.parse(leerTexto(nodo, "fecha-nacimiento")),
                    Integer.parseInt(leerTexto(nodo, "nivel")),
                    Double.parseDouble(leerTexto(nodo, "vida")),
                    leerTexto(nodo, "descripcion"),
                    leerTexto(nodo, "arma"),
                    Integer.parseInt(leerTexto(nodo, "mana"))));
        }

        NodeList npcs = documento.getElementsByTagName("NPC");
        for (int i = 0; i < npcs.getLength(); i++) {
            Element nodo = (Element) npcs.item(i);
            leidos.add(new NPC(
                    leerTexto(nodo, "nombre"),
                    LocalDate.parse(leerTexto(nodo, "fecha-nacimiento")),
                    Integer.parseInt(leerTexto(nodo, "nivel")),
                    Double.parseDouble(leerTexto(nodo, "vida")),
                    leerTexto(nodo, "descripcion"),
                    leerTexto(nodo, "ubicacion"),
                    Integer.parseInt(leerTexto(nodo, "misiones"))));
        }

        NodeList enemigos = documento.getElementsByTagName("Enemigo");
        for (int i = 0; i < enemigos.getLength(); i++) {
            Element nodo = (Element) enemigos.item(i);
            leidos.add(new Enemigo(
                    leerTexto(nodo, "nombre"),
                    LocalDate.parse(leerTexto(nodo, "fecha-nacimiento")),
                    Integer.parseInt(leerTexto(nodo, "nivel")),
                    Double.parseDouble(leerTexto(nodo, "vida")),
                    leerTexto(nodo, "descripcion"),
                    leerTexto(nodo, "tipo-ataque"),
                    Integer.parseInt(leerTexto(nodo, "recompensa-runas"))));
        }

        personajes = leidos;
    }

    private String leerTexto(Element nodo, String etiqueta) {
        return nodo.getElementsByTagName(etiqueta).item(0).getTextContent();
    }
}