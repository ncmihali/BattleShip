package edu.duke.ncm31.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

public class TextPlayer {
  final ArrayList<String> shipsToPlace; // player's ships
  public HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  final AbstractShipFactory<Character> shipFactory;
  public Board<Character> theBoard; // player's board
  final BoardTextView view; // player's view
  final BufferedReader inputReader;
  final PrintStream out;
  final String name; // player's name
  public int move_ship_remaining; // number of remaining moves for move ship
  public int sonar_scan_remaining; // number of sonar scans remaining for player
  public int are_computer; // check if player is a computer
  
  public TextPlayer(String name, Board<Character> board, BufferedReader input, PrintStream out, AbstractShipFactory<Character> shipFactory){
    // default constructor
    /** @param name is the name of the player
     * @param board is the player's board
     * @param input and out are the IO handlers */
    this.name = name;
    this.theBoard = board;
    this.inputReader = input;
    this.out = out;
    this.shipFactory = shipFactory;
    this.view = new BoardTextView(theBoard);
    this.shipsToPlace = new ArrayList<String>();
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    this.move_ship_remaining = 3;
    this.sonar_scan_remaining = 3;
    setupShipCreationMap(); // run both setupshipcreationmap and list to add ships to the user inventory
    setupShipCreationList();
    are_computer = 0; // default to a human player
  }

  protected Coordinate coordinateTopLeft(Ship<Character> ship){
    /**
     * this method will find the actual top left coordinate of any ship
     * @param ship is the ship we want to find the top left of */
    Coordinate top_left = new Coordinate(19,9);
    int smallest_row = 20;
    int smallest_column = 20;
    for(Coordinate i : ship.getCoordinates()){ // scan through coords and find top_left
      if (i.getRow() < top_left.getRow() || i.getColumn() < top_left.getColumn()){
        top_left.column = i.column;
        top_left.row = i.row;
      }
      if (i.getColumn() < smallest_column) smallest_column = i.getColumn();
      if (i.getRow() < smallest_row) smallest_row = i.getRow();
    }
    // after, check the battleship and carrier test cases where their top lef's are "outside of the ship"
    if(ship.getName() == "Battleship" && (ship.getOrientation() == 'U' || ship.getOrientation() == 'L')){
      if(ship.getOrientation() == 'U'){
        if(top_left.column > smallest_column){
          top_left.column -= 1;
        }
        else top_left.row -= 1;
      }
      else{
        if(top_left.column > smallest_column){
          top_left.column -= 1;
        }
        else top_left.row -= 1;
      }
    }
    if(ship.getName() == "Carrier" && (ship.getOrientation() == 'R' || ship.getOrientation() == 'L')){
      if(ship.getOrientation() == 'R'){
        if(top_left.column > smallest_column){
          top_left.column -= 1;
        }
        else top_left.row -= 1;
      }
      else{
        if(top_left.column > smallest_column){
          top_left.column -= 2;
        }
        else top_left.row -= 1;
      }
    }
    return top_left; // return the top_left coordiante
  }
  
  protected void setupShipCreationMap(){
    /** This method creates a mapping for each type
     * of ship */
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestoryer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
  }

  protected void setupShipCreationList(){
    /** This method adds differnt ships to the players arsonol */
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
  }
  
