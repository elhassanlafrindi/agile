package net.lhm.projagile.Services;

import jakarta.transaction.Transactional;
import net.lhm.projagile.Repositories.SprintBacklogRepo;
import net.lhm.projagile.dto.SprintBacklogDTO;
import net.lhm.projagile.entities.SprintBacklog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintBacklogServiceImpl implements SprintBacklogService {
    private final SprintBacklogRepo sprintBacklogRepo;

    public SprintBacklogServiceImpl(SprintBacklogRepo sprintBacklogRepo) {
        this.sprintBacklogRepo = sprintBacklogRepo;
    }

    @Override
    @Transactional
    public SprintBacklog createSprintBacklog(SprintBacklogDTO sprintBacklogDTO) {
        return sprintBacklogRepo.save(SprintBacklog.builder()
                .title(sprintBacklogDTO.getTitle())
                .build());
    }

    @Override
    public SprintBacklog updateSprintBacklog(Integer id, SprintBacklogDTO sprintBacklogDTO) {
        Optional<SprintBacklog> sprintBacklogOptional = sprintBacklogRepo.findById(id);
        if (sprintBacklogOptional.isPresent()) {
            SprintBacklog sprintBacklog = sprintBacklogOptional.get();
            sprintBacklog.setTitle(sprintBacklogDTO.getTitle());
            return sprintBacklogRepo.save(sprintBacklog);
        }
        throw new RuntimeException("SprintBacklog non trouvé !");
    }

    @Override
    public void deleteSprintBacklog(Integer id) {
        if (!sprintBacklogRepo.existsById(id)) {
            throw new RuntimeException("SprintBacklog non trouvé !");
        }
        sprintBacklogRepo.deleteById(id);
    }

    @Override
    public List<SprintBacklog> getAllSprintBacklogs() {
        return sprintBacklogRepo.findAll();
    }

    @Override
    public SprintBacklog getSprintBacklogById(Integer id) {
        return sprintBacklogRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("SprintBacklog non trouvé !"));
    }
}
