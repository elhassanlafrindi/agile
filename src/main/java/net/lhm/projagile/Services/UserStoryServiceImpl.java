package net.lhm.projagile.Services;

import jakarta.transaction.Transactional;
import net.lhm.projagile.Repositories.UserStoryRepo;
import net.lhm.projagile.dto.UserStoryDTO;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.UserStory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<UserStory> getAllUserStories() {
        return userStoryRepo.findAll();
    }

    @Override
    public UserStory getUserStoryById(Integer id) {
        return userStoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("UserStory non trouvée !"));
    }

    @Override
    public List<UserStory> getByStatus(Statut statut) {
        return userStoryRepo.findByStatut(statut);
    }
}
