![](Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.001.png)

UD 02: MANEJO DE FICHEROS 

SEMANA 6: CONEXIÓN Y OPERACIÓN CON BASES DE DATOS RELACIONALES ![](Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.002.png)

Esta semana exploraremos bases de datos relacionales utilizando **PostgreSQL**. Para ello, crea- remos una base de datos con información sobre la escuela de magia **Hogwarts**, alojada en una instancia en la nube gestionada por **Amazon RDS** (Relational Database Service). 

El objetivo de esta práctica será conectarnos a esta base de datos remota y operar con ella a través del driver **JDBC**. 

PASO 1: INSTANCIA DE BASE DE DATOS AMAZON RDS ![](Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.003.png)

Inicia el laboratorio AWS Academy Learner Lab y busca en la consola de AWS el servicio RDS y haz clic en él. 

![](Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.004.jpeg)

En el menú vertical izquierdo, haz clic en **Bases de Datos**, donde inicialmente te aparecerá un listado vacío de instancias de bases de datos. Haz clic en el botón **Crear base de datos**, el cual abre una nueva página configuraremos las características de nuestra instancia. 

Las opciones que marcar son: 

- En **Elegir un método de creación de base de datos** selecciona **Creación estándar**. 
- En **Opciones de motor**, dentro de **Tipo de motor** selecciona **PostgreSQL**. 
- En **Plantillas**, selecciona **Capa gratuita**. 
- En **Configuración**: 
- En **Identificador de instancias de bases de datos**, escribe el nombre que quie- res que identifique a tu instancia dentro del listado que tendrás en Amazon. Por ejemplo, **ad-postgres**.  
- En el apartado **Configuración de credenciales**, pon como **Nombre de usuario maestro** el usuario maestro de la instancia, por defecto **postgres**. Añade una **contraseña maestra** de mínimo 8 caracteres para identificarte como el usuario maestro. Utiliza una contraseña sencilla pero no relacionada con tus contrase- ñas personales. 
- En **Conectividad**: 
- En **Acceso Público**, marca **Sí**, para que la instancia y las bases de datos que con- tenga sean accesibles desde Internet y, por tanto, desde tu aplicación Java. 
- En **Grupo de seguridad de VPC (firewall)** selecciona **Crear nuevo** y ponle de nombre **RDS-PostgreSQL**. A posteriori configuraremos el tráfico entrante de la instancia. 
- En **Autenticación de bases de datos** debes marcar la opción de Autenticación con contraseña. 
- En **Supervisión**, **desactiva** la opción de **Activar Perfomance Insights**. 
- En **Configuración adicional**, dentro de **Copia de seguridad**, **desactiva** la opción de **habilitar las copias de seguridad automatizadas**. 

Ya puedes ir al final de la página y darle a **Crear base de datos**. Saltará una ventaja emergente con **Complementos sugeridos para la instancia**. Haz clic en **Ocultar complementos du- rante 30 días** y haz clic en el botón **Cerrar**. La instancia de base de datos estará creándose. 

![](Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.005.jpeg)

Una vez recibamos el mensaje de que la instancia de base de datos se ha creado correcta- mente, hacemos clic en su **identificador** para conocer algunos detalles de su configuración. En la nueva ventana que se abre, tenemos información en diversos apartados de los cuales nos interesan sobre todo 2: Conectividad y seguridad, y Configuración. 

En **Conectividad y seguridad** tenemos, por un lado, la información del **punto de enlace** de la instancia, la cual utilizaremos junto al **puerto** de escucha para formar la URL con la que co- nectarnos a ella. 

![](Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.006.png)

En esa misma pestaña, un poco más a la derecha, en el bloque **Seguridad**, tenemos un enlace a la configuración del grupo de seguridad **RDS-PostreSQL** creado. Haz clic en él y luego en el ID de grupo de seguridad correspondiente para llegar a las reglas de entrada creadas. 

Recordemos que el tráfico entrante a las instancias de AWS queda denegado hasta que no se marque explícitamente lo contrario y que nos interese que cualquier máquina pueda acceder a la instancia. Por lo tanto, tenemos que **editar las reglas de entrada** para conseguir la si- guiente configuración: 

![](Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.007.jpeg)

Es decir, se permite el tráfico entrante desde cualquier origen al puerto 5432, el puerto están- dar de PostgreSQL. 

Si volvemos a la información de la instancia, en la pestaña **Configuración** tenemos informa- ción sobre la **versión del motor** (útil para buscar clientes compatibles) y del usuario maestro. 

