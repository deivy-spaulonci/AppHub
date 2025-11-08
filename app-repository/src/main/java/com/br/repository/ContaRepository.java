package com.br.repository;

import com.br.entity.*;
import com.br.filter.ContaFilter;
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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public interface ContaRepository extends JpaRepository<Conta, BigInteger>, JpaSpecificationExecutor<Conta> {

    default List getContaByTipo(EntityManager entityManager){
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<Conta> root = query.from(Conta.class);

        query.multiselect(root.get(Conta_.tipoConta).get(TipoConta_.nome),cb.sum(root.get(Conta_.valor)));
        query.groupBy(root.get(Conta_.tipoConta));

        Query qry = entityManager.createQuery(query);
        return qry.getResultList();
    }

    default BigDecimal getSumConta(ContaFilter contaFilter, EntityManager entityManager){
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<Conta> root = query.from(Conta.class);

        query.multiselect(cb.sum(root.get("valor")));
        query.where(getSpecificatonConta(contaFilter).toPredicate(root, query, cb));
        Query qry = entityManager.createQuery(query);
        return (BigDecimal) qry.getSingleResult();
    }

    default Page<Conta> listContaPaged(Pageable pageable,  ContaFilter contaFilter) {
        return findAll(getSpecificatonConta(contaFilter), pageable);
    }

    default List<Conta> listContaSorted(Sort sort, ContaFilter contaFilter) {
        return findAll(getSpecificatonConta(contaFilter), sort);
    }

    default Specification<Conta> getSpecificatonConta(ContaFilter contaFilter){
        return new Specification<Conta>() {
            @Override
            public Predicate toPredicate(Root<Conta> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if(contaFilter!=null){
                    if (Objects.nonNull(contaFilter.getId()))
                        predicates.add(criteriaBuilder.equal(root.get(Conta_.ID), contaFilter.getId()));
                    if (Objects.nonNull(contaFilter.getIdTipoConta()))
                        predicates.add(criteriaBuilder.equal(root.get(Conta_.tipoConta).get(TipoConta_.id), contaFilter.getIdTipoConta()));

                    if (Objects.nonNull(contaFilter.getEmissaoInicial()) && Objects.nonNull(contaFilter.getEmissaoFinal()))
                        predicates.add(criteriaBuilder.between(root.get(Conta_.emissao), contaFilter.getEmissaoInicial(), contaFilter.getEmissaoFinal()));
                    if (Objects.nonNull(contaFilter.getEmissaoInicial()))
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Conta_.emissao), contaFilter.getEmissaoInicial()));
                    if (Objects.nonNull(contaFilter.getEmissaoFinal()))
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Conta_.emissao), contaFilter.getEmissaoFinal()));

                    if (Objects.nonNull(contaFilter.getVencimentoInicial()) && Objects.nonNull(contaFilter.getVencimentoFinal()))
                        predicates.add(criteriaBuilder.between(root.get(Conta_.vencimento), contaFilter.getVencimentoInicial(), contaFilter.getVencimentoFinal()));
                    if (Objects.nonNull(contaFilter.getVencimentoInicial()))
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Conta_.vencimento), contaFilter.getVencimentoInicial()));
                    if (Objects.nonNull(contaFilter.getVencimentoFinal()))
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Conta_.vencimento), contaFilter.getVencimentoFinal()));

                    if(Objects.nonNull(contaFilter.getContaStatus())){
                        if(contaFilter.getContaStatus().equals(ContaStatus.PAGO)){
                            predicates.add(criteriaBuilder.isNotNull(root.get(Conta_.formaPagamento)));
                            predicates.add(criteriaBuilder.isNotNull(root.get(Conta_.dataPagamento)));
                        }else{
                            predicates.add(criteriaBuilder.isNull(root.get(Conta_.formaPagamento)));
                            switch (contaFilter.getContaStatus()){
                                case ABERTO -> predicates.add(criteriaBuilder.greaterThan(root.get(Conta_.vencimento), LocalDate.now()));
                                case HOJE -> predicates.add(criteriaBuilder.equal(root.get(Conta_.vencimento), LocalDate.now()));
                                case ATRASADO -> predicates.add(criteriaBuilder.lessThan(root.get(Conta_.vencimento), LocalDate.now()));
                            }
                        }
                    }
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    @org.springframework.data.jpa.repository.Query(value = """
        SELECT
            MONTH(c.DATA_PAGAMENTO) AS mesint,            
            SUM(c.VALOR) AS total
        FROM CONTA c
        WHERE YEAR(c.DATA_PAGAMENTO) = :ano
        GROUP BY mesint
        ORDER BY mesint
        """, nativeQuery = true)
    List findGastoPorAno(@Param("ano") int ano);
}