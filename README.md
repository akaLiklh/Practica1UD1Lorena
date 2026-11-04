# Practica1UD1Lorena · Elden Ring: Creación de personajes
---

## Tabla de contenidos

1. [Funcionalidades](#funcionalidades)
2. [Requisitos y librerías](#requisitos-y-librerías)
3. [Cómo ejecutar el proyecto](#cómo-ejecutar-el-proyecto)
4. [Guía de uso](#guía-de-uso)
5. [Estructura del proyecto](#estructura-del-proyecto)
6. [Diseño: MVC y herencia](#diseño-mvc-y-herencia)
7. [Formato del fichero XML](#formato-del-fichero-xml)
8. [Tipos de datos](#tipos-de-datos)
9. [Requisitos de la práctica](#requisitos-de-la-práctica)
10. [Seguimiento con Git](#seguimiento-con-git)

---

## Funcionalidades

- **Portada** con imagen y botón de inicio.
- **Formulario de personaje**: nombre, género (radio button), fecha de nacimiento (DatePicker), clase inicial y objeto inicial (desplegables).
- Al cambiar el género, la lista de clases cambia a su forma masculina o femenina (por ejemplo *Guerrero / Guerrera*) y se actualiza la imagen.
- **Pantalla de gestión** con lista de personajes y ficha detallada de cada uno.
- **Nuevo**, **Actualizar** y **Borrar** personajes (con confirmación al borrar).
- **Exportar** todos los personajes a un fichero **XML** (DOM).
- **Importar** personajes desde un XML, validando el contenido. Si el fichero falla, la lista actual no se pierde.
- **Validaciones**: nombre obligatorio, nombre no repetido, fecha obligatoria y no posterior a hoy.
- **Cuadro de diálogo de confirmación** al cerrar la ventana.
- Diseño personalizado: colores verde y dorado, icono de aplicación, título de ventana e imágenes de los objetos.

## Requisitos y librerías

| Herramienta | Versión |
|---|---|
| JDK | 8 |
| IDE | IntelliJ IDEA |
| jgoodies-common | 1.8.1 |
| jgoodies-forms | 1.8.0 |
| LGoodDatePicker | 10.3.1 |

Los tres `.jar` están en la carpeta [`libs/`](libs).

## Cómo ejecutar el proyecto

### Desde IntelliJ

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/akaLiklh/Practica1UD1Lorena.git
   ```
2. Abrir la carpeta con IntelliJ IDEA y configurar el **JDK 8** (*File → Project Structure → Project SDK*).
3. Comprobar que los `.jar` de `libs/` están añadidos como librerías (*File → Project Structure → Libraries*).
4. Ejecutar la clase `com.lorena.eldenring.Main`.

### Desde el JAR

```bash
java -jar Practica1UD1Lorena.jar
```

> Se necesita Java 8 o superior instalado.

## Guía de uso

1. **Portada**: pulsa el botón del menú para empezar.
2. **Crear personaje**: rellena el nombre, elige género, fecha de nacimiento, clase y objeto inicial, y pulsa el botón de terminar.
3. **Gestión**:
   - Selecciona un personaje de la lista para ver su ficha.
   - **Nuevo**: vuelve al formulario vacío.
   - **Actualizar**: abre el formulario con los datos del personaje seleccionado.
   - **Borrar**: elimina el personaje seleccionado (pide confirmación).
   - **Exportar**: guarda todos los personajes en un `.xml`.
   - **Importar**: carga personajes desde un `.xml` (sustituye los actuales, avisando antes).
4. **Cerrar**: al cerrar la ventana aparece un cuadro de diálogo de confirmación.

## Estructura del proyecto

```
Practica1UD1Lorena/
├── libs/                               # Librerías .jar
├── src/
│   ├── util/
│   │   └── Util.java                   # Clase de utilidades (estilos, diálogos, JFileChooser, ventanas)
│   └── com/lorena/eldenring/
│       ├── Main.java                   # Punto de entrada
│       ├── base/                       # Clases de dominio
│       │   ├── Personaje.java          # Clase padre abstracta
│       │   ├── PersonajeMasculino.java # Clase hija
│       │   ├── PersonajeFemenino.java  # Clase hija
│       │   ├── ClasesPersonaje.java    # Enum: clases iniciales
│       │   └── ItemInicial.java        # Enum: objetos iniciales
│       ├── gui/                        # Modelo, vistas y controlador
│       │   ├── CharacterModel.java     # MODELO
│       │   ├── CharacterController.java# CONTROLADOR
│       │   ├── FrontPage.java          # VISTA: portada
│       │   ├── CharacterInformation.java # VISTA: formulario
│       │   └── gestionFicheros.java    # VISTA: gestión de personajes
│       └── images/                     # Iconos e imágenes
└── README.md
```

## Diseño: MVC y herencia

### MVC

| Capa | Clase | Responsabilidad |
|---|---|---|
| **Modelo** | `CharacterModel` | Guarda la lista de personajes y hace alta, modificación, baja, exportar e importar XML. No conoce la interfaz. |
| **Vista** | `FrontPage`, `CharacterInformation`, `gestionFicheros` | Componentes Swing. No contienen lógica de negocio. |
| **Controlador** | `CharacterController` | Escucha los botones y la lista (`ActionListener`, `ListSelectionListener`), lee la vista, valida y llama al modelo. |

### Herencia

```
            Personaje (abstract)
           /                    \
PersonajeMasculino        PersonajeFemenino
```

- `Personaje` guarda los datos comunes y define los métodos abstractos `getGenero()` y `getNombreClase()`.
- Cada hija los implementa a su manera (por ejemplo, el nombre de la clase en masculino o femenino).
- El controlador crea una hija u otra según el **radio button** seleccionado.

## Formato del fichero XML

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Personajes>
  <PersonajeMasculino>
    <nombre>Lorena</nombre>
    <fecha-nacimiento>2000-05-14</fecha-nacimiento>
    <clase>GUERRERO</clase>
    <nivel>8</nivel>
    <item-inicial>SEMILLA_DORADA</item-inicial>
  </PersonajeMasculino>
</Personajes>
```

- El nombre de la etiqueta de cada personaje (`PersonajeMasculino` / `PersonajeFemenino`) indica qué clase hija hay que crear al importar.
- El `nivel` se exporta, pero al importar se recalcula a partir de la clase.

## Tipos de datos

| Tipo | Dónde se usa |
|---|---|
| `String` | Nombre del personaje |
| Numérico (`int`) | Nivel inicial de la clase |
| Fecha (`LocalDate`) | Fecha de nacimiento (con `DatePicker` de LGoodDatePicker) |
| `enum` | Clase inicial y objeto inicial |

## Requisitos de la práctica

### Requisitos mínimos

- [x] Diseño de la aplicación: RadioButton, DatePicker, Label, TextField, TextArea y Button.
- [x] Herencia, estructura MVC y uso de fechas con LGoodDatePicker.
- [x] Nuevo, importación y exportación.
- [x] Botón cerrar con cuadro de diálogo y clase `Util`.
- [x] Tipos de datos String, numéricos y fechas.
- [x] Seguimiento con Git con commits en varios días.

### Requisitos opcionales

- [x] Campos extra (género, clase, objeto inicial).
- [x] Funcionalidades extra (Actualizar, Borrar, validaciones).
- [x] RadioButton asociados a la herencia con cambio de campos (Masculino / Femenino).
- [ ] JAR funcional.
- [ ] Javadoc.
- [x] Modificación del diseño (colores, icono, título, imágenes).

> Marca o desmarca las casillas según lo que hayas terminado.

## Seguimiento con Git

Repositorio: <https://github.com/akaLiklh/Practica1UD1Lorena>

Ramas: `main` y `lorena-proyecto-completo`.

Historial de commits:

| Fecha | Commit |
|---|---|
| 01/11/2026 | Primer commit y comienzo del proyecto, ideas y estructura |
| 01/11/2026 | Añadir `.gitignore` y eliminar archivos generados |
| 01/11/2026 | Clase `Util`: mensajes de error e información y gestión de ficheros |
| 01/11/2026 | Creación de interfaces: portada y rellenar campos |
| 03/11/2026 | Personalización de estilo, icono y ajuste de pantallas |
| 03/11/2026 | Mejora de interfaz |
| 03/11/2026 | Práctica terminada |

## Autora

**Lorena** · Acceso a Datos · Profesora: Elena Jiménez Fuentes
