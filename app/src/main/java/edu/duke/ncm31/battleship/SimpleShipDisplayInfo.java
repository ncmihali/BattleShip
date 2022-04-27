package edu.duke.ncm31.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  public T myData; // data of ship
  public T onHit; // data on a hit

  public SimpleShipDisplayInfo(T d, T hit){
    // sets constructor d and hit to myData and onHit
    this.myData = d;
    this.onHit = hit;
  }

  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if(hit) return onHit;
    else return myData;
  }
}
