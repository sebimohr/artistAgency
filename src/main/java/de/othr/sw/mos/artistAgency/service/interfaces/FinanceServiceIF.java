package de.othr.sw.mos.artistAgency.service.interfaces;

import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public interface FinanceServiceIF {
    FinanceLog registerFinanceLog(FinanceLog financeLog) throws Exception;

    FinanceLog updateFinanceLog(FinanceLog financeLogUpdated) throws Exception;

    FinanceLog getFinanceLogById(Long financeLogId) throws Exception;

    List<FinanceLog> getFinanceLogByUserId(Long userId);

    List<FinanceLog> getAllFinanceLogs();
}
