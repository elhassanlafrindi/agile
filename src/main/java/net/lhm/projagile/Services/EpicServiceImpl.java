package net.lhm.projagile.Services;

import net.lhm.projagile.Repositories.EpicRepo;
import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.UserStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EpicServiceImpl implements EpicService {
    @Autowired
    private EpicRepo epicRepository;

    @Override
    public Epic addEpic(Integer id, String titre, String description) {
        Epic epic = new Epic();
        epic.setId(id);
        epic.setNom(titre);
        epic.setDescription(description);
        return epicRepository.save(epic);
    }

    @Override
    public void updateEpic(Integer id,String titre, String description) {
        Optional<Epic> existingEpic = epicRepository.findById(id);
        if (existingEpic.isPresent()) {
            Epic epic = existingEpic.get();
            epic.setNom(titre);
            epic.setDescription(description);
            epicRepository.save(epic);
        } else {
            throw new RuntimeException("Epic non trouvé !");
        }
    }

    @Override
    public void deleteEpic(Integer id) {
        epicRepository.deleteById(id);
    }

    @Override
    public void addUserStory(UserStory userStory) {
        Optional<Epic> existingEpic = epicRepository.findById(userStory.getId());
        if (existingEpic.isPresent()) {
            Epic epic = existingEpic.get();
            epic.getUserStories().add(userStory);
            epicRepository.save(epic);
        } else {
            throw new RuntimeException("Epic non trouvé !");
        }
    }

    @Override
    public void removeUserStory(UserStory userStory) {
        Optional<Epic> existingEpic = epicRepository.findById(userStory.getId());
        if (existingEpic.isPresent()) {
            Epic epic = existingEpic.get();
            epic.getUserStories().remove(userStory);
            epicRepository.save(epic);
        } else {
            throw new RuntimeException("Epic non trouvé !");
        }
    }

    @Override
    public  List<Epic> getAllEpics() {
        return epicRepository.findAll();
    }

    @Override
    public Epic getEpicById(Integer id) {
        return epicRepository.findById(id).orElseThrow(() -> new RuntimeException("Epic non trouvé !"));
    }
}
