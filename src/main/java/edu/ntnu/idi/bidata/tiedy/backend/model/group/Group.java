package edu.ntnu.idi.bidata.tiedy.backend.model.group;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a group of users, specifically family members. Provides functionality to manage the
 * family members within the group.
 *
 * @author Nick Heggø
 * @version 2025.03.28
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

  // ------------------------   Public Interface  ------------------------

  public boolean addMember(UUID userId, boolean isAdmin) {
    if (members.containsKey(userId)) {
      return false;
    }
    return members.put(userId, isAdmin) == null;
  }

  public void removeMember(UUID userId) {
    members.remove(userId);
  }

  public boolean updateMemberPermission(UUID userId, boolean isAdmin) {
    if (!members.containsKey(userId)) {
      return false;
    }

    return Objects.nonNull(members.replace(userId, isAdmin));
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
   * Sets the optional description of a group, if passed null will be replaced with empty string
   *
   * @param description the text description of the group
   */
  public void setDescription(String description) {
    this.description = (description == null) ? "" : description;
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
}
