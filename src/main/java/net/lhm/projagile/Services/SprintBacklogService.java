package net.lhm.projagile.Services;

import net.lhm.projagile.dto.request.SprintBacklogDTO;
import net.lhm.projagile.dto.response.SprintBacklogResponseDTO;

import java.util.Date;
import java.util.List;

public interface SprintBacklogService {
    void createSprintBacklog(SprintBacklogDTO sprintBacklogDTO);
    void updateSprintBacklog(long id, SprintBacklogDTO sprintBacklogDTO);
    void deleteSprintBacklog(long id);
    List<SprintBacklogResponseDTO> getAllSprintBacklog();
    SprintBacklogResponseDTO getSprintBacklogById(long id);
    void addUserStoryToSprintBacklog(long id, int[] userStoryIds);
    void deleteUserStoryFromSprintBacklog(long id, int[] userStoryIds);
    void endOfSprintBacklog(long id, Date date);
}