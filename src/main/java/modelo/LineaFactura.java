package modelo;

import javax.persistence.*;
import java.io.Serializable;
import modelo.enums.TipoIVA;

@Entity
@Table(name = "LineasFactura")
public class LineaFactura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "articulo_id", nullable = false)
    private Articulo articulo;

    private Integer cantidad;

    @Column(name = "precio_venta_unitario")
    private Double precioVentaUnitario;

    @Column(name = "iva_aplicado")
    @Enumerated(EnumType.STRING)
    private TipoIVA ivaAplicado;

    public LineaFactura() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getPrecioVentaUnitario() { return precioVentaUnitario; }
    public void setPrecioVentaUnitario(Double precioVentaUnitario) { this.precioVentaUnitario = precioVentaUnitario; }

    public TipoIVA getIvaAplicado() { return ivaAplicado; }
    public void setIvaAplicado(TipoIVA ivaAplicado) { this.ivaAplicado = ivaAplicado; }
}