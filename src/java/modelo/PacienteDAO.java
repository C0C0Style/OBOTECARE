package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int r;

    public Paciente buscar(int id){
        Paciente p = new Paciente();
       String sql = "select * from producto where idproducto =" + id; 
       
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                p.setId(rs.getInt(1));
                p.setDescripcion(rs.getString(2));
                p.setPrecio(rs.getDouble(3));
                p.setStock(rs.getInt(4));
                p.setEstado(rs.getString(5));
            }
        } catch (Exception e) {
        }
        return p;
    }
    
    public int actualizarstock(int id, int stock){
        String sql = "update producto set Stock = ? where idproducto = ?";
        try {
           con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, stock);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }
    public List listar() {
        String sql = "select * from producto";
        List<Paciente> lista = new ArrayList<>();

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Paciente pd = new Paciente();
                pd.setId(rs.getInt(1));
                pd.setDescripcion(rs.getString(2));
                pd.setPrecio(rs.getDouble(3));
                pd.setStock(rs.getInt(4));
                pd.setEstado(rs.getString(5));
                lista.add(pd);
            }
        } catch (Exception e) {
        }

        return lista;
    }

   public int agregar(Paciente pd) {
    int r = 0; // Inicializa r en 0
    String sql = "INSERT INTO producto (Nombres, Precio, Stock, Estado) VALUES (?, ?, ?, ?)";

    try {
        con = cn.Conexion();
        ps = con.prepareStatement(sql);
        ps.setString(1, pd.getDescripcion()); // ¿El campo en la BD es 'Nombres' y en Java 'Descripcion'?
        ps.setDouble(2, pd.getPrecio());
        ps.setInt(3, pd.getStock());
        ps.setString(4, pd.getEstado());

        r = ps.executeUpdate(); // Guarda cuántas filas fueron afectadas

        if (r > 0) {
            System.out.println("Producto agregado correctamente.");
        } else {
            System.out.println("No se insertó el producto.");
        }

    } catch (Exception e) {
        System.out.println("Error al agregar producto: " + e.getMessage());
    }

    return r; // Retorna si la inserción fue exitosa o no
}


    public Paciente listarId(int id) {
        Paciente prod = new Paciente();
        String sql = "select * from producto where IdProducto=" + id;

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                prod.setId(rs.getInt(1));
                prod.setDescripcion(rs.getString(2));
                prod.setPrecio(rs.getDouble(3));
                prod.setStock(rs.getInt(4));
                prod.setEstado(rs.getString(5));
            }
        } catch (Exception e) {
        }

        return prod;
    }

   public int actualizar(Paciente pr) {
    String sql = "UPDATE producto SET Nombres=?, Precio=?, Stock=?, Estado=? WHERE IdProducto=?";
    int r = 0;

    try {
        con = cn.Conexion(); // Asumiendo que cn es tu clase de conexión
        ps = con.prepareStatement(sql);
        ps.setString(1, pr.getDescripcion());
        ps.setDouble(2, pr.getPrecio());
        ps.setInt(3, pr.getStock());
        ps.setString(4, pr.getEstado());
        ps.setInt(5, pr.getId());

        r = ps.executeUpdate(); // Asigna el número de filas afectadas

    } catch (Exception e) {
        e.printStackTrace(); // Imprime el error en la consola para depuración
    } 
            return r; 
    }
   



    public void delete(int id) {
        String sql = "delete from producto where IdProducto=" + id;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}
