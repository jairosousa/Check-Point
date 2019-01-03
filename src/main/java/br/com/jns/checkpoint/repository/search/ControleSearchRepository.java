package br.com.jns.checkpoint.repository.search;

import br.com.jns.checkpoint.domain.Controle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Controle entity.
 */
public interface ControleSearchRepository extends ElasticsearchRepository<Controle, Long> {
}
