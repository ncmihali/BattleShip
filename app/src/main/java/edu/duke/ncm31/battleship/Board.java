package edu.duke.ncm31.battleship;

import java.util.ArrayList;

public interface Board<T> {
  // obtain the list of ships on board
  public ArrayList<Ship<T>> getShips();
  
  // obtain the width of the board
  public int getWidth();

  // obtain the height of the board
  public int getHeight();

  // checks if the other player won
  public boolean check_if_won();

  // obtains info on 'my' board
  public T whatIsAtForSelf(Coordinate where);

  // obtains info of 'enemy' board
  public T whatIsAtForEnemy(Coordinate where);

  // try's to add ship with no errors
  public String tryAddShip(Ship<T> toAdd);

  // method to fire at a coordiante of this's ship
  public Ship<T> fireAt(Coordinate c);
}
