package com.software.service.methodology.abstraction;

import com.software.service.methodology.DevelopmentImplementation;
import com.software.service.methodology.DevelopmentMethodology;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class MethodologyService<T> implements DevelopmentMethodology<T> {

    private final DevelopmentImplementation<T> implementation;

    @Override
    public T save(Long projectId, T arrangement) {
        log.info("Saving arrangement");
        var savedArrangement = implementation.saveEntity(projectId, arrangement);
        log.info("Saved arrangement");
        return savedArrangement;
    }

    @Override
    public List<T> getAllArrangements(Long projectId) {
        log.info("Fetching all arrangements");
        var arrangements = implementation.findAllEntities(projectId);
        log.info("Fetched arrangements");
        return arrangements;
    }

    @Override
    public T getArrangementById(Long projectId, Long arrangementId) {
        log.info("Fetching arrangement with ID: {} for project: {}", arrangementId, projectId);
        var arrangement = implementation.getByIdEntity(projectId, arrangementId);
        log.info("Fetched arrangement");
        return arrangement;
    }

    @Override
    public void deleteArrangement(Long projectId, Long arrangementId) {
        log.info("Deleting arrangement with ID: {}", arrangementId);
        implementation.deleteEntity(projectId, arrangementId);
        log.info("Deleted arrangement with ID: {}", arrangementId);
    }
}
