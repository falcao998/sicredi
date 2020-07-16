package br.com.cooperativismo.sicredi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cooperativismo.sicredi.domain.model.Pauta;
import br.com.cooperativismo.sicredi.service.PautaService;

@RestController
@RequestMapping("/pautas")
public class PautaController {
	
	@Autowired
	private PautaService service;
	
	@GetMapping
	public ResponseEntity<List<Pauta>> findAllPauta() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findByIdPauta(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<Object> savePauta(@Valid @RequestBody Pauta pauta) {
		return service.save(pauta);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePauta(@PathVariable Long id) {
		return service.delete(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pauta> deletePauta(@PathVariable Long id, @Valid @RequestBody Pauta pauta) {
		return service.update(id, pauta);
	}

}