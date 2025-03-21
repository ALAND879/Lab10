/**
 * Clase abstracta que representa una persona en el sistema de biblioteca.
 * Sirve como base para representar a los usuarios persona del sistema
 * 
 * @author Roberto Salazar Márquez 
 * @version 1.0
 */

public abstract class Persona {
    /** Nombre de la persona */
    private String nombre;
    /** Identificador único de la persona */
    private String id;
    /** Correo electrónico de contacto */
    private String email;
    /** Número telefónico de contacto */
    private String telefono;

    /**
     * Constructor de la clase Persona.
     * @param nombre Nombre de la persona
     * @param id Identificador único de la persona
     */
    public Persona(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
        this.email = "usuario@servidor.com";
        this.telefono = "0000000000";
    }

    
    /**
     * Obtiene el nombre de la persona.
     * @return nombre de la persona
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el ID de la persona.
     * @return identificador único de la persona
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el correo electrónico de la persona.
     * @param email nuevo correo electrónico
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Obtiene el email de la persona.
     * @return identificador único de la persona
     */
    public String getEmail() {
        return new String(this.email);
    }
    
    /**
     * Establece el número telefónico de la persona.
     * @param telefono nuevo número telefónico
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    /**
     * Obtiene el telefono de la persona.
     * @return identificador único de la persona
     */
    public String getTelefono() {
        return new String(this.telefono);
    }
    
    /**
     * Genera una representación en texto de la persona.
     * @return String con la información completa de la persona
     */
    public String toString() {
        return "Nombre: " + nombre + " ID: " + id + "\nEmail: " + email + "\nTeléfono: " + telefono + "\nTipo: " + obtenerTipo();
    }


    // Método abstracto que deberán implementar las clases hijas
    public abstract String obtenerTipo();
}