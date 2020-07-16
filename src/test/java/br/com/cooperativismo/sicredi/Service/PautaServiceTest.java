package br.com.cooperativismo.sicredi.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import br.com.cooperativismo.sicredi.domain.model.Pauta;
import br.com.cooperativismo.sicredi.domain.reository.PautaRepository;
import br.com.cooperativismo.sicredi.service.PautaService;

@DataJdbcTest
public class PautaServiceTest {

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
		doThrow(Exception.class).when(repository).save(any());
		
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
		doReturn(Optional.empty()).when(repository).deleteById(anyLong());
		
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
}