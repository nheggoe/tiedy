package edu.ntnu.idi.bidata.tiedy.backend.user;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Nick Heggø
 * @version 2025.02.27
 */
public class Family {

  private final Set<User> familyMembers;

  public Family(Collection<User> users) {
    familyMembers = new HashSet<>(users);
    assertNoneEmptyFamily();
  }

  public Family(User user) {
    this(Collections.singleton(user));
  }

  /**
   * Adds a new User to the set of family members.
   *
   * @param user the User object to be added to the family members set
   */
  public void addFamilyMember(User user) {
    familyMembers.add(user);
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
    familyMembers.remove(user);
  }

  /**
   * Retrieves the set of family members associated with the current Family instance.
   *
   * @return a set of User objects representing the family members
   */
  public Set<User> getFamilyMembers() {
    return familyMembers;
  }

  private void assertNoneEmptyFamily() {
    if (familyMembers.isEmpty()) {
      throw new IllegalStateException("Creating a empty family is prohibited!");
    }
  }
}
