package br.com.jns.checkpoint.service;

import br.com.jns.checkpoint.repository.filter.ControleFilter;
import br.com.jns.checkpoint.service.dto.ControleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Controle.
 */
public interface ControleService {

    /**
     * Save a controle.
     *
     * @param controleDTO the entity to save
     * @return the persisted entity
     */
    ControleDTO save(ControleDTO controleDTO);

    /**
     * Get all the controles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ControleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" controle.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ControleDTO> findOne(Long id);

    /**
     * Delete the "id" controle.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the controle corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ControleDTO> search(String query, Pageable pageable);

    Page<ControleDTO> filtrar(ControleFilter filter, Pageable pageable);

    List<ControleDTO> findControle(ControleFilter filter);
}
