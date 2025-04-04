package net.lhm.projagile.Controllers;

import net.lhm.projagile.Services.UserStoryService;
import net.lhm.projagile.entities.UserStory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/userstories")
public class UserStoryController {
    private final UserStoryService userStoryService;

    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @PostMapping("/add")
    public UserStory create(@RequestBody UserStory userStory) {
        return userStoryService.createUserStory(userStory);
    }

    @PutMapping("/{id}")
    public UserStory update(@PathVariable Integer id, @RequestBody UserStory userStory) {
        return userStoryService.updateUserStory(id, userStory);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userStoryService.deleteUserStory(id);
    }

    @GetMapping
    public List<UserStory> getAll() {
        return userStoryService.getAllUserStories();
    }

    @GetMapping("/{id}")
    public UserStory getById(@PathVariable Integer id) {
        return userStoryService.getUserStoryById(id);
    }
}
