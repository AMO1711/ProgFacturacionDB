package modelo;

import modelo.enums.TipoIVA;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Articulos")
public class Articulo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String familia;
    private String categoria;
    private String unidadVenta;
    private Double precioCoste;
    private Double precioVenta;

    @Enumerated(EnumType.STRING)
    private TipoIVA ivaPercent;

    private Integer stockActual;
    private Integer stockMinimo;
    private String codigoBarras;
    private String proveedor;
    private Boolean activo = true;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagen;

    public Articulo() {}

    public Articulo(Long id, String codigo, String nombre, String descripcion, String familia,
                    String categoria, String unidadVenta, Double precioCoste, Double precioVenta,
                    TipoIVA ivaPercent, Integer stockActual, Integer stockMinimo, String codigoBarras,
                    String proveedor, Boolean activo, byte[] imagen) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.familia = familia;
        this.categoria = categoria;
        this.unidadVenta = unidadVenta;
        this.precioCoste = precioCoste;
        this.precioVenta = precioVenta;
        this.ivaPercent = ivaPercent;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.codigoBarras = codigoBarras;
        this.proveedor = proveedor;
        this.activo = activo;
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUnidadVenta() {
        return unidadVenta;
    }

    public void setUnidadVenta(String unidadVenta) {
        this.unidadVenta = unidadVenta;
    }

    public Double getPrecioCoste() {
        return precioCoste;
    }

    public void setPrecioCoste(Double precioCoste) {
        this.precioCoste = precioCoste;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public TipoIVA getIvaPercent() {
        return ivaPercent;
    }

    public void setIvaPercent(TipoIVA ivaPercent) {
        this.ivaPercent = ivaPercent;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}