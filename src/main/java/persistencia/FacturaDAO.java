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

    public Factura buscarPorNumero(int numero) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Usamos HQL para referirnos a la propiedad del objeto Java
            String hql = "FROM Factura f WHERE f.numeroFactura = :num";
            Query<Factura> query = session.createQuery(hql, Factura.class);
            query.setParameter("num", numero);

            Factura f = query.uniqueResult();

            // IMPORTANTE: Forzamos la carga de las líneas antes de cerrar la sesión
            if (f != null) {
                f.getLineas().size();
            }

            return f;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}