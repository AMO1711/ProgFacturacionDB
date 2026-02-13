package modelo;

import modelo.enums.EstadoFactura;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Facturas")
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_factura", nullable = false, unique = true)
    private Integer numeroFactura;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private EstadoFactura estado;

    @Column(name = "total_base")
    private Double totalBase = 0.0;

    @Column(name = "total_iva")
    private Double totalIva = 0.0;

    @Column(name = "total_factura")
    private Double totalFactura = 0.0;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LineaFactura> lineas = new ArrayList<>();

    public Factura() {
        this.fecha = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EstadoFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }

    public Double getTotalBase() {
        return totalBase;
    }

    public void setTotalBase(Double totalBase) {
        this.totalBase = totalBase;
    }

    public Double getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(Double totalIva) {
        this.totalIva = totalIva;
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public List<LineaFactura> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaFactura> lineas) {
        this.lineas = lineas;
    }
}