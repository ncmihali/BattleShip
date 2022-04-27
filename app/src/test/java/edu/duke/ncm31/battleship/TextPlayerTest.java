package edu.duke.ncm31.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  @Test
  public void test_read_placement() throws IOException {
    /**
     * Creates a input stream and app to verify correct placement */
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1,2), 'V');
    expected[1] = new Placement(new Coordinate(2,8), 'H');
    expected[2] = new Placement(new Coordinate(0,4), 'V');
    /**
     * Cycles through placements off of buffer and coordinates and verfies 
     * they are all equal. */
    for(int i = 0; i < expected.length; i++){
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]);
      String byte_string = bytes.toString();
      System.out.println(byte_string);
      //assertEquals(prompt + "\n", bytes.toString()); //should print prompt and new line
      bytes.reset();
    }
  }

  @Test
  public void test_sonarScan() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(3, 4, "C2\n", bytes);
    Board<Character> test_board = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory f1 = new V1ShipFactory();
    test_board.tryAddShip(f1.makeBattleship(new Placement("A0U")));
    test_board.tryAddShip(f1.makeCarrier(new Placement("D0L")));
    test_board.tryAddShip(f1.makeDestoryer(new Placement("G5H")));
    test_board.tryAddShip(f1.makeSubmarine(new Placement("F2H")));
    player.sonarScan(test_board);
    bytes.reset();
    player = createTextPlayer(3, 4, "F2\n", bytes);
    player.sonarScan(test_board);
    bytes.reset();
    player = createTextPlayer(3, 4, "H3\n", bytes);
    player.sonarScan(test_board);
    bytes.reset();
    player = createTextPlayer(3, 4, "S5\n", bytes);
    player.sonarScan(test_board);
    bytes.reset();
    player = createTextPlayer(3, 4, "D1\n", bytes);
    player.sonarScan(test_board);
    bytes.reset();
    player = createTextPlayer(3, 4, "DD1\nA4\n", bytes);
    player.sonarScan(test_board);
    bytes.reset();
    player = createTextPlayer(3, 4, "A9\n", bytes);
    player.sonarScan(test_board);
    bytes.reset();
    assertEquals(3, player.sonar_scan_remaining);
  }

  @Test
  public void test_readInput() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(3, 4, "F2\n", bytes);
    Board<Character> test_board = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory f1 = new V1ShipFactory();
    test_board.tryAddShip(f1.makeBattleship(new Placement("A0U")));
    test_board.tryAddShip(f1.makeCarrier(new Placement("D0L")));
    test_board.tryAddShip(f1.makeDestoryer(new Placement("G5H")));
    test_board.tryAddShip(f1.makeSubmarine(new Placement("F2H")));
    assertEquals(true, player.read_coordinate_text().equals(new Coordinate(5,2)));
    bytes.reset();
    player = createTextPlayer(3, 4, "F12\nF4\n", bytes);
    assertEquals(true, player.read_coordinate_text().equals(new Coordinate(5,4)));
    bytes.reset();
 } 

  @Test
  public void test_verifyInput() throws IOException{
     ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(3, 4, "F2\n", bytes);
    assertEquals(true, player.verify_input('F'));
    assertEquals(false, player.verify_input('G'));
  }
  
  @Test
  public void test_doOnePlacement() throws IOException {
    /** 
     * This test tests the doOnePlacement method, where it will print out
     * the board after one has placed a ship. */
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(3, 4, "B2H\nB1V\n", bytes);
    player.doOnePlacement("Submarine", player.shipCreationFns.get("Submarine"));
    bytes.reset();
    String expected = "  0|1|2\n" +
      "A  | |  A\n" +
      "B  |s|  B\n" +
      "C  |s|  C\n" +
      "D  | |  D\n" +
      "  0|1|2\n";
    assertEquals(expected, (player.view).displayMyOwnBoard());
  }

  private TextPlayer createTextPlayer(int w, int h, String inputData, ByteArrayOutputStream bytes){
    // helper function to create a new text player class instance
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

}
