package net.lhm.projagile.Services;

import net.lhm.projagile.dto.request.EpicDTO;
import net.lhm.projagile.dto.response.EpicResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EpicService {
    void createEpic(EpicDTO epicDTO);
    void updateEpic(long id, EpicDTO epicDTO);
    void deleteEpic(long id);
    Page<EpicResponseDTO> getAllEpics(Pageable pageable);
    EpicResponseDTO getEpicById(long id); // Changed to return single Epic without pagination
    void addUserStoryToEpic(long id, long[] userStoryIds);
    void deleteUserStoryFromEpic(long id, long[] userStoryIds);
}