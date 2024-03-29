package de.othr.sw.mos.artistAgency.repository;

import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinanceRepository extends JpaRepository<FinanceLog, Long> {
    Optional<FinanceLog> findByID(Long financeId);

    List<FinanceLog> findAllByUser_ID(Long userId);
}
