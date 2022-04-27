package edu.duke.ncm31.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T>{
  private final int width;
  private final int height;
  public ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private final HashSet<Coordinate> enemyMisses;
  final T missInfo;
  public HashMap<Coordinate, String> enemyConstantDisplay;
  
  public BattleShipBoard(int w, int h, InBoundsRuleChecker<T> placementChecker, T missInfo){
    /** this constructs the battleshipboard class variables and checks for errors
     * w is the width, and h is the height of the new board. 
     * @param w is width
     * @param h is height
     * @param placementChecker is our new rule checker
     * @param missInfo is needed in case to record a miss of the enemy ship */
    if (w <= 0){
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0){
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }

    this.missInfo = missInfo;
    this.enemyMisses = new HashSet<Coordinate>();
    this.placementChecker = placementChecker;
    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<Ship<T>>();
    this.enemyConstantDisplay = new HashMap<Coordinate, String>();
  }

  public BattleShipBoard(int w, int h, T missInfo){
    this(w, h, new InBoundsRuleChecker<T>(new NoCollisionsRuleChecker<T>(null)), missInfo);
  }

  public boolean check_if_won(){
    // this method checks if a board has lost all of its ships
    // if TRUE, means the OTHER PLAYER won the game
    for(Ship<T> temp : this.myShips){
      if(temp.isSunk()) continue;
      else return false;
    }
    return true;
  }

  public ArrayList<Ship<T>> getShips(){
    return myShips;
  }
  
  public int getWidth(){
    /* returns the width of the board */
    return width;
  }

  public int getHeight(){
    /* returns the height of the board */
    return height;
  }

  public String tryAddShip(Ship<T> toAdd){
    /** trys to add a ship to the board 
     * @param toAdd is the ship we want to add to our arsonal */
    if(placementChecker.checkPlacement(toAdd, this) == null){
      myShips.add(toAdd);
      return null;
    }
    System.out.println(placementChecker.checkPlacement(toAdd, this));
    return placementChecker.checkPlacement(toAdd, this);
  }

  public T whatIsAtForSelf(Coordinate where){
    // calls whatIsAt with boolean true for self
    /** @param where is sending info to whatIsAt(self) */
    return whatIsAt(where, true);
  }

  public T whatIsAtForEnemy(Coordinate where){
    return whatIsAt(where, false);
  }

  protected T whatIsAt(Coordinate where, boolean isSelf){
    /* returns the display of which ship is at which cordinate */
    /** @param isSelf checks if we are looking at our own board or the enemy's. */
    for(Ship<T> s: myShips){
      if(s.occupiesCoordinates(where)){
        return s.getDisplayInfoAt(where, isSelf);
      }
    } 
    for(Coordinate temp : enemyMisses){
      if(temp.equals(where)){
        if(!isSelf) return missInfo;
      }
    }
    return null;
  }

  public Ship<T> fireAt(Coordinate c){
    /** This method allows the user to shoot at anything on the board 
     * @param c is the coordinate to fire at */
    
    for(Ship<T> s : myShips){
      if(s.occupiesCoordinates(c)){
        if(enemyMisses.contains(c)){
          enemyMisses.remove(c);
        }
        enemyConstantDisplay.put(c, "M");
        s.recordHitAt(c);
        return s;
      }
    }
    enemyMisses.add(c);
    
    return null;
  }
}
