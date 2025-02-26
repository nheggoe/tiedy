package edu.ntnu.idi.bidata.tiedy.backend.level;

/**
 * The LevelSystem class represent a users level and experience.
 * It contains methods to get and set level and experience, and to calculate the amount needed for the next level.
 *
 * @author Odin Arvhage
 * @version 2025.02.25
 */
public class LevelSystem {
  private int level;
  private int experience;
  private int experienceToNextLevel;


  /**
   * Creates an instance of the LevelSystem class with the provided level, experience and experience to next level.
   *
   * @param level                 The level that the user will have.
   * @param experience            The current amount of experience accumulated. Will always be set to 0 outside of exceptions.
   * @param experienceToNextLevel The experience that is needed to reach the next level.
   */
  public LevelSystem(int level, int experience, int experienceToNextLevel) {
    setLevel(level);
    setExperience(experience);
    setExperienceToNextLevel(experienceToNextLevel);
  }

  /**
   * Sets the level of the user.
   *
   * @param level The level that the user will have.
   */
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * Retrieves the level of the user.
   *
   * @return The level of the user.
   */
  public int getLevel() {
    return level;
  }

  /**
   * Sets the experience amount of the user.
   *
   * @param experience The amount of experience to be set.
   */
  public void setExperience(int experience) {
    this.experience = experience;
  }

  /**
   * Returns the experience amount of the user.
   *
   * @return The amount of experience.
   */
  public int getExperience() {
    return experience;
  }

  /**
   * Sets the experience needed to reach the next level.
   *
   * @param experienceToNextLevel Amount of experience needed to level up.
   */
  public void setExperienceToNextLevel(int experienceToNextLevel) {
    this.experienceToNextLevel = experienceToNextLevel;
  }

  /**
   * Returns the amount of experience needed to reach the next level.
   *
   * @return The amount of experience needed to reach the next level.
   */
  public int getExperienceToNextLevel() {
    return experienceToNextLevel;
  }

  /**
   * Calculates the amount of experience needed to reach the next level based on the increment.
   *
   * @param increment The amount of experience the next level up will be incremented by.
   */
  public void calculateExperienceToNextLevel(int increment) {
    this.experienceToNextLevel += increment;
  }

  /**
   * Resets the users experience to 0. To be used when a level up happens.
   */
  public void resetExperience() {
    this.experience = 0;
  }

  /**
   * Checks the users current experience against the amount of experience needed for the next level.
   * To be used each time the experience of a user is updated.
   *
   * @return True if the user has enough experience to level up, false otherwise.
   */
  public boolean checkLevelUp() {
    return getExperience() >= getExperienceToNextLevel();
  }

  /**
   * Levels up the user by increasing the level by 1.
   */
  public void increaseLevelByOne() {
    this.level++;
  }

  /**
   * Levels up the user by increasing the level by 1 and resets the experience to 0.
   * Calculates the amount of experience needed for the next level based on the increment.
   *
   * @param increment The amount of experience the next level up will be incremented by.
   */
  public void levelUp(int increment) {
    if (checkLevelUp()) {
      increaseLevelByOne();
      resetExperience();
      calculateExperienceToNextLevel(increment);
    }
  }
}

