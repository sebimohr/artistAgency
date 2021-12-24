package de.othr.sw.mos.artistAgency.service;

import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import de.othr.sw.mos.artistAgency.repository.FinanceRepository;
import de.othr.sw.mos.artistAgency.service.interfaces.FinanceServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceService implements FinanceServiceIF {

    private final FinanceRepository financeRepo;

    @Autowired
    public FinanceService(FinanceRepository financeRepo) {
        this.financeRepo = financeRepo;
    }

    @Override
    public FinanceLog registerFinanceLog(FinanceLog financeLog) throws Exception {
        var foundFinanceLogOptional = financeRepo.findByFinanceId(financeLog.getFinanceId());

        if(foundFinanceLogOptional.isEmpty()) {
            var newFinanceLog = new FinanceLog(financeLog.getFinanceId());

            newFinanceLog.setUsername(financeLog.getUsername());
            if(financeLog.getArtistPaidDate() != null)
                newFinanceLog.setArtistPaidDate(financeLog.getArtistPaidDate());
            if(financeLog.getArtistPaidAmount() != null)
                newFinanceLog.setArtistPaidAmount(financeLog.getArtistPaidAmount());

            return financeRepo.save(newFinanceLog);
        }

        // TODO: throw own exception
        throw new Exception("FinanceLog with ID already exists.");
    }

    @Override
    public FinanceLog getFinanceLogById(Long financeLogId) throws Exception {
        return financeRepo.findByFinanceId(financeLogId).orElseThrow( () ->
                new Exception("FinanceLog with id " + financeLogId + " not found!"));
    }

    @Override
    public List<FinanceLog> getFinanceLogByUsername(String username) {
        return financeRepo.findAllByUsername(username);
    }
}
