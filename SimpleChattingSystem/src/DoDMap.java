import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class DoDMap {
    /* Map name */
    public String mapName;
    /* Gold required for the human player to win */
    public int goldRequired;


    /**
     * A method that creates a list of the files in the root folder, filters so that only '.txt. files are added to
     * the list and retrieves the names of the maps from their respective text files and added to an arrayList. The
     * map names are then printed and the user is asked to select a map. This map choice is taken and rounded to the
     * nearest available map e.g. if you enter 6 and there are only 4 maps, the fourth map will be chosen. The program
     * will print a message to confirm the user's selection and return the file name.
     *
     * @return fileName String - A string containing the file name of the map that has been chosen by the user and
     * therefore the name of the file to be read
     */
    protected String getMapChoice(Socket serverSocket) {
        /* Initialise a new file object containing the current directory*/
        File currentDir = new File ("");
        /* Convert the file path of the currentdir variable to a string*/
        String filePath = String.valueOf(currentDir.getAbsoluteFile());
        File dungeonsFolder = new File(filePath);

        /* Create a new array list to hold the names of the map files*/
        ArrayList<String> dungeonFileNames = new ArrayList<String>();

        /* Try to sort through all files in the specified directory, if no files are found, catch with Exception e.
         * Filter all files so that only text files are added to the map files array list. */
        try {
            for (File file : dungeonsFolder.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    dungeonFileNames.add(file.getName());
                }
            }
        }catch(Exception e){
            DoDLogic.sendMessage("Files not found");
        }

        /* Create a new array list of dungeon names as specified in the file*/
        ArrayList<String> dungeonNames = new ArrayList<String>();
        /* Create variables to read from text files */
        String line;
        String currentMapName;
		/* For every map file found previously, open it using the BufferedReader, find the map name from the first line
		 after the title, print this in the appropriate format and add to the dungeon names array list*/
        for (int mapCounter = 0; mapCounter < (dungeonFileNames.size()); mapCounter++) {
            try {
                String fileName = filePath + "/" + dungeonFileNames.get(mapCounter);
                BufferedReader mapFile = new BufferedReader(new FileReader(fileName));
                line = mapFile.readLine();
                currentMapName = line.substring(5);
                DoDLogic.sendMessage(mapCounter + ". " + currentMapName);
                dungeonNames.add(currentMapName);
                mapFile.close();
            }
            catch (IOException ignore) {
            }
        }

        /* Ask the player which of the maps they would like to play, repeat until a valid answer (integer) is entered*/
        String mapChoice = null;

        while (!isNumeric(mapChoice)) {
            DoDLogic.sendMessage("Please enter the number of the map you would like to play");
            mapChoice = DoDInputHandler.getNextCommand(serverSocket);
        }

        int mapNo = Integer.parseInt(mapChoice);

        /* Round the inputted integer to the nearest correct map number */
        if (mapNo > (dungeonNames.size() -1)) {
            DoDLogic.sendMessage("Your response has been adjusted to the closest valid number");
            mapNo = (dungeonNames.size() -1);
        } else if (mapNo < 0) {
            DoDLogic.sendMessage("Your response has been adjusted to the closest valid number");
            mapNo = 0;
        }

        /* Announce to the user what map they have selected*/
        DoDLogic.sendMessage("You are playing " + dungeonNames.get(mapNo) + "\n");
        mapName = dungeonFileNames.get(mapNo);
        String fileName = filePath + "/" + mapName;
        return fileName;
    }


    /* Declare main array list that will hold the map that the user is playing. This is an array list where each
 	element is an arraylist of characters. This effectively creates a 2 dimensional array list*/
    public ArrayList<ArrayList<Character>> mapList = new ArrayList<ArrayList<Character>>();


    /**
     * A method that reads the map from the specified file and saves it to the 2 dimension array list allowing it to be
     * accessed through the object
     *
     * @param fileName String - A string containing the name of the file of the chosen map to be read.
     */
    protected void readMap(String fileName){
        /* Initialise the necessary text file reading variables*/
        String line;
        int lineNo = 1;
        BufferedReader mapFile = null;

        /* Open map text file*/
        try {
            mapFile = new BufferedReader(new FileReader(fileName));
        }
        catch (Exception ignored){;
        }

        /* Continue to read all lines of the file*/
        try{
            while(lineNo>0){
                /* Ignore lines before 2 as these are not relevant to the map itself*/
                if (lineNo>2){
                    /* Read a new line from the text file*/
                    line = mapFile.readLine();
                    /* Add a new element consisting of an ArrayList to the map ArrayList */
                    mapList.add(new ArrayList<>());
                    /* Split the line into an array of characters and add each character to the inner array list*/
                    for (char chr: line.toCharArray()){
                        mapList.get(lineNo-3).add(chr);
                    }
                }
                /* Read the second line to obtain the amount of gold required for this map*/
                else if (lineNo == 2){
                    line = mapFile.readLine();
                    goldRequired = Integer.parseInt(line.substring(4));
                }
                /* Otherwise ignore the line*/
                else{
                    mapFile.readLine();
                }
                lineNo++;
            }
        }
        catch (Exception ignored){

        }
    }


    /**
     * A method that determines whether or not a string consists of numbers by whether it can be parsed to an integer
     * or not
     *
     * @param stringToTest - A string that is to be tested as to whether it consists of only numbers.
     * @return - A true or false values as to whether the number can be parsed to an integer or not.
     */
    public static boolean isNumeric(String stringToTest) {
        boolean result = false;

        if (stringToTest != null) {
            try {
                Integer.parseInt(stringToTest);
                result = true;
            } catch (NumberFormatException ignored) {
            }
        }
        return result;
    }
}
