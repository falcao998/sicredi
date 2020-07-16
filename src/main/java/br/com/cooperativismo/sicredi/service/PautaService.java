package br.com.cooperativismo.sicredi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import br.com.cooperativismo.sicredi.domain.model.Pauta;
import br.com.cooperativismo.sicredi.domain.reository.PautaRepository;

public class PautaService implements ServicePattern<Pauta, Long> {

	@Autowired
	private PautaRepository repository;

	@Override
	public ResponseEntity<List<Pauta>> findAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@Override
	public ResponseEntity<Object> findById(Long id) {
		Optional<Pauta> pauta = repository.findById(id);
		if(pauta.isPresent())
			return ResponseEntity.ok(pauta.get());
		else
			return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Object> save(Pauta pauta) {
		try {
			return ResponseEntity.created(null).body(repository.save(pauta));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro para inserir uma nova pauta");
		}
	}

	@Override
	public ResponseEntity<Object> delete(Long id) {
		Optional<Pauta> pauta = repository.findById(id);
		if(pauta.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else
			return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Pauta> update(Long id, Pauta pauta) {
		Optional<Pauta> optional = repository.findById(id);
		if(optional.isPresent()) {
			pauta.setId(id);
			return ResponseEntity.ok(repository.save(pauta));
		} else
			return ResponseEntity.notFound().build();
	}
}