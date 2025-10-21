PASO 1: C ONEX IÓN C ON J DBC

Basándote en los ejemplos proporcionados en GitHub, implementa los métodos necesarios
para conectarte a la base de datos Hogwarts desde tu proyecto Java usando JDBC.

Ten en cuenta que mantener las credenciales de conexión a la vista en el código es una mala
práctica, por lo que es recomendable utilizar un fichero de propiedades para ello.


## PASO 2: MO DELAD O DE ENT ID AD Y CONS ULTA

Crea una clase Java que modele la tabla **Asignatura** y, posteriormente, crea un método en una
clase **Operaciones** que devuelta un listado de todas las asignaturas.

PASO 5: OPE RA CION ES CRUD

Finalmente, implementa en la clase Operaciones los siguientes métodos:

**1. Consulta de estudiantes por casa**

Crea un método que consulte los nombres y apellidos de todos los estudiantes de una casa
cuyo nombre pasarás por parámetro. Haz la prueba con Gryffindor.

**2. Obtener la mascota de un estudiante específico**

Crea un método que consulte y muestre la mascota de un estudiante cuyo nombre y apellido
pases como parámetros. Haz la prueba con Hermione Granger.

**3. Número de estudiantes por casa**

Crea un método que consulte y devuelva el número de estudiantes en cada casa de Hogwarts.

**4. Insertar una nueva asignatura**

Crea un método que permita insertar una nueva asignatura en la tabla Asignatura. Debes pro-

porcionar los valores necesarios, pudiendo utilizar como ejemplo los datos de este módulo

**5. Modificar el aula de una asignatura**

Crea un método que permita modificar el aula de una asignatura específica. El método debe
tomar como parámetros el id de la asignatura y la nueva aula. Utiliza la asignatura creada y
ponle el nombre que quieras al aula.

**6. Eliminar una asignatura**

Crea un método que permita eliminar una asignatura de la tabla. Ejecuta este método con la
asignatura creada anteriormente.
