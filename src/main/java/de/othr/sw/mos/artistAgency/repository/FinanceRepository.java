package de.othr.sw.mos.artistAgency.repository;

import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepository extends JpaRepository<FinanceLog, Long> {
}
