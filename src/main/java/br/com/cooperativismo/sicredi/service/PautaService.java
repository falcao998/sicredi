package br.com.cooperativismo.sicredi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.cooperativismo.sicredi.domain.model.Pauta;
import br.com.cooperativismo.sicredi.domain.reository.PautaRepository;

@Service
public class PautaService implements ServicePattern<Pauta, Long> {

	@Autowired
	private PautaRepository repository;
	
	RestTemplate restTemplate = new RestTemplate();
	
	private static final ResponseEntity<Object> NOT_FOUND = new ResponseEntity<>("Pauta não encontrada.", HttpStatus.NOT_FOUND);

	@Override
	public ResponseEntity<List<Pauta>> findAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@Override
	public ResponseEntity<Object> findById(Long id) {
		try {
			return ResponseEntity.ok(repository.findById(id).orElseThrow());
		} catch (NoSuchElementException e) {
			return NOT_FOUND;
		}
	}

	@Override
	public ResponseEntity<Object> save(Pauta pauta) {
		try {
			return new ResponseEntity<>(repository.save(pauta), HttpStatus.CREATED);
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

	public ResponseEntity<Object> votacao(Long id, String userCPF, String voto) {
		Pauta pauta = repository.findById(id).orElse(null);
		if(pauta != null) {
			if (pauta.getFimSessao() != null) {
				if (pauta.getFimSessao().isAfter(LocalDateTime.now())) {
					if(pauta.getVotos().containsKey(userCPF))
						return ResponseEntity.badRequest().body("Associado já votou.");
					else {
						ResponseEntity<String> response = restTemplate.getForEntity("https://user-info.herokuapp.com/users/"+userCPF, String.class);
						System.out.println(response);
						pauta.getVotos().put(userCPF, voto);
						return ResponseEntity.ok(repository.save(pauta));
					}
				} else
				    return ResponseEntity.badRequest().body("Sessão encerrada.");
			} else {
				return ResponseEntity.badRequest().body("Sessão da pauta não iniciada.");
			}
		} else
		    return NOT_FOUND;
	}
}
