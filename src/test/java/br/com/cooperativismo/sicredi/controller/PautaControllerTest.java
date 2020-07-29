package br.com.cooperativismo.sicredi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import org.springframework.test.web.servlet.MvcResult;

import br.com.cooperativismo.sicredi.domain.model.Pauta;
import br.com.cooperativismo.sicredi.domain.reository.PautaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PautaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PautaRepository repository;
	
	Pauta pauta = new Pauta("Sicredi");
	Pauta pauta2 = new Pauta("Sicredi");
	Pauta pauta3 = new Pauta("Sicredi");
	
	@BeforeEach
	void sutup() {
		if(!repository.findById(1L).isPresent())
			repository.save(pauta);
		if(!repository.findById(2L).isPresent())
			repository.save(pauta2);
		if(!repository.findById(3L).isPresent())
			repository.save(pauta3);
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
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("PUT - /pautas - NOT_FOUND")
	void whenUpdate_thenReturnNOTFOUND() throws Exception {
		mockMvc.perform(
				put("/pautas/{id}", 30L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(" { \"nome\" : \"Sicredi_Aprovado\" } "))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("PUT - /pautas/abrirsessao - OK")
	void whenAbrirSessao_thenReturnOK() throws Exception {
		mockMvc.perform(
				put("/pautas/abrisessao/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.param("minutos", "30"))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("PUT - /pautas/abrirsessao - NOT_FOUND")
	void whenIniciarSessao_thenReturnNOTFOUND() throws Exception {
		mockMvc.perform(
				put("/pautas/abrisessao/{id}", 15L)
				.contentType(MediaType.APPLICATION_JSON)
				.param("minutos", "30"))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("PUT - /pautas/abrirsessao - BAD_REQUEST")
	void whenAbrirSessao_thenReturnBADREQUEST() throws Exception {
		pauta2.setInicioSessao(LocalDateTime.now());
		pauta2.setId(2L);
		repository.save(pauta2);
		
		mockMvc.perform(
				put("/pautas/abrisessao/{id}", 2L)
				.contentType(MediaType.APPLICATION_JSON)
				.param("minutos", "30"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("PUT - /pautas/votacao - OK")
	void whenVotacao_thenReturnOK() throws Exception {
		mockMvc.perform(
				put("/pautas/votacao/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", "1")
				.param("voto", "Sim"))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("PUT - /pautas/votacao - NOT_FOUND")
	void whenVotaca_thenReturnNOTFOUND() throws Exception {
		mockMvc.perform(
				put("/pautas/votacao/{id}", 20L)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", "1")
				.param("voto", "Sim"))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("PUT - /pautas/votacao - BAD_REQUEST_1")
	void whenVotaca_thenReturnBADREQUEST_1() throws Exception {
		pauta3.setFimSessao(LocalDateTime.now().plusMinutes(30));
		pauta3.getVotos().put("499.224.020-84", "Não");
		pauta3.setId(3L);
		repository.save(pauta3);
		
		MvcResult result = mockMvc.perform(
				put("/pautas/votacao/{id}", 3L)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", "1")
				.param("voto", "Sim"))
				.andExpect(status().isBadRequest())
				.andReturn();
		
		assertEquals("Associado já votou.", result.getResponse().getContentAsString());
	}
	
	@Test
	@DisplayName("Serivce - Votaca - BAD_REQUEST_2")
	void whenVotaca_thenReturnBADREQUEST_2() throws Exception {
		pauta.setFimSessao(LocalDateTime.now());
		pauta.getVotos().put("499.224.020-84", "Não");
		pauta.setId(4L);
		repository.save(pauta);
		
		MvcResult result = mockMvc.perform(
				put("/pautas/votacao/{id}", 4L)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", "1")
				.param("voto", "Sim"))
				.andExpect(status().isBadRequest())
				.andReturn();
		
		assertEquals("Sessão encerrada.", result.getResponse().getContentAsString());
	}
	
	@Test
	@DisplayName("Serivce - Votaca - BAD_REQUEST_3")
	void whenVotaca_thenReturnBADREQUEST_3() throws Exception {
		pauta2.setId(5L);
		repository.save(pauta2);
		
		MvcResult result = mockMvc.perform(
				put("/pautas/votacao/{id}", 5L)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", "1")
				.param("voto", "Sim"))
				.andExpect(status().isBadRequest())
				.andReturn();
		
		assertEquals("Sessão da pauta não iniciada.", result.getResponse().getContentAsString());
	}
}