package edu.ntnu.idi.bidata.tiedy.backend.state.repository;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import java.util.List;
import java.util.UUID;

public class JsonGroupRepository extends JsonRepository<Group> implements GroupRepository {

  public JsonGroupRepository() {
    super(Group.class, Group::getId);
  }

  @Override
  public List<Group> findAllByUserId(UUID userId) {
    return getAll().stream().filter(group -> group.getMembers().containsKey(userId)).toList();
  }

  @Override
  public List<Group> findByAdmin(UUID userId) {
    return List.of();
  }

  @Override
  public boolean addMember(UUID groupId, UUID userId, boolean isAdmin) {
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
