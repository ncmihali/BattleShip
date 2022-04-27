package edu.duke.ncm31.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T>{
  final String name;

  public String getName(){
    return name;
  }
  
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> shipDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo){
    // Final constructor, which passes to the parent or super construct of BasicShip
    super(makeCoords(upperLeft, width, height), shipDisplayInfo, enemyDisplayInfo);
    this.name = name;
  }

  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit){
    // This passes parameters to the above constructor
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
  }

  public RectangleShip(Coordinate upperLeft, T data, T onHit){
    // This passes parameters to above constructor
    this("testship", upperLeft, 1, 1, data, onHit);
  }
  
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height){
    /**
     * This method creates a level of coordinates for a ship to be placed in 
     * @param upperLeft is the starting Coordinate with
     * @param width and height */
    HashSet<Coordinate> ans = new HashSet<Coordinate>();
    int start_column = upperLeft.getColumn();
    int start_row = upperLeft.getRow();
    for(int i = start_row; i < start_row + height; i++){
      for(int k = start_column; k < start_column + width; k++){
        Coordinate new_coord = new Coordinate(i, k);
        ans.add(new_coord);
      }
    }

    return ans;
  }
}
