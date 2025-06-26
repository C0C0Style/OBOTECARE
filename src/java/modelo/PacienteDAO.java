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

    public Paciente buscar(int id) {
        Paciente p = new Paciente();
        String sql = "SELECT * FROM paciente WHERE id = ?";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setDiagnostico(rs.getString("diagnostico"));
                p.setNumeroDocumento(rs.getString("numeroDocumento"));
                p.setFechaNacimiento(rs.getString("fechaNacimiento"));
                p.setDireccion(rs.getString("direccion"));
                p.setTelefono(rs.getString("telefono"));
                p.setCorreo(rs.getString("correo"));
                p.setHistorial(rs.getString("historial"));
                p.setEstado(rs.getString("estado"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }

    public List<Paciente> listar() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM paciente";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setId(rs.getInt("id"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setDiagnostico(rs.getString("diagnostico"));
                p.setNumeroDocumento(rs.getString("numeroDocumento"));
                p.setFechaNacimiento(rs.getString("fechaNacimiento"));
                p.setDireccion(rs.getString("direccion"));
                p.setTelefono(rs.getString("telefono"));
                p.setCorreo(rs.getString("correo"));
                p.setHistorial(rs.getString("historial"));
                p.setEstado(rs.getString("estado"));
                p.setIdAcudiente(rs.getInt("idAcudiente"));
                p.setParentesco(rs.getString("parentesco"));
                p.setTelefonoContacto(rs.getString("telefonoContacto"));

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public int agregar(Paciente p) {
        String sql = "INSERT INTO paciente (nombres, apellidos, diagnostico, numeroDocumento, fechaNacimiento, direccion, telefono, correo, historial, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidos());
            ps.setString(3, p.getDiagnostico());
            ps.setString(4, p.getNumeroDocumento());
            ps.setString(5, p.getFechaNacimiento());
            ps.setString(6, p.getDireccion());
            ps.setString(7, p.getTelefono());
            ps.setString(8, p.getCorreo());
            ps.setString(9, p.getHistorial());
            ps.setString(10, p.getEstado());

            r = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    public int actualizar(Paciente p) {
        String sql = "UPDATE paciente SET nombres=?, apellidos=?, diagnostico=?, numeroDocumento=?, fechaNacimiento=?, direccion=?, telefono=?, correo=?, historial=?, estado=? WHERE id=?";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidos());
            ps.setString(3, p.getDiagnostico());
            ps.setString(4, p.getNumeroDocumento());
            ps.setString(5, p.getFechaNacimiento());
            ps.setString(6, p.getDireccion());
            ps.setString(7, p.getTelefono());
            ps.setString(8, p.getCorreo());
            ps.setString(9, p.getHistorial());
            ps.setString(10, p.getEstado());
            ps.setInt(11, p.getId());

            r = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM paciente WHERE id = ?";

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Paciente listarId(int id) {
        Paciente p = new Paciente();
        String sql = "SELECT * FROM paciente WHERE id = ?";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setDiagnostico(rs.getString("diagnostico"));
                p.setNumeroDocumento(rs.getString("numerodocumento"));
                p.setFechaNacimiento(rs.getString("fechanacimiento"));
                p.setDireccion(rs.getString("direccion"));
                p.setTelefono(rs.getString("telefono"));
                p.setCorreo(rs.getString("correo"));
                p.setEstado(rs.getString("estado"));
                p.setHistorial(rs.getString("historial"));
                p.setIdAcudiente(rs.getInt("idAcudiente"));
                p.setParentesco(rs.getString("parentesco"));
                p.setTelefonoContacto(rs.getString("telefonoContacto"));

                // Si tiene un idAcudiente válido, se carga
                if (p.getIdAcudiente() > 0) {
                    AcudienteDAO adao = new AcudienteDAO();
                    Acudiente acu = adao.listarId(p.getIdAcudiente());
                    p.setAcudiente(acu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public void asignarAcudiente(int idPaciente, int idAcudiente, String parentesco, String telefonoContacto) {
        String sql = "UPDATE paciente SET idAcudiente = ?, parentesco = ?, telefonoContacto = ? WHERE id = ?";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idAcudiente);
            ps.setString(2, parentesco);
            ps.setString(3, telefonoContacto);
            ps.setInt(4, idPaciente);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarAsignacionAcudiente(int idPaciente) {
        String sql = "UPDATE paciente SET idAcudiente = NULL, parentesco = NULL, telefonoContacto = NULL WHERE id = ?";
        try {
            Connection con = cn.Conexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Paciente> listarPorAcudiente(int idAcudiente) {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM paciente WHERE idAcudiente = ?";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idAcudiente);
            rs = ps.executeQuery();
            while (rs.next()) {
                Paciente p = new Paciente();
                p.setId(rs.getInt("id"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setNumeroDocumento(rs.getString("numeroDocumento"));
                p.setTelefono(rs.getString("telefono"));
                p.setCorreo(rs.getString("correo"));
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

}
