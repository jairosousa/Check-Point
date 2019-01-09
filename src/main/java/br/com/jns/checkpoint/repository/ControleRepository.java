package br.com.jns.checkpoint.repository;

import br.com.jns.checkpoint.domain.Controle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Controle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControleRepository extends JpaRepository<Controle, Long> {

}
