package com.software.service.methodology.factory;

import com.software.domain.methodology.kanban.KanbanBoard;
import com.software.domain.methodology.scrum.Sprint;
import com.software.service.methodology.abstraction.MethodologyService;
import com.software.service.methodology.implementor.KanbanServiceImpl;
import com.software.service.methodology.implementor.ScrumServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MethodologyServiceFactory {
    private final ScrumServiceImpl scrumService;
    private final KanbanServiceImpl kanbanService;

    @Autowired
    public MethodologyServiceFactory(ScrumServiceImpl scrumService, KanbanServiceImpl kanbanService) {
        this.scrumService = scrumService;
        this.kanbanService = kanbanService;
    }

    public MethodologyService<Sprint> getScrumMethodologyService() {
        return new MethodologyService<>(scrumService);
    }

    public MethodologyService<KanbanBoard> getKanbanMethodologyService() {
        return new MethodologyService<>(kanbanService);
    }

}
