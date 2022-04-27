package edu.duke.ncm31.battleship;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public abstract class BasicShip<T> implements Ship<T>{
  protected ShipDisplayInfo<T> myDisplayInfo; // my current display info
  public HashMap<Coordinate, Boolean> myPieces; // the ships pieces
  protected ShipDisplayInfo<T> enemyDisplayInfo; // enemyDisplay info
  public char shipOrientation;
  
  /*public BasicShip(Coordinate location){
    myPieces = new HashMap<Coordinate, Boolean>();
    myPieces.put(location, false);
  }*/

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo){
    /**
       @param myDisplayInfo holds info about ships being hit and locations currently
       @param where is set to myPieces */
    this.enemyDisplayInfo = enemyDisplayInfo;
    this.myDisplayInfo = myDisplayInfo;
    this.myPieces = new HashMap<Coordinate, Boolean>();
    for(Coordinate c : where){
      myPieces.put(c, false);
    }
  }

  @Override
  public void setOrientation(char orientation){
    /** set the orientation of a ship
     * @param orientation is the character orientation of user input */
    this.shipOrientation = orientation;
  }
  
  @Override
  public char getOrientation(){
    // obtain orientation of any ship
    return shipOrientation;
  }
  
  @Override
  public Iterable<Coordinate> getCoordinates(){
    // retrieve al the coordinates of a ship
    return myPieces.keySet();
  }
  
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    /** @param where is for checking if a ship occupies that coordinate */
    return (!(myPieces.get(where) == null));
  }

  @Override
  public boolean isSunk() {
    // This method checks if all parts of the ship have been hit
    for(boolean val : myPieces.values()){
      if(val) continue;
      else return false;
    }
    return true;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    /** @param where will record a hit of a ship at the coordinate */
    myPieces.remove(where);
    myPieces.put(where, true);
  }

  @Override
  public boolean wasHitAt(Coordinate where){
    /** @param where will see if the pieces were a part of the coordinate */
    return myPieces.get(where);
  }

  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    // This function returns the display info of the cordinate
    if(myShip){
      return myDisplayInfo.getInfo(where, wasHitAt(where));
    }
    else{
      return enemyDisplayInfo.getInfo(where, wasHitAt(where));
    }
  }

  @Override
  public void moveMyShip(int x_direction, int y_direction){
    /** 
     * this method will move a ship ONLY in a deltax and deltay position 
     * @param x and y direction is the delta to change by, NO rotations here */
    Set<Entry<Coordinate, Boolean>> setOfEntries = myPieces.entrySet();
    HashMap<Coordinate, Boolean> values_to_add = new HashMap<Coordinate, Boolean>();
    for(Iterator<Entry<Coordinate, Boolean>> it = setOfEntries.iterator(); it.hasNext();){
      Entry<Coordinate, Boolean> entry = it.next();
      Coordinate c = entry.getKey();
      Boolean val = entry.getValue();
      int start_row = c.getRow();
      int start_column = c.getColumn();
      int new_column = (int)(start_column + x_direction);
      int new_row = (int)(start_row + y_direction);
      if(val == true){
        it.remove();
        values_to_add.put(new Coordinate(new_row, new_column), true);
      }
      else{
        it.remove();
        values_to_add.put(new Coordinate(new_row, new_column), false);
      }
    }
    myPieces.putAll(values_to_add);
  }
  
  @Override
  public void rotateRight(){
    /**
     * this method will rotate a ship 90 degrees clockwise
     * using matrix rotation equations! */
    Set<Entry<Coordinate, Boolean>> setOfEntries = myPieces.entrySet();
    HashMap<Coordinate, Boolean> values_to_add = new HashMap<Coordinate, Boolean>();
    for(Iterator<Entry<Coordinate, Boolean>> it = setOfEntries.iterator(); it.hasNext();){
      Entry<Coordinate, Boolean> entry = it.next();
      Coordinate c = entry.getKey();
      Boolean val = entry.getValue();
      int start_row = c.getRow();
      int start_column = c.getColumn();
      // for every coordinate, replace with a new x, y position!
      int new_row = (int)((start_column*Math.sin(Math.toRadians(90.0))) + (start_row*Math.cos(Math.toRadians(90.0))));
      int new_column = (int)((start_column*Math.cos(Math.toRadians(90.0))) - (start_row*Math.sin(Math.toRadians(90.0))));
      if(val == true){
        it.remove();
        values_to_add.put(new Coordinate(new_row, new_column), true);
      }
      else{
        it.remove();
        values_to_add.put(new Coordinate(new_row, new_column), false);
      }
    }
    myPieces.putAll(values_to_add);
  }
  
  protected void checkCoordinateInThisShip(Coordinate c) {
    /** @param c is the coordinate to see if it is even on the ship */
    if(myPieces.containsKey(c)) return;
    else throw new IllegalArgumentException("Coordinate not in myPieces\n");
  }
  
}
