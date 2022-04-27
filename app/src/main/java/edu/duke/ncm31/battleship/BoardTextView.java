package edu.duke.ncm31.battleship;

import java.util.function.Function;

public class BoardTextView {
  /* This class handles text diplay of the board, one for the player's and 
   * one for the enemy's */

  private final Board<Character> toDisplay;
  public String[][] enemyDisplayArray;
  /**
   * this constructs a BoardView, given the board it will display.
   * @param toDisplay is the board to display. 
   * @throws IllegalArgumentException if the board is larger than 10x26. */
  public BoardTextView(Board<Character> toDisplay){
    this.enemyDisplayArray = new String[toDisplay.getHeight()][toDisplay.getWidth()];
    this.toDisplay = toDisplay;
    if(toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26){
      throw new IllegalArgumentException("Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
 }

  public String displayMyOwnBoard(){
    // calls displayAnyBoard with what is at for this
    return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
  }
  
  public String displayEnemyBoard(){
    // calls displayAnyBoard for enemy's board
    return displayAnyBoard((c)->toDisplay.whatIsAtForEnemy(c));
  }
  
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn){
    /** this function creates anyboard for the enmy or self
     * @param getSquareFn is our functional parameter that we use to send enemy or our own data */
    StringBuilder ans = new StringBuilder(makeHeader());
    char currentLetter = 'A';
    for(int row = 0; row < toDisplay.getHeight(); row++){
      StringBuilder temp = new StringBuilder("");
      temp.append(currentLetter);
      temp.append(" ");
      String sep = "";
      for(int column = 0; column < toDisplay.getWidth(); column++){
        temp.append(sep);
        Coordinate temp_coord = new Coordinate(row, column);
        if(getSquareFn.apply(temp_coord) != null){
          temp.append(getSquareFn.apply(temp_coord));
        }
        else{
          temp.append(" ");
        }
        sep = "|";
      }
      temp.append(" ");
      temp.append(currentLetter);
      currentLetter += 1;
      temp.append("\n");
      ans.append(temp);
    }
    StringBuilder temp2 = new StringBuilder(makeHeader());
    ans.append(temp2);
    return (ans.toString()).toString(); // return the string of our stringBuilder
  }

  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader){
    /** this method displays both boards next to each other
     * @param enemyView is the other board's view
     * @param myHeader is the header that the current player sees
     * @param enemyHeader is the other player's 'ocean' */
    String [] myLines = this.displayMyOwnBoard().split("\n"); // break strings into arrays
    String [] enemyLines = enemyView.displayEnemyBoard().split("\n");
    int width = this.toDisplay.getWidth();
    int height = this.toDisplay.getHeight();
    StringBuilder ans = new StringBuilder();
    ans.append("     " + myHeader);
    for(int i = (2*width) + 3; i < (2 * width) + 22; i++){
      ans.append(" "); // add spaces in between headers
    }
    ans.append(enemyHeader + "\n");
    int k = 0;
    while(height + 2 > 0){
      ans.append(myLines[k]);
      for(int i = (2*width) + 3; i < (2 * width) + 19; i++){
        ans.append(" "); // add spaces in between boards
      }
      if(k == 0){
        ans.append("  " + enemyLines[k] + "\n");
      }
      else if(k == enemyView.toDisplay.getHeight() + 1){
        ans.append("  " + enemyLines[k] + "\n");
      }
      else{
        ans.append(enemyLines[k] + "\n");
      }
      k += 1;
      height -= 1;
    }
    return ans.toString();
  }

  String makeHeader(){
    // This creates the older header 0,1,2,3... 
    StringBuilder ans = new StringBuilder("  "); //show 2 spaces
    String sep = ""; //nothing to | to separate vals
    for(int i = 0; i < toDisplay.getWidth(); i++){
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }
}
