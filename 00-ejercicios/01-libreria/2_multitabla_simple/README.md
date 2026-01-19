# 02-libreria Multitabla Simple

Se debe diseñar y crear una base de datos para una librería. La base de datos contará con una única tabla llamada Libro, la cual almacenará la información básica de los libros disponibles.

## Descripción de las tablas: 

La tabla **Libro** contiene los siguientes atributos
- *id* (Int): identificador único del libro, de tipo entero y con incremento automático.
- *título* (String): nombre del libro, de tipo texto.
- *año de publicación* (String): año en el que el libro fue publicado, de tipo entero. 
- *id_autor* (Int)

Estos son los atributos de la tabla **Autor**
- *id* (Int): identificador único del autor, de tipo entero y con incremento automático.
- *nombre* (String): Nombre y apellidos del autor.
- *Fecha de nacimiento* (String): Fecha de nacimiento del autor.

---

El ejercicio tiene el resultado, en un **examen** sería el mismo código pero **eliminando el ViewModel y el package entero de data**.

---

## Operaciones CRUD

Para probar que el sistema funciona adecuadamente se han realizado pruebas con los siguientes **datos de ejemplo**.

| Título                          | Autor                  | Año de Publicación |
|---------------------------------|------------------------|--------------------|
| Cien Años de Soledad            | Gabriel García Márquez | 1967               |
| 1984                            | George Orwell          | 1949               |
| Rebelión en la Granja           | George Orwell          | 1945               |
| Orgullo y Prejuicio             | Jane Austen            | 1813               |
| El orgullo del dragon           | iria G Parente         | 2019               |

Realiza todas las **operaciones CRUD**:

### CREATE ➕
Al pulsar el botón añadir libro, se abre una ventana para que introduzcamos la información necesaria para crear un libro.

### READ 👀
- Por defecto la ventana va a cargar todos los libros.
- Hay **un textField**. pero la búsqueda puede ser en base a dos parámetros, se acepta tanto la *titulo* cómo el *autor*.
- Hay un **botón aplicar filtros** para que se apliquen los filtros solo si han sido completados.
    - Búsqueda por modelo.
    - Búsqueda por matricula.
    - Si uno de los textField no está informado, no se aplica en la búsqueda.

### UPDATE ✏️
- La lista de VentanaVer contiene un botón IconButton editar ✏️.
- Al pulsar ese botón se abre un formulario completo con la información del libro.
- Hay dos opciones rechazar y aceptar, en caso de haber aceptado los cambios, se actualiza la base de datos.

### DELETE 🗑️
- La lista de VentanaVer contiene un boton IconButton eliminar 🗑️.
- Al pulsarse se elimina el libro y se informa de que se ha eliminado.

---

## Ventanas
Las ventanas de las que dispone la App son las siguientes.

### VentanaVer
Ventana principal del sistema, tiene una tabla con toda la información.

## VentanalibroForm
Sirve tanto para editar libros cómo para crearlos. Al fin y al cabo para ambos casos el formulario es el mismo, con la diferencia de que el el caso de edición se debe realizar una función update y el formulario debe mostrar toda la información ya completada por defecto.

## Diferencias respecto a 1_simple
- **Botón de eliminación simplificado en VentanaVer**, ahora no pide confirmación de eliminación.
- VentanaCrearLibro y VentanaEditarLibro:
    - Se eliminan las ventanas, ahora desde VentanaVer se llama directamenta a LibroForm, modificando los parametros de entrada de la ventana.
    - Se **elimina el formulario con DropdowMenu** para sustiturlo por uno en el que se introduce la id del autor directamente. Con el fin de simplificar la interfaz.
- Ahora la tabla Libro se unifica con la tabla Autor mediante FK.