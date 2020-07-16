package br.com.cooperativismo.sicredi.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

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
public class Sessao {
	
	private static final Long TEMPO_DEFAULT = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@NotNull
	@OneToOne
	private Pauta pauta;
	
	@NonNull
	@NotNull
	private LocalDateTime inicioSessao;
	
	@NotNull
	private LocalDateTime fimSessao;
}
