
package modelo; // Asegúrate de que el paquete sea correcto

import config.Conexion; // Tu clase de conexión a la base de datos
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp; // Necesario para convertir LocalDateTime a/desde Timestamp
import java.util.ArrayList;
import java.util.List;

public class RecordatorioDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion(); // Instancia de tu clase de conexión

    // Método para agregar un nuevo recordatorio
    public int agregar(Recordatorio recordatorio) {
        String sql = "INSERT INTO recordatorios (idPaciente, fechaRecordatorio, descripcion, estado, idProfesionalCreador) VALUES (?, ?, ?, ?, ?)";
        int r = 0;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, recordatorio.getIdPaciente());
            ps.setTimestamp(2, Timestamp.valueOf(recordatorio.getFechaRecordatorio())); // Convertir LocalDateTime
            ps.setString(3, recordatorio.getDescripcion());
            ps.setString(4, recordatorio.getEstado());
            // Manejar idProfesionalCreador que puede ser 0 o NULL en la DB
            if (recordatorio.getIdProfesionalCreador() > 0) {
                ps.setInt(5, recordatorio.getIdProfesionalCreador());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER); // Establecer como NULL si no hay profesional
            }
            
            r = ps.executeUpdate();
            System.out.println("Debug RecordatorioDAO: Recordatorio agregado, filas afectadas: " + r);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return r;
    }

    // Método para listar todos los recordatorios (útil para una vista de administración general)
    public List<Recordatorio> listar() {
        List<Recordatorio> lista = new ArrayList<>();
        // LEFT JOIN con paciente y profesional para obtener sus nombres
        String sql = "SELECT r.*, p.Nombres AS nombrePaciente, p.Apellidos AS apellidoPaciente, " +
                     "prof.Nombres AS nombreProfesionalCreador " +
                     "FROM recordatorios r " +
                     "JOIN paciente p ON r.idPaciente = p.id " + // INNER JOIN porque un recordatorio SIEMPRE tiene un paciente
                     "LEFT JOIN profesional prof ON r.idProfesionalCreador = prof.IdEmpleado"; // LEFT JOIN porque el creador puede ser NULL
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Recordatorio rec = new Recordatorio();
                rec.setIdRecordatorio(rs.getInt("idRecordatorio"));
                rec.setIdPaciente(rs.getInt("idPaciente"));
                rec.setFechaRecordatorio(rs.getTimestamp("fechaRecordatorio").toLocalDateTime());
                rec.setDescripcion(rs.getString("descripcion"));
                rec.setEstado(rs.getString("estado"));
                rec.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                rec.setIdProfesionalCreador(rs.getInt("idProfesionalCreador"));

                // Cargar objeto Paciente anidado
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("idPaciente")); 
                paciente.setNombres(rs.getString("nombrePaciente"));
                paciente.setApellidos(rs.getString("apellidoPaciente"));
                rec.setPaciente(paciente);

                // Cargar objeto Profesional anidado si existe
                if (rs.getInt("idProfesionalCreador") > 0) { // Si el ID del profesional es válido
                    Profesional profesional = new Profesional();
                    profesional.setId(rs.getInt("idProfesionalCreador")); // Suponiendo getId() para IdEmpleado
                    profesional.setNom(rs.getString("nombreProfesionalCreador"));
                    rec.setProfesionalCreador(profesional);
                }
                
                lista.add(rec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return lista;
    }

    // Método para listar recordatorios por ID de paciente (muy útil para mostrar recordatorios de un paciente específico)
    public List<Recordatorio> listarPorPaciente(int idPaciente) {
        List<Recordatorio> lista = new ArrayList<>();
        String sql = "SELECT r.*, p.Nombres AS nombrePaciente, p.Apellidos AS apellidoPaciente, " +
                     "prof.Nombres AS nombreProfesionalCreador " +
                     "FROM recordatorios r " +
                     "JOIN paciente p ON r.idPaciente = p.id " +
                     "LEFT JOIN profesional prof ON r.idProfesionalCreador = prof.IdEmpleado " +
                     "WHERE r.idPaciente = ?"; // Filtra por el ID del paciente
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            rs = ps.executeQuery();
            while (rs.next()) {
                Recordatorio rec = new Recordatorio();
                rec.setIdRecordatorio(rs.getInt("idRecordatorio"));
                rec.setIdPaciente(rs.getInt("idPaciente"));
                rec.setFechaRecordatorio(rs.getTimestamp("fechaRecordatorio").toLocalDateTime());
                rec.setDescripcion(rs.getString("descripcion"));
                rec.setEstado(rs.getString("estado"));
                rec.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                rec.setIdProfesionalCreador(rs.getInt("idProfesionalCreador"));

                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("idPaciente")); 
                paciente.setNombres(rs.getString("nombrePaciente"));
                paciente.setApellidos(rs.getString("apellidoPaciente"));
                rec.setPaciente(paciente);

                if (rs.getInt("idProfesionalCreador") > 0) {
                    Profesional profesional = new Profesional();
                    profesional.setId(rs.getInt("idProfesionalCreador"));
                    profesional.setNom(rs.getString("nombreProfesionalCreador"));
                    rec.setProfesionalCreador(profesional);
                }
                lista.add(rec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return lista;
    }

    // Método para obtener un recordatorio por su ID (para edición)
    public Recordatorio listarId(int idRecordatorio) {
        Recordatorio rec = null;
        String sql = "SELECT r.*, p.Nombres AS nombrePaciente, p.Apellidos AS apellidoPaciente, " +
                     "prof.Nombres AS nombreProfesionalCreador " +
                     "FROM recordatorios r " +
                     "JOIN paciente p ON r.idPaciente = p.id " +
                     "LEFT JOIN profesional prof ON r.idProfesionalCreador = prof.IdEmpleado " +
                     "WHERE r.idRecordatorio = ?";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRecordatorio);
            rs = ps.executeQuery();
            if (rs.next()) { // Usar if en lugar de while porque esperamos un solo resultado
                rec = new Recordatorio();
                rec.setIdRecordatorio(rs.getInt("idRecordatorio"));
                rec.setIdPaciente(rs.getInt("idPaciente"));
                rec.setFechaRecordatorio(rs.getTimestamp("fechaRecordatorio").toLocalDateTime());
                rec.setDescripcion(rs.getString("descripcion"));
                rec.setEstado(rs.getString("estado"));
                rec.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                rec.setIdProfesionalCreador(rs.getInt("idProfesionalCreador"));

                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("idPaciente")); 
                paciente.setNombres(rs.getString("nombrePaciente"));
                paciente.setApellidos(rs.getString("apellidoPaciente"));
                rec.setPaciente(paciente);

                if (rs.getInt("idProfesionalCreador") > 0) {
                    Profesional profesional = new Profesional();
                    profesional.setId(rs.getInt("idProfesionalCreador"));
                    profesional.setNom(rs.getString("nombreProfesionalCreador"));
                    rec.setProfesionalCreador(profesional);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rec;
    }

    // Método para actualizar un recordatorio
    public int actualizar(Recordatorio recordatorio) {
        String sql = "UPDATE recordatorios SET idPaciente=?, fechaRecordatorio=?, descripcion=?, estado=?, idProfesionalCreador=? WHERE idRecordatorio=?";
        int r = 0;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, recordatorio.getIdPaciente());
            ps.setTimestamp(2, Timestamp.valueOf(recordatorio.getFechaRecordatorio()));
            ps.setString(3, recordatorio.getDescripcion());
            ps.setString(4, recordatorio.getEstado());
            if (recordatorio.getIdProfesionalCreador() > 0) {
                ps.setInt(5, recordatorio.getIdProfesionalCreador());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setInt(6, recordatorio.getIdRecordatorio());
            r = ps.executeUpdate();
            System.out.println("Debug RecordatorioDAO: Recordatorio actualizado, filas afectadas: " + r);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return r;
    }

    // Método para eliminar un recordatorio
    public int delete(int idRecordatorio) {
        String sql = "DELETE FROM recordatorios WHERE idRecordatorio=?";
        int r = 0;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRecordatorio);
            r = ps.executeUpdate();
            System.out.println("Debug RecordatorioDAO: Recordatorio eliminado, filas afectadas: " + r);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return r;
    }
}