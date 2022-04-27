package edu.duke.ncm31.battleship;

public class NoCollisionsRuleChecker<T> extends PlacementRuleChecker<T> {

  public NoCollisionsRuleChecker(PlacementRuleChecker<T> next){
    super(next);
  }
  
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    // This checks if a ship has been placed in an innacurate spot, like on another ship.
    // params similar to ones in InBoundsChecker
    Iterable<Coordinate> shipcoord = theShip.getCoordinates();
    for(Coordinate temp : shipcoord){
      if(theBoard.whatIsAtForSelf(temp) != null) {
        return "That placement is invalid: the ship overlaps another ship.";
      }
    }
    return null;
  }

}
