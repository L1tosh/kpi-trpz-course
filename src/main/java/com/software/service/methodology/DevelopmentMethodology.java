package com.software.service.methodology;

import java.util.List;

public interface DevelopmentMethodology<T> {
    T save(Long projectId, T arrangement);
    List<T> getAllArrangements(Long projectId);
    T getArrangementById(Long projectId, Long arrangementId);
    void deleteArrangement(Long projectId, Long arrangementId);
}
