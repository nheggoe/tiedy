package edu.ntnu.idi.bidata.tiedy.backend.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
  User nick = new User("NickHeg","nickhe.stud.no",0);

  @Test
  void testGetUserName() {
    nick.setUserName("Nick Heggø");
    assertEquals("Nick Heggø",nick.getUserName());
  }
  @Test
  void testGetUserNameWrong() {
    nick.setUserName("Nick Heggø");
    assertNotEquals("NickHeg  ",nick.getUserName());
  }


  @Test
  void testGetEmailAddress() {
    nick.setEmailAddress("nickhe@stud.ntnu.no");
    assertEquals("nickhe@stud.ntnu.no", nick.getEmailAddress());
  }
  @Test
  void testGetEmailAddressWrong(){
    nick.setEmailAddress("nickhe@stud.ntnu.no");
    assertNotEquals("nickhe.stud.no", nick.getEmailAddress());
  }


  @Test
  void testGetUserID(){
    nick.setUserID(42);
    assertEquals(42, nick.getUserID());
  }
  @Test
  void testGetUserIDWrong(){
    nick.setUserID(42);
    assertNotEquals(0, nick.getUserID());
  }


  @Test
  void testisUserIDCorrect(){
    nick.setUserID(42);
    assertTrue(nick.isUserIDCorrect(42));
  }

  @Test
  void testCheckUserName(){
    nick.setUserName("Nick Heggø");
    assertTrue(nick.isUserNameCorrect("Nick Heggø"));
  }

  @Test
  void testCheckEmailAddress() {
    nick.setEmailAddress("nickhe@stud.ntnu.no");
    assertTrue(nick.isEmailAddressCorrect("nickhe@stud.ntnu.no"));
  }

  @Test
  void testUserInformationUserNameWrong(){
    assertFalse(nick.isUserInformationCorrect("NickHeggø","nickhe.stud.no",0));
  }
  @Test
  void testUserInformationUserIDWrong(){
    assertFalse(nick.isUserInformationCorrect("NickHeg","nickhe.stud.no",44));
  }
  @Test
  void testUserInformationEmailAddressWrong(){
    assertFalse(nick.isUserInformationCorrect("NickHeg","nickhe@stud.ntnu.no",0));
  }

}
