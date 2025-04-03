package edu.ntnu.idi.bidata.tiedy.backend.model.group;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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

  private Group(UUID id, LocalDateTime createdAt, Map<UUID, Boolean> members) {
    if (id == null || createdAt == null || members == null) {
      throw new IllegalArgumentException("Group ID, creation time, and members map cannot be null");
    }
    this.id = id;
    this.createdAt = createdAt;
    this.members = members;
  }

  /**
   * Creates an empty instance of a group
   *
   * @param name
   * @param description
   */
  public Group(String name, String description, User user) {
    this();
    setName(name);
    setDescription(description);
    addMember(user, true);
  }

  // ------------------------   Public Interface  ------------------------

  public void addMember(User user, boolean isAdmin) {
    members.put(user.getId(), isAdmin);
  }

  public void removeMember(User user) {}

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
