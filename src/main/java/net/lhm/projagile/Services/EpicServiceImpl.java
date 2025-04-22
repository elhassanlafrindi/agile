package net.lhm.projagile.Services;

import net.lhm.projagile.Repositories.EpicRepo;
import net.lhm.projagile.Repositories.UserStoryRepo;
import net.lhm.projagile.dto.EpicDTO;
import net.lhm.projagile.dto.UserStoryDTO;
import net.lhm.projagile.dtoResponse.EpicDTORes;
import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.UserStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public List<EpicDTORes> getAllEpics() {
        List<Epic> epics = epicRepository.findAll();
        List<EpicDTORes> epicDTOResList = new ArrayList<>();

        for (Epic epic : epics) {
            // Convert List<UserStory> to List<UserStoryDTO>
            List<UserStoryDTO> userStoryDTOs = epic.getUsetStory().stream().map(us -> UserStoryDTO.builder()
                    .id(us.getId())
                    .title(us.getTitle())
                    .description(us.getDescription())
                    .priorite(us.getPriorite())
                    .statut(us.getStatut())
                    .build()
            ).toList();

            // Build the EpicDTORes object
            EpicDTORes dto = EpicDTORes.builder()
                    .id(epic.getId())
                    .titre(epic.getTitre())
                    .description(epic.getDescription())
                    .usetStory(userStoryDTOs)
                    .build();

            epicDTOResList.add(dto);
        }

        return epicDTOResList;
    }


    @Override
    public EpicDTORes getEpicById(Integer id) {

            Optional<Epic> epic= epicRepository.findById(id);

        if (epic.isPresent()) {

            List<UserStoryDTO> userStoryDTOs = epic.get().getUsetStory().stream().map(us -> UserStoryDTO.builder()
                    .id(us.getId())
                    .title(us.getTitle())
                    .description(us.getDescription())
                    .priorite(us.getPriorite())
                    .statut(us.getStatut())
                    .build()
            ).toList();

            // Build the EpicDTORes object
            EpicDTORes dto = EpicDTORes.builder()
                    .id(epic.get().getId())
                    .titre(epic.get().getTitre())
                    .description(epic.get().getDescription())
                    .usetStory(userStoryDTOs)
                    .build();

    return dto;
        }

       return null;
    }
}



