package net.lhm.projagile.Services;

import net.lhm.projagile.dto.request.UserStoryDTO;
import net.lhm.projagile.dto.response.UserStoryResponseDTO;
import net.lhm.projagile.entities.Priority;
import net.lhm.projagile.entities.Statut;

import java.util.List;

public interface UserStoryService {
        void createUserStory(UserStoryDTO userStoryDTO);
        void updateUserStory(int userStoryID,UserStoryDTO userStoryDTO);
        void deleteUserStory(int userStoryID);
        void updateUserStoryStatut(int userStoryID, Statut statut);
        List<UserStoryResponseDTO> getAllUserStory();
        List<UserStoryResponseDTO> getAllUserStoryByStatus(Statut statut);
        UserStoryResponseDTO getUserStoryById(int id); // Changed method name and return type
        List<UserStoryResponseDTO> getAllUserStoryByPriority(Priority priority);
}