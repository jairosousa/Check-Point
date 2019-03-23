package br.com.jns.checkpoint.repository.controle;

import br.com.jns.checkpoint.domain.Controle;
import br.com.jns.checkpoint.domain.Controle_;
import br.com.jns.checkpoint.repository.filter.ControleFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ControleRepositoryImpl implements ControleRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Controle> filtrar(ControleFilter filter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Controle> criteria = builder.createQuery(Controle.class);
        Root<Controle> root = criteria.from(Controle.class);

//        Criar Restriçoes
        Predicate[] predicates = criarRestricoes(filter, builder, root);

        criteria.where(predicates);

        criteria.orderBy(builder.desc(root.get(Controle_.data)));

        TypedQuery<Controle> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private Long total(ControleFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Controle> root = criteria.from(Controle.class);

        Predicate[] predicates = criarRestricoes(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Controle> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalregistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalregistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalregistrosPorPagina);
    }

    private Predicate[] criarRestricoes(ControleFilter filter, CriteriaBuilder builder, Root<Controle> root) {
        List<Predicate> predicates = new ArrayList<>();

        System.out.println(filter.toString());

        if (!StringUtils.isEmpty(filter.getDataVencimentoDe())) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Controle_.data),
                filter.getDataVencimentoDe()));
            if (StringUtils.isEmpty(filter.getDataVencimentoAte())) {
                predicates.add(builder.lessThanOrEqualTo(root.get(Controle_.data),
                    filter.getDataVencimentoDe()));
            }
        }
        if (!StringUtils.isEmpty(filter.getDataVencimentoAte())) {
            predicates.add(builder.lessThanOrEqualTo(root.get(Controle_.data),
                filter.getDataVencimentoAte()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    @Override
    public List<Controle> findGerarPdf(ControleFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Controle> criteria = builder.createQuery(Controle.class);
        Root<Controle> root = criteria.from(Controle.class);

//        Criar Restriçoes
        Predicate[] predicates = criarRestricoes(filter, builder, root);

        criteria.where(predicates);

        criteria.orderBy(builder.desc(root.get(Controle_.data)));

        TypedQuery<Controle> query = manager.createQuery(criteria);

        return query.getResultList();
    }

}
