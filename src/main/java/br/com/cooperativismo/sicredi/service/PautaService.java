package br.com.cooperativismo.sicredi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cooperativismo.sicredi.domain.model.Pauta;
import br.com.cooperativismo.sicredi.domain.reository.PautaRepository;

@Service
public class PautaService implements ServicePattern<Pauta, Long> {

	@Autowired
	private PautaRepository repository;
	
	private static final ResponseEntity<Object> NOT_FOUND = new ResponseEntity<Object>("Pauta não encontrada.", HttpStatus.NOT_FOUND);

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
			return NOT_FOUND;
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
			return NOT_FOUND;
	}

	@Override
	public ResponseEntity<Object> update(Long id, Pauta pauta) {
		Optional<Pauta> optional = repository.findById(id);
		if(optional.isPresent()) {
			pauta.setId(id);
			return ResponseEntity.ok(repository.save(pauta));
		} else
			return NOT_FOUND;
	}

	public ResponseEntity<Object> abrirSessao(Long id, Optional<Long> minutos) {
		Pauta pauta = repository.findById(id).orElse(null);
		if(pauta != null) {
			if(pauta.getInicioSessao() == null) {
				pauta.iniciarSessao(minutos);
				return ResponseEntity.ok(repository.save(pauta));
			} else {
				return ResponseEntity.badRequest().body("Sessão da pauta já iniciada.");
			}
		} else
			return NOT_FOUND;
	}

	public ResponseEntity<Object> votacao(Long id, int userId, String voto) {
		Pauta pauta = repository.findById(id).orElse(null);
		if(pauta != null) {
			if(pauta.getInicioSessao() != null) {
				pauta.getVotos().put(userId, voto);
				return ResponseEntity.ok(repository.save(pauta));
			} else {
				return ResponseEntity.badRequest().body("Sessão da pauta já iniciada.");
			}
		} else
			return NOT_FOUND;
	}
}
