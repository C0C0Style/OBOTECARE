
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.*;
import config.Conexion;

/**
 *
 * @author User
 */
public class VentaDAO {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;

    public String GenerarSerie() {
        String numeroserie = "";
        String sql = "select max(NumeroSerie) from ventas";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                numeroserie = rs.getString(1);

            }
        } catch (Exception e) {
        }
        return numeroserie;
    }

    public String IdVentas() {
        String idventas = "";
        String sql = "select max(IdVentas) from ventas";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                idventas = rs.getString(1);

            }
        } catch (Exception e) {
        }
        return idventas;
    }
    
    public int  guardarVenta(Venta ve){
        String sql = "insert into ventas(IdCliente,IdEmpleado,NumeroSerie,FechaVentas,Monto,Estado)values(?,?,?,?,?,?)";
        try {
           con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, ve.getIdcliente());
            ps.setInt(2, ve.getIdempleado());
            ps.setString(3, ve.getNumserie());
            ps.setString(4, ve.getFecha());
            ps.setDouble(5, ve.getMonto());
            ps.setString(6, ve.getEstado());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("aaaaaaaaaa"  + e.getMessage());
        }
        return r;
    }
    
    public int guardarDetalleventa(Venta ve){
        String sql = "insert into detalle_ventas(IdVentas,IdProducto,Cantidad,PrecioVenta)values(?,?,?,?)";
        try {
           con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, ve.getId());
            ps.setInt(2, ve.getIdproducto());
            ps.setInt(3, ve.getCantidad());
            ps.setDouble(4, ve.getPrecio());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }
}
