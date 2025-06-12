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
    String sql = "SELECT * FROM empleado WHERE User = ? AND contraseña = ?";

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
    public List listar() {
        String sql = "select * from empleado";
        List<Profesional> lista = new ArrayList<>();

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Profesional em = new Profesional();
                em.setId(rs.getInt(1));
                em.setDni(rs.getString(2));
                em.setNom(rs.getString(3));
                em.setTel(rs.getString(4));
                em.setEstado(rs.getString(5));
                em.setUser(rs.getString(6));
                lista.add(em);
            }
        } catch (Exception e) {
        }

        return lista;
    }

    public int agregar(Profesional em) {
        String sql = "insert into empleado(Dni,Nombres,Telefono,Estado,User) values (?,?,?,?,?)";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, em.getDni());
            ps.setString(2, em.getNom());
            ps.setString(3, em.getTel());
            ps.setString(4, em.getEstado());
            ps.setString(5, em.getUser());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }

    public Profesional listarId(int id) {
        Profesional emp = new Profesional();
        String sql = "select * from empleado where IdEmpleado=" + id;

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
    String sql = "UPDATE empleado SET Dni=?, Nombres=?, Telefono=?, Estado=?, User=? WHERE IdEmpleado=?";

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
        String sql = "delete from empleado where IdEmpleado=" + id;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}
