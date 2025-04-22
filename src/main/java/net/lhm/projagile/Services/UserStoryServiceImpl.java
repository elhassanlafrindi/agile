package net.lhm.projagile.Services;

import jakarta.transaction.Transactional;
import net.lhm.projagile.Repositories.UserStoryRepo;
import net.lhm.projagile.dto.*;
import net.lhm.projagile.dtoResponse.UserStoryDTORes;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.UserStory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserStoryServiceImpl implements UserStoryService {
    private final UserStoryRepo userStoryRepo;

    public UserStoryServiceImpl(UserStoryRepo userStoryRepo) {
        this.userStoryRepo = userStoryRepo;
    }

    @Override
    @Transactional
    public UserStory createUserStory(UserStoryDTO userStory) {
        return userStoryRepo.save(new UserStory().builder().title(userStory.getTitle())
                .description(userStory.getDescription())
                .statut(userStory.getStatut())
                 .priorite(userStory.getPriorite())
                .build());
    }

    @Override
    public UserStory updateUserStory(Integer id, UserStoryDTO userStorydto) {
        Optional<UserStory> userStoryOptional = userStoryRepo.findById(id);
        if (userStoryOptional.isPresent()) {
            UserStory userStory = userStoryOptional.get();
            userStory.setTitle(userStorydto.getTitle());
            userStory.setDescription(userStorydto.getDescription());
            userStory.setPriorite(userStorydto.getPriorite());
            userStory.setStatut(userStorydto.getStatut());

            return userStoryRepo.save(userStory);
        }
        throw new RuntimeException("UserStory non trouvée !");
    }

    @Override
    public void deleteUserStory(Integer id) {
        if (!userStoryRepo.existsById(id)) {
            throw new RuntimeException("UserStory non trouvée !");
        }
        userStoryRepo.deleteById(id);
    }
    @Override
    public List<UserStoryDTORes> getAllUserStories() {
        List<UserStory> userStories = userStoryRepo.findAll();
        List<UserStoryDTORes> userStoryDTOResList = userStories.stream().map(this::convertToDTORes).collect(Collectors.toList());

        return userStoryDTOResList;
    }


    @Override
    public UserStoryDTORes getUserStoryById(Integer id) {
        UserStory us = userStoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("UserStory non trouvée !"));
        return convertToDTORes(us);
    }

    @Override
    public List<UserStoryDTORes> getByStatus(Statut statut) {
        List<UserStory> userStories = userStoryRepo.findByStatut(statut);
        return userStories.stream().map(this::convertToDTORes).collect(Collectors.toList());
    }

    @Override
    public void prioriserUserStory(int iduserstory, int priority) {
        if (priority < 1 || priority > 10) {
            throw new IllegalArgumentException("La priorité doit être entre 1 et 10.");
        }

        UserStory userStory = userStoryRepo.findById(iduserstory)
                .orElseThrow(() -> new RuntimeException("UserStory non trouvée !"));

        userStory.setPriorite(priority);
        userStoryRepo.save(userStory);
    }

    private UserStoryDTORes convertToDTORes(UserStory us) {
        List<EpicDTO> epicDTOs = us.getEpics().stream().map(epic -> EpicDTO.builder()
                .id(epic.getId())
                .titre(epic.getTitre())
                .description(epic.getDescription())
                .build()
        ).toList();

        UserStoryDTORes dto = new UserStoryDTORes();
        dto.setId(us.getId());
        dto.setTitle(us.getTitle());
        dto.setDescription(us.getDescription());
        dto.setPriorite(us.getPriorite());
        dto.setStatut(us.getStatut());
        dto.setEpics(epicDTOs);

        if (us.getProductBacklog() != null) {
            dto.setProductBacklog(ProductBacklogDTO.builder()
                    .id(us.getProductBacklog().getId())
                    .nom(us.getProductBacklog().getNom())
                    .build());
        }

        if (us.getSprintBacklog() != null) {
            dto.setSprintBacklog(SprintBacklogDTO.builder()
                    .id(us.getSprintBacklog().getId())
                    .title(us.getSprintBacklog().getTitle())
                    .build());
        }

        if (us.getTask() != null) {
            dto.setTask(us.getTask().stream().map(task -> TaskDTO.builder()
                    .id(task.getId())
                    .titre(task.getTitre())
                    .description(task.getDescription())
                    .statut(task.getStatut())
                    .build()).collect(Collectors.toSet()));
        }

        return dto;
    }

}
