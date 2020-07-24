-----------------------
Dungeons of Doom readme
-----------------------


---Introduction---
Dungeons of Doom is a text based adventure game with a single player. Users are presented with a choice of maps to play on from files found in the specified folder. The game consists of the player hunting a 'dungeon' maze to find and collect gold before escaping and claiming victory over the dungeon. However, a bot will seek out the player with the aim of keeping the gold in the dungeon and stopping the player!

The player may leave the dungeon at any time, however, if they have not collected the correct amount of gold or they are not standing on the escape E tile with enough gold they will have lost the game. 


---How to begin---
Please do not rename the folder 'DoD Code1' or any other original elements contained in this folder unless explicitly asked to do so.

To begin playing please ensure that the map is place inside the folder "DoD Code1" before running, you should note that other maps are already in place here. The map file should be laid out in the same format the example ones to be found on moodle. 

Please ensure that the maps have been placed in the correct folder by running the program in a Windows based IDE and check that you are presented with the correct number of maps with the correct names. If not, please check you have followed the instructions above carefully!

To begin running on Linux, upload the "SimpleChatSystem" folder to your a directory under your H drive.

Using your chosen software, navigate to the folder "SimpleChatSystem".

To compile, enter the command "javac -d out/ src/*.java"

To run, enter the command "java -cp out/ ChatServer"

Followed by, "java -cp out/ ChatClient"

Followed by, "java -cp out/ ChatServer"

This will start the game. If the set up has been done correctly the map files will be available for you to chose from, if it has not been done correctly, an error message stating such will be displayed.


---How to play---
*Please see the 'How to begin' section above and ensure that a map in a similar style to those on moodle has been placed inside the correct file*

1. Begin running the game by compiling and running the ChatServer.java, ChatClient.java and DoDLogic.java by following the instructions above.

2. You will now be presented with the choice of the maps loaded in from the file if this has been set up correctly. To proceed, enter a single integer number relating to the number next to the map name and press enter. Any invalid numerical inputs will be rounded to the nearest valid choice otherwise invalid inputs will be ignored.

3. You have now entered the main running of the game where a variety of input commands can be entered to perform a variety of actions. These can be performed by the user by typing them into the console. The commands are case insensitive however the user should avoid incorrect spelling and other typos as this will be an invalid input but still consume the user's turn. The valid inputs (case insensitive) are listed below, all of which will consume the player's turn regardless of their success or not. 

a) HELLO - This input will result in the output of a string consisting of the total amount of gold that is required for you (the human player) to win the game. 

b) GOLD - This input will result in the output of a string consisting of the current gold owned by the you, that is, how much gold you have collected on this game so far.
 
c) MOVE X - Where X is a single character representing the compass direction in which you wish to move. If you wish to move North (upwards) X is to be replaced by a N, if you wish to move East (right) X is to be replaced by an E. This input will result in the output of either 'SUCCESS' or 'FAIL' reflecting whether or not you have tried to make an illegal move such as into a wall # tile.

d) PICKUP - This input will result in the output of either 'SUCCESS' or 'FAIL' reflecting whether you have entered this input command when the human player currently shares a location with a gold G tile. If there is gold on that square, your gold collected score will increase by one, the tile will be replaced by an empty one and 'SUCCESS' will be outputted. 

e) LOOK - This input will result in the output of a 5x5 grid showing the current map around the player. It will show all relevant walls #, empty tiles ., gold G, exits E as well as your location P at the center of the map and the bot B if it is within this range (Remember! If you can see it, it can probably see you, RUN!)

f) QUIT - This input will result in the output of either 'WIN' or 'LOSE' reflecting whether or not you have met the conditions necessary to win the game. These conditions are that you have collected the necessary amount of gold and you are on escape E tile.

4. The bot will now take their turn. This either consist of a movement or a look (the nature of how this decision is listed below). If, after the bot's movement, they are 'on top of you' ie. sharing a tile with you then they win and you lose. If this is the case, a message will be outputted. 

5. If the bot does not 'capture' you and you do not quit the game, the game will continue until either you quit or it does capture you.


---How it works---
The game runs from the GameLogic class with all other objects and methods being called from there. To begin the main() method is called before this instantiates the GameLogic object, calling the constructor which in turn instantiates the other necessary Map, HumanPlayer and Bot objects. The program then moves onto the runGame method which starts the game up by calling the necessary methods of the map class in order to select the map the player wants to play on. 

Once the map is chosen the player and the bot are placed in starting positions on the map such that they neither share a position with each other nor a wall. From here the game begins a loop which runs while the gameRunning method produces a true result. This method, gameRunning, determines if the bot has caught the player or the player has decided to quit the game while on an escape E tile. If either of these conditions are true, the method produces a false result and the game stops. 

From the main loop, the program alternates between the player's and the bot's turns. 

On the player's turn the program takes the input from using the getInputFromConsole method in the HumanPlayer object and passes this to the getNextAction method which serves the dual purpose of both checking if the inputted command is a valid one and also, if it is valid then it calls the appropriate method to handle this input. Since all the command methods return a string, this result is outputted onto the screen to show a variety of things such as whether the players movement has been successful, the local map, or the amount of gold the player has collected. 

On the bot's turn the program will do a variety of things dependant on the previous turn. If the bot used the look command last time and saw that the player was in range, the bot will begin a chase in that it will try to move in the direction of the player unless it attempts to move into a wall in which case it will not move and it will have 'lost' the player meaning it will no longer chase you. Alternatively, if the bot has not spotted the player in it's look function yet it will alternate between looking and moving in a random direction, starting with an inital look. 