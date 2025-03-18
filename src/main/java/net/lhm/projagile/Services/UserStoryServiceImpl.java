package net.lhm.projagile.Services;

import net.lhm.projagile.Repositories.UserStoryRepo;
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
    public UserStory createUserStory(UserStory userStory) {
        return userStoryRepo.save(userStory);
    }

    @Override
    public UserStory updateUserStory(Integer id, UserStory userStory) {
        Optional<UserStory> existing = userStoryRepo.findById(id);
        if (existing.isPresent()) {
            userStory.setId(id);
            return userStoryRepo.save(userStory);
        }
        throw new RuntimeException("UserStory non trouvée !");
    }

    @Override
    public void deleteUserStory(Integer id) {
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
}
