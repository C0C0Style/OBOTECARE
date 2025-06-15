package modelo;

public class Paciente {
    private int id;
    private String nombres;
    private String apellidos;
    private String diagnostico;
    private String numeroDocumento;
    private String fechaNacimiento;
    private String direccion;
    private String telefono;
    private String correo;
    private String historial;
    private String estado;

    // NUEVOS CAMPOS
    private int idAcudiente;
    private String parentesco;
    private String telefonoContacto;

    // Opcional: para mostrar datos del acudiente asociado
    private Acudiente acudiente;

    public Paciente() {
    }

    public Paciente(int id, String nombres, String apellidos, String diagnostico, String numeroDocumento,
                    String fechaNacimiento, String direccion, String telefono, String correo,
                    String historial, String estado) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.diagnostico = diagnostico;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.historial = historial;
        this.estado = estado;
    }

    // Getters y setters completos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdAcudiente() {
        return idAcudiente;
    }

    public void setIdAcudiente(int idAcudiente) {
        this.idAcudiente = idAcudiente;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Acudiente getAcudiente() {
        return acudiente;
    }

    public void setAcudiente(Acudiente acudiente) {
        this.acudiente = acudiente;
    }
}
