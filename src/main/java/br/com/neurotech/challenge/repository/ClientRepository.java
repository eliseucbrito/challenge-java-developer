package br.com.neurotech.challenge.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.neurotech.challenge.entity.NeurotechClient;

@Repository
public interface ClientRepository extends JpaRepository<NeurotechClient, Long> {

  @Query("""
         SELECT c
           FROM NeurotechClient c
          WHERE c.birthDate BETWEEN :minDate AND :maxDate
          AND c.income BETWEEN :minIncome AND :maxIncome
      """)
  List<NeurotechClient> filter(
      @Param("minDate") LocalDate minDate,
      @Param("maxDate") LocalDate maxDate,
      @Param("minIncome") Double minIncome,
      @Param("maxIncome") Double maxIncome);
}
