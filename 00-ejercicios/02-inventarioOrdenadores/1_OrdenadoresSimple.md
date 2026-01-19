
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


### CREATE (VentanaCrear)
Una ventana específica en la que se muestra un formulario para introducir información y crear nuevos ordenadores.

### READ (VentanaVerLista)
Sirve para realizar búsquedas de ordenadores. Se puede mostrar un listado completo de los ordenadores o este puede ser filtrado por contiene en el nombre o modelo.

- Por defecto la ventana va a cargar los ordenadores por orden alfabético de la codificación.
- Hay **dos textField**, uno para buscar por identificador y otro por modelo.
- Hay un **botón aplicar filtros** para que se apliquen los filtros solo si han sido completados.
- Si se aplican los filtros y el textField está vacío, se tiene que mostrar la lista por defecto de la ventana.
- La lista de VentanaVer contiene un botón **IconButton ver** 👁️. Al pulsarse se accede a **VentanaVerInfo**, en la que se muestra toda la información del ordenador.

### UPDATE (VentanaEditar)
- La lista de VentanaVer contiene un botón **IconButton editar** ✏️.
- En la ventana editar se puede ver el mismo formulario para crear ordenadores.
- Hay dos opciones: rechazar y aceptar. En caso de haber aceptado los cambios, se actualiza la base de datos.

### DELETE
- La lista de VentanaVer contiene un botón **IconButton eliminar** 🗑️. 
- Al piilsar, se elimina el ordenador de la base de datos.
