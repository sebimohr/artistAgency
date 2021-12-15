package de.othr.sw.mos.artistAgency.repository;

import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import org.springframework.data.repository.CrudRepository;

public interface FinanceRepository extends CrudRepository<FinanceLog, Long> {
}
