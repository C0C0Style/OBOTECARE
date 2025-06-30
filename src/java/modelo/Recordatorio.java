
package modelo; // Asegúrate de que el paquete sea correcto

import java.time.LocalDateTime; // Para manejar fechas y horas modernas

public class Recordatorio {
    private int idRecordatorio;
    private int idPaciente;
    private LocalDateTime fechaRecordatorio; 
    private String descripcion;
    private String estado;
    private LocalDateTime fechaCreacion;
    private int idProfesionalCreador; 

    // Puedes agregar objetos Paciente y Profesional para cargar la información completa
    // Esto es útil para mostrar, por ejemplo, el nombre del paciente o del profesional que creó el recordatorio
    private Paciente paciente; 
    private Profesional profesionalCreador; 

    public Recordatorio() {
        // Constructor vacío
    }

    // Constructor para cuando se va a agregar un nuevo recordatorio (ID y fechaCreacion son auto-generados)
    public Recordatorio(int idPaciente, LocalDateTime fechaRecordatorio, String descripcion, String estado, int idProfesionalCreador) {
        this.idPaciente = idPaciente;
        this.fechaRecordatorio = fechaRecordatorio;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idProfesionalCreador = idProfesionalCreador;
    }

    // --- Getters y Setters ---

    public int getIdRecordatorio() {
        return idRecordatorio;
    }

    public void setIdRecordatorio(int idRecordatorio) {
        this.idRecordatorio = idRecordatorio;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public LocalDateTime getFechaRecordatorio() {
        return fechaRecordatorio;
    }

    public void setFechaRecordatorio(LocalDateTime fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdProfesionalCreador() {
        return idProfesionalCreador;
    }

    public void setIdProfesionalCreador(int idProfesionalCreador) {
        this.idProfesionalCreador = idProfesionalCreador;
    }

    // Getters y Setters para los objetos anidados (útiles para la JSP)
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profesional getProfesionalCreador() {
        return profesionalCreador;
    }

    public void setProfesionalCreador(Profesional profesionalCreador) {
        this.profesionalCreador = profesionalCreador;
    }
}