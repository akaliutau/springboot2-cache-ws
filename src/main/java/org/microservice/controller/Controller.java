package org.microservice.controller;

import org.microservice.dao.CacheService;
import org.microservice.model.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

/**
 * This is a web service class which will be exposed for rest service. The
 * annotation @RequestMapping is used at method level for Rest Web Service URL
 * mapping.
 * 
 * @author Aliaksei Kaliutau
 * @since Version 1.0
 */
@RestController
public class Controller {

	@Autowired
	private CacheService cacheService;

	/**
	 * The method to get the pair (key=>value) from cache
	 *
	 * @param key
	 *            - the key of pair
	 * @return Pair
	 */
	@RequestMapping(value = "/{key}", method = RequestMethod.GET)
	public ResponseEntity<Pair> retrievePair(@PathVariable String key) {
		Pair pair = cacheService.retrievePair(key);
		ResponseEntity<Pair> response = (pair.getValue() == null) ? new ResponseEntity<Pair>(pair, HttpStatus.NOT_FOUND)
				: new ResponseEntity<Pair>(pair, HttpStatus.OK);
		return response;
	}

	/**
	 * The method to add the pair (key=>value) to cache
	 * 
	 * @param pair
	 * @return Pair
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Pair> addPair(@RequestBody Pair pair) {

		Boolean success = cacheService.addPair(pair);

		ResponseEntity<Pair> response = (success) ? new ResponseEntity<Pair>(pair, HttpStatus.OK)
				: new ResponseEntity<Pair>(pair, HttpStatus.BAD_REQUEST);

		return response;
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleError(Exception e) {
		String error = (e == null) ? "Unknown error" : ("Error: " + e.getMessage());
		return new ResponseEntity<String>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}