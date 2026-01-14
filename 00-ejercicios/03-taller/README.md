# Borrador enunciado Taller
## Parte 1
Crea una base de datos llamada taller, en la que la unica tabla que hay es coche. Realiza todas las operaciones CRUD.

Los datos son
- Matricula
- Modelo
- color
- fechaEntrada

## Parte 2

El taller esta creciendo y se ha tomado la decisión de que se quiere tener un mayor poder sobre los datos que trabaja el taller, crea una base de datos que cumpla las siguentes caracteristiacas.

**vehiculo**
id (PK)
id_cliente (FK)
matrícula (única)
marca
modelo
año
color

**cliente**
id (PK)
nombre
apellidos
DNI / NIF
teléfono
email
dirección

**reparaciones**
id (PK)
id_vehiculo (FK)
descripción_servicio (cambio de aceite, frenos, etc.)
horas_trabajo
precio_mano_obra