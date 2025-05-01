package com.br.repository;

import com.br.entity.Conta;
import com.br.entity.Fornecedor;
import com.br.entity.Fornecedor_;
import com.br.filter.ContaFilter;
import jakarta.persistence.EntityManager;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, BigInteger>, JpaSpecificationExecutor<Fornecedor> {

    Fornecedor findByCnpj(String cnpj);

    default Page<Fornecedor> listFornecedorPaged(Pageable pageable,
                                                 String busca,
                                                 EntityManager entityManager) {
        return findAll(getSpecificatonFornecedor(busca), pageable);
    }

    default List<Fornecedor> listFornecedorSorted(Sort sort,
                                             String busca,
                                        EntityManager entityManager) {
        return findAll(getSpecificatonFornecedor(busca), sort);
    }

    default List<Fornecedor> listFornecedor(String busca,
                                            EntityManager entityManager) {
        return findAll(getSpecificatonFornecedor(busca));
    }

    default Specification<Fornecedor> getSpecificatonFornecedor(String busca){
        return new Specification<Fornecedor>() {
            @Override
            public Predicate toPredicate(Root<Fornecedor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (Objects.nonNull(busca) && !busca.isEmpty()) {
                    Predicate name = criteriaBuilder.like(criteriaBuilder.lower(root.get(Fornecedor_.nome)), "%" + busca.toLowerCase() + "%");
                    Predicate legalName = criteriaBuilder.like(criteriaBuilder.lower(root.get(Fornecedor_.razaoSocial)), "%" + busca.toLowerCase() + "%");
                    Predicate cnpj = criteriaBuilder.like(criteriaBuilder.lower(root.get(Fornecedor_.cnpj)), "%" + busca.toLowerCase() + "%");
                    predicates.add(criteriaBuilder.or(name, legalName, cnpj));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}