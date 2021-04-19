package com.wench.prometheus.data.expression;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpressionRepository extends JpaRepository<Expression, Long> {

    @Query(
            value = "SELECT * FROM expressions WHERE username=?1",
            nativeQuery = true
    )
    List<Expression> getCalculationByUserName(String username);
}
