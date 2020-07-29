package br.com.cooperativismo.sicredi.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.cooperativismo.sicredi.domain.model.Pauta;
import br.com.cooperativismo.sicredi.domain.reository.PautaRepository;
import br.com.cooperativismo.sicredi.service.PautaService;

@SpringBootTest
class PautaServiceTest {

	@Autowired
	private PautaService service;
	
	@MockBean
	private PautaRepository repository;
	
	@Test
	@DisplayName("Serivce - FindById - OK")
	void whenFindById_thenReturnOK() {
		doReturn(Optional.of(new Pauta())).when(repository).findById(anyLong());
		
		assertEquals(HttpStatus.OK, service.findById(1L).getStatusCode());
	}
	
	@Test
	@DisplayName("Serivce - FindById - NOT_FOUND")
	void whenFindById_thenReturnNOTFOUND() {
		doReturn(Optional.empty()).when(repository).findById(anyLong());
		
		assertEquals(HttpStatus.NOT_FOUND, service.findById(1L).getStatusCode());
	}
	
	@Test
	@DisplayName("Serivce - Save - CREATED")
	void whenSave_thenReturnCREATED() {
		doReturn(new Pauta()).when(repository).save(any());
		
		assertEquals(HttpStatus.CREATED, service.save(new Pauta()).getStatusCode());
	}
	
	@Test
	@DisplayName("Serivce - Save - BAD_REQUEST")
	void whenSave_thenReturnBADREQUEST() {
		doThrow(ConstraintViolationException.class).when(repository).save(any());
		
		assertEquals(HttpStatus.BAD_REQUEST, service.save(new Pauta()).getStatusCode());
	}
	
	@Test
	@DisplayName("Serivce - Delete - NOT_CONTENT")
	void whenDelete_thenReturnNOCONTENT() {
		doReturn(Optional.of(new Pauta())).when(repository).findById(anyLong());
		doNothing().when(repository).deleteById(anyLong());
		
		assertEquals(HttpStatus.NO_CONTENT, service.delete(1L).getStatusCode());
	}
	
	@Test
	@DisplayName("Serivce - Delete - NOT_FOUND")
	void whenDelete_thenReturnNOTFOUND() {
		doReturn(Optional.empty()).when(repository).findById(anyLong());
		
		assertEquals(HttpStatus.NOT_FOUND, service.delete(1L).getStatusCode());
	}

	@Test
	@DisplayName("Serivce - Update - OK")
	void whenUpdate_thenReturnOK() {
		doReturn(Optional.of(new Pauta())).when(repository).findById(anyLong());
		doReturn(new Pauta()).when(repository).save(any());
		
		assertEquals(HttpStatus.OK, service.update(1L, new Pauta()).getStatusCode());
	}
	
	@Test
	@DisplayName("Serivce - Update - NOT_FOUND")
	void whenUpdate_thenReturnNOTFOUND() {
		doReturn(Optional.empty()).when(repository).findById(anyLong());
		
		assertEquals(HttpStatus.NOT_FOUND, service.update(1L, new Pauta()).getStatusCode());
	}
	
	@Test
	@DisplayName("Serivce - AbrirSessao - OK")
	void whenAbrirSessao_thenReturnOK() {
		Pauta pauta = new Pauta("Teste Sicredi");
		doReturn(Optional.of(pauta)).when(repository).findById(anyLong());
		doReturn(pauta).when(repository).save(any());
		
		assertEquals(HttpStatus.OK, service.abrirSessao(1L, Optional.of(90L)).getStatusCode());
	}
	
	@Test
	@DisplayName("Serivce - AbrirSessao - NOT_FOUND")
	void whenIniciarSessao_thenReturnNOTFOUND() {
		doReturn(Optional.empty()).when(repository).findById(anyLong());
		
		assertEquals(HttpStatus.NOT_FOUND, service.abrirSessao(1L, Optional.of(90L)).getStatusCode());
	}
	
	@Test
	@DisplayName("Serivce - AbrirSessao - BAD_REQUEST")
	void whenAbrirSessao_thenReturnBADREQUEST() {
		Pauta pauta = new Pauta("Teste Sicredi");
		pauta.setInicioSessao(LocalDateTime.now());
		doReturn(Optional.of(pauta)).when(repository).findById(anyLong());
		doReturn(pauta).when(repository).save(any());
		
		assertEquals(HttpStatus.BAD_REQUEST, service.abrirSessao(1L, Optional.of(90L)).getStatusCode());
	}
	
//	@Test
//	@DisplayName("Serivce - Votacao - OK")
//	void whenVotacao_thenReturnOK() {
//		Pauta pauta = new Pauta("Teste Sicredi");
//		pauta.setFimSessao(LocalDateTime.now().plusMinutes(60));
//		doReturn(Optional.of(pauta)).when(repository).findById(anyLong());
//		doReturn(pauta).when(repository).save(any());
//		
//		assertEquals(HttpStatus.OK, service.votacao(1L, "499.224.020-84", "Sim").getStatusCode());
//	}
	
	@Test
	@DisplayName("Serivce - Votacao - BAD_REQUEST")
	void whenVotacao_thenReturnBADREQUEST() {
		Pauta pauta = new Pauta("Teste Sicredi");
		pauta.setFimSessao(LocalDateTime.now().plusMinutes(60));
		doReturn(Optional.of(pauta)).when(repository).findById(anyLong());
		doReturn(pauta).when(repository).save(any());
		
		ResponseEntity<Object> response = service.votacao(1L, "499.224.020-84", "Sim");
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("CPF incorreto", response.getBody());
	}
	
	@Test
	@DisplayName("Serivce - Votaca - NOT_FOUND")
	void whenVotaca_thenReturnNOTFOUND() {
		doReturn(Optional.empty()).when(repository).findById(anyLong());
		
		assertEquals(HttpStatus.NOT_FOUND, service.votacao(1L, "49922402084", "Sim").getStatusCode());
	}
	
	@Test
	@DisplayName("Serivce - Votaca - BAD_REQUEST_1")
	void whenVotaca_thenReturnBADREQUEST_1() {
		Pauta pauta = new Pauta("Teste Sicredi");
		pauta.setFimSessao(LocalDateTime.now().plusMinutes(30));
		pauta.getVotos().put("49922402084", "Não");
		doReturn(Optional.of(pauta)).when(repository).findById(anyLong());
		doReturn(pauta).when(repository).save(any());
		
		ResponseEntity<Object> response = service.votacao(1L, "49922402084", "Sim");
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Associado já votou.", response.getBody());
	}
	
	@Test
	@DisplayName("Serivce - Votaca - BAD_REQUEST_2")
	void whenVotaca_thenReturnBADREQUEST_2() {
		Pauta pauta = new Pauta("Teste Sicredi");
		pauta.setFimSessao(LocalDateTime.now());
		pauta.getVotos().put("49922402084", "Não");
		doReturn(Optional.of(pauta)).when(repository).findById(anyLong());
		doReturn(pauta).when(repository).save(any());
		
		ResponseEntity<Object> response = service.votacao(1L, "49922402084", "Sim");
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Sessão encerrada.", response.getBody());
	}
	
	@Test
	@DisplayName("Serivce - Votaca - BAD_REQUEST_3")
	void whenVotaca_thenReturnBADREQUEST_3() {
		Pauta pauta = new Pauta("Teste Sicredi");
		doReturn(Optional.of(pauta)).when(repository).findById(anyLong());
		doReturn(pauta).when(repository).save(any());
		
		ResponseEntity<Object> response = service.votacao(1L, "49922402084", "Sim");
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Sessão da pauta não iniciada.", response.getBody());
	}
}