package de.othr.sw.mos.artistAgency.service;

import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import de.othr.sw.mos.artistAgency.exception.FinanceServiceException;
import de.othr.sw.mos.artistAgency.repository.FinanceRepository;
import de.othr.sw.mos.artistAgency.service.interfaces.FinanceServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class FinanceService implements FinanceServiceIF {

    private final FinanceRepository financeRepo;

    @Autowired
    public FinanceService(FinanceRepository financeRepo) {
        this.financeRepo = financeRepo;
    }

    @Override
    @Transactional
    public FinanceLog registerFinanceLog(FinanceLog financeLog) throws FinanceServiceException {
        var foundFinanceLogOptional = financeRepo.findByID(financeLog.getID());

        if(foundFinanceLogOptional.isEmpty()) {
            var newFinanceLog = new FinanceLog();

            newFinanceLog.setUser(financeLog.getUser());
            newFinanceLog.setEvent(financeLog.getEvent());
            if(financeLog.getArtistPaidDate() != null)
                newFinanceLog.setArtistPaidDate(financeLog.getArtistPaidDate());
            if(financeLog.getArtistPaidAmount() != null)
                newFinanceLog.setArtistPaidAmount(financeLog.getArtistPaidAmount());

            return financeRepo.save(newFinanceLog);
        }

        throw new FinanceServiceException("FinanceLog with ID already exists.");
    }

    @Override
    @Transactional
    public FinanceLog updateFinanceLog(Long financeId, FinanceLog financeLogUpdated) throws FinanceServiceException {
        var financeLog = getFinanceLogById(financeId);

        financeLog.setArtistPaidDate(financeLogUpdated.getArtistPaidDate());
        financeLog.setArtistPaidAmount(financeLogUpdated.getArtistPaidAmount());

        return financeLog;
    }

    @Override
    public FinanceLog getFinanceLogById(Long financeLogId) throws FinanceServiceException {
        return financeRepo.findByID(financeLogId).orElseThrow( () ->
                new FinanceServiceException("FinanceLog with id " + financeLogId + " not found!"));
    }

    @Override
    public List<FinanceLog> getFinanceLogByUserId(Long userId) {
        return financeRepo.findAllByUser_ID(userId);
    }

    @Override
    public List<FinanceLog> getAllFinanceLogs() {
        return financeRepo.findAll();
    }
}
