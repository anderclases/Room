# 01-libreria Simple

Se debe diseñar y crear una base de datos para una librería. La base de datos contará con una única tabla llamada Libro, la cual almacenará la información básica de los libros disponibles.

La tabla **Libro** deberá contener los siguientes campos:
- *id*: identificador único del libro, de tipo entero y con incremento automático.
- *título*: nombre del libro, de tipo texto.
- *año de publicación*: año en el que el libro fue publicado, de tipo entero. 

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

### CREATE (VentanaCrear)
Una ventana especifica en la que se muestra un formulario para introducir información y crear nuevos libros.


### READ (VentanaVer)
Sirve para realizar búsquedas de libros. Se puede mostrar un listado completo de los libros o este puede ser filtrado por contiene en el nombre o Autor.

- Por defecto la ventana va a cargar todos los libros por orden alfabético.
- Hay **dos textField** uno para el Autor otro para el titulo.
- Hay un **botón aplicar filtros** para que se apliquen los filtros solo si han sido completados.
- si se aplican los filtros y el textField está vacio se tiene que mostrar la lista por defecto de la ventana.

### UPDATE (VentanaEditar)
- La lista de VentanaVer contiene un boton IconButton editar ✏️.
- En la ventana editar se peude ver el mismo formulario para crear libros.
- Hay dos opcciones rechazar y aceptar, en caso de haber aceptado los cambios, se actualiza la base de datos.

### DELETE (VentanaVer)
- La lista de VentanaVer contiene un boton IconButton eliminar 🗑️. 
- Al pusarse sale un aviso, ¿desea eliminar el libro? 
- Al aceptar se recarga la lista y ese libro ha sido eliminado de la base de datos.
