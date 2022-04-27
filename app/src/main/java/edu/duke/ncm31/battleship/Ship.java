package edu.duke.ncm31.battleship;

public interface Ship<T> {
  /** 
   * Get all of the Coordinates that this Ship occupies.
   * @return An Iterable with the coordinates that this Ship occupies */
  public Iterable<Coordinate> getCoordinates();
  
  /**
   * return the orientation of the ship
   */
  public char getOrientation();
  
  /**
   * set the ship's orientation */
   public void setOrientation(char orientation);
  
  /** 
   * move my ship x or y units */
  public void moveMyShip(int x, int y);
  
  /**
   * This rotates the ship 90 degrees clockwise once! */
  public void rotateRight();
  
  /**
   * Check if this ship occupies the given coordinate.
   * @param where is the Coordinate to check if this Ship occupies
   * @return true if where is inside this ship, else false. */

  public boolean occupiesCoordinates(Coordinate where);

  /**
   * Check if this ship has been  hit meaning it has been sunk.
   * @return true if it has been sunk, else false.
   */
  public boolean isSunk();

  /**
   * Make this ship record thta it has been hit at a given coordinate. 
   * The specified coordinate must be a part of the ship.
   * @param where specifies the coordinates that were hit.
   * @throws IllegalArgumentException if where is not part of the ship. */
  public void recordHitAt(Coordinate where);

  /**
   * Check if the ship was hit at a specific coordinate.
   * @param where is the coordinates to check
   * @return true if this ship was hit at the indicated coordiantes, false else.
   * @throws IllegalArgumentException fit he coordinates are not part of the ship. */
  public boolean wasHitAt(Coordinate where);

  /**
   * Return the view-specific info at the given coordiante.  This coordiante must be a par of the ship.
   * @param where is the coordiante to return info for
   * @param myShip signals if it is our own board or enemy's
   * @throws IllegalArgumentException if where is not part of the ship
   * @return the view-specific info at that coordinate. */
  public T getDisplayInfoAt(Coordinate where, boolean myShip);

  /**
   * Get the name of the Ship, like 'sumarine'.
   * @return the name of the ship */
  public String getName();
}
