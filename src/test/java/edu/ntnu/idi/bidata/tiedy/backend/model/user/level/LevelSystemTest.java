package edu.ntnu.idi.bidata.tiedy.backend.model.user.level;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LevelSystemTest {

  private LevelSystem levelSystem;

  @BeforeEach
  void setup() {
    levelSystem = new LevelSystem();
  }

  @Test
  void testLevelUp() {
    assertThat(levelSystem.getCurrentLevel()).isZero();
    assertThat(levelSystem.getCurrentExperience()).isZero();

    for (int i = 0; i < 10; i++) { // complete 10 tasks
      levelSystem.completeTask();
    }

    assertThat(levelSystem.getCompletedTasks()).isEqualTo(10);
    assertThat(levelSystem.getCurrentLevel()).isGreaterThan(2);
    assertThat(levelSystem.getTotalExperience()).isGreaterThan(90);
  }

  @Test
  void testLevelingAlgorithm() {
    int currentExperienceThreshold = levelSystem.getExperienceThreshold();
    for (int i = 0; i < 20; i++) { // test for 20 levels
      while (!levelSystem.completeTask()) { // until level up
        levelSystem.completeTask();
      }
      int nextExperienceThreshold = levelSystem.getExperienceThreshold();

      assertThat(nextExperienceThreshold)
          .withFailMessage("Level up should get more difficult")
          .isGreaterThan(currentExperienceThreshold);

      currentExperienceThreshold = nextExperienceThreshold;
    }
  }
}
