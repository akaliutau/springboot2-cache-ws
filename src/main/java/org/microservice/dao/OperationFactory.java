package org.microservice.dao;

import java.time.Instant;
import java.util.List;

import org.hibernate.Session;
import org.microservice.model.Cached;
import org.microservice.model.Pair;

public class OperationFactory {

	public OperationFactory() {
	}

	public Cached searchByKey(Session session, String key) {
		List<Cached> res = session.createQuery("select c from Cached c where c.key = :key", Cached.class)
				.setParameter("key", key).getResultList();
		if (res != null && res.size() > 0) {
			return res.get(0);
		}
		return null;
	}

	public Integer deleteById(Session session, Long id) {
		Integer res = session.createQuery("delete from Cached c where c.id = :id").setParameter("id", id)
				.executeUpdate();
		return res;
	}

	public Long countDead(Session session, Long lifeTime) {
		Long timestamp = Instant.now().toEpochMilli() - lifeTime;
		Long res = session.createQuery("select count(*) from Cached c where c.timestamp < :timestamp", Long.class)
				.setParameter("timestamp", timestamp).getSingleResult();
		return res;
	}

	public Integer deleteDead(Session session, Long lifeTime) {
		Long timestamp = Instant.now().toEpochMilli() - lifeTime;
		Integer res = session.createQuery("delete from Cached c where c.timestamp < :timestamp")
				.setParameter("timestamp", timestamp).executeUpdate();
		return res;

	}

	public Long findOldest(Session session) {
		List<Cached> res = session.createQuery("select c from Cached c order by timestamp asc", Cached.class)
				.setMaxResults(1).getResultList();
		return (res.isEmpty()) ? null : res.get(0).getId();
	}

	public DMLType upsert(Session session, Pair pair) {
		List<Cached> res = session.createQuery("select c from Cached c where c.key = :key", Cached.class)
				.setParameter("key", pair.getKey()).getResultList();
		if (res.isEmpty()) {
			Cached newCached = new Cached(pair.getKey(), pair.getValue());
			session.save(newCached);
		} else {
			Cached cached = res.get(0);
			cached.updateValue(pair.getValue());
			session.update(cached);
		}
		return (res.isEmpty()) ? DMLType.INSERT : DMLType.UPDATE;
	}

}
