package net.lhm.projagile.Services;

import net.lhm.projagile.dto.UserStoryDTO;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.UserStory;

import java.util.List;

public interface UserStoryService {
    UserStory createUserStory(UserStoryDTO userStory);
    UserStory updateUserStory(Integer id, UserStoryDTO userStory);
    void deleteUserStory(Integer id);
    List<UserStory> getAllUserStories();
    UserStory getUserStoryById(Integer id);
    List<UserStory> getByStatus(Statut statut);
}
