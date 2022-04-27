package edu.duke.ncm31.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character> {

  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    // Create the submarine ship
    return createShip(where, 1, 2, 's', "Submarine");
  }

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    // Create the Battleship
    return createShip(where, 1, 4, 'b', "Battleship");
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    // Create the carrier ship
    return createShip(where, 1, 6, 'c', "Carrier");
  }

  @Override
  public Ship<Character> makeDestoryer(Placement where) {
    // Create the destroyer ship
    return createShip(where, 1, 3, 'd', "Destroyer");
  }

  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name){
    /** this helper function creates the ship
     * @param w and h are the width and height of the ship
     * @param letter is the letter for the ship on board 
     * @param name is the name of the ship */
    if(letter == 's' || letter == 'd'){
      if(where.getOrientation() == 'V'){
        RectangleShip<Character> new_ship = new RectangleShip<Character>(name, where.getWhere(), w, h, letter, '*');
        return new_ship;
      }
      else{
        RectangleShip<Character> new_ship = new RectangleShip<Character>(name, where.getWhere(), h, w, letter, '*');
        return new_ship;
      }
    }
    else{
      /** here I make the unique ships for carriers and battleships: */
      if(where.getOrientation() == 'U'){
        UniqueShip<Character> new_ship = new UniqueShip<Character>(name, where.getWhere(), letter, letter, '*', 'U');
        return new_ship;
      }
      if(where.getOrientation() == 'R'){
        UniqueShip<Character> new_ship = new UniqueShip<Character>(name, where.getWhere(), letter, letter, '*', 'R');
        return new_ship;
      }
      if(where.getOrientation() == 'L'){
        UniqueShip<Character> new_ship = new UniqueShip<Character>(name, where.getWhere(), letter, letter, '*', 'L');
        return new_ship;
      }
      else{
        UniqueShip<Character> new_ship = new UniqueShip<Character>(name, where.getWhere(), letter, letter, '*', 'D');
        return new_ship;
      }
    }
  }
  
}
