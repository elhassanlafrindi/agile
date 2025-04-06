package net.lhm.projagile.Services;

import net.lhm.projagile.dto.EpicDTO;
import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.Statut;


import java.util.List;

public interface EpicService {
    Epic addEpic(EpicDTO epicDTO);
    void updateEpic(Integer id, EpicDTO epicDTO);
    void deleteEpic(Integer id);
    void addUserStory(int userStoryId,int epicId);
    void removeUserStory(int userStoryId,int epicId);
    List<Epic> getAllEpics();
    Epic getEpicById(Integer id);

}
