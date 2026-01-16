# 03-libreria Multitabla Avanzada

Se debe diseñar y crear una base de datos relacional para una librería. El objetivo es normalizar la información en tres tablas interconectadas para evitar la duplicidad de datos y mejorar la integridad de la información.


---

El ejercicio tiene el resultado, en un **examen** sería el mismo código pero **eliminando el ViewModel y el package entero de data**.

---

## 1. Estructura de la Base de Datos

La base de datos deberá constar de las siguientes tres entidades:

### Tabla: `Categoria`
| Campo | Tipo de Dato | Descripción |
| :--- | :--- | :--- |
| **id** | int | Identificador único (Clave Primaria, Autoincrementable). |
| **nombre** | String | Nombre del género o temática (Único). |
| **descripcion** | String | Breve descripción de la categoría (Opcional). |

### Tabla: `Autor`
| Campo | Tipo de Dato | Descripción |
| :--- | :--- | :--- |
| **id** | int | Identificador único (Clave Primaria, Autoincrementable). |
| **nombre** | String | Nombre completo del escritor. |
| **nacionalidad** | String | País de origen del autor. |
| **fecha_nacimiento** | Fecha / String | Registro cronológico del nacimiento. |

### Tabla: `Libro`
| Campo | Tipo de Dato | Descripción |
| :--- | :--- | :--- |
| **id** | int | Identificador único (Clave Primaria, Autoincrementable). |
| **titulo** | String | Nombre de la obra. |
| **publish** | int | Año de edición del libro. |
| **autor_id** | int | Clave foránea (FK) que referencia a `Autor(id)`. |
| **categoria_id** | int | Clave foránea (FK) que referencia a `Categoria(id)`. |


---

## 2. Operaciones CRUD y Requisitos de Interfaz

Para validar el sistema, se deben implementar las siguientes funcionalidades en la aplicación:

### CREATE (VentanaCrear)
Formulario para dar de alta nuevos libros.
* **Campos de entrada**: Título y Año de publicación (Texto/Entero).
* **Selectores (Spinners/Dropdowns)**: El usuario no debe escribir el nombre del autor o la categoría manualmente. Debe seleccionarlos de una lista desplegable que obtenga los datos existentes de las tablas `Autor` y `Categoria`.

### READ (VentanaVer)
Pantalla principal con el listado de libros registrados.
* **Visualización**: La lista debe mostrar el título, el nombre del autor y el nombre de la categoría (requiere uso de `JOIN` en la consulta SQL).
* **Ordenación**: Por defecto, los libros aparecerán en orden alfabético por título.
* **Filtros**:
    * Un `TextField` para filtrar por nombre de **Autor**.
    * Un `TextField` para filtrar por **Título**.
    * Un botón **"Aplicar Filtros"**. Si los campos están vacíos, se muestra la lista completa. Si tienen texto, se aplica el filtro mediante la cláusula `LIKE`.

### UPDATE (VentanaEditar)
* Se accede mediante un icono de edición ✏️ en la lista.
* Carga el mismo formulario de creación con los datos actuales del libro.
* Permite modificar cualquier campo, incluyendo la reasignación de Autor o Categoría mediante los selectores.

### DELETE (VentanaVer)
* Se accede mediante un icono de eliminación 🗑️.
* Debe mostrar un diálogo de confirmación: *¿Desea eliminar el libro?*.
* **Regla de integridad**: La eliminación de un libro solo borra el registro en la tabla `Libro`, manteniendo intactos los registros de `Autor` y `Categoria`.

---

## 3. Datos de Prueba

| Título | Autor | Categoría | Año |
| :--- | :--- | :--- | :--- |
| Cien Años de Soledad | Gabriel García Márquez | Ficción | 1967 |
| 1984 | George Orwell | Distopía | 1949 |
| Rebelión en la Granja | George Orwell | Sátira | 1945 |
| Orgullo y Prejuicio | Jane Austen | Romance | 1813 |
| El orgullo del dragón | Iria G. Parente | Ficción | 2019 |

Para poder realizar estas pruebas es necesario insertar los siguientes datos en la base de datos.

Crea un botón de **insertar datos de pruebas** que inserte la información necesaria para visualizar esta ventana. Esta función además de ser útil para tener ya varios datos con los que realizar pruebas es totalmente necesaría cómo mínimo para insertar las categorias en la base de datos.

Estas son las entidades que hay que insertar

### Tabla: `Categoria`

| id | nombre | descripción |
| :--- | :--- | :--- |
| 1 | Ficción | Obras basadas en la imaginación con elementos narrativos inventados. |
| 2 | Distopía | Sociedades imaginarias indeseables donde el control social es opresivo. |
| 3 | Sátira | Uso del humor, la ironía o el ridículo para criticar vicios o instituciones. |
| 4 | Romance | Historias centradas en relaciones amorosas y vínculos emocionales. |

### Tabla: `Autor`
| id | nombre | nacionalidad | fecha_nacimiento |
| :--- | :--- | :--- | :--- |
| 1 | Gabriel García Márquez | Colombiana | 06/03/1927 |
| 2 | George Orwell | Británica | 25/06/1903 |
| 3 | Jane Austen | Británica | 16/12/1775 |
| 4 | Iria G. Parente | Española | 29/10/1993 |

### Tabla: `Libro`
| id | título | año_publicacion | autor_id | categoria_id |
| :--- | :--- | :--- | :--- | :--- |
| 1 | Cien Años de Soledad | 1967 | 1 | 1 (Ficción) |
| 2 | 1984 | 1949 | 2 | 2 (Distopía) |
| 3 | Rebelión en la Granja | 1945 | 2 | 3 (Sátira) |
| 4 | Orgullo y Prejuicio | 1813 | 3 | 4 (Romance) |
| 5 | El orgullo del dragón | 2019 | 4 | 1 (Ficción) |

