package edu.duke.ncm31.battleship;

public class Coordinate {
  public  int row;
  public  int column;

  public Coordinate(String descr){
    /** construct takes a 
     * @param descr which is a input string and turns it into a 
     * new Coordinate which has both row and column*/
    String descr_final = descr.toUpperCase();
    char row_char = (descr_final.charAt(0));
    if(row_char > 'Z' || row_char < 'A'){
      throw new IllegalArgumentException("Not a valid row character!\n");
    }
    if(descr_final.length() < 2){
      throw new IllegalArgumentException("Need a column value!\n");
    }
    int column_num = 0;
    for(int i = 1; i < descr.length(); i++){ // error checking of num section
      char temp = (descr_final.charAt(i));
      if(temp > '9' || temp < '0'){
        throw new IllegalArgumentException("not a valid column number!\n");
      }
      int temp_int = Integer.parseInt(String.valueOf(temp));
      column_num += temp_int;
      column_num = column_num * 10;
    }

    column_num = column_num / 10;
    if(column_num > 10){
      throw new IllegalArgumentException("Column needs to be less than 11\n");
    }
    
    row = row_char % 65; // alphabet row detection
    column = column_num;
  }
  
  public Coordinate(int r, int c){
    // simpler constructor setting row and column
    this.row = r;
    this.column = c;
  }
  
  public int getRow(){
    // returns row
    return this.row;
  }

  public int getColumn(){
    // returns column
    return this.column;
  }

  @Override
  public boolean equals(Object o){
    // overrided equals method to check if 2 Coordinates are equal
    if(o.getClass().equals(getClass())){
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  @Override
  public String toString(){
    return "("+row+", " + column + ")";
  }

  @Override
  public int hashCode(){
    return toString().hashCode();
  }
}
