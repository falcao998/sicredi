package br.com.cooperativismo.sicredi.domain.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
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
@Table(name = "pauta")
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
	
	@ElementCollection
	@CollectionTable(name = "pauta_voto_mapping", 
		      joinColumns = {@JoinColumn(name = "pauta_id", referencedColumnName = "id")})
	@MapKeyColumn(name = "user_id")
	@Column(name = "voto")
	private Map<Integer, String> votos = new HashMap<>();
	
	@SuppressWarnings("unused")
	private Integer countSim;
	
	@SuppressWarnings("unused")
	private Integer countNao;
	
	public Integer getCountSim() {
		return votos.entrySet().stream().filter(e -> e.getValue().equalsIgnoreCase("Sim")).map(Map.Entry::getValue).collect(Collectors.toList()).size();
	}
	
	public Integer getCountNao() {
		return votos.entrySet().stream().filter(e -> e.getValue().equalsIgnoreCase("Não")).map(Map.Entry::getValue).collect(Collectors.toList()).size();
	}
	
	public void iniciarSessao(Optional<Long> minutos) {
		this.inicioSessao = LocalDateTime.now();
		if(minutos.isPresent())
			this.fimSessao = this.inicioSessao.plusMinutes(minutos.get());
		else
			this.fimSessao = this.inicioSessao.plusMinutes(TEMPO_DEFAULT);
	}
}
