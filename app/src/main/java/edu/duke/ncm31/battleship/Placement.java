package edu.duke.ncm31.battleship;

public class Placement {
  public final Coordinate  where;
  public final char  orientation;

  public Placement(String input){
    /** @param input is our input palcement coordiante with orientation
     * this constructor finds the coordiante and orientatin out of input and rules checks */
    String input_final = input.toUpperCase();
    String cord = input_final.substring(0, 2);
    Coordinate c1 = new Coordinate(cord);
    where = c1;
    char orient = input_final.charAt(2);
    // check orientations for new U, D, L, and R
    if(orient != 'V' && orient != 'H' && orient != 'U' && orient != 'D' && orient != 'L' && orient != 'R'){
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.\n");
    }
    orientation = orient;
  }
  
  public Placement(Coordinate where, char  orientation){
    /** another way to construct Placement is to just give it a where and orientation
     * @param where and orientation are the inputs that are set with orientation rule checked */
    char orientation_new = Character.toUpperCase(orientation);
    if (orientation_new != 'V' && orientation_new != 'H' && orientation_new != 'U' && orientation_new != 'D' && orientation_new != 'L' && orientation_new != 'R'){
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.\n");
    }
    this.where = where;
    this.orientation = orientation_new;
  }

  public Coordinate getWhere(){
    // return coordiante where
    return this.where;
  }

  public char getOrientation(){
    // return char orientation
    return this.orientation;
  }

  @Override
  public boolean equals(Object o){
    if(o.getClass().equals(getClass())){
      Placement p = (Placement) o;
      return where.equals(p.where) && orientation == p.orientation;
    }
    return false;
  }

  @Override
  public String toString(){
    return "" + orientation;
  }

  @Override
  public int hashCode(){
    return toString().hashCode();
  }
}
