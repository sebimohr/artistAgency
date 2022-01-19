package de.othr.sw.mos.artistAgency.service.interfaces;

import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import de.othr.sw.mos.artistAgency.exception.FinanceServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FinanceServiceIF {
    void registerFinanceLog(FinanceLog financeLog) throws FinanceServiceException;

    void updateFinanceLog(Long financeId, FinanceLog financeLogUpdated) throws FinanceServiceException;

    FinanceLog getFinanceLogById(Long financeLogId) throws FinanceServiceException;

    List<FinanceLog> getFinanceLogByUserId(Long userId);

    List<FinanceLog> getAllFinanceLogs();
}
