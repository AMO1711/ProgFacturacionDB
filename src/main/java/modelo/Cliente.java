package modelo;

import modelo.enums.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Clientes")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 9)
    private String nif;

    @Column(nullable = false, length = 100)
    private String nombre;

    private String direccion;
    private String poblacion;

    @Enumerated(EnumType.STRING)
    private Provincia provincia;

    @Column(length = 5)
    private String codigoPostal;

    private String telefonoFijo;
    private String telefonoMovil;
    private String email;
    private String web;

    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    private Double limiteCredito;

    @Column(length = 20)
    private String cuentaBancaria;

    private Boolean activo = true;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagen;

    public Cliente() {}

    public Cliente(Long id, String nif, String nombre, String direccion, String poblacion,
                   Provincia provincia, String codigoPostal, String telefonoFijo, String telefonoMovil,
                   String email, String web, FormaPago formaPago, Double limiteCredito, String cuentaBancaria,
                   Boolean activo, String observaciones, byte[] imagen) {
        this.id = id;
        this.nif = nif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
        this.telefonoFijo = telefonoFijo;
        this.telefonoMovil = telefonoMovil;
        this.email = email;
        this.web = web;
        this.formaPago = formaPago;
        this.limiteCredito = limiteCredito;
        this.cuentaBancaria = cuentaBancaria;
        this.activo = activo;
        this.observaciones = observaciones;
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(Double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    // Aquí irían los métodos de validación que comentamos:
    // validarDNI() y validarCuenta()
}