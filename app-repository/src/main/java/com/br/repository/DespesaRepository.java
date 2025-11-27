package com.br.repository;

import com.br.entity.*;
import com.br.filter.DespesaFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, BigInteger>, JpaSpecificationExecutor<Despesa> {


    default BigDecimal getSumTotalDespesa(DespesaFilter despesaFilter, EntityManager entityManager) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<Despesa> root = query.from(Despesa.class);

        query.multiselect(cb.sum(root.get(Despesa_.valor)));
        query.where(getSpecificatonDespesa(despesaFilter).toPredicate(root, query, cb));
        Query qry = entityManager.createQuery(query);
        return (BigDecimal) qry.getSingleResult();
    }

    default List getDespesaByTipo(EntityManager entityManager) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<Despesa> root = query.from(Despesa.class);
        query.multiselect(
                root.get(Despesa_.tipoDespesa).get(TipoDespesa_.nome),
                cb.sum(root.get(Despesa_.valor)));
        query.groupBy(root.get(Despesa_.tipoDespesa));
        Query qry = entityManager.createQuery(query);
        return qry.getResultList();
    }

    default Page<Despesa> listDespesaPaged(Pageable pageable,  DespesaFilter despesaFilter) {
        return findAll(getSpecificatonDespesa(despesaFilter), pageable);
    }

    default List<Despesa> listDespesaSorted(Sort sort,  DespesaFilter despesaFilter) {
        return findAll(getSpecificatonDespesa(despesaFilter), sort);
    }

    default Specification<Despesa> getSpecificatonDespesa(DespesaFilter despesaFilter) {
        return new Specification<Despesa>() {
            @Override
            public Predicate toPredicate(Root<Despesa> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (Objects.nonNull(despesaFilter.getId()))
                    predicates.add(criteriaBuilder.equal(root.get(Despesa_.id), despesaFilter.getId()));
                if (Objects.nonNull(despesaFilter.getIdTipoDespesa()))
                    predicates.add(criteriaBuilder.equal(root.get(Despesa_.tipoDespesa).get(TipoDespesa_.id), despesaFilter.getIdTipoDespesa()));
                if (Objects.nonNull(despesaFilter.getIdFornecedor()))
                    predicates.add(criteriaBuilder.equal(root.get(Despesa_.fornecedor).get(Fornecedor_.id), despesaFilter.getIdFornecedor()));
                if (Objects.nonNull(despesaFilter.getDataInicial()) && Objects.nonNull(despesaFilter.getDataFinal()))
                    predicates.add(criteriaBuilder.between(root.get(Despesa_.dataPagamento), despesaFilter.getDataInicial(), despesaFilter.getDataFinal()));
                if (Objects.nonNull(despesaFilter.getDataInicial()))
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Despesa_.dataPagamento), despesaFilter.getDataInicial()));
                if (Objects.nonNull(despesaFilter.getDataFinal()))
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Despesa_.dataPagamento), despesaFilter.getDataFinal()));
                if (Objects.nonNull(despesaFilter.getIdFormaPagamento()))
                    predicates.add(criteriaBuilder.equal(root.get(Despesa_.formaPagamento).get(FormaPagamento_.id), despesaFilter.getIdFormaPagamento()));

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    default List findGastoPorAno(EntityManager entityManager, int ano) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        final Root<Despesa> root = query.from(Despesa.class);
        var monthExpression = cb.function("month", Integer.class, root.get(Despesa_.dataPagamento));
        query.multiselect(monthExpression,cb.sum(root.get(Despesa_.valor)));
        query.where(cb.equal(cb.function("year", Integer.class, root.get(Despesa_.dataPagamento)), ano));
        query.groupBy(monthExpression);
        query.orderBy(cb.asc(monthExpression));
        Query qry = entityManager.createQuery(query);
        return qry.getResultList();
    }


    default List findGastoTotalPorAno(EntityManager entityManager) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        final Root<Despesa> root = query.from(Despesa.class);

        var yearExpression = cb.function("year", Integer.class, root.get(Despesa_.dataPagamento));

        query.multiselect(
                yearExpression,
                cb.sum(root.get(Despesa_.valor))
        );

        query.groupBy(yearExpression);
        query.orderBy(cb.asc(yearExpression));

        Query qry = entityManager.createQuery(query);
        return qry.getResultList();
    }
}