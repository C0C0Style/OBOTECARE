package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import controlador.Validar;

public class ProfesionalDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int r;

    public Profesional validar(String user, String password) {
    Profesional em = new Profesional();
    String sql = "SELECT * FROM profesional WHERE User = ? AND contraseña = ?";

    try {
        con = cn.Conexion();
        ps = con.prepareStatement(sql);
        ps.setString(1, user);
        ps.setString(2, password);
        rs = ps.executeQuery();

        while (rs.next()) {
            em.setId(rs.getInt("IdEmpleado"));
            em.setUser(rs.getString("User"));
            em.setDni(rs.getString("Dni"));
            em.setNom(rs.getString("Nombres"));
        }

        con.close();
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }

    return em;
}


    //CRUD
   public List<Profesional> listar() {
    String sql = "SELECT * FROM Profesional"; // Asegúrate que el nombre de tabla esté bien
    List<Profesional> lista = new ArrayList<>();

    try {
        con = cn.Conexion();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Profesional p = new Profesional();
            p.setId(rs.getInt("idEmpleado")); // Asegúrate que los campos existen
            p.setDni(rs.getString("Dni"));
            p.setNom(rs.getString("Nombres"));
            p.setTel(rs.getString("Telefono"));
            p.setEstado(rs.getString("Estado"));
            
            lista.add(p);
        }
    } catch (Exception e) {
        System.out.println("Error al listar profesionales:");
        e.printStackTrace();
    }
    return lista;
}
    public int agregar(Profesional em) {
    String sql = "INSERT INTO Profesional (Dni, Nombres, Telefono, Estado, User, contraseña) VALUES (?,?,?,?,?,?)";
    
    try {
        con = cn.Conexion();
        ps = con.prepareStatement(sql);
        ps.setString(1, em.getDni());
        ps.setString(2, em.getNom());
        ps.setString(3, em.getTel());
        ps.setString(4, em.getEstado()); // Debe ser "1" o "2"
        ps.setString(5, em.getUser());
        ps.setString(6, em.getPass());
        
        return ps.executeUpdate();
    } catch (Exception e) {
        System.out.println("Error al agregar profesional: " + e.getMessage());
        return 0;
    } finally {
        // Cerrar recursos
    }
}

    public Profesional listarId(int id) {
        Profesional emp = new Profesional();
        String sql = "select * from profesional where IdEmpleado=" + id;

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                emp.setDni(rs.getString(2));
                emp.setNom(rs.getString(3));
                emp.setTel(rs.getString(4));
                emp.setEstado(rs.getString(5));
                emp.setUser(rs.getString(6));
            }
        } catch (Exception e) {
        }

        return emp;
    }

    public int actualizar(Profesional em) {
    int r = 0;
    String sql = "UPDATE profesional SET Dni=?, Nombres=?, Telefono=?, Estado=?, User=? WHERE IdEmpleado=?";

    try {
        con = cn.Conexion();
        ps = con.prepareStatement(sql);
        ps.setString(1, em.getDni());
        ps.setString(2, em.getNom());
        ps.setString(3, em.getTel());
        ps.setString(4, em.getEstado());
        ps.setString(5, em.getUser());
        ps.setInt(6, em.getId()); // Corregido: usar índice 6 en lugar de 7

        r = ps.executeUpdate(); // Guarda el número de filas actualizadas
        
        if (r > 0) {
            System.out.println("Empleado actualizado correctamente.");
        } else {
            System.out.println("No se actualizó ningún registro.");
        }
        
    } catch (Exception e) {
        System.out.println("Error en la actualización: " + e.getMessage());
    }
    
    return r;
}

    public void delete(int id) {
        String sql = "delete from profesional where IdEmpleado=" + id;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}
