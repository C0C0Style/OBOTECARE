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
    
    public Acudiente buscar(String dni){
        Acudiente cl = new Acudiente();
        String sql = "select * from cliente where Dni="+dni;
        
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                cl.setId(rs.getInt(1));
                cl.setDni(rs.getString(2));
                cl.setNombres(rs.getString(3));
                cl.setDireccion(rs.getString(4));
                cl.setEstado(rs.getString(5));
            }
        } catch (Exception e) {
        }
        
        return cl;
    }

    public List listar() {
        String sql = "select * from cliente";
        List<Acudiente> lista = new ArrayList<>();

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Acudiente cl = new Acudiente();
                cl.setId(rs.getInt(1));
                cl.setDni(rs.getString(2));
                cl.setNombres(rs.getString(3));
                cl.setDireccion(rs.getString(4));
                cl.setEstado(rs.getString(5));
                lista.add(cl);
            }
        } catch (Exception e) {
        }

        return lista;
    }

    public int agregar(Acudiente cl) {
        String sql = "insert into cliente(Dni,Nombres,Direccion,Estado) values (?,?,?,?)";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, cl.getDni());
            ps.setString(2, cl.getNombres());
            ps.setString(3, cl.getDireccion());
            ps.setString(4, cl.getEstado());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }

    public Acudiente listarId(int id) {
        Acudiente cli = new Acudiente();
        String sql = "select * from cliente where IdCliente=" + id;

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                cli.setDni(rs.getString(2));
                cli.setNombres(rs.getString(3));
                cli.setDireccion(rs.getString(4));
                cli.setEstado(rs.getString(5));
            }
        } catch (Exception e) {
        }

        return cli;
    }

    public int actualizar(Acudiente cl) {
        String sql = "update cliente set Dni=?,Nombres=?,Direccion=?,Estado=? where IdCliente=?";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, cl.getDni());
            ps.setString(2, cl.getNombres());
            ps.setString(3, cl.getDireccion());
            ps.setString(4, cl.getEstado());
            ps.setInt(5, cl.getId());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }

    public void delete(int id) {
        String sql = "delete from cliente where IdCliente=" + id;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}
