package edu.duke.ncm31.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected =
      expectedHeader+
      "A  |  A\n"+
      "B  |  B\n"+
      expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }
  // the following are also tests of various board sizes!
  @Test
  public void test_display_empty_3by2(){
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n" +
      "B  | |  B\n";
    emptyBoardHelper(3, 2, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_empty_3by5(){
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n" +
      "B  | |  B\n" +
      "C  | |  C\n" +
      "D  | |  D\n" +
      "E  | |  E\n";
    emptyBoardHelper(3, 5, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_4x3(){
    /**
     * This test tests for a ship at location B1 */
    String expectedHeader = "  0|1|2|3\n";
    String expected = expectedHeader+ 
      "A  | | |  A\n" +
      "B  |s| |  B\n" +
      "C  | | |  C\n" + expectedHeader;
    Board<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    BoardTextView view = new BoardTextView(b1);
    Coordinate c1 = new Coordinate(1, 1);
    b1.tryAddShip(new RectangleShip<Character>(c1, 's', '*'));
    assertEquals(expected, view.displayMyOwnBoard());
  }
  
  @Test
  public void test_invalid_board_size(){
    // tests invalid board sizes with throws
    Board<Character> wideBoard = new BattleShipBoard<Character>(11,20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10,27, 'X');
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_EnemyBoard(){
    /** Test dipslay for an enemy board! */
    String expectedHeader = "  0|1|2|3\n";
    String expected = expectedHeader+ 
      "A  | | |  A\n" +
      "B  |s| |  B\n" +
      "C  |s| |  C\n" + expectedHeader;
    Board<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    BoardTextView view = new BoardTextView(b1);
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s1 = factory.makeSubmarine(new Placement("B1V"));
    b1.tryAddShip(s1);
    assertEquals(expected, view.displayMyOwnBoard());
    assertEquals('s', b1.whatIsAtForSelf(new Coordinate(1, 1)));
    assertNotEquals('s', b1.whatIsAtForEnemy(new Coordinate(1, 1)));
     expected = expectedHeader+ 
      "A  | | |  A\n" +
      "B  | | |  B\n" +
      "C  | | |  C\n" + expectedHeader;
     assertEquals(expected, view.displayEnemyBoard());
  }
  
  @Test
  public void test_myBoardandEnemyBoard(){
    /** this will test both my board and the enemy's board side by side */
    String expected =
      "     player A                   player B\n" +
      "  0|1|2|3                    0|1|2|3\n" + 
      "A  |s| |  A                A  | | |  A\n" +
      "B  |s| |  B                B  | | |  B\n" +
      "C  | | |  C                C  | | |  C\n" +
      "  0|1|2|3                    0|1|2|3\n";
    Board<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    Board<Character> enemyBoard = new BattleShipBoard<Character>(4, 3, 'X');
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s1 = factory.makeSubmarine(new Placement("A1V"));
    b1.tryAddShip(s1);
    s1 = factory.makeSubmarine(new Placement("A0H"));
    enemyBoard.tryAddShip(s1);
    BoardTextView view = new BoardTextView(b1);
    BoardTextView enemyView = new BoardTextView(enemyBoard);
    assertEquals(expected, view.displayMyBoardWithEnemyNextToIt(enemyView, "player A", "player B"));
  }
  
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody){
    // helper function to create a blank board
    /** @param w and h are width and height
     * @param expectedHeader and expectedBody are combine to assertEquals check */
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

}
