package net.lhm.projagile.Services;

import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.UserStory;

import java.util.List;

public interface EpicService {
    Epic addEpic(Integer id, String titre, String description);
    void updateEpic(Integer id,String titre, String description);
    void deleteEpic(Integer id);
    void addUserStory(UserStory userStory);
    void removeUserStory(UserStory userStory);
    List<Epic> getAllEpics();
    Epic getEpicById(Integer id);
}
