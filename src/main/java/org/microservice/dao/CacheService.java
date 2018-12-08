package org.microservice.dao;

import org.microservice.configuration.CacheSettings;
import org.microservice.model.Cached;
import org.microservice.model.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Main service class
 * @author Alex Kalutov
 * @since Version 1.0
 */
@Component
public class CacheService {
	public static Boolean DMLProcessing = false;

	public final long scanRate = 120000L;

	@Autowired
	private HibernateServiceImpl hibernateService;

	@Autowired
	private CacheSettings cacheSettings;

	private Integer numberOfKeys = 0;

	/**
	 * Used to perform the search by key
	 * @param key
	 * @return Pair
	 */
	public Pair retrievePair(String key) {
		Cached found = (Cached) hibernateService.execute(OperationType.SEARCH_BY_KEY, key);
		Pair pair;
		if (found != null) {
			pair = new Pair(found.getKey(), found.getValue());
		} else {
			pair = new Pair(key);
		}
		return pair;
	}

	/**
	 * Adds the pair key=>value to the cache, updates counters
	 * If the number of saved keys exceeds max number, remove the oldest pair
	 * @param pair
	 * @return true
	 */
	public Boolean addPair(Pair pair) {
		DMLType result = (DMLType) hibernateService.execute(OperationType.UPSERT, pair);
		if (result.equals(DMLType.INSERT)) {
			if (numberOfKeys >= cacheSettings.getMaxNumberOfKeys()) {
				// find and delete the oldest key
				Long listToDelete = (Long) hibernateService.execute(OperationType.FIND_OLDEST);
				if (listToDelete != null) {
					hibernateService.execute(OperationType.DELETE_BY_ID, (Long) listToDelete);
				}
			} else {
				incNumberKeys(1);
			}
		}
		return true;
	}

	/**
	 * Scheduled method, executed every {@code scanRate} ms 
	 * First, count the number of records with expired timestamp field
	 * Next, delete such records if needed
	 */
	@Scheduled(fixedRate = scanRate)
	public void recordsCollector() {
		// added to avoid thread conflict
		if (DMLProcessing)
			return;
		DMLProcessing = true;
		try {
			Long counted = (Long) hibernateService.execute(OperationType.COUNT_EXPIRED, cacheSettings.getLifeTime());
			if (counted > 0) {
				Integer deleted = (Integer) hibernateService.execute(OperationType.DELETE_EXPIRED,
						cacheSettings.getLifeTime());
				decNumberKeys(deleted);
			}
		} catch (Exception e) {

		}
		DMLProcessing = false;

	}

	public synchronized void incNumberKeys(Integer i) {
		numberOfKeys += i;
	}

	public synchronized void decNumberKeys(Integer i) {
		numberOfKeys -= i;
		if (numberOfKeys < 0) {
			numberOfKeys = 0;
		}
	}

}
