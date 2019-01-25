package br.com.jns.checkpoint.web.rest;

import br.com.jns.checkpoint.repository.filter.ControleFilter;
import com.codahale.metrics.annotation.Timed;
import br.com.jns.checkpoint.service.ControleService;
import br.com.jns.checkpoint.web.rest.errors.BadRequestAlertException;
import br.com.jns.checkpoint.web.rest.util.HeaderUtil;
import br.com.jns.checkpoint.web.rest.util.PaginationUtil;
import br.com.jns.checkpoint.service.dto.ControleDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Controle.
 */
@RestController
@RequestMapping("/api")
public class ControleResource {

    private final Logger log = LoggerFactory.getLogger(ControleResource.class);

    private static final String ENTITY_NAME = "controle";

    private final ControleService controleService;

    public ControleResource(ControleService controleService) {
        this.controleService = controleService;
    }

    /**
     * POST  /controles : Create a new controle.
     *
     * @param controleDTO the controleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new controleDTO, or with status 400 (Bad Request) if the controle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/controles")
    @Timed
    public ResponseEntity<ControleDTO> createControle(@Valid @RequestBody ControleDTO controleDTO) throws URISyntaxException {
        log.debug("REST request to save Controle : {}", controleDTO);
        if (controleDTO.getId() != null) {
            throw new BadRequestAlertException("A new controle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControleDTO result = controleService.save(controleDTO);
        return ResponseEntity.created(new URI("/api/controles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /controles : Updates an existing controle.
     *
     * @param controleDTO the controleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated controleDTO,
     * or with status 400 (Bad Request) if the controleDTO is not valid,
     * or with status 500 (Internal Server Error) if the controleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/controles")
    @Timed
    public ResponseEntity<ControleDTO> updateControle(@Valid @RequestBody ControleDTO controleDTO) throws URISyntaxException {
        log.debug("REST request to update Controle : {}", controleDTO);
        if (controleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ControleDTO result = controleService.save(controleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, controleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /controles : get all the controles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of controles in body
     */
//    @GetMapping("/controles")
//    @Timed
//    public ResponseEntity<List<ControleDTO>> getAllControles(Pageable pageable) {
//        log.debug("REST request to get a page of Controles");
//        Page<ControleDTO> page = controleService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/controles");
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }

    @GetMapping("/controles")
    @Timed
    public ResponseEntity<List<ControleDTO>> pesquisar(ControleFilter filter, Pageable pageable) {
        log.debug("REST request to get a page of Controles");
        Page<ControleDTO> page = controleService.filtrar(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/controles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /controles/:id : get the "id" controle.
     *
     * @param id the id of the controleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the controleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/controles/{id}")
    @Timed
    public ResponseEntity<ControleDTO> getControle(@PathVariable Long id) {
        log.debug("REST request to get Controle : {}", id);
        Optional<ControleDTO> controleDTO = controleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(controleDTO);
    }

    /**
     * DELETE  /controles/:id : delete the "id" controle.
     *
     * @param id the id of the controleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/controles/{id}")
    @Timed
    public ResponseEntity<Void> deleteControle(@PathVariable Long id) {
        log.debug("REST request to delete Controle : {}", id);
        controleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/controles?query=:query : search for the controle corresponding
     * to the query.
     *
     * @param query the query of the controle search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/controles")
    @Timed
    public ResponseEntity<List<ControleDTO>> searchControles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Controles for query {}", query);
        Page<ControleDTO> page = controleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/controles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
