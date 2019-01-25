package br.com.jns.checkpoint.repository.controle;

import br.com.jns.checkpoint.domain.Controle;
import br.com.jns.checkpoint.repository.filter.ControleFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ControleRepositoryQuery {
    public Page<Controle> filtrar(ControleFilter filter, Pageable pageable);
}
