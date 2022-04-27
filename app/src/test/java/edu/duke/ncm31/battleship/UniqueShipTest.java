package edu.duke.ncm31.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class UniqueShipTest {
  @Test
  public void test_Battleship() {
    // this will test the unique ship constructor
    UniqueShip<Character> test_ship = new UniqueShip<Character>("Battleship", new Coordinate(0, 0), 'b', 'b', '*', 'U');
    assertEquals("Battleship", test_ship.getName());
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 1)));
    assertEquals(false, test_ship.occupiesCoordinates(new Coordinate(0, 0)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(1, 0)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(1, 1)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(1, 2)));

    test_ship = new UniqueShip<Character>("Battleship", new Coordinate(0, 0), 'b', 'b', '*', 'R');
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 0)));
    assertEquals(false, test_ship.occupiesCoordinates(new Coordinate(0, 1)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(1, 0)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(1, 1)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(2, 0)));

    test_ship = new UniqueShip<Character>("Battleship", new Coordinate(0, 0), 'b', 'b', '*', 'D');
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 0)));
    assertEquals(false, test_ship.occupiesCoordinates(new Coordinate(1, 0)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 1)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 2)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(1, 1)));

    test_ship = new UniqueShip<Character>("Battleship", new Coordinate(0, 0), 'b', 'b', '*', 'L');
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 1)));
    assertEquals(false, test_ship.occupiesCoordinates(new Coordinate(0, 0)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(1, 0)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(1, 1)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(2, 1)));
  }

  @Test
  public void test_Carrier() {
    // this will test the unique ship constructor
    UniqueShip<Character> test_ship = new UniqueShip<Character>("Carrier", new Coordinate(0, 0), 'c', 'c', '*', 'U');
    assertEquals("Carrier", test_ship.getName());
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 0)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(1, 0)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(2, 0)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(3, 1)));

    test_ship = new UniqueShip<Character>("Carrier", new Coordinate(0, 0), 'c', 'c', '*', 'R');
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 1)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 2)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 3)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 4)));

    test_ship = new UniqueShip<Character>("Carrier", new Coordinate(0, 0), 'c', 'c', '*', 'D');
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 0)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(1, 0)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(2, 1)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(3, 1)));

    test_ship = new UniqueShip<Character>("Carrier", new Coordinate(0, 0), 'c', 'c', '*', 'L');
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 2)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(1, 0)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 3)));
    assertEquals(true, test_ship.occupiesCoordinates(new Coordinate(0, 4)));
  }
}
