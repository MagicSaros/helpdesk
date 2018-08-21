package com.epam.repository;

import com.epam.entity.History;
import java.util.List;

public interface HistoryRepository {

    History addHistory(History history);

    List<History> getHistoriesByTicketId(Long id);
}
