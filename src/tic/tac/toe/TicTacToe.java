/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import java.util.Scanner;

/**
 *
 * @author Gunawan
 */
public class TicTacToe {

    // Name-constants to represent the seeds and cell contents
   public static final int EMPTY = 0;
   public static final int CROSS = 1;
   public static final int NOUGHT = 2;
 
   // Name-constants to represent the various states of the game
   public static final int PLAYING = 0;
   public static final int DRAW = 1;
   public static final int CROSS_WON = 2;
   public static final int NOUGHT_WON = 3;
 
   // The game board and the game status
   public static int ROWS, COLS; // number of rows and columns
   public static int[][] board ; // game board in 2D array
                                                      //  containing (EMPTY, CROSS, NOUGHT)
   public static int currentState;  // the current state of the game
                                    // (PLAYING, D4RAW, CROSS_WON, NOUGHT_WON)
   public static int currentPlayer; // the current player (CROSS or NOUGHT)
   public static int currntRow, currentCol; // current seed's row and column
 
   public static Scanner in = new Scanner(System.in); // the input Scanner

   /** The entry main method (the program starts here) */
   public static void main(String[] args) {
       
       boolean validInput = false;  // for input validation
    do {
        System.out.print("PLEASE ENTER SIZE OF TICTAC TOE EXAMPLE:3 :");
        ROWS = in.nextInt(); //initialize numbers of row
        COLS = ROWS; //initialize numbers of column
        
        board = new int[ROWS][COLS];
        
        if (ROWS < 3){ //Checking size of game
            System.out.println("THE ROW AND COLUMN MUST GREATER THAN 3 OR 3");
        }else if (ROWS >= 3){         
            
            // Initialize the game-board and current status
            initGame();
        
            // Play the game once
            do {
               playerMove(currentPlayer); // update currentRow and currentCol
               updateGame(currentPlayer, currntRow, currentCol); // update currentState
               printBoard();
               // Print message if game-over
               if (currentState == CROSS_WON) {
                  System.out.println("'X' won! Bye!");
               } else if (currentState == NOUGHT_WON) {
                  System.out.println("'O' won! Bye!");
               } else if (currentState == DRAW) {
                  System.out.println("It's a Draw! Bye!");
               }
               // Switch player
               currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
            } while (currentState == PLAYING); // repeat if not game-over
        }
        else{
            System.out.println("Error");
        }
    } while (!validInput);  // repeat until input is valid
   }
 
   /** Initialize the game-board contents and the current states */
   public static void initGame() {
       
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            board[row][col] = EMPTY;  // all cells empty
         }
      }
      currentState = PLAYING; // ready to play
      currentPlayer = CROSS;  // cross plays first
   }
 
   /** Player with the "theSeed" makes one move, with input validation.
       Update global variables "currentRow" and "currentCol". */
   public static void playerMove(int theSeed) {
      boolean validInput = false;  // for input validation
      do {
         if (theSeed == CROSS) {
            System.out.print("Player 'X', enter your move (row[1-"+ROWS+"] column[1-"+COLS+"]): ");
         } else {
            System.out.print("Player 'O', enter your move (row[1-"+ROWS+"] column[1-"+COLS+"]): ");
         }
         int row = in.nextInt() - 1;  // array index starts at 0 instead of 1
         int col = in.nextInt() - 1;
         if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col] == EMPTY) {
            currntRow = row;
            currentCol = col;
            board[currntRow][currentCol] = theSeed;  // update game-board content
            validInput = true;  // input okay, exit loop
         } else {
            System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                  + ") is not valid. Try again...");
         }
      } while (!validInput);  // repeat until input is valid
   }
 
   /** Update the "currentState" after the player with "theSeed" has placed on
       (currentRow, currentCol). */
   public static void updateGame(int theSeed, int currentRow, int currentCol) {
      if (hasWon(theSeed, currentRow, currentCol)) {  // check if winning move
         currentState = (theSeed == CROSS) ? CROSS_WON : NOUGHT_WON;
      } else if (isDraw()) {  // check for draw
         currentState = DRAW;
      }
      // Otherwise, no change to currentState (still PLAYING).
   }
 
   /** Return true if it is a draw (no more empty cell) */
   // TODO: Shall declare draw if no player can "possibly" win
   public static boolean isDraw() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (board[row][col] == EMPTY) {
               return false;  // an empty cell found, not draw, exit
            }
         }
      }
      return true;  // no empty cell, it's a draw
   }
 
   /** Return true if the player with "theSeed" has won after placing at
       (currentRow, currentCol) */
   public static boolean hasWon(int theSeed, int currentRow, int currentCol) {
       //System.out.println("theseed: "+theSeed);
       //System.out.println("currentRow: "+currentRow);
       //System.out.println("currentCol: "+currentCol);
       
       for(int i=0;i<ROWS;i++){ // same in the row
           
           if (board[currentRow][i] != theSeed)
               break;
           
           if (i == ROWS-1){
               return true;
           }
       }
       
       for(int i=0;i<ROWS;i++){ // same in the column
           
           if (board[i][currentCol] != theSeed)
               break;
           
           if (i == ROWS-1){
               return true;
           }
       }
       
       if (currentRow == currentCol){ // same in the diagonal
            for(int i=0;i<ROWS;i++){
                 if (board[i][i] != theSeed)
                    break;
                 
                 if (i == ROWS-1){
                    return true;
                 }
            }
       }
       
       if (currentRow + currentCol == ROWS-1){ // same in the opposite diagonal
            for(int i=0;i<ROWS;i++){
                 if (board[i][(ROWS-1)-i] != theSeed)
                    break;
                 
                 if (i == ROWS-1){
                    return true;
                 }
            }
       }
       
       return false;
   }
 
   /** Print the game board */
   public static void printBoard() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            printCell(board[row][col]); // print each of the cells
            if (col != COLS - 1) {
               System.out.print("|");   // print vertical partition
            }
         }
         System.out.println();
         if (row != ROWS - 1) {
             for (int col = 0; col < COLS; ++col) {
               System.out.print("----"); // print horizontal partition
             }
         }
         System.out.println();
      }
      System.out.println();
   }
 
   /** Print a cell with the specified "content" */
   public static void printCell(int content) {
      switch (content) {
         case EMPTY:  System.out.print("   "); break;
         case NOUGHT: System.out.print(" O "); break;
         case CROSS:  System.out.print(" X "); break;
      }
   }
    
}
