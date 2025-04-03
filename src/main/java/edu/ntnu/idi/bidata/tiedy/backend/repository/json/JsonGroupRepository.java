package edu.ntnu.idi.bidata.tiedy.backend.repository.json;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.repository.GroupRepository;
import java.util.List;
import java.util.UUID;

public class JsonGroupRepository extends JsonRepository<Group> implements GroupRepository {

  private static JsonGroupRepository instance;

  private JsonGroupRepository() {
    super(Group.class, Group::getId);
  }

  public static synchronized JsonGroupRepository getInstance() {
    if (instance == null) {
      instance = new JsonGroupRepository();
    }
    return instance;
  }

  @Override
  public List<Group> findAllByUserId(UUID userId) {
    return getAll().stream().filter(group -> group.getMembers().containsKey(userId)).toList();
  }

  @Override
  public List<Group> findByAdmin(UUID userId) {
    return findAllByUserId(userId).stream()
        .dropWhile(group -> !group.getMembers().get(userId)) //  returns true if isAdmin
        .toList();
  }

  @Override
  public boolean addMember(UUID groupId, UUID userId, boolean isAdmin) {
    Group foundGroup =
        getAll().stream()
            .filter(group -> group.getId().equals(groupId))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Goup not found!"));

    // FIXME require UserObject, get user repository?
    // group.addMember();
    return false;
  }

  @Override
  public boolean removeMember(UUID groupId, UUID userId) {
    return false;
  }

  @Override
  public boolean updateMemberAdminStatus(UUID groupId, UUID userId, boolean isAdmin) {
    return false;
  }
}
