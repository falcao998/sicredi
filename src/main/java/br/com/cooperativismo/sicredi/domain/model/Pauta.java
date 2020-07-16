package br.com.cooperativismo.sicredi.domain.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Pauta {
	
	private static final long TEMPO_DEFAULT = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@NonNull
	private String nome;
	
	private LocalDateTime inicioSessao;
	
	private LocalDateTime fimSessao;
	
	private Map<Long, String> votos = new HashMap<Long, String>();
	
	public void iniciarSessao(Optional<Long> minutos) {
		this.inicioSessao = LocalDateTime.now();
		if(minutos.isPresent())
			this.fimSessao = this.inicioSessao.plusMinutes(minutos.get());
		else
			this.fimSessao = this.inicioSessao.plusMinutes(TEMPO_DEFAULT);
	}
}
