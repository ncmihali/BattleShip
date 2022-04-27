package edu.duke.ncm31.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  @Test
  public void test_makeShip() {
    V1ShipFactory f1 = new V1ShipFactory();
    Ship<Character> b1 = f1.makeBattleship(new Placement("A1U"));
    Ship<Character> c1 = f1.makeCarrier(new Placement("F2D"));
    Ship<Character> b2 = f1.makeBattleship(new Placement("A1L"));
    checkShip(b1, "Battleship", 'b', new Coordinate(0, 2));
    checkShip(b1, "Battleship", 'b', new Coordinate(1, 3));
    checkShip(c1, "Carrier", 'c', new Coordinate(5, 2));
    checkShip(c1, "Carrier", 'c', new Coordinate(6, 2));
    checkShip(b2, "Battleship", 'b', new Coordinate(0, 2));
    checkShip(b2, "Battleship", 'b', new Coordinate(1, 1));
    
    Ship<Character> d1 = f1.makeDestoryer(new Placement("C3H"));
    Ship<Character> s1 = f1.makeSubmarine(new Placement("D1V"));
    checkShip(d1, "Destroyer", 'd', new Coordinate(2, 3));
    checkShip(s1, "Submarine", 's', new Coordinate(3, 1));
  }

  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate expectedCoord){
    assertEquals(testShip.getName(), expectedName);
    assertEquals(testShip.occupiesCoordinates(expectedCoord), true);
    assertEquals(testShip.getDisplayInfoAt(expectedCoord, true), expectedLetter);
  }

}
