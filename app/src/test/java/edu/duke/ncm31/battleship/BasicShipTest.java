package edu.duke.ncm31.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicShipTest {
  @Test
  public void test_getOrientation(){
    Ship<Character> r1 = new RectangleShip<Character>("test_ship", new Coordinate(0, 0), 3, 1, 'd', '*');
    r1.setOrientation('H');
    r1.recordHitAt(new Coordinate(0, 0));
    assertEquals('H', r1.getOrientation());
    r1.rotateRight();
    r1 = new RectangleShip<Character>("test_ship", new Coordinate(0, 0), 3, 1, 'd', '*');
    r1.recordHitAt(new Coordinate(0, 0));
    r1.moveMyShip(2, 2);
    Iterable<Coordinate> r1set = r1.getCoordinates();
    for (Coordinate i : r1set){
      assertEquals(true, r1.occupiesCoordinates(i));
    }
  }
  
  @Test
  public void test_recordHitAt_and_wasHitAt() {
    // here we test a hit and record functions of a ship
    RectangleShip<Character> r1 = new RectangleShip<Character>("testname", new Coordinate(1,2), 2, 1, 's', '*');
    assertEquals(false, r1.wasHitAt(new Coordinate(1, 2)));
    r1.recordHitAt(new Coordinate(1, 2));
    assertEquals(true, r1.wasHitAt(new Coordinate(1,2)));
  }

  @Test
  public void test_getCoordiante(){
    // here we test the getCoordinate function
    RectangleShip<Character> r1 = new RectangleShip<Character>("test_ship", new Coordinate(0, 0), 3, 1, 'd', '*');
    Iterable<Coordinate> r1set = r1.getCoordinates();
    for (Coordinate i : r1set){
      assertEquals(true, r1.occupiesCoordinates(i));
    }
  }

  @Test
  public void test_testCoordatShip(){
    // here we test the coordinates of a ship and return a exception if not
    RectangleShip<Character> r1 = new RectangleShip<Character>("test_ship", new Coordinate(0, 0), 3, 1, 'd', '*');
    Iterable<Coordinate> r1set = r1.getCoordinates();
    for (Coordinate i : r1set){
      assertEquals(true, r1.occupiesCoordinates(i));
    }
    assertThrows(IllegalArgumentException.class, () -> r1.checkCoordinateInThisShip(new Coordinate(1, 1)));
    r1.checkCoordinateInThisShip(new Coordinate(0,1));
  }

}
