package edu.duke.ncm31.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionsRuleCheckerTest {
  @Test
  public void test_NoCollisionRule() {
    /**
     * Checks to see if a ship is about to hit another ship when being places */
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory v1 = new V1ShipFactory();
    Ship<Character> s1 = v1.makeBattleship(new Placement("A4D"));
    Ship<Character> s2 = v1.makeCarrier(new Placement("B5U"));
    b1.tryAddShip(s1);
    PlacementRuleChecker<Character> test = new NoCollisionsRuleChecker<Character>(null);
    assertNotEquals(null, test.checkPlacement(s2, b1));
    Ship<Character> s3 = v1.makeBattleship(new Placement("G1R"));
    assertEquals(null, test.checkPlacement(s3,b1));
  }

  @Test
  public void test_both_NoCollision_Bounds(){
    /**
     * Checks the chain of requirements to see if both rules work together! */
    Board<Character> b1 = new BattleShipBoard<Character>(10,15, 'X');
    V1ShipFactory v1 = new V1ShipFactory();
    Ship<Character> s1 = v1.makeBattleship(new Placement("A1U"));
    Ship<Character> s2 = v1.makeCarrier(new Placement("N2U"));
    Ship<Character> s3 = v1.makeDestoryer(new Placement("A1H"));
    PlacementRuleChecker<Character> new_rules = new InBoundsRuleChecker<Character>(new NoCollisionsRuleChecker<Character>(null));
    assertEquals(null, new_rules.checkPlacement(s1, b1));
    b1.tryAddShip(s1);
    assertNotEquals(null, new_rules.checkPlacement(s3, b1));
    b1.tryAddShip(s3);
    assertNotEquals(null, new_rules.checkPlacement(s2, b1));
  }

}
