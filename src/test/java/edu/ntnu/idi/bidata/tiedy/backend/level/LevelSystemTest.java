package edu.ntnu.idi.bidata.tiedy.backend.level;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelSystemTest {
  LevelSystem levelSystem = new LevelSystem(1, 40, 100);

  @Test
  void testGetLevel() {
    assertEquals(1, levelSystem.getLevel());
  }

  @Test
  void testGetLevelWrong() {
    assertNotEquals(0, levelSystem.getLevel());
  }

  @Test
  void testGetExperience() {
    assertEquals(40, levelSystem.getExperience());
  }

  @Test
  void testGetExperienceWrong() {
    assertNotEquals(0, levelSystem.getExperience());
  }

  @Test
  void getExperienceToNextLevel() {
    assertEquals(100, levelSystem.getExperienceToNextLevel());
  }

  @Test
  void getExperienceToNextLevelWrong() {
    assertNotEquals(0, levelSystem.getExperienceToNextLevel());
  }


  @Test
  void testCheckLevelUp() {
    assertFalse(levelSystem.checkLevelUp());
  }


  @Test
  void testSetLevel() {
    levelSystem.setLevel(4);
    assertEquals(4, levelSystem.getLevel());
  }

  @Test
  void testSetLevelWrong() {
    levelSystem.setLevel(4);
    assertNotEquals(1, levelSystem.getLevel());
  }

  @Test
  void testSetExperience() {
    levelSystem.setExperience(50);
    assertEquals(50, levelSystem.getExperience());
  }

  @Test
  void testSetExperienceWrong() {
    levelSystem.setExperience(50);
    assertNotEquals(40, levelSystem.getExperience());
  }

  @Test
  void testSetExperienceToNextLevel() {
    levelSystem.setExperienceToNextLevel(200);
    assertEquals(200, levelSystem.getExperienceToNextLevel());
  }

  @Test
  void testSetExperienceToNextLevelWrong() {
    levelSystem.setExperienceToNextLevel(200);
    assertNotEquals(100, levelSystem.getExperienceToNextLevel());
  }

  @Test
  void testLevelUp() {
    levelSystem.setExperience(100);
    levelSystem.levelUp(10);
    assertEquals(2, levelSystem.getLevel());
  }

  @Test
  void TestLevelUpWrong() {
    levelSystem.setExperience(100);
    levelSystem.levelUp(10);
    assertNotEquals(1, levelSystem.getLevel());
  }

  @Test
  void testResetExperience() {
    levelSystem.resetExperience();
    assertEquals(0, levelSystem.getExperience());
  }

  @Test
  void testCalculateExperienceToNextLevel() {
    levelSystem.calculateExperienceToNextLevel(50);
    assertEquals(150, levelSystem.getExperienceToNextLevel());
  }

  @Test
  void testIncreaseLevelByOne() {
    levelSystem.increaseLevelByOne();
    assertEquals(2, levelSystem.getLevel());
  }

  @Test
  void testCheckLevelUpAtBoundary() {
    levelSystem.setExperience(100);
    assertTrue(levelSystem.checkLevelUp());
  }
}