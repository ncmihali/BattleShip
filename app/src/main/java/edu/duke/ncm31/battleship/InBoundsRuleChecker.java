package edu.duke.ncm31.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  public InBoundsRuleChecker(PlacementRuleChecker<T> next){
    super(next);
  }
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    /** goes through and tests InBound rules
     * @param theShip is the ship we are checking
     * @param theBoard is the board we are using to obtain data */
    Iterable<Coordinate> shipcoord = theShip.getCoordinates();
    int max_y = theBoard.getHeight();
    int max_x = theBoard.getWidth();
    /**
     * iterator through all coordinates and make sure they follow the rules! */
    for(Coordinate temp : shipcoord){
      if(temp.getRow() < max_y && temp.getRow() >= 0 && temp.getColumn() < max_x && temp.getColumn() >= 0){
        // if in bounds, move to next battle ship coordinate
        continue;
      }
      if(temp.getRow() < 0){
        return "That placement is invalid: the ship goes off the top of the board.";
      }
      if(temp.getRow() >= max_y){
        // if the coordinate is lower than the bottom:
        return "That placement is invalid: the ship goes off the bottom of the board.";
      }
      if(temp.getColumn() >= max_x){
        // if the coordinate is past the right side (> width):
        return "That placement is invalid: the ship goes off the right of the board.";
      }
      if(temp.getColumn() < 0){
        // if the coordinate is past the left side of the board:
        return "That placement is invalid: the ship goes off the left of the board.";
      }
    }
    return null;
  }

}
