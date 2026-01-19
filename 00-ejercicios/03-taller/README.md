# 03-Taller
## Parte 1
Desarrolla una base de datos de un taller de coches, el taller tiene un inventario de los coches en los que está trabajando.

---

Los **coches** contienen está información:
- id Int incremental
- Matricula String
- Modelo String
- fechaEntrada String
- nombreResponsable String

Realiza todas las **operaciones CRUD**:
### CREATE
Al pulsar el botón añadir coche, se abre una ventana para que introduzcamos la información necesaria para crear un coche.

### READ
- Por defecto la ventana va a cargar todos los coches.
- Hay **un textField**. pero la búsqueda puede ser en base a dos parámetros, se acepta tanto la *matrícula* cómo el *modelo*.
- Hay un **botón aplicar filtros** para que se apliquen los filtros solo si han sido completados.
### UPDATE
- La lista de VentanaVer contiene un botón IconButton editar ✏️.
- Al pulsar ese botón se abre un formulario completo con la información del coche. 
- Hay dos opciones rechazar y aceptar, en caso de haber aceptado los cambios, se actualiza la base de datos.

### DELETE
- La lista de VentanaVer contiene un boton IconButton eliminar 🗑️.
- Al pulsarse se elimina el coche y se informa con Toast de que se ha eliminado.

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
### mecanico
- id Int incremental
- dni String
- Nombre String
### reparacion
- id Int incremental
- descipcion String
- coste Int
- id_coche Int FK(coche)

Aunque hay que diseñar esta base de datos, a nivel de ventanas solo se va a integrar la tabla de mecánico, es decir, en reparación se desarrolla una tabla y un DAO con las funciones básicas pero en esta fase del desarrollo no se le va a dar uso.

- Modifica la VentanaVer para que se muestre el nombre del mecánico.
- Añade la ventana CrearMecanico. (1pt)
- Modifica la ventana AñadirCoche para que se asigne un mecánico. (1pt)
- Crea las tablas correctamente relacionadas. (1pt)
- Modifica los datos de pruebas para que tengan en cuenta la relación con los mecánicos.


---

## Ventanas

Las ventanas de las que dispone la App son las siguientes.
### VentanaVer
Ventana principal del sistema, tiene una tabla con toda la información.
## VentanaCocheForm
Sirve tanto para editar coches cómo para crearlos. Al fin y al cabo para ambos casos el formulario es el mismo, con la diferencia de que el el caso de edición se debe realizar una función update y el formulario debe mostrar toda la información ya completada por defecto.

---

## Rubrica (COMPLETAR)
- Parte 1
- Parte 2

---
## Anotaciones
- Usa solo un ViewModel para simplificar el proyecto.

---
## Entrega

La entrega de esta actividad puede ser un proyecto cómo dos, ambos tienen que tener el mismo package, pero cada directorio tiene que tener un nombre en el que pone parte1 o parte2.
