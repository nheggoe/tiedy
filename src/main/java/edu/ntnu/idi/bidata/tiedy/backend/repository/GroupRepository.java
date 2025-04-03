package edu.ntnu.idi.bidata.tiedy.backend.repository;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import java.util.List;
import java.util.UUID;

/**
 * This interface defines a repository for managing Group entities. It extends the generic
 * DataRepository interface, providing additional task-specific CRUD and query methods.
 *
 * @author Nick Heggø
 * @version 2025.04.03
 */
public interface GroupRepository extends DataRepository<Group> {

  /**
   * Finds all groups that a specific user is a member of.
   *
   * @param userId the UUID of the user
   * @return a list of groups that the user is a member of
   */
  List<Group> findAllByUserId(UUID userId);

  /**
   * @param userId the ID of the user
   * @return a list of groups where the user is an admin
   */
  List<Group> findByAdmin(UUID userId);

  /**
   * Adds a user to a group.
   *
   * @param groupId the ID of the group
   * @param userId the ID of the user to add
   * @param isAdmin whether the user should be an admin in the group
   * @return true if the user was added to the group, false otherwise
   */
  boolean addMember(UUID groupId, UUID userId, boolean isAdmin);

  /**
   * Removes a user from a group
   *
   * @param groupId the ID of the group
   * @param userId the ID of the user to remove
   * @return true if the user was removed from the group, false otherwise
   */
  boolean removeMember(UUID groupId, UUID userId);

  /**
   * Updates a user's admin status in a group.
   *
   * @param groupId the ID of the group
   * @param userId the ID of the user
   * @param isAdmin the new admin status
   * @return true if the admin status was updated, false otherwise.
   */
  boolean updateMemberAdminStatus(UUID groupId, UUID userId, boolean isAdmin);
}
