package br.com.jns.checkpoint.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ControleSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ControleSearchRepositoryMockConfiguration {

    @MockBean
    private ControleSearchRepository mockControleSearchRepository;

}
