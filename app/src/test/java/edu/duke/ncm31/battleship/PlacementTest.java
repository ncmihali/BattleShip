package edu.duke.ncm31.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_assignment() {
    Coordinate c1 = new Coordinate("A2");
    Placement p1 = new Placement(c1, 'V');
    assertEquals('V', p1.getOrientation());
    assertEquals(c1, p1.getWhere());
    Placement p2 = new Placement("B4h");
    assertEquals('H', p2.getOrientation());
    assertEquals(new Coordinate("B4"), p2.getWhere());
    Placement p3 = new Placement("A0D");
    Placement p4 = new Placement("B4U");
    Placement p5 = new Placement("F3R");
    Placement p6 = new Placement("d3L");
    assertEquals('L', p6.getOrientation());
    assertEquals('R', p5.getOrientation());
    assertEquals('U', p4.getOrientation());
    assertEquals('D', p3.getOrientation());
  }

  @Test
  public void test_equality(){
    Coordinate c1 = new Coordinate("B7");
    Coordinate c2 = new Coordinate("A3");
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c1, 'V');
    assertEquals(p1, p2);
    Placement p3 = new Placement(c2, 'H');
    Placement p4 = new Placement(c2, 'V');
    assertNotEquals(p3, p4);
    assertNotEquals(p1, p3);
  }

  @Test
  public void test_throws_errors(){
    Coordinate c1 = new Coordinate("B7");
    assertThrows(IllegalArgumentException.class, () -> new Placement(c1, 'Q'));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A3P"));
  }

  @Test
  public void test_equals(){
    Placement p1 = new Placement("A3h");
    Placement p2 = new Placement("A3h");
    Placement p3 = new Placement("A3v");
    Placement p4 = new Placement("B3h");
    assertEquals(p1, p2);
    assertEquals(p1, p1);
    assertNotEquals(p1, p3);
    assertNotEquals(p1, p4);
    assertNotEquals(p3, p4);
    assertNotEquals(p1, "V");
  }
  
  @Test
  public void test_hash(){
    Placement p1 = new Placement("A3H");
    Placement p2 = new Placement("A3H");
    Placement p3 = new Placement("B1V");
    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p2, p3);
  }

  @Test
  public void test_toString(){
    Placement p1 = new Placement("A4v");
    Placement p2 = new Placement("B2h");
    assertEquals("H", p2.toString());
    assertEquals("V", p1.toString());
    assertNotEquals("P", p2.toString());
  }
}
