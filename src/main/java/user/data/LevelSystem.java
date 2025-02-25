package user.data;

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

  public void setLevel(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }

  public int getExperience() {
    return experience;
  }

  public void setExperienceToNextLevel(int experienceToNextLevel) {
    this.experienceToNextLevel = experienceToNextLevel;
  }

  public int getExperienceToNextLevel() {
    return experienceToNextLevel;
  }

  public void calculateExperienceToNextLevel() {
    this.experienceToNextLevel += 10;
  }

  public void checkLevelUp() {
    if (experience >= experienceToNextLevel) {
      level++;
      this.experience -= experienceToNextLevel;
      calculateExperienceToNextLevel();
    }
  }
}
