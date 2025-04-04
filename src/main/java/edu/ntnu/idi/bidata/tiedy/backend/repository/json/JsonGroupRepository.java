package edu.ntnu.idi.bidata.tiedy.backend.repository.json;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.repository.GroupRepository;
import java.util.List;
import java.util.Objects;
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
        getAll().stream().filter(group -> group.getId().equals(groupId)).findFirst().orElse(null);

    if (foundGroup == null) {
      return false;
    }

    return foundGroup.addMember(userId, isAdmin);
  }

  @Override
  public boolean removeMember(UUID groupId, UUID userId) {
    Group group = getById(groupId).orElse(null);

    if (Objects.isNull(group)) {
      return false;
    }
    group.removeMember(userId);
    return true;
  }

  @Override
  public boolean updateMemberAdminStatus(UUID groupId, UUID userId, boolean isAdmin) {
    if (Objects.isNull(groupId) || Objects.isNull(userId)) {
      return false;
    }

    Group group = getById(groupId).orElse(null);

    if (Objects.isNull(group)) {
      return false;
    }
    return group.updateMemberPermission(userId, isAdmin);
  }
}
