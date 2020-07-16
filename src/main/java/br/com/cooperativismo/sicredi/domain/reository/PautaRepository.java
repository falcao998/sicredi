package br.com.cooperativismo.sicredi.domain.reository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cooperativismo.sicredi.domain.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long>{

}
