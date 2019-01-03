package br.com.jns.checkpoint.service.impl;

import br.com.jns.checkpoint.service.ControleService;
import br.com.jns.checkpoint.domain.Controle;
import br.com.jns.checkpoint.repository.ControleRepository;
import br.com.jns.checkpoint.repository.search.ControleSearchRepository;
import br.com.jns.checkpoint.service.dto.ControleDTO;
import br.com.jns.checkpoint.service.mapper.ControleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Controle.
 */
@Service
@Transactional
public class ControleServiceImpl implements ControleService {

    private final Logger log = LoggerFactory.getLogger(ControleServiceImpl.class);

    private final ControleRepository controleRepository;

    private final ControleMapper controleMapper;

    private final ControleSearchRepository controleSearchRepository;

    public ControleServiceImpl(ControleRepository controleRepository, ControleMapper controleMapper, ControleSearchRepository controleSearchRepository) {
        this.controleRepository = controleRepository;
        this.controleMapper = controleMapper;
        this.controleSearchRepository = controleSearchRepository;
    }

    /**
     * Save a controle.
     *
     * @param controleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ControleDTO save(ControleDTO controleDTO) {
        log.debug("Request to save Controle : {}", controleDTO);

        Controle controle = controleMapper.toEntity(controleDTO);
        controle = controleRepository.save(controle);
        ControleDTO result = controleMapper.toDto(controle);
        controleSearchRepository.save(controle);
        return result;
    }

    /**
     * Get all the controles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ControleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Controles");
        return controleRepository.findAll(pageable)
            .map(controleMapper::toDto);
    }


    /**
     * Get one controle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ControleDTO> findOne(Long id) {
        log.debug("Request to get Controle : {}", id);
        return controleRepository.findById(id)
            .map(controleMapper::toDto);
    }

    /**
     * Delete the controle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Controle : {}", id);
        controleRepository.deleteById(id);
        controleSearchRepository.deleteById(id);
    }

    /**
     * Search for the controle corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ControleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Controles for query {}", query);
        return controleSearchRepository.search(queryStringQuery(query), pageable)
            .map(controleMapper::toDto);
    }
}
