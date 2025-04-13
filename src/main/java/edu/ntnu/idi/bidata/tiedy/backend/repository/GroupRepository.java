package edu.ntnu.idi.bidata.tiedy.backend.repository;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * This interface defines a repository for managing Group entities. It extends the generic
 * DataRepository interface, providing additional task-specific CRUD and query methods.
 *
 * @author Nick Heggø
 * @version 2025.04.11
 */
public interface GroupRepository extends DataRepository<Group> {

  /**
   * Finds all groups that a specific user is a member of.
   *
   * @param userId the UUID of the user
   * @return a stream of groups that the user is a member of
   */
  Stream<Group> getGroupsByUserId(UUID userId);

  /**
   * Retrieves a list of groups where the specified user is an admin.
   *
   * @param userId the UUID of the user whose admin groups are to be found
   * @return a stream of Group objects where the user is an admin
   */
  Stream<Group> getGropsByUserIdWhereUserIsAdmin(UUID userId);

  /**
   * Adds a user to a group with the specified membership and admin status.
   *
   * @param groupId the unique identifier of the group
   * @param userId the unique identifier of the user to be added
   * @param isAdmin a boolean indicating whether the user should be granted admin privileges
   * @return true if the user was successfully added to the group, false otherwise
   */
  boolean addMemberToGroup(UUID groupId, UUID userId, boolean isAdmin);

  /**
   * Removes a user from a specified group.
   *
   * @param groupId the unique identifier of the group from which the user will be removed
   * @param userId the unique identifier of the user to be removed
   * @return true if the user was successfully removed from the group, false otherwise
   */
  boolean removeMemberFromGroup(UUID groupId, UUID userId);

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
