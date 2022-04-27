package edu.duke.ncm31.battleship;

import java.util.HashSet;

public class UniqueShip<T> extends BasicShip<T> {
  final String name;
  public UniqueShip(String name, Coordinate upperLeft, char shipType, char orientation, ShipDisplayInfo<T> shipDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo){
    /** this method is similar to RectangShip other than the type of ship it takes to create the new unique 
     * ship dimensions */
    super(makeCoords(upperLeft, shipType, orientation), shipDisplayInfo, enemyDisplayInfo);
    this.name = name;
  }

  public UniqueShip(String name, Coordinate upperLeft, char shipType, T data, T onHit, char orientation){
    this(name, upperLeft, shipType, orientation, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
  }

  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, char shipType, char orientation){
    /**
     * this method creates a HashSet of new coordinates for the unique ship!
     * @param upperLeft is the top left of the request placement
     * @param shipType is now the type of ship, 'b' or 'c'
     * @param orientation is now the orientation of the ship! */
    HashSet<Coordinate> ans = new HashSet<Coordinate>();
    int start_column = upperLeft.getColumn();
    int start_row = upperLeft.getRow();
    if(shipType == 'c'){
      // these first check if we are placing a carrier
      // or a battleship and then will check orientation, which
      // we will then construct the coordinates based off of the new special ship requirements!
      if(orientation == 'U'){
        for(int i = start_row; i < start_row + 5; i++){
          for(int k = start_column; k < start_column + 2; k++){
            if(i < start_row + 2 && k == start_column){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
            if(i >= start_row + 2 && i < start_row + 4){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
            if(i >= start_row + 4 && k == start_column + 1){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
          }
        }
      }
      if(orientation == 'L'){
        for(int i = start_row; i < start_row + 2; i++){
          for(int k = start_column; k < start_column + 5; k++){
            if(k >= start_column + 2 && i == start_row){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
            if(k < start_column + 4 && i == start_row + 1){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
          }
        }
      }
      if(orientation == 'R'){
        for(int i = start_row; i < start_row + 2; i++){
          for(int k = start_column; k < start_column + 5; k++){
            if(k >= start_column + 1 && i == start_row){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
            if(k < start_column + 3 && i == start_row + 1){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
          }
        }
      }
      if(orientation == 'D'){
        for(int i = start_row; i < start_row + 5; i++){
          for(int k = start_column; k < start_column + 2; k++){
            if(i < start_row + 1 && k == start_column){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
            if(i >= start_row + 1 && i < start_row + 3){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
            if(i >= start_row + 3 && k == start_column + 1){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
          }
        }
      }
    }

    /** now onto the battleship! */
    else{
      if(orientation == 'U'){
        for(int i = start_row; i < start_row + 2; i++){
          for(int k = start_column; k < start_column + 3; k++){
            if(k > start_column && i == start_row && k < start_column + 2){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
            if(i == start_row + 1){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
          }
        }
      }
      if(orientation == 'L'){
        for(int i = start_row; i < start_row + 3; i++){
          for(int k = start_column; k < start_column + 2; k++){
            if(i < start_row + 1  && k == start_column + 1){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
            if(i < start_row + 2 && i >= start_row + 1){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
            if(i > start_row + 1 && k == start_column + 1){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
          }
        }
      }
      if(orientation == 'R'){
        for(int i = start_row; i < start_row + 3; i++){
          for(int k = start_column; k < start_column + 2; k++){
            if(i < start_row + 1  && k == start_column){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
            if(i < start_row + 2 && i >= start_row + 1){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
            if(i > start_row + 1 && k == start_column){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
          }
        }
      }
      if(orientation == 'D'){
        for(int i = start_row; i < start_row + 2; i++){
          for(int k = start_column; k < start_column + 3; k++){
            if(k > start_column && i == start_row + 1 && k < start_column + 2){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
            if(i == start_row){
              Coordinate new_coord = new Coordinate(i, k);
              ans.add(new_coord);
            }
          }
        }
      }
    }
    // return the new set of ship coordinates
    return ans;
  }

  public String getName() {
    // return name
    return name;
  }
}
