package edu.ntnu.idi.bidata.tiedy.backend.repository.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Testing") // FIXME
class JsonGroupRepositoryTest {

  private JsonGroupRepository repository;
  private User exampleUser;

  @BeforeEach
  void setUp() {
    repository = JsonGroupRepository.getInstance();

    // Clear any existing groups before each test
    repository.getAll().clear();

    // Add example groups to the repository
    exampleUser = createExampleUser();
    Group group1 = createExampleGroup("Group 1", "Description 1", exampleUser.getId(), true);
    Group group2 = createExampleGroup("Group 2", "Description 2", exampleUser.getId(), false);
    repository.add(group1);
    repository.add(group2);
  }

  @Test
  void testFindAllByUserId() {
    List<Group> groups = repository.findAllByUserId(exampleUser.getId());
    assertEquals(2, groups.size());
    assertTrue(groups.stream().anyMatch(g -> g.getName().equals("Group 1")));
    assertTrue(groups.stream().anyMatch(g -> g.getName().equals("Group 2")));
  }

  private Group createExampleGroup(String name, String description, UUID userId, boolean isAdmin) {
    Group group = new Group(name, description, createExampleUser());
    group.getMembers().put(userId, isAdmin);
    return group;
  }

  private User createExampleUser() {
    return new User("testUser", "StrongPassword123!");
  }
}
