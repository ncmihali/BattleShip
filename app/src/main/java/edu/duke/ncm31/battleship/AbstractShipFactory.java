package edu.duke.ncm31.battleship;

/**
 * This interface represents an Abstract Factory pattern for Ship creation.
 */
public interface AbstractShipFactory<T> {
  /** Make a sumarine.
   * @param where specified the location and orientaiton of the ship
   * @ return the ship created for the sumarine. */
  public Ship<T> makeSubmarine(Placement where);

  /** Make a battleship
   * @param where and orientation of the ship
   * @return the ship. */
  public Ship<T> makeBattleship(Placement where);

  /** Make a carrier.
   * @param where specifies the location and orientation
   * @return the ship created.
   */
  public Ship<T> makeCarrier(Placement where);

  /** Make a destoryer.
   * @param where and orientation
   * @return the ship.
   */
  public Ship<T> makeDestoryer(Placement where);
}
