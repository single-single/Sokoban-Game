# COMP2013_CW_scysx1
##### Name: Shiqi XIN
##### ID: 20125731


### How to compile the code
###### This project is built with gradle 6.5.1, developing on intelliJ IDEA 2020.2 on Mac.
###### To compile and run this project, you only need to configure gradle and use intelliJ IDEA to open the project to run normally.


### The javadoc path here
###### To view Javadocs, just open comp2013_cw_scysx1/Javadocs/index.html


### Implemented features list

####  Start scene:
###### A start scene.
###### Name input text field.
###### Button to move to game.
###### Colour choice box.
###### Label changes with the selected colour.

####  Game scene(View):
###### Button to pause the game.
###### Real-time move count and time count.
###### Change the direction of the keeper as move.

####  Game scene(Menu):
###### File: Load file.
###### File: Save file.
###### File: Exit the game.
###### Level: Undo.
###### Level: ToggleMusic.
###### Level: ToggleDebug.
###### Level: Reset Level.

#### Game scene(Others)
###### An extra level of game.
###### Permanent high score lists sort by time and moves.
###### Dialog of current level high score list.
###### Next level button on dialog box when finishing the current level.
###### Exit button on dialog box when finishing the game.


### Not implemented features list
###### Nothing in the marking scheme. All requirements are achieved.


### New class
###### -[Model](src/main/java/sample/mvc/Model.java) class: All the code about function implementation in the original Main has been transferred here, and a series of new functions have been added.
###### -[SubModel](src/main/java/sample/mvc/SubModel.java) class: Some non-core functions are separated from the Model and put here.
###### -[View](src/main/java/sample/mvc/View.java) class: All the code related to the display in the original Main has been transferred here, and the start interface and some styles have been added.


### Modified class
###### -[GameGrid](src/main/java/sample/start/GameGrid.java) class: Removed useless methods.
###### -[Level](src/main/java/sample/start/Level.java) class: Removed useless methods, and made some refactoring to adapt to the new features in StartMeUp.
###### -[StartMeUp](src/main/java/sample/start/StartMeUp.java) class: Added a series of methods to support the Model, and deleted the useless methods.
###### -[Main](src/main/java/sample/Main.java) class: Refactor it, split it into MVC.
###### -[Controller](src/main/java/sample/mvc/Controller.java) class: Refactor it to connect Model and View.
###### -[GameObject](src/main/java/sample/objects/GameObject.java) class: Removed useless methods.
###### -[GraphicObject](src/main/java/sample/objects/GraphicObject.java) class: Fill picture with objects in it.
###### -[GameLogger](src/main/java/sample/logger/GameLogger.java) class: Added singleton mode.


### Test class
###### -[GameGridTest](src/test/java/sample/start/GameGridTest.java) class: Test GameGrid class.
###### -[LevelTest](src/test/java/sample/start/LevelTest.java) class: Test Level class.
###### -[StartMeUpTest](src/test/java/sample/start/StartMeUpTest.java) class: Test StartMeUp class.
