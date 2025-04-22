package net.lhm.projagile.Services;

import net.lhm.projagile.dto.UserStoryDTO;
import net.lhm.projagile.dtoResponse.UserStoryDTORes;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.UserStory;

import java.util.List;

public interface UserStoryService {
    UserStory createUserStory(UserStoryDTO userStory);
    UserStory updateUserStory(Integer id, UserStoryDTO userStory);
    void deleteUserStory(Integer id);
    List<UserStoryDTORes> getAllUserStories();
    UserStoryDTORes getUserStoryById(Integer id);
    List<UserStoryDTORes> getByStatus(Statut statut);
    void prioriserUserStory(int iduserstory,int priority);
}
