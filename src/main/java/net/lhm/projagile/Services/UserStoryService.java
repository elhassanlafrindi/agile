package net.lhm.projagile.Services;

import net.lhm.projagile.entities.UserStory;

import java.util.List;

public interface UserStoryService {
    UserStory createUserStory(UserStory userStory);
    UserStory updateUserStory(Integer id, UserStory userStory);
    void deleteUserStory(Integer id);
    List<UserStory> getAllUserStories();
    UserStory getUserStoryById(Integer id);
}
