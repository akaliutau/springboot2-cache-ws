package org.microservice.dao;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.microservice.model.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HibernateServiceImpl {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	private OperationFactory operationFactory;

	public HibernateServiceImpl() {
		this.operationFactory = new OperationFactory();
	}

	public Object execute(OperationType operationType) {
		Transaction tr = null;
		Object result = null;
		try {
			Session session = getCurrentSession();
			tr = session.beginTransaction();
			if (operationType.equals(OperationType.FIND_OLDEST)) {
				result = operationFactory.findOldest(session);
			}
			tr.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (tr != null && tr.isActive()) {
				tr.rollback();
			}
		}
		return result;
	}

	public Object execute(OperationType operationType, Object arg1) {
		Transaction tr = null;
		Object result = null;
		try {
			Session session = getCurrentSession();
			tr = session.beginTransaction();
			System.out.println(operationType);
			if (operationType.equals(OperationType.SEARCH_BY_KEY)) {
				result = operationFactory.searchByKey(session, (String) arg1);
			} else if (operationType.equals(OperationType.DELETE_BY_ID)) {
				result = operationFactory.deleteById(session, (Long) arg1);
			} else if (operationType.equals(OperationType.UPSERT)) {
				result = operationFactory.upsert(session, (Pair) arg1);
			} else if (operationType.equals(OperationType.COUNT_EXPIRED)) {
				result = operationFactory.countExpired(session, (Long) arg1);
			} else if (operationType.equals(OperationType.DELETE_EXPIRED)) {
				result = operationFactory.deleteExpired(session, (Long) arg1);
			}
			tr.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (tr != null && tr.isActive()) {
				tr.rollback();
			}
		}
		return result;
	}

	public Session getCurrentSession() {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
		return session;
	}

}
