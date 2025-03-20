package edu.ntnu.idi.bidata.tiedy.backend.user;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a group of users, specifically family members. Provides functionality to manage the
 * family members within the group.
 *
 * @author Nick Heggø
 * @version 2025.03.19
 */
public class Group {

  private final Set<User> members;

  /**
   * Constructs a new Group instance with the specified collection of users. This collection
   * represents the initial set of family members.
   *
   * @param users the collection of User objects to initialize the family members set (must not be
   *     empty)
   * @throws IllegalStateException if the provided collection is empty
   * @throws NullPointerException if the provided collection is null
   */
  public Group(Collection<User> users) {
    members = new HashSet<>(users);
    assertNoneEmptyFamily();
  }

  /**
   * Constructs a new Group instance with a single User as the initial member. The provided user
   * will be added to the group as the only initial family member.
   *
   * @param user the User object to be added as the sole member of the group
   * @throws NullPointerException if the provided user is null
   */
  public Group(User user) {
    this(Collections.singleton(user));
  }

  /**
   * Adds a new User to the set of family members.
   *
   * @param user the User object to be added to the family members set
   */
  public void addFamilyMember(User user) {
    members.add(user);
  }

  /**
   * Removes a specified user from the family members set.
   *
   * @param user the User object to be removed from the family members set
   */
  public void removeFamilyMember(User user) {
    if (user == null) {
      throw new NullPointerException("User cannot be null!");
    }
    members.remove(user);
  }

  /**
   * Retrieves the set of family members associated with the current Family instance.
   *
   * @return a set of User objects representing the family members
   */
  public Set<User> getMembers() {
    return members;
  }

  private void assertNoneEmptyFamily() {
    if (members.isEmpty()) {
      throw new IllegalStateException("Creating a empty family is prohibited!");
    }
  }
}
