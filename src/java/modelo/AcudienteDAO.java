package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AcudienteDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int r;

    public List<Acudiente> listar() {
        List<Acudiente> lista = new ArrayList<>();
        String sql = "SELECT * FROM acudiente";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Acudiente a = new Acudiente();
                a.setId(rs.getInt("id"));
                a.setNombres(rs.getString("nombres"));
                a.setApellidos(rs.getString("apellidos"));
                a.setDocumento(rs.getString("documento"));
                a.setTelefono(rs.getString("telefono"));
                a.setCorreo(rs.getString("correo"));
                lista.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Acudiente buscarPorDocumento(String documento) {
        String sql = "SELECT * FROM acudiente WHERE documento = ?";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, documento);
            rs = ps.executeQuery();

            if (rs.next()) {
                Acudiente a = new Acudiente();
                a.setId(rs.getInt("id"));
                a.setNombres(rs.getString("nombres"));
                a.setApellidos(rs.getString("apellidos"));
                a.setDocumento(rs.getString("documento"));
                a.setTelefono(rs.getString("telefono"));
                a.setCorreo(rs.getString("correo"));
                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Acudiente listarId(int id) {
        Acudiente a = new Acudiente();
        String sql = "SELECT * FROM acudiente WHERE id = ?";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                a.setId(rs.getInt("id"));
                a.setNombres(rs.getString("nombres"));
                a.setApellidos(rs.getString("apellidos"));
                a.setDocumento(rs.getString("documento"));
                a.setTelefono(rs.getString("telefono"));
                a.setCorreo(rs.getString("correo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }

    public int agregar(Acudiente a) {
        String sql = "INSERT INTO acudiente (nombres, apellidos, documento, telefono, correo) VALUES (?, ?, ?, ?, ?)";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, a.getNombres());
            ps.setString(2, a.getApellidos());
            ps.setString(3, a.getDocumento());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getCorreo());
            r = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    public int actualizar(Acudiente a) {
        String sql = "UPDATE acudiente SET nombres=?, apellidos=?, documento=?, telefono=?, correo=? WHERE id=?";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, a.getNombres());
            ps.setString(2, a.getApellidos());
            ps.setString(3, a.getDocumento());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getCorreo());
            ps.setInt(6, a.getId());
            r = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM acudiente WHERE id = ?";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
