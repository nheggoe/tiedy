package edu.ntnu.idi.bidata.tiedy.backend.model.group;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

/**
 * Represents a group of users, specifically family members. Provides functionality to manage the
 * family members within the group.
 *
 * @author Nick Heggø
 * @version 2025.04.12
 */
public class Group {

  private final UUID id; // groupID
  private final LocalDateTime createdAt;
  private final Map<UUID, Boolean> members; // K: userID, V: flag for permission

  private String name;
  private String description;

  private Group() {
    this.id = UUID.randomUUID();
    this.createdAt = LocalDateTime.now();
    this.members = new HashMap<>();
  }

  /**
   * Constructs a new Group instance with the specified name, description, and an initial member.
   *
   * @param name the name of the group must not be null or blank
   * @param description in the description of the group, if null, it will be set to an empty string
   * @param userId the UUID of the initial member to be added to the group as an admin
   */
  public Group(String name, String description, UUID userId) {
    this();
    setName(name);
    setDescription(description);
    addMember(userId, true);
  }

  /**
   * Creates a new Group instance by copying the data from an existing Group object.
   *
   * @param other the Group instance to copy; must not be null and should contain valid values for
   *     all fields
   */
  public Group(@NonNull Group other) {
    this.id = other.id;
    this.createdAt = other.createdAt;
    this.members = new HashMap<>(other.members);
    this.name = other.name;
    this.description = other.description;
  }

  // ------------------------   Overrides ------------------------

  @Override
  public String toString() {
    return "Group{id=%s, createdAt=%s, members=%s, name='%s', description='%s'}"
        .formatted(id, createdAt, members, name, description);
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Group group)) {
      return false;
    }
    return id.equals(group.id) && createdAt.equals(group.createdAt);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + createdAt.hashCode();
    return result;
  }

  // ------------------------   Public Interface  ------------------------

  /**
   * Adds a member to the group with the specified UUID and role (admin or non-admin). If the member
   * already exists in the group, the method does not add them again.
   *
   * @param userId the UUID of the user to be added to the group
   * @param isAdmin a boolean indicating whether the user should have admin privileges
   * @return true if the member was successfully added to the group, false if the user already
   *     exists
   */
  public boolean addMember(UUID userId, boolean isAdmin) {
    if (members.containsKey(userId)) {
      return false;
    }
    return members.put(userId, isAdmin) == null;
  }

  /**
   * Removes a member from the group identified by their unique UUID. If the specified member does
   * not exist within the group, the method performs no action.
   *
   * @param userId the UUID of the member to be removed from the group; must not be null
   */
  public void removeMember(UUID userId) {
    members.remove(userId);
  }

  /**
   * Updates the administrative permission of an existing group member.
   *
   * <p>This method modifies the role of a specified member in the group (admin or non-admin). If
   * the member does not exist in the group, no changes are made, and the method returns false.
   *
   * @param userId the unique identifier of the member whose role is to be updated; must not be null
   * @param isAdmin a boolean indicating whether the member should be given admin privileges (true)
   *     or non-admin privileges (false)
   * @return true if the role was successfully updated, false if the member does not exist
   */
  public boolean updateMemberPermission(UUID userId, boolean isAdmin) {
    if (!members.containsKey(userId)) {
      return false;
    }

    return members.replace(userId, isAdmin) != null;
  }

  // ------------------------   Getters and Setters ------------------------

  public UUID getId() {
    return id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Map<UUID, Boolean> getMembers() {
    return Map.copyOf(members);
  }

  public String getName() {
    return name;
  }

  /**
   * Sets the name of the group. The name must not be null or blank.
   *
   * @param name the name of the group; must be a non-null and non-blank string
   * @throws IllegalArgumentException if the provided name is null or blank
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Group name cannot be blank!");
    }
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  /**
   * Sets the optional description of a group, if passed null will be replaced with empty string.
   *
   * @param description the text description of the group
   */
  public void setDescription(String description) {
    this.description = (description == null) ? "" : description;
  }
}
