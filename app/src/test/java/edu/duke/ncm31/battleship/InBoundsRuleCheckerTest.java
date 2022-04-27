package edu.duke.ncm31.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_InBounds() {
    /**
     * This checks for if a ship is in bounds of the board */
    V1ShipFactory v1 = new V1ShipFactory();
    Board<Character> board1= new BattleShipBoard<Character>(10,20, 'X');
    
    Ship<Character> s1 = v1.makeBattleship(new Placement("A2D"));
    Ship<Character> s2 = v1.makeCarrier(new Placement("B9R"));
    Ship<Character> s4 = v1.makeCarrier(new Placement(new Coordinate(-3, 4), 'L'));
    Ship<Character> s5 = v1.makeBattleship(new Placement(new Coordinate(4, -1), 'L'));
    PlacementRuleChecker<Character> newTest = new InBoundsRuleChecker<Character>(null);
    assertEquals(null, newTest.checkPlacement(s1, board1)); // correct placement
    assertNotEquals(null, newTest.checkPlacement(s4, board1));
    assertNotEquals(null, newTest.checkPlacement(s5, board1));
    assertNotEquals(null, newTest.checkPlacement(s2, board1)); // incorrect placement
    Ship<Character> s3 = v1.makeBattleship(new Placement("I3D")); // incorrect placement
    assertEquals(null, newTest.checkPlacement(s3, board1));
  }

}
