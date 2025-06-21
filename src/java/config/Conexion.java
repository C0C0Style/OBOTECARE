package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String url = "jdbc:mysql://localhost:3306/bd_obote";
    private static final String user = "root";
    private static final String pass = "";

    public static Connection Conexion() throws SQLException {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error al conectar a la base de datos", e);
        } finally {
            if (conexion != null) {
                System.out.println("Conexión exitosa con la base de datos");
            }
        }
        return conexion;
    }
}
