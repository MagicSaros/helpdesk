package com.epam.service;

import com.epam.entity.History;
import java.util.List;

public interface HistoryService {

    History addHistory(History history);

    List<History> getHistoriesByTicketId(Long id);
}
