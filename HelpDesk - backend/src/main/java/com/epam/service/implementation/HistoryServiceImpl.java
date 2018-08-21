package com.epam.service.implementation;

import com.epam.entity.History;
import com.epam.repository.HistoryRepository;
import com.epam.service.HistoryService;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public History addHistory(History history) {
        long time = new Date().getTime();
        history.setDate(new Timestamp(time));
        return historyRepository.addHistory(history);
    }

    @Override
    public List<History> getHistoriesByTicketId(Long id) {
        return historyRepository.getHistoriesByTicketId(id);
    }
}
