package com.wench.prometheus.data.expression;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpressionRepository extends CrudRepository<Expression, Long> {
}
