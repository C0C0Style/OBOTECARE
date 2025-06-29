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

    // En tu archivo: PacienteDAO.java

public List<Paciente> listar() {
        List<Paciente> lista = new ArrayList<>();
        
        // Consulta SQL con LEFT JOIN para obtener los datos del profesional
        // Seleccionamos todas las columnas de paciente (p.*)
        // y las columnas específicas de profesional que necesitas
        // Asegúrate de que los nombres de las columnas en prof. coincidan con tu tabla 'profesional'
        String sql = "SELECT p.*, prof.IdEmpleado, prof.Dni, prof.Nombres AS NombreProfesional, " +
                     "prof.Telefono, prof.Estado, prof.User, prof.Contraseña, prof.IdUsuario " +
                     "FROM paciente p " +
                     "LEFT JOIN profesional prof ON p.idProfesional = prof.IdEmpleado"; //

        try {
            con = cn.Conexion(); // Asegúrate de que 'cn' está accesible y funcional
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

                // Asigna el ID de la clave foránea idProfesional directamente del paciente
                p.setIdProfesional(rs.getInt("idProfesional")); 

                // Aquí es donde creamos y asignamos el objeto Profesional
                // Comprobamos si el LEFT JOIN encontró un profesional (IdEmpleado no es NULL/0)
                if (rs.getInt("IdEmpleado") > 0) { 
                Profesional profesional = new Profesional();
                profesional.setId(rs.getInt("IdEmpleado")); 
                profesional.setNom(rs.getString("NombreProfesional")); 
                profesional.setDni(rs.getString("Dni")); 
                profesional.setTel(rs.getString("Telefono")); 
                profesional.setEstado(rs.getString("Estado")); 
                profesional.setUser(rs.getString("User")); 
                profesional.setPass(rs.getString("Contraseña")); 
                // Asegúrate de que este setId() es para IdUsuario si es un campo diferente,
                // o elimínalo si no tienes IdUsuario en Profesional
   

                    p.setProfesional(profesional); 
                } else {
                    p.setProfesional(null); 
                }

                // --- NUEVAS LÍNEAS DE DEPURACIÓN (CRÍTICAS) ---
                if (p.getProfesional() != null) {
                    System.out.println("Debug DAO Listar (Profesional Objeto): Paciente ID: " + p.getId() + 
                                       ", Profesional Objeto ID: " + p.getProfesional().getId() + // <-- VERIFICA ESTE VALOR
                                       ", Profesional Objeto Nombre: " + p.getProfesional().getNom());
                } else {
                    System.out.println("Debug DAO Listar (Profesional Objeto): Paciente ID: " + p.getId() +
                                       ", Profesional NO ASIGNADO (objeto es null).");
                }
                
                // --- LÍNEAS DE DEPURACIÓN (Mantén estas líneas, son muy útiles) ---
                System.out.println("Debug DAO Listar: Paciente ID: " + p.getId() + 
                                   ", idProfesional FK: " + p.getIdProfesional() +
                                   ", Objeto Profesional (nombre): " + (p.getProfesional() != null ? p.getProfesional().getNom() : "NULL / No Asignado"));
                // -----------------------------------------------------------------

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Esto imprimirá cualquier error en la consola del servidor
        } finally {
            // Asegúrate de cerrar tus recursos de la base de datos
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
     public List<Paciente> listarPacientesPorProfesional(int idProfesional) {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT p.*, prof.IdEmpleado, prof.Dni, prof.Nombres AS NombreProfesional, " +
                     "prof.Telefono, prof.Estado, prof.User, prof.Contraseña, prof.IdUsuario " +
                     "FROM paciente p " +
                     "LEFT JOIN profesional prof ON p.idProfesional = prof.IdEmpleado " +
                     "WHERE p.idProfesional = ?"; // <-- ¡Aquí está el filtro!

        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProfesional); // Setea el ID del profesional en la consulta
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
                p.setIdProfesional(rs.getInt("idProfesional")); 

                // Lógica para asignar el objeto Profesional (igual que en listar())
                if (rs.getInt("IdEmpleado") > 0) { 
                    Profesional profesional = new Profesional();
                    profesional.setId(rs.getInt("IdEmpleado")); 
                    profesional.setNom(rs.getString("NombreProfesional")); 
                    profesional.setDni(rs.getString("Dni")); 
                    profesional.setTel(rs.getString("Telefono")); 
                    profesional.setEstado(rs.getString("Estado")); 
                    profesional.setUser(rs.getString("User")); 
                    profesional.setPass(rs.getString("Contraseña")); 
                    

                    p.setProfesional(profesional); 
                } else {
                    p.setProfesional(null); 
                }
                
                lista.add(p);
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
    
     // Nuevo método para asignar profesional
    public int asignarProfesional(int idPaciente, int idProfesional) { // <-- Cambia el retorno a 'int'
    String sql = "UPDATE paciente SET idProfesional = ? WHERE id = ?";
    int r = 0; // <-- Variable para el resultado
    try {
        con = cn.Conexion();
        ps = con.prepareStatement(sql);
        if (idProfesional > 0) {
            ps.setInt(1, idProfesional);
        } else {
            ps.setNull(1, java.sql.Types.INTEGER);
        }
        ps.setInt(2, idPaciente);
        r = ps.executeUpdate(); // <-- Guarda el resultado aquí
        System.out.println("Debug DAO: Filas actualizadas para asignación de profesional: " + r); // <-- Debug
    } catch (Exception e) {
        e.printStackTrace(); // Esto enviará el error a los logs del servidor
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    return r; // <-- Retorna el número de filas afectadas
}

    // Nuevo método para eliminar asignación de profesional
    public void eliminarAsignacionProfesional(int idPaciente) {
        String sql = "UPDATE paciente SET idProfesional = NULL WHERE id = ?";
        try {
            Connection con = cn.Conexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            ps.executeUpdate();
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
    }


}
