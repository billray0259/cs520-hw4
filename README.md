# Instruction Notes

* support possible extensions aiming to satisfy the open/closed principle (specifically information hiding with encapsulation)
* enable individual components to be tested in isolation
* make coherent and atomic commits (in particular for the first 3 sections below), and use descriptive log messages

## Understandability 10%
* Update README to document new functionality [X]
* Generate javadoc and commit it **(take screenshot of javadoc)** [X]
* Incremental commits about modifications

## Extensibility 30% [X]
* Functionality to play against human or computer player
* RowGameController applies either Strategy design pattern or template method design pattern
  * The helper method is the move method
* If playing human
  * Use original move method
* If playing computer
  * User modified move method

* User is player 1

## Testability 10%
* Regression test ensuring existing 9 tests pass [X]
* New test:
  * Against a computer player: After performing a legal move, the game is updated appropriately.
* Applies test case template from test driven development lecture
* **(take screenshot of tests passing)**

## Debuggability 40%
* Add breakpoint for undo **(take screenshot of breakpoint)**
* Run in debug mode
* Perform these actions in the game:
  * Move **(take screenshot of variables view showing components are set appropriately after calling move)**
  * Undo **(take screenshot of variables view showing components are empty again after calling undo)**

## Deliverables 10%
* submit .zip to gradescope named hw4.zip containing
  * tictactoe folder
    * updated source files
    * test cases
    * git log
    * NOT .git folder
  * debugging.pdf
    * 5 screenshots
* App compiles and runs
* test suite compiles, runs, and all tests pass