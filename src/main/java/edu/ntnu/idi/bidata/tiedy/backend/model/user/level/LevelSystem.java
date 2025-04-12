package edu.ntnu.idi.bidata.tiedy.backend.model.user.level;

/**
 * The LevelSystem class represent a user's level and experience. It contains methods to get and set
 * level and experience, and to calculate the amount needed for the next level.
 *
 * @author Odin Arvhage and Nick Heggø
 * @version 2025.04.11
 */
public class LevelSystem {

  // Stats
  private int currentLevel;
  private int currentExperience;
  private int totalExperience;
  private int completedTasks;

  // Algorithm
  private int experienceThreshold;

  public LevelSystem() {
    this.currentLevel = 0;
    this.currentExperience = 0;
    this.totalExperience = 0;
    this.completedTasks = 0;
    this.experienceThreshold = 10;
  }

  /**
   * Marks a task as completed by incrementing the user's experience, total experience, and the
   * number of completed tasks. This method also evaluates if the user meets the criteria for
   * leveling up, and processes the leveling up if applicable.
   *
   * @return true if the user levels up as a result of completing the task, false otherwise.
   */
  public boolean completeTask() {
    currentExperience += 10;
    totalExperience += 10;
    completedTasks++;
    return handleLevelUp();
  }

  /**
   * Retrieves the level of the user.
   *
   * @return The level of the user.
   */
  public int getCurrentLevel() {
    return currentLevel;
  }

  /**
   * Returns the experience amount of the user.
   *
   * @return The amount of experience.
   */
  public int getCurrentExperience() {
    return currentExperience;
  }

  public int getCompletedTasks() {
    return completedTasks;
  }

  public int getExperienceThreshold() {
    return experienceThreshold;
  }

  public int getTotalExperience() {
    return totalExperience;
  }

  private boolean handleLevelUp() {
    boolean isLeveledUp = false;
    while (isReadyForLevelUp()) {
      increaseLevel();
      adjustCurrentExperience();
      computeExperienceToNextLevel();
      isLeveledUp = true;
    }
    return isLeveledUp;
  }

  private boolean isReadyForLevelUp() {
    return currentExperience >= experienceThreshold;
  }

  private void increaseLevel() {
    this.currentLevel++;
  }

  private void adjustCurrentExperience() {
    this.currentExperience -= experienceThreshold;
  }

  private void computeExperienceToNextLevel() {
    this.experienceThreshold += (int) (experienceThreshold * 0.1);
  }
}
