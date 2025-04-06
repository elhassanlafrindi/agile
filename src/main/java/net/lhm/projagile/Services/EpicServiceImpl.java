package net.lhm.projagile.Services;

import net.lhm.projagile.Repositories.EpicRepo;
import net.lhm.projagile.Repositories.UserStoryRepo;
import net.lhm.projagile.dto.EpicDTO;
import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.UserStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EpicServiceImpl implements EpicService {
    @Autowired
    private EpicRepo epicRepository;
    @Autowired
    private UserStoryRepo userStoryRepo;
    @Override
    public Epic addEpic(EpicDTO epicDTO) {
       return  epicRepository.save(new Epic().builder()
               .titre(epicDTO.getTitre())
               .description(epicDTO.getDescription())
               .build());
    }

    @Override
    public void updateEpic(Integer id,EpicDTO epicDTO) {
        Optional<Epic> existingEpic = epicRepository.findById(id);
        if (existingEpic.isPresent()) {
            Epic epic = existingEpic.get();
            epic.setTitre(epicDTO.getTitre());
            epic.setDescription(epicDTO.getDescription());
            epicRepository.save(epic);
        } else {
            throw new RuntimeException("Epic non trouvé !");
        }
    }

    @Override
    public void deleteEpic(Integer id) {
        if(!epicRepository.existsById(id))
            throw  new RuntimeException("epic non trouvé");
        epicRepository.deleteById(id);

    }

    @Override
    public void addUserStory(int userStoryId,int epicId) {
        Optional<Epic> existingEpic = epicRepository.findById(epicId);
        Optional<UserStory> existingUserStory=userStoryRepo.findById(userStoryId);
        if (existingEpic.isPresent() && existingUserStory.isPresent()) {
            Epic epic = existingEpic.get();
            UserStory userStory = existingUserStory.get();
            List<Epic> epics= userStory.getEpics().stream().filter(ep->ep.getId()==epic.getId()).collect(Collectors.toList());
                 if(epics.isEmpty()) {
                     userStory.getEpics().add(epic);
                     userStoryRepo.save(userStory);
                 }
                 else {
                     throw new RuntimeException("this UserStory exist in  this Epic");
                 }


        } else {
            throw new RuntimeException("Epic or UserStory non trouvé !");
        }
    }

    @Override
    public void removeUserStory(int userStoryId,int epicId) {
        Optional<Epic> existingEpic = epicRepository.findById(epicId);
        Optional<UserStory> existingUserStory=userStoryRepo.findById(userStoryId);
        if (existingEpic.isPresent() && existingUserStory.isPresent()) {
            Epic epic = existingEpic.get();
            UserStory userStory = existingUserStory.get();
            if(userStory.getEpics().contains(epic)) {
                userStory.getEpics().remove(epic);
                userStoryRepo.save(userStory);
            }
            else{
                throw new RuntimeException("this UserStory  does not exist in  this Epic");
            }
        } else {
            throw new RuntimeException("Epic or UserStory non trouvé !");
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
