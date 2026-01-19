
# 02-inventarioOrdenadores

Se debe diseñar y crear una base de datos para la gestión de inventario de ordenadores de un colegio. La base de datos contará con una única tabla llamada Ordenador, la cual almacenará la información básica de los ordenadores disponibles.

La tabla **Ordenador** deberá contener los siguientes campos:
- *id*: identificador único del ordenador, de tipo entero y con incremento automático.
- *identificador*: Código único que tiene cada uno de los ordenadores.
- *modelo*: Modelo del equipo.
- *año de activacion*: Año en el que el equipo ha sido adquirido por el centro. 

---

## Operaciones CRUD

Para probar que el sistema funciona adecuadamente se han realizado pruebas con los siguientes **datos de ejemplo**.

| Identificador | Modelo                  | Año de activación |
|---------------|-------------------------|--------------------|
| PC-001        | HP EliteBook 850 G6     | 2021               |
| PC-002        | HP EliteBook 850 G6     | 2020               |
| PC-003        | HP EliteBook 850 G6     | 2019               |
| PC-004        | HP EliteBook 850 G6     | 2022               |
| PC-005        | HP EliteBook 850 G6     | 2023               |
| PC-006        | Acer Veriton X          | 2021               |
| PC-007        | Acer Veriton X          | 2020               |
| PC-008        | Acer Veriton X          | 2019               |
| PC-009        | Acer Veriton X          | 2022               |




### CREATE
Una ventana específica en la que se muestra un formulario para introducir información y crear nuevos ordenadores.

### READ
Sirve para realizar búsquedas de ordenadores. Se puede mostrar un listado completo de los ordenadores o este puede ser filtrado por contiene en el nombre o modelo.

- Por defecto la ventana va a cargar los ordenadores por orden alfabético de la codificación.
- Hay **dos textField**, uno para buscar por identificador y otro por modelo.
- Hay un **botón aplicar filtros** para que se apliquen los filtros solo si han sido completados.
- Si se aplican los filtros y el textField está vacío, se tiene que mostrar la lista por defecto de la ventana.
- La lista de VentanaVer contiene un botón **IconButton ver** 👁️. Al pulsarse se accede a **VentanaVerInfo**, en la que se muestra toda la información del ordenador.

### UPDATE
- La lista de VentanaVer contiene un botón **IconButton editar** ✏️.
- En la ventana editar se puede ver el mismo formulario para crear ordenadores.
- Hay dos opciones: rechazar y aceptar. En caso de haber aceptado los cambios, se actualiza la base de datos.

### DELETE
- La lista de VentanaVer contiene un botón **IconButton eliminar** 🗑️. 
- Al pulsar, se elimina el ordenador de la base de datos.

---

## Parte 2

Cada alumno del centro es propietario de un ordenador.

La tabla **Alumno** contiene los siguientes parámetros:

Datos tabla alumno

| Nombre | id_ordenador | Curso               |
|--------|--------------|---------------------|
| Mikel  | 1            | ESO_1               |
| Naroa  | 2            | ESO_2               |
| Lander | 3            | Batx_1              |
| Leire  | 4            | ESO_3               |



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
- Dentro del botón de datos de prueba elimina algún alumno para comprobar que el sistema de eliminación también funciona. **0,5 puntos**
- Crea una ventana con un formulario para crear alumnos. **1 punto**
- Modifica la VentanaVer para que se muestre el id del alumno **1 punto**. Si muestra nombre del alumno **0,5 extra**.
- Aprovecha un texfield para que sirva para buscar por nombre del alumno. **0,5 puntos (extra)**

---

## Anotaciones
- Usa solo un ViewModel para simplificar el proyecto.
- Los mensajes informativos son válidos tanto Text cómo Toast.
- Se recomienda el uso de MyLog para escribir trazas en el código y facilitar la identificación de errores.
- Los campos obligatorios de informar deben enviar mensajes de error en caso de no haber sido informados.

---

## Entrega

La entrega de esta actividad puede ser un proyecto cómo dos, ambos tienen que tener el mismo package, pero cada directorio tiene que tener un nombre en el que pone parte1 o parte2.