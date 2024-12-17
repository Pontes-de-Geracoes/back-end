package com.pontedegeracoes.api.entitys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NecessityTest {

  private Necessity necessity;

  @BeforeEach
  public void setUp() {
    necessity = new Necessity("Food", "Basic food items");
  }

  @Test
  public void testNecessityConstructor() {
    assertNotNull(necessity);
    assertEquals("Food", necessity.getName());
    assertEquals("Basic food items", necessity.getDescription());
    assertNotNull(necessity.getUsers());
    assertEquals(0, necessity.getUsers().size());
  }

  @Test
  public void testSetName() {
    necessity.setName("Water");
    assertEquals("Water", necessity.getName());
  }

  @Test
  public void testSetDescription() {
    necessity.setDescription("Clean drinking water");
    assertEquals("Clean drinking water", necessity.getDescription());
  }

  @Test
  public void testSetUsers() {
    HashSet<User> users = new HashSet<>();
    necessity.setUsers(users);
    assertEquals(users, necessity.getUsers());
  }

  @Test
  public void testToString() {
    String expected = "{\n" +
        "name='Food'\n" +
        ", description='Basic food items'\n" +
        ", users='[]'\n" +
        "}";
    assertEquals(expected, necessity.toString());
  }
}