package edu.duke.ncm31.battleship;

public interface ShipDisplayInfo<T> {
  /** method to return Info at a coordiante and if it was hit
   * or not
   * @param where is the coord, and 
   * @param hit is the boolean if the ship has been hit */
  public T getInfo(Coordinate where, boolean hit);
}
