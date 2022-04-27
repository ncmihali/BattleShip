package edu.duke.ncm31.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  /* the following contains tests, testing the width and height of our Board code */
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_getShips(){
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory v1 = new V1ShipFactory();
    Ship<Character> s1 = v1.makeCarrier(new Placement("A2R"));
    Ship<Character> s2 = v1.makeBattleship(new Placement("G3U"));
    Ship<Character> s3 = v1.makeBattleship(new Placement("I1D"));
    assertEquals(null, b1.tryAddShip(s1));
    assertEquals(null, b1.tryAddShip(s2));
    assertEquals(null, b1.tryAddShip(s3));
    for(Ship<Character> i : b1.getShips()){
      assertEquals(i.getName() == "Carrier" || i.getName() == "Battleship", true);
    }
  }
  
  @Test
  public void test_invalid_dimensions(){
    /* this tests for invalid board dimesnsions */
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
  }

  @Test
  public void test_addShip(){
    /* tests to add a ship given the new placement rules! */
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory v1 = new V1ShipFactory();
    Ship<Character> s1 = v1.makeCarrier(new Placement("A2R"));
    Ship<Character> s2 = v1.makeBattleship(new Placement("G3U"));
    Ship<Character> s3 = v1.makeBattleship(new Placement("I1D"));
    Ship<Character> s4 = v1.makeDestoryer(new Placement("A9H"));
    Ship<Character> s5 = v1.makeBattleship(new Placement("C9U"));
    assertEquals(null, b1.tryAddShip(s1));
    assertEquals(null, b1.tryAddShip(s2));
    assertEquals(null, b1.tryAddShip(s3));
    assertNotEquals(null, b1.tryAddShip(s4));
    assertNotEquals(null, b1.tryAddShip(s5));
  }
  
  @Test
  public void testWhatIsAtBoard(){
    // this test the whatIsAt methods
    Board<Character> b1 = new BattleShipBoard<Character>(3, 3, 'X');
    char[][] expected = new char[3][3];
    for(int i = 0; i < 3; i++){
      for(int k = 0; k < 3; k++){
        expected[i][k] = ' ';
      }
    }
    Coordinate c1 = new Coordinate(1, 1);
    Ship<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
    //b1.tryAddShip(s1);
    expected[1][1] = 's';
    //checkWhatIs1, null);
    for(int i = 0; i < b1.getHeight(); i++){
      for(int k = 0; k < b1.getWidth(); k++){
        Coordinate c2 = new Coordinate(i, k);
        assertEquals(null, b1.whatIsAtForSelf(c1));
      }
    }
    b1.tryAddShip(s1);
    Coordinate c2 = new Coordinate(1, 1);
    assertEquals('s', b1.whatIsAtForSelf(c2));
  }

  @Test
  public void test_whatIsAt(){
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory v1 = new V1ShipFactory();
    Ship<Character> s1 = v1.makeSubmarine(new Placement("A2V"));
    Ship<Character> s2 = v1.makeBattleship(new Placement("F3U"));
    b1.tryAddShip(s2);
    b1.tryAddShip(s1);
    assertSame(s1, b1.fireAt(new Coordinate(0, 2)));
    assertSame(s2, b1.fireAt(new Coordinate(5 ,4)));
    assertSame(null, b1.fireAt(new Coordinate(1, 5)));
    assertSame(null, b1.fireAt(new Coordinate(1, 8)));
    assertEquals('*', b1.whatIsAtForSelf(new Coordinate(0, 2)));
    assertEquals('s', b1.whatIsAtForEnemy(new Coordinate(0, 2)));
    assertEquals('X', b1.whatIsAt(new Coordinate(1, 5), false));
    assertEquals(null, b1.whatIsAt(new Coordinate(1, 5), true));
  }
  
  @Test
  public void test_fireAt(){
    // this will test to see if our fireAt method works
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory v1 = new V1ShipFactory();
    Ship<Character> s1 = v1.makeSubmarine(new Placement("A2V"));
    Ship<Character> s2 = v1.makeBattleship(new Placement("F3U"));
    b1.tryAddShip(s2);
    b1.tryAddShip(s1);
    assertSame(s1, b1.fireAt(new Coordinate(0, 2)));
    assertSame(s2, b1.fireAt(new Coordinate(5 ,4)));
    assertSame(null, b1.fireAt(new Coordinate(1, 5)));
    assertEquals('*', b1.whatIsAtForSelf(new Coordinate(0, 2)));
    assertEquals('s', b1.whatIsAtForEnemy(new Coordinate(0, 2)));
    assertEquals('X', b1.whatIsAtForEnemy(new Coordinate(1, 5)));
  }

  @Test
  public void test_if_won(){
    /** tests if all ships have been hit and the player won */
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory v1 = new V1ShipFactory();
    Ship<Character> s1 = v1.makeSubmarine(new Placement("A2V"));
    Ship<Character> s2 = v1.makeSubmarine(new Placement("A3V"));
    b1.tryAddShip(s2);
    b1.tryAddShip(s1);
    b1.fireAt(new Coordinate(0, 2));
    b1.fireAt(new Coordinate(1 ,2));
    b1.fireAt(new Coordinate(0, 3));
    assertEquals(false, b1.check_if_won());
    b1.fireAt(new Coordinate(1, 3));
    assertEquals(true, b1.check_if_won());
  }
  /*
  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expect){
    for(int i = 0; i < b.getHeight(); i++){
      for(int k = 0; k < b.getWidth(); k++){
        Coordinate c1 = new Coordinate(i, k;)
        assertEquals(null, b.whatIsAt(c1);
      }
    }
  }
  */
}
