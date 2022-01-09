package de.othr.sw.mos.artistAgency.service.interfaces;

import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import de.othr.sw.mos.artistAgency.exception.FinanceServiceException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public interface FinanceServiceIF {
    FinanceLog registerFinanceLog(FinanceLog financeLog) throws FinanceServiceException;

    FinanceLog updateFinanceLog(Long financeId, FinanceLog financeLogUpdated) throws FinanceServiceException;

    FinanceLog getFinanceLogById(Long financeLogId) throws FinanceServiceException;

    List<FinanceLog> getFinanceLogByUserId(Long userId);

    List<FinanceLog> getAllFinanceLogs();
}
