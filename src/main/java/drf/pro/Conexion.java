package drf.pro;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    private static Connection conexion = null;

    static {
        Properties props = new Properties();
        try (InputStream input = Conexion.class.getClassLoader().getResourceAsStream("credenciales.properties")) {
            if (input == null) {
                System.out.println("No se encontró el archivo de propiedades.");

            }else {
                props.load(input);
                String url = props.getProperty("url");
                String user = props.getProperty("user");
                String password = props.getProperty("password");

                conexion = DriverManager.getConnection(url, user, password);
                System.out.println("Conexión establecida");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConexion() {
        return conexion;
    }
}
