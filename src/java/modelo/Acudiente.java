package modelo;

public class Acudiente {
    private int id;
    private String nombres;
    private String apellidos;
    private String documento;
    private String telefono;
    private String correo;

    public Acudiente() {}

    public Acudiente(int id, String nombres, String apellidos, String documento, String telefono, String correo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.telefono = telefono;
        this.correo = correo;
    }

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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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
}
