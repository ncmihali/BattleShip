package edu.duke.ncm31.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_ShipDisplayInfo() {
    SimpleShipDisplayInfo<Coordinate> i = new SimpleShipDisplayInfo<Coordinate>(new Coordinate(1,2), new Coordinate(2,2));
    assertEquals(new Coordinate(1,2), i.myData);
    assertEquals(new Coordinate(2,2), i.onHit);
    assertEquals(i.onHit, i.getInfo(i.myData, true));
    assertEquals(i.myData, i.getInfo(i.myData, false));
  }

}
