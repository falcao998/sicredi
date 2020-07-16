package br.com.cooperativismo.sicredi.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PautaTest {

	static Validator validator;
	
	@BeforeAll
	static void init() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	@DisplayName("Teste campo nome")
	void whenCampoNomePreenchido_thenRetornarOk() {
		Pauta pauta = new Pauta("Sicredi");
		
		Set<ConstraintViolation<Pauta>> erro = validator.validate(pauta);
		
		assertEquals(0, erro.size());
	}
	
	@Test
	@DisplayName("Teste campo nome")
	void whenCampoNomeNaoPreenchido_thenRetornarErro() {
		Pauta pauta = new Pauta();
		
		Set<ConstraintViolation<Pauta>> erro = validator.validate(pauta);
		
		assertEquals(1, erro.size());
	}
}
