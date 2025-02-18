package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.persist(entity);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("Can't add a smile into DB");
            } finally {
                session.close();
            }
            return entity;
        }
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get a smile details from DB");
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            Query<Smile> getAllSmilesQuery = session.createQuery("from Smile", Smile.class);
            return getAllSmilesQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all smiles from DB");
        }
    }
}
