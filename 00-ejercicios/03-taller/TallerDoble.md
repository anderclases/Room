# 03-Taller
## Parte 1
Desarrolla una base de datos de un taller de coches, el taller tiene un inventario de los coches en los que está trabajando.

Este enunciado está repartido en dos partes, en la primera parte la base de datos es **simple con una única tabla**. En la segunda se pide mejorar el sistema añadiendo una segunda tabla e incluyendo funcionalidades.

---

Los **coches** contienen está información:
- id Int incremental
- Matricula String
- Modelo String
- fechaEntrada String
- nombreResponsable String

Realiza todas las **operaciones CRUD**:

### CREATE ➕
Al pulsar el botón añadir coche, se abre una ventana para que introduzcamos la información necesaria para crear un coche.

### READ 👀
- Por defecto la ventana va a cargar todos los coches.
- Hay **un textField**. pero la búsqueda puede ser en base a dos parámetros, se acepta tanto la *matrícula* cómo el *modelo*.
- Hay un **botón aplicar filtros** para que se apliquen los filtros solo si han sido completados.
    - Búsqueda por modelo.
    - Búsqueda por matricula.
    - Si uno de los textField no está informado, no se aplica en la búsqueda.

### UPDATE ✏️
- La lista de VentanaVer contiene un botón IconButton editar ✏️.
- Al pulsar ese botón se abre un formulario completo con la información del coche.
- Hay dos opciones rechazar y aceptar, en caso de haber aceptado los cambios, se actualiza la base de datos.

### DELETE 🗑️
- La lista de VentanaVer contiene un boton IconButton eliminar 🗑️.
- Al pulsarse se elimina el coche y se informa de que se ha eliminado.

---

## Parte 2
Para realizar este proyecto se recomienda copiar el proyecto anterior e implementar este, para asegurar que la parte 1 funciona.

El taller ha decidido mejorar la base de datos para almacenar información más precisa. Para ello han decidido que van a asignar un mecánico responsable a cada uno de los coches, además cada reparación o tarea realizada se registrará en el sistema.

Las nuevas tablas van a a ser:
## coche
- Matricula String
- Modelo String
- color String
- fechaEntrada String
- id_mecanico Int FK(mecanico)

### mecánico
- id Int incremental
- dni String
- Nombre String

### reparación
- id Int incremental
- descipcion String
- coste Int
- id_coche Int FK(coche)

Aunque hay que diseñar esta base de datos, a nivel de ventanas solo se va a integrar la tabla de mecánico, es decir, en reparación se desarrolla una tabla y un DAO con las funciones básicas pero en esta fase del desarrollo no se le va a dar uso.

- Modifica la VentanaVer para que se muestre el nombre del mecánico.
- Añade la ventana CrearMecanico.
- Modifica la ventana AñadirCoche para que se asigne un mecánico.
- Crea las tablas correctamente relacionadas.
- Modifica los datos de pruebas para que tengan en cuenta la relación con los mecánicos.

---

## Ventanas
Las ventanas de las que dispone la App son las siguientes.

### VentanaVer
Ventana principal del sistema, tiene una tabla con toda la información.

## VentanaCocheForm
Sirve tanto para editar coches cómo para crearlos. Al fin y al cabo para ambos casos el formulario es el mismo, con la diferencia de que el el caso de edición se debe realizar una función update y el formulario debe mostrar toda la información ya completada por defecto.

---

## 📊 Rubrica
### Parte 1 (6 puntos)
#### Botón insertar datos de prueba **(1)**
- Crea un botón que inserta datos de prueba en la ventana, al pulsarse los datos se insertan correctamente.

#### CREATE **(1)**
- Al pulsar el botón añadir entra en una ventana para completar los datos.
- Al pulsar aceptar la entidad **se añade** a la base de datos

#### READ **(2)**
- La ventana principal muestra el listado de las entidades que se encuentran en la base de datos. **1 punto**
- Se aplican todos los filtros que se piden y funcionan. **1 punto**

#### UPDATE **(1)**
- Al pulsar el botón de editar entra en una ventana con un formulario para la edición de los datos de la entidad.
- Al seleccionar aceptar, la entidad **se modifica** en base de datos.

#### DELETE **(1)**
- Se elimina el elemento de la base de datos. **0,5 puntos**
- Se crea un log que informa de la eliminacion. **0,25 puntos**
- Se muestra un mensaje en ventana informando de la eliminación. **0,25 puntos**

### Parte 2 (4 puntos)
- Crea la estructura de tablas del enunciado, respetando la lógica esperada en claves foráneas. **1 punto**
- Crea un botón que inserte datos de prueba para esta nueva estructura. **0,5 puntos**
- Dentro del botón de datos de prueba elimina algún mecánico para comprobar que el sistema de eliminación también funciona. **0,5 puntos**
- Crea una ventana con un formulario para crear mecánicos. **1 punto**
- Modifica la VentanaVer para que se muestre el id del mecánico **1 punto**. Si muestra nombre del mecánico **0,5 extra**.
- Aprovecha un texfield para que sirva para buscar por nombre del mecánico. **0,5 puntos (extra)**

---

## Anotaciones
- Usa solo un ViewModel para simplificar el proyecto.
- Los mensajes informativos son válidos tanto Text cómo Toast.
- Se recomienda el uso de MyLog para escribir trazas en el código y facilitar la identificación de errores.
- Los campos obligatorios de informar deben enviar mensajes de error en caso de no haber sido informados.

---

## Entrega

La entrega de esta actividad puede ser un proyecto cómo dos, ambos tienen que tener el mismo package, pero cada directorio tiene que tener un nombre en el que pone parte1 o parte2.