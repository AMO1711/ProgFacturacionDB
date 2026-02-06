package persistencia;

import modelo.Factura;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class FacturaDAO extends GenericDAO<Factura> {
    public FacturaDAO() {
        super(Factura.class);
    }

    public Integer obtenerUltimoNumeroFactura() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Integer> query = session.createQuery("select max(f.numeroFactura) from Factura f", Integer.class);
            return query.uniqueResult();
        }
    }
}