package br.com.cooperativismo.sicredi.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface ServicePattern<T, ID> {

	ResponseEntity<List<T>> findAll();
	
	ResponseEntity<Object> findById(ID id);
	
	ResponseEntity<Object> save(T t);
	
	ResponseEntity<Object> delete(ID id);
	
	ResponseEntity<Object> update(ID id, T t);
}