  public Placement readPlacement(String prompt) throws IOException{
    // Print prompt and reads in user placement
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  public Coordinate read_coordinate_text() throws IOException{
    // this method reads just a coordiante from input and turns it into a new Coordainte
    out.println("Where would you like to fire at?");
    String s = inputReader.readLine().toUpperCase();
    while(s.length() > 2 || s.isBlank() || s.isEmpty() || s.charAt(0) < 'A' || s.charAt(0) > 'U' || Integer.parseInt(String.valueOf(s.charAt(1))) > 9){
      out.println("Please enter a valid coordinate");
      s = inputReader.readLine().toUpperCase();
    }
    Coordinate c = new Coordinate(s);
    return c;
  }

  protected boolean verify_input(char input){
    if(input != 'F' && input != 'M' && input != 'S') return false;
    return true;
  }

  protected void moveShip() throws IOException{
    /** 
     * this is the moveShip method where if the user selects 'M', it will
     * prompt to this method asking where they want to place a selected ship */
    out.println("Which ship would you like to move?");
    String s = inputReader.readLine().toUpperCase();
    while(s.length() > 2 || s.isBlank() || s.isEmpty() || s.charAt(0) < 'A' || s.charAt(0) > 'T' || Integer.parseInt(String.valueOf(s.charAt(1))) > 9){
      out.println("Please enter a valid coordinate");
      s = inputReader.readLine().toUpperCase();
    }
    Coordinate c = new Coordinate(s);
    ArrayList<Ship<Character>> to_be_added = new ArrayList<Ship<Character>>(); // create an alterante ArrayLIst of the ship wanting to be moved!
    for(Iterator<Ship<Character>> temp = this.theBoard.getShips().iterator(); temp.hasNext();){
      // iterate over all of the ships to see if a ship was selected by the user's coordinate
      Ship<Character> val = temp.next();
      if(val.occupiesCoordinates(c)){
        // if so, choose a new coordinate and orientation to place the ship given the rule checkers!
        Placement new_placement = readPlacement("Player " + name + " where would you like to put your " + val.getName());
        PlacementRuleChecker<Character> rule_checker = new InBoundsRuleChecker<Character>(new NoCollisionsRuleChecker<Character>(null));
        V1ShipFactory v1 = new V1ShipFactory();
        Ship<Character> test_ship;
        if(val.getName() == "Battleship"){
          test_ship = v1.makeBattleship(new_placement);
        }
        else if(val.getName() == "Carrier"){
          test_ship = v1.makeCarrier(new_placement);
        }
        else if(val.getName() == "Destroyer"){
          test_ship = v1.makeDestoryer(new_placement);
        }
        else{
          test_ship = v1.makeSubmarine(new_placement);
        }
        
        if(rule_checker.checkPlacement(test_ship, theBoard) != null){
          out.println("This is not a valid placement, please try a new placement");
          new_placement = readPlacement("Player " + name + " where would you like to put your " + val.getName());
          if(val.getName() == "Battleship") test_ship = v1.makeBattleship(new_placement);
          else if(val.getName() == "Carrier") test_ship = v1.makeCarrier(new_placement);
          else if(val.getName() == "Destroyer") test_ship = v1.makeDestoryer(new_placement);
          else test_ship = v1.makeSubmarine(new_placement);
        }
        /** here, this will check the previous ships orientation with the new
         * requested orientation and make the respective rotations of the ship
         * moving all coordinates INCLUDING the hit coordinates of the ship relative
         * to its original position! */
        if(new_placement.getOrientation() == 'V' && val.getOrientation() == 'H'){
          val.rotateRight();
          val.setOrientation('V');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'V' && val.getOrientation() == 'V'){
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'H' && val.getOrientation() == 'H'){
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'H' && val.getOrientation() == 'V'){
          val.rotateRight();
          val.rotateRight();
          val.rotateRight();
          val.rotateRight();
          val.rotateRight();
          val.setOrientation('H');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'U' && val.getOrientation() == 'R'){ // right then up
          val.rotateRight();
          val.rotateRight();
          val.rotateRight();
          val.setOrientation('U');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'U' && val.getOrientation() == 'D'){ // down then up
          val.rotateRight();
          val.rotateRight();
          val.setOrientation('U');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'U' && val.getOrientation() == 'L'){ // left then up
          val.rotateRight();
          val.setOrientation('U');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'R' && val.getOrientation() == 'U'){ // up then to the right
          val.rotateRight();
          val.setOrientation('R');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'R' && val.getOrientation() == 'D'){ // from down to right MAY BE WRONG
          val.rotateRight();
          val.setOrientation('R');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'R' && val.getOrientation() == 'L'){ // from left to right
          val.rotateRight();
          val.rotateRight();
          val.setOrientation('R');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row); 
        }
        else if(new_placement.getOrientation() == 'D' && val.getOrientation() == 'U'){ // from up to down
          val.rotateRight();
          val.rotateRight();
          val.setOrientation('D');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'D' && val.getOrientation() == 'R'){ // from right to down
          val.rotateRight();
          val.setOrientation('D');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'D' && val.getOrientation() == 'L'){ // from left to down
          val.rotateRight();
          val.rotateRight();
          val.rotateRight();
          val.setOrientation('D');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'L' && val.getOrientation() == 'U'){ // first up then left
          val.rotateRight();
          val.rotateRight();
          val.rotateRight();
          val.setOrientation('L');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'L' && val.getOrientation() == 'R'){ // from right to left
          val.rotateRight();
          val.rotateRight();
          val.setOrientation('L');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'L' && val.getOrientation() == 'D'){ // from down to left
          val.rotateRight();
          val.setOrientation('L');
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'D' && val.getOrientation() == 'D'){ // stay the same orientation
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'L' && val.getOrientation() == 'L'){ // stay the same oreintation
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'R' && val.getOrientation() == 'R'){ // stay the same orientation
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        else if(new_placement.getOrientation() == 'U' && val.getOrientation() == 'U'){ // stay the same orientation
          Coordinate top_left = coordinateTopLeft(val);
          Coordinate new_top_left = new_placement.getWhere();
          int move_row = new_top_left.getRow() - top_left.getRow();
          int move_column = new_top_left.getColumn() - top_left.getColumn();
          val.moveMyShip(move_column, move_row);
        }
        
        to_be_added.add(val); // after all respective rotations, we add the ship back to the board
      }
    }
    this.theBoard.getShips().addAll(to_be_added);
    out.print(view.displayMyOwnBoard()); // print board as requested
  }

  protected void sonarScan(Board<Character> enemyBoard) throws IOException{
    /**
     * this method achieves the sonar scan
     * @param enemyBoard is the enemy's board where we can collect
     * data to share with you */
    out.println("Where would you like to sonar scan?");
    String s = inputReader.readLine().toUpperCase();
    while(s.length() > 2 || s.isBlank() || s.isEmpty() || s.charAt(0) < 'A' || s.charAt(0) > 'T' || Integer.parseInt(String.valueOf(s.charAt(1))) > 9){
      out.println("Please enter a valid coordinate");
      s = inputReader.readLine().toUpperCase();
    }
    Coordinate c = new Coordinate(s);
    int submarine_count = 0; // enemy ship counts
    int destroyer_count = 0;
    int battleship_count = 0;
    int carrier_count = 0;
    int k = 0;
    for(int i = c.getColumn() - 3; i < c.getColumn() + 4; i++){
      if(i < 0 || i > 9){
        if(i < c.getColumn()){
          k += 1;
        }
        else k -= 1;
        continue;
      }
      if(i > c.getColumn()) k -= 1;
      if(i <= c.getColumn() && i > c.getColumn() - 3) k += 1;
      for(int j = c.getRow() - k; j < c.getRow() + k + 1; j++){
        if(j > enemyBoard.getHeight()){
          continue;
        }
        if(j < 0){
          continue;
        }
        for(Ship<Character> it : enemyBoard.getShips()){
          if(it.occupiesCoordinates(new Coordinate(j, i))){
            if(it.getName() == "Battleship") battleship_count += 1;
            if(it.getName() == "Carrier") carrier_count += 1;
            if(it.getName() == "Submarine") submarine_count += 1;
            if(it.getName() == "Destroyer") destroyer_count += 1;
          }
        }
      }
      
    }
    out.println("Submarines occupy " + submarine_count + " squares"); // print out all of our counts!
    out.println("Destroyers occupy " + destroyer_count + " squares");
    out.println("Battleships occupy " + battleship_count + " squares");
    out.println("Carriers occupy " + carrier_count + " squares");
  }
  
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, Character which_player_turn) throws IOException{
    /** this method handles one turn,
     * @param enemyBoard is the current player's enemyBoard
     * @param enemyView is the current player's enemyView
     * @param which_player is the variable which decides who's turn it currently is */
    if(which_player_turn == 'A'){
      System.out.print("\nPlayer " + name + "'s turn:\n");
    
        out.print(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Player B's ocean"));
        out.println("Possible actions for Player " + name + ":\n");
        out.println("F Fire at a square");
        out.println("M Move a ship to another square (" + this.move_ship_remaining + " remaining)");
        out.println("S Sonar scan (" + this.sonar_scan_remaining + " remaining)\n");
        String input = inputReader.readLine().toUpperCase();
        // now a player can choose to type F, M, or S, where F is our normal firing method
        while(!verify_input(input.charAt(0)) || (input == null) || (input.charAt(0) == 'M' && move_ship_remaining <= 0) || (input.charAt(0) == 'S' && sonar_scan_remaining <= 0)){
          // checks if we ran out of turns for a move and requests a new input
          out.println("Need to type 'F', 'M', 'S' or you ran out of turns for a move!"); 
          input = inputReader.readLine().toUpperCase();
        }
        if(input.charAt(0) == 'F'){
          Coordinate hit_coord = read_coordinate_text();
          if(enemyBoard.fireAt(hit_coord) != null) {
            out.println("You hit a " + enemyBoard.fireAt(hit_coord).getName() + "!");
          }
          else out.println("You missed!");
        }
        else if(input.charAt(0) == 'M'){
          this.move_ship_remaining -= 1;
          moveShip();
        }
        else if(input.charAt(0) == 'S'){
          this.sonar_scan_remaining -= 1;
          sonarScan(enemyBoard);
        }
    }
    
    if(which_player_turn == 'B'){
      System.out.print("\nPlayer " + name + "'s turn:\n");
        out.print(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Player A's ocean"));
        out.println("Possible actions for Player " + name + ":\n");
        out.println("F Fire at a square");
        out.println("M Move a ship to another square (" + this.move_ship_remaining + " remaining)");
        out.println("S Sonar scan (" + this.sonar_scan_remaining + " remaining)\n");
        String input = inputReader.readLine().toUpperCase();
        
        while(!verify_input(input.charAt(0)) || (input == null) || (input.charAt(0) == 'M' && move_ship_remaining <= 0) || (input.charAt(0) == 'S' && sonar_scan_remaining <= 0)){
          out.println("Need to type 'F', 'M', or 'S' or you ran out of turns for a move!");
          input = inputReader.readLine().toUpperCase(); 
        }
      
        if(input.charAt(0) == 'F'){
          Coordinate hit_coord = read_coordinate_text();
          if(enemyBoard.fireAt(hit_coord) != null) out.println("You hit a " + enemyBoard.fireAt(hit_coord).getName() + "!");
          else out.println("You missed!");
        }
        else if(input.charAt(0) == 'M'){
          this.move_ship_remaining -= 1;
          moveShip();
        }
        else if(input.charAt(0) == 'S'){
          this.sonar_scan_remaining -= 1;
          sonarScan(enemyBoard);
        }
    }
  }

  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException{
    // This method creates a new ship based on user input and places
    // it on theBoard
    Placement new_placement = readPlacement("Player " + name + " where would you like to put your " + shipName);
    while ((shipName == "Battleship" || shipName == "Cruiser") && (new_placement.getOrientation() != 'U' && new_placement.getOrientation() != 'D' && new_placement.getOrientation() != 'L' && new_placement.getOrientation() != 'R')){
      new_placement = readPlacement("Please try a valid coordinate and orientation for this ship!");
    }
    Ship<Character> new_ship = createFn.apply(new_placement);
    
    new_ship.setOrientation(new_placement.getOrientation());
    while(theBoard.tryAddShip(new_ship) != null){
      new_placement = readPlacement("Player " + name + " where would you like to put your " + shipName);
      new_ship = createFn.apply(new_placement);
      new_ship.setOrientation(new_placement.getOrientation());
    }
    out.print(view.displayMyOwnBoard());
  }
}
