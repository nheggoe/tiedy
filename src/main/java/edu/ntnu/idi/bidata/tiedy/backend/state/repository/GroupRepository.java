package edu.ntnu.idi.bidata.tiedy.backend.state.repository;

import edu.ntnu.idi.bidata.tiedy.backend.user.Group;
import java.util.List;
import java.util.UUID;

public interface GroupRepository extends DataRepository<Group> {

  /**
   * Finds all groups that a specific user is a member of.
   *
   * @param userId the UUID of the user
   * @return a list of groups that the user is a member of
   */
  List<Group> findAllByUserId(UUID userId);

  /**
   * @param userId
   * @return
   */
  List<Group> findByAdmin(UUID userId);

  boolean addMember(UUID groupId, UUID userId, boolean isAdmin);

  boolean removeMember(UUID groupId, UUID userId);

  boolean updateMemberAdminStatus(UUID groupId, UUID userId, boolean isAdmin);
}
