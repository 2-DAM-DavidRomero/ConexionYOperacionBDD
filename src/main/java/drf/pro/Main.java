package drf.pro;

public class Main {
    public static void main(String[] args) {
        Asignatura AD = new Asignatura("Acceso a datos","2DAM",true);

        Conexion.getConexion();

        Operaciones.volcarAsignaturas();

        System.out.println("\nConsulta de estudiantes por casa: ");
        Operaciones.consultaEstudiantesPorCasa("Gryffindor");

        System.out.println("\nObtener la mascota de un estudiante específico: ");
        Operaciones.encontrarMacota("Hermione","Granger");

        System.out.println("\nNúmero de estudiantes por casa: ");
        Operaciones.numeroDeEstudiantesEnCadaCasa();

        System.out.println("\nInsertar una nueva asignatura: ");
        Operaciones.insertarNuevaAsignatura(AD);

        System.out.println("\nModificar el aula de una asignatura: ");
        AD.setAula("Sala de los menesteres");
        Operaciones.modificarAulaDeAsignatura(AD);

        System.out.println("\nEliminar una asignatura: ");
        Operaciones.elimiarAsignatura(AD);
    }
}