package br.com.cooperativismo.sicredi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.cooperativismo.sicredi.domain.model.Pauta;
import br.com.cooperativismo.sicredi.domain.reository.PautaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PautaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PautaRepository repository;
	
	@BeforeEach
	void sutup() {
		if(!repository.findById(1L).isPresent())
			repository.save(new Pauta("Sicredi"));
		if(!repository.findById(2L).isPresent())
			repository.save(new Pauta("Sicredi2"));
		if(!repository.findById(3L).isPresent())
			repository.save(new Pauta("Sicredi3"));
	}
	
	@Test
	@DisplayName("GET - /pautas/{id} - OK")
	void whenFindById_thenReturnOK() throws Exception {
		mockMvc.perform(
				get("/pautas/{id}", 1L))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("GET - /pautas/{id} - NOT_FOUND")
	void whenFindById_thenReturnNOTFOUND() throws Exception {
		mockMvc.perform(
				get("/pautas/{id}", 25L))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("POST - /pautas - CREATED")
	void whenSave_thenReturnCREATED() throws Exception {
		mockMvc.perform(
				post("/pautas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(" { \"nome\" : \"Sicredi2\" } "))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("POST - /pautas - BAD_REQUEST")
	void whenSave_thenReturnBADREQUEST() throws Exception {
		mockMvc.perform(
				post("/pautas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(" { \"nome\" : null } "))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("DELETE - /pautas - NOT_CONTENT")
	void whenDelete_thenReturnNOCONTENT() throws Exception {
		mockMvc.perform(
				delete("/pautas/{id}", 2L))
				.andExpect(status().isNoContent());
	}
	
	@Test
	@DisplayName("DELETE - /pautas - NOT_FOUND")
	void whenDelete_thenReturnNOTFOUND() throws Exception {
		mockMvc.perform(
				delete("/pautas/{id}", 20L))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("PUT - /pautas - OK")
	void whenUpdate_thenReturnOK() throws Exception {
		mockMvc.perform(
				put("/pautas/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(" { \"nome\" : \"Sicredi_Aprovado\" } "))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("PUT - /pautas - NOT_FOUND")
	void whenUpdate_thenReturnNOTFOUND() throws Exception {
		mockMvc.perform(
				put("/pautas/{id}", 30L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(" { \"nome\" : \"Sicredi_Aprovado\" } "));
	}
}