package edu.ntnu.idi.bidata.tiedy.backend.repository.json;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.repository.GroupRepository;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * A repository for managing {@link Group} entities stored in a JSON-based persistence layer. This
 * class extends the base functionality provided by {@link JsonRepository} and implements the
 * domain-specific operations defined in the {@link GroupRepository} interface. It uses a singleton
 * design pattern to ensure only one instance of the repository is created and used.
 *
 * @author Nick Heggø
 * @version 2025.04.11
 * @see Group
 * @see JsonRepository
 * @see GroupRepository
 */
public class JsonGroupRepository extends JsonRepository<Group> implements GroupRepository {

  private static JsonGroupRepository instance;

  private JsonGroupRepository() {
    super(Group.class, Group::getId);
  }

  /**
   * Retrieves the singleton instance of {@link JsonGroupRepository}. This ensures that only one
   * instance of this repository is created and used throughout the application to manage {@link
   * Group} entities stored in a JSON-based persistence layer.
   *
   * @return the singleton instance of {@link JsonGroupRepository}
   */
  public static synchronized JsonGroupRepository getInstance() {
    if (instance == null) {
      instance = new JsonGroupRepository();
    }
    return instance;
  }

  @Override
  public Stream<Group> getGroupsByUserId(UUID userId) {
    return getAll().filter(group -> group.getMembers().containsKey(userId));
  }

  @Override
  public Stream<Group> getGropsByUserIdWhereUserIsAdmin(UUID userId) {
    return getGroupsByUserId(userId)
        .filter(group -> group.getMembers().get(userId)); // returns true if isAdmin
  }

  @Override
  public boolean addMemberToGroup(UUID groupId, UUID userId, boolean isAdmin) {
    Group foundGroup =
        getAll().filter(group -> group.getId().equals(groupId)).findFirst().orElse(null);

    if (foundGroup == null) {
      return false;
    }

    return foundGroup.addMember(userId, isAdmin);
  }

  @Override
  public boolean removeMemberFromGroup(UUID groupId, UUID userId) {
    Group group = getById(groupId).orElse(null);
    if (group == null) {
      return false;
    }
    group.removeMember(userId);
    return true;
  }

  @Override
  public boolean updateMemberAdminStatus(UUID groupId, UUID userId, boolean isAdmin) {
    if ((groupId == null) || (userId == null)) {
      return false;
    }

    Group group = getById(groupId).orElse(null);

    if (group == null) {
      return false;
    }

    return group.updateMemberPermission(userId, isAdmin);
  }
}
