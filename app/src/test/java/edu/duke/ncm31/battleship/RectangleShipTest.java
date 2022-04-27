package edu.duke.ncm31.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoordinates() {
    /** 
     * This function tests the creation of coordinates */
    //RectangleShip r1 = new RectangleShip();
    HashSet<Coordinate> test = new HashSet<Coordinate>();
    test.add(new Coordinate(1, 2));
    test.add(new Coordinate(1, 3));
    test.add(new Coordinate(1, 4));
    assertEquals(test, RectangleShip.makeCoords(new Coordinate(1,2),3,1));
  }

  @Test
  public void test_constructor(){
    /**
     * This test tests the new construction of RectangleShip class */
    HashSet<Coordinate> test = new HashSet<Coordinate>();
    test.add(new Coordinate(1, 2));
    test.add(new Coordinate(1,3));
    
    RectangleShip<Character> r1 = new RectangleShip<Character>("testname",new Coordinate(1, 2), 2, 1, 's', '*');
    assertEquals(true, r1.occupiesCoordinates(new Coordinate(1, 2)));
    assertEquals(true, r1.occupiesCoordinates(new Coordinate(1, 3)));
    assertEquals(false, r1.occupiesCoordinates(new Coordinate(1,4)));
  }

  @Test
  public void test_isSunk(){
    /**
     * This function will create a new rectangleship and see if it has sunk or not */
    RectangleShip<Character> r1 = new RectangleShip<Character>("testname",new Coordinate(0, 0), 3, 1, 's', '*');
    r1.recordHitAt(new Coordinate(0, 0));
    r1.recordHitAt(new Coordinate(0, 1));
    r1.recordHitAt(new Coordinate(0, 2));
    assertEquals(true, r1.isSunk());
    RectangleShip<Character> r2 = new RectangleShip<Character>("testname",new Coordinate(0,0), 3, 1, 'b', '*');
    assertEquals(false, r2.isSunk());
  }

  @Test
  public void test_CoordonShip(){
    
    //assertThrows(IllegalArgumentException.class, () -> {r3.checkCoordinateInThisShip(new Coordinate(0, 3));});
  }

  @Test
  public void test_getDipslayInfoAt(){
    RectangleShip<Character> r1 = new RectangleShip<Character>("testname",new Coordinate(0, 0), 3, 2, 's', '*');
    r1.recordHitAt(new Coordinate(0,0));
    assertEquals("testname", r1.getName());
    
  }

}