PASO 2: IMPORTAR FICHERO SQL EN BASE DE DATOS ![ref1]

Aunque tenemos creada la instancia de la base de datos, tendremos que crear una base de da- tos e importar un fichero SQL para probarla. 

Para ello, resulta muy sencillo utilizar una herramienta de línea de comandos de PostgreSQL, la cuál tendremos que instalar (si no la tienes ya). 

Si no tienes PostgreSQL instalado en **Windows**, el [siguiente enlace ](https://sbp.enterprisedb.com/getfile.jsp?fileid=1259174)nos lleva al instalador de la última versión, la 17.0. Durante la instalación, en la Selección de Componentes puedes omitir el PostgreSQL Server si quieres, pero debe estar marcado Command Line Tools (y te puede ayudar tener también pgAdmin4). 

Abre PowerShell como administrador y ejecuta el siguiente comando para agregar PostreSQL al PATH: 



|[System.Environment]::SetEnvironmentVariable("Path", $env:Path + ";C:\Program |
| - |
|Files\PostgreSQL\17\bin", [System.EnvironmentVariableTarget]::Machine) |

Tras esto, cierra la ventana de Powershell, abre una nueva y ejecuta **psql --version** para com- probar que tienes psql instalado: 

![](Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.009.png)

En **Linux (Ubuntu)**, es más sencillo al poder ejecutar la siguiente secuencia de comandos para actualizar el índice de paquetes e instalar el paquete de PostgresSQL. 



|sudo apt update |
| - |
|sudo apt install postgresql postgresql-contrib |

La sintaxis del comando psql para conectarse a una base de datos remota en PostgreSQL es la siguiente: 

**psql -h punto\_de\_enlace -U usuario\_maestro -f fichero.sql -d base\_de\_datos** 

Al utilizar el parámetro **-f**, se ejecutará el script SQL del fichero. Por otro lado, como no hay ba- ses de datos creadas en la instancia, omite para la ejecución del script el parámetro **-d**. 

![](Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.010.jpeg)

Ahora ya podemos hacer consultas a la base de datos desde psql, aunque en los siguientes pa- sos las haremos desde nuestra aplicación Java: 

![](Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.011.jpeg)

Puedes salir de la consola de PostgreSQL escribiendo \q. PASO 3: CONEXIÓN CON JDBC ![](Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.012.png)

Basándote en los ejemplos proporcionados en GitHub, implementa los métodos necesarios para conectarte a la base de datos Hogwarts desde tu proyecto Java usando JDBC. 

Ten en cuenta que mantener las credenciales de conexión a la vista en el código es una mala práctica, por lo que es recomendable utilizar un fichero de propiedades para ello. 

PASO 4: MODELADO DE ENTIDAD Y CONSULTA ![ref1]

Crea una clase Java que modele la tabla **Asignatura** y, posteriormente, crea un método en una clase **Operaciones** que devuelta un listado de todas las asignaturas. 

PASO 5: OPERACIONES CRUD ![](Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.013.png)

Finalmente, implementa en la clase Operaciones los siguientes métodos: 

1. **Consulta de estudiantes por casa** 

Crea un método que consulte los nombres y apellidos de todos los estudiantes de una casa cuyo nombre pasarás por parámetro. Haz la prueba con Gryffindor. 

2. **Obtener la mascota de un estudiante específico** 

Crea un método que consulte y muestre la mascota de un estudiante cuyo nombre y apellido pases como parámetros. Haz la prueba con Hermione Granger. 

3. **Número de estudiantes por casa** 

Crea un método que consulte y devuelva el número de estudiantes en cada casa de Hogwarts. 

4. **Insertar una nueva asignatura** 

Crea un método que permita insertar una nueva asignatura en la tabla Asignatura. Debes pro- porcionar los valores necesarios, pudiendo utilizar como ejemplo los datos de este módulo  

5. **Modificar el aula de una asignatura** 

Crea un método que permita modificar el aula de una asignatura específica. El método debe tomar como parámetros el id de la asignatura y la nueva aula. Utiliza la asignatura creada y ponle el nombre que quieras al aula. 

6. **Eliminar una asignatura** 

Crea un método que permita eliminar una asignatura de la tabla. Ejecuta este método con la asignatura creada anteriormente. 
6 

[ref1]: Aspose.Words.96be4bc3-f07e-425f-ae2b-4b9b3cd4e9a0.008.png
