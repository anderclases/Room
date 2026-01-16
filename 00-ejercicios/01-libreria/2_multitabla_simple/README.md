# 02-libreria Multitabla Simple

Se debe diseñar y crear una base de datos para una librería. La base de datos contará con una única tabla llamada Libro, la cual almacenará la información básica de los libros disponibles.

## Descripción de las tablas: 

### Libro
- *id* (Int): identificador único del libro, de tipo entero y con incremento automático.
- *título* (String): nombre del libro, de tipo texto.
- *año de publicación* (String): año en el que el libro fue publicado, de tipo entero. 
- *id_autor* (Int): 

### Autor
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

### CREATE (VentanaCrearAutor y VentanaCrearLibro)
Una ventana especifica en la que se muestra un formulario para introducir información y crear nuevos libros.


### READ (VentanaVer)
Sirve para realizar búsquedas de libros. Se puede mostrar un listado completo de los libros o este puede ser filtrado por contiene en el nombre o Autor.

- Por defecto la ventana va a cargar todos los libros por orden alfabético.
- Hay **dos textField** uno para el Autor otro para el titulo.
- Hay un **botón aplicar filtros** para que se apliquen los filtros solo si han sido completados.
- si se aplican los filtros y el textField está vacio se tiene que mostrar la lista por defecto de la ventana.

### UPDATE (VentanaEditarAutor y VentanaEditarLibro)
- La lista de VentanaVer contiene un boton IconButton editar ✏️.
- En la ventana editar se puede ver el mismo formulario para crear libros.
- Hay dos opcciones rechazar y aceptar, en caso de haber aceptado los cambios, se actualiza la base de datos.

### DELETE (VentanaVer)
- La lista de VentanaVer contiene un boton IconButton eliminar 🗑️. 
- Al pusarse sale un aviso, ¿desea eliminar el libro? 
- Al aceptar se recarga la lista y ese libro ha sido eliminado de la base de datos.

## Descripción de las ventanas
### VentanaVer
La ventana central del proyecto.

Está formada por 3 partes:
- Botones de acción.
- Formulario de filtros.
- Tabla de resultados.

#### Botones de acción.
- Insert pruebas:  Inserta libros y autores para las pruebas.
- Añadir libro: Navega a VentanaCrearLibro.
- Añadir Autor: Navega a VentanaCrearAutor.
- Aplicar filtros: 

#### Formulario de filtros.
Hay dos OutlinedTextField uno planteado para filtrar por titulo de libro y otro para filtrar por titulo de autor.

#### Tabla de resultados.
Una tabla en la que se muestran todos los libros o los resultados de las búsquedas filtradas.

### VentanaCrearAutor


### VentanaCrearLibro


### VentanaEditarAuto


### VentanaEditarLibro
Formulario con el libro seleccionado y se pue

## Diferencias respecto a 1_simple
- **Botón de eliminación simplificado en VentanaVer**, ahora no pide confirmación de eliminación.
- VentanaCrearLibro y VentanaEditarLibro:
    - Se eliminan las ventanas, ahora desde VentanaVer se llama directamenta a LibroForm, modificando los parametros de entrada de la ventana.
    - Se **elimina el formulario con DropdowMenu** para sustiturlo por uno en el que se introduce la id del autor directamente. Con el fin de simplificar la interfaz.
- Ahora la tabla Libro se unifica con la tabla Autor mediante FK.