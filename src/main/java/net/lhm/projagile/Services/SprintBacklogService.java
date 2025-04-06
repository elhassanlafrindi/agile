package net.lhm.projagile.Services;

import net.lhm.projagile.dto.SprintBacklogDTO;
import net.lhm.projagile.entities.SprintBacklog;
import java.util.List;

public interface SprintBacklogService {
    SprintBacklog createSprintBacklog(SprintBacklogDTO sprintBacklogDTO);
    SprintBacklog updateSprintBacklog(Integer id, SprintBacklogDTO sprintBacklogDTO);
    void deleteSprintBacklog(Integer id);
    List<SprintBacklog> getAllSprintBacklogs();
    SprintBacklog getSprintBacklogById(Integer id);
}
