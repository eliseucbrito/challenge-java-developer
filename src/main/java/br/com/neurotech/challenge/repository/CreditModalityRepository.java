package br.com.neurotech.challenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.neurotech.challenge.entity.CreditModality.CreditModality;
import br.com.neurotech.challenge.entity.CreditModality.CreditType;

@Repository
public interface CreditModalityRepository extends JpaRepository<CreditModality, Long> {

  @Query("""
          SELECT cr
            FROM CreditModality cr
           WHERE (:creditType IS NULL OR cr.creditType = :creditType)
             AND (:minIncome IS NULL OR cr.minIncome >= :minIncome)
             AND (:maxIncome IS NULL OR cr.maxIncome <= :maxIncome OR cr.maxIncome IS NULL)
             AND (:minAge    IS NULL OR cr.minAge >= :minAge)
             AND (:maxAge    IS NULL OR cr.maxAge <= :maxAge OR cr.maxAge IS NULL)
      """)
  List<CreditModality> filter(
      @Param("creditType") CreditType creditType,
      @Param("minIncome") Double minIncome,
      @Param("maxIncome") Double maxIncome,
      @Param("minAge") Integer minAge,
      @Param("maxAge") Integer maxAge);

}
