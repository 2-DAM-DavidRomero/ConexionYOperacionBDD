package drf.pro;

import java.sql.*;
import java.util.ArrayList;

public class Operaciones {
    private static Connection conexion;
    private static ArrayList<Asignatura> listaAsignaturas = new ArrayList<>();

    //PASO 4: MODELADO DE ENTIDAD Y CONSULTA
    public static void volcarAsignaturas() {
        conexion = Conexion.getConexion();
        try(Statement st = conexion.createStatement()) {
            ResultSet consulta = st.executeQuery("SELECT * FROM asignatura");

            while (consulta.next()){
                Asignatura insercion = new Asignatura(
                        consulta.getInt("id_asignatura"),
                        consulta.getString("nombre_asignatura"),
                        consulta.getString("aula"),
                        consulta.getBoolean("obligatoria"));
                listaAsignaturas.add(insercion);
                //System.out.println(insercion);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    //Utilidad para encontrar los id que sean necesarios para los otros metodos
    private static int sacarId(String tabla, String columnaBusqueda, String valorBusqueda, String columnaRetorno) {
        String consulta = String.format(
                "SELECT %s FROM %s WHERE %s = '%s'",
                columnaRetorno, tabla, columnaBusqueda, valorBusqueda
        );

        try (Statement st = conexion.createStatement()) {
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                return rs.getInt(columnaRetorno);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    //1. Consulta de estudiantes por casa
    public static void consultaEstudiantesPorCasa(String casa){
        conexion = Conexion.getConexion();
        int idCasa = sacarId("casa","nombre_casa",casa,"id_casa");

        if (idCasa != 0) {
            try (Statement st = conexion.createStatement()) {
                String consulta = "SELECT nombre, apellido FROM estudiante WHERE id_casa='" + idCasa + "'";
                ResultSet rs = st.executeQuery(consulta);
                System.out.println(casa);

                while (rs.next()) {
                    String alumno = String.format("Nombre: %s %s", rs.getString("nombre"), rs.getString("apellido"));
                    System.out.println(alumno);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else
            System.out.println("No se a encontrado la casa");
    }

    //2. Obtener la mascota de un estudiante específico
    public static void encontrarMacota(String nombre, String apellido){
        int idEstudiante = sacarId("estudiante", "nombre", nombre, "id_estudiante");

        if (idEstudiante != 0) {
            try (Statement st = conexion.createStatement()) {
                String consulta = "SELECT nombre_mascota FROM mascota WHERE id_estudiante='" + idEstudiante + "'";
                ResultSet rs = st.executeQuery(consulta);

                System.out.println(nombre+" "+ apellido);
                while (rs.next()) {
                    String alumno = String.format("Nombre: %s", rs.getString("nombre_mascota"));
                    System.out.println(alumno);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else
            System.out.println("No se a encontrado estudiante");

    }

    //3. Número de estudiantes por casa
    public static void numeroDeEstudiantesEnCadaCasa(){
        try (Statement st = conexion.createStatement()) {
            String consulta =
                    "SELECT casa.nombre_casa, count(id_estudiante) " +
                    "From estudiante " +
                    "JOIN casa ON casa.id_casa = estudiante.id_casa " +
                    "GROUP BY casa.nombre_casa";
            ResultSet rs = st.executeQuery(consulta);

            while (rs.next()){
                System.out.println(rs.getString("nombre_casa")+":"+rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //4. Insertar una nueva asignatura
    public static void insertarNuevaAsignatura(Asignatura asignatura){
        try (Statement st = conexion.createStatement()) {
            String consulta = String.format(
                    "INSERT INTO Asignatura (nombre_asignatura, aula, obligatoria) " +
                    "VALUES ('%s', '%s', %b)",
                    asignatura.getNombre_asignatura(), asignatura.getAula(), asignatura.getObligatoria()
            );
            int rs = st.executeUpdate(consulta);

            if (rs==1){
                System.out.println("Asignatura añadida con éxito");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //5. Modificar el aula de una asignatura
    public static void modificarAulaDeAsignatura(Asignatura asignatura){
        int idAsignatura= sacarId("Asignatura", "nombre_asignatura", asignatura.getNombre_asignatura(), "id_asignatura");

        if (idAsignatura!=0) {
            try (Statement st = conexion.createStatement()) {

                String consulta = String.format(
                        "UPDATE Asignatura " +
                                "SET aula = '%s' " +
                                "WHERE id_asignatura = %d",
                        asignatura.getAula(), idAsignatura
                );

                int rs = st.executeUpdate(consulta);
                if (rs == 1) {
                    System.out.println("Aula modificada con éxito");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("La asignatura no existe");
        }
    }

    //6. Eliminar una asignatura
    public static void elimiarAsignatura(Asignatura asignatura){
        int idAsignatura = sacarId("Asignatura", "nombre_asignatura", asignatura.getNombre_asignatura(), "id_asignatura");
        if (idAsignatura!=0) {
            try (Statement st = conexion.createStatement()) {
                String consulta = String.format(
                        "DELETE FROM Asignatura WHERE id_asignatura = %d",
                        idAsignatura
                );

                int rs = st.executeUpdate(consulta);
                if (rs == 1) {
                    System.out.println("Asignatura eliminada con éxito");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else
            System.out.println("La asignatura no existe");
    }
}