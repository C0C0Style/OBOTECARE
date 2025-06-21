package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.util.Base64;

public class UsuarioDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int r;

    public Usuario validar(String user, String passwordInput) {
        Usuario u = new Usuario();
        String sql = "SELECT * FROM usuario WHERE User = ?";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, user);
            rs = ps.executeQuery();

            if (rs.next()) {
                String passwordBD = rs.getString("Contraseña");
                String passwordCifrada = cifrar(passwordInput);

                // Validar si la contraseña coincide en texto plano o cifrada
                if (passwordInput.equals(passwordBD) || passwordCifrada.equals(passwordBD)) {
                    u.setId(rs.getInt("IdUsuario"));
                    u.setUser(rs.getString("User"));
                    u.setPass(passwordBD);
                    u.setDni(rs.getString("Dni"));
                    u.setNom(rs.getString("Nombre"));
                    u.setRol(rs.getString("Rol"));
                    u.setCorreo(rs.getString("Correo"));
                    u.setEstado(rs.getString("Estado"));
                }
            }

            con.close();
        } catch (Exception e) {
            System.out.println("Error en validación: " + e.getMessage());
        }

        return u;
    }

    private String cifrar(String textoClaro) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(textoClaro.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            System.out.println("Error al encriptar: " + e.getMessage());
            return null;
        }
    }

    // Listar todos los usuarios
    public List<Usuario> listar() {
        String sql = "SELECT * FROM usuario";
        List<Usuario> lista = new ArrayList<>();

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("IdUsuario"));
                u.setDni(rs.getString("Dni"));
                u.setNom(rs.getString("Nombre"));
                u.setRol(rs.getString("Rol"));
                u.setCorreo(rs.getString("Correo"));
                u.setEstado(rs.getString("Estado"));
                u.setUser(rs.getString("User"));
                u.setPass(rs.getString("Contraseña")); // Incluido en caso de que quieras usarlo
                lista.add(u);
            }
        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        }

        return lista;
    }

    // Agregar nuevo usuario
    public int agregar(Usuario u) {
        String sql = "INSERT INTO usuario(Dni, Nombre, Rol, Correo, Estado, User, Contraseña) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getDni());
            ps.setString(2, u.getNom());
            ps.setString(3, u.getRol());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getEstado());
            ps.setString(6, u.getUser());
            ps.setString(7, u.getPass());
            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al agregar: " + e.getMessage());
        }

        return r;
    }

    // Listar usuario por ID
    public Usuario listarId(int id) {
        Usuario u = new Usuario();
        String sql = "SELECT * FROM usuario WHERE IdUsuario = " + id;

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                u.setId(rs.getInt("IdUsuario"));
                u.setDni(rs.getString("Dni"));
                u.setNom(rs.getString("Nombre"));
                u.setRol(rs.getString("Rol"));
                u.setCorreo(rs.getString("Correo"));
                u.setEstado(rs.getString("Estado"));
                u.setUser(rs.getString("User"));
                u.setPass(rs.getString("Contraseña")); // importante para conservarla si no se edita
            }
        } catch (Exception e) {
            System.out.println("Error al buscar ID: " + e.getMessage());
        }

        return u;
    }

    // Actualizar usuario
    public int actualizar(Usuario u) {
        String sql = "UPDATE usuario SET Dni=?, Nombre=?, Rol=?, Correo=?, Estado=?, User=?, Contraseña=? WHERE IdUsuario=?";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getDni());
            ps.setString(2, u.getNom());
            ps.setString(3, u.getRol());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getEstado());
            ps.setString(6, u.getUser());
            ps.setString(7, u.getPass()); // ✅ aseguramos actualización
            ps.setInt(8, u.getId());

            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }

        return r;
    }

    // Eliminar usuario
    public void delete(int id) {
        String sql = "DELETE FROM usuario WHERE IdUsuario = " + id;

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
}
