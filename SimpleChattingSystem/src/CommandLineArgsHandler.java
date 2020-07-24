public class CommandLineArgsHandler {
    /**
     * A method that handles the linear searching of the array of strings for the required argument and then returns the
     * next item in the array as this should be the value.
     *
     * @param argType - A variable holding the type of argument that is being searched for eg. an IP address or a
     *                port number.
     * @param args - An array of strings of the arguments given by the user at the execution of the file.
     * @return - Returns either the value of the argument being searched for eg. the port number if the port number was
     * being searched for. If it cannot be found, returns null.
     */
    private static String getArgs(String argType, String[] args){
        //Performs a linear search of all the arguments
        for (int i = 0; i<args.length-1;i++){
            if (args[i].equals(argType)){
                //Attempts to retrieve the next string in the array
                try {
                    return args[i + 1];
                }
                catch(Exception e){
                    break;
                }
            }
        }
        return null;
    }


    /**
     * A method that handles the request for a port number from a array of arguments passed to the program. Calls the
     * getArgs method with the appropriate arguments , tests if the returned port number is not null. If not null,
     * parses it to a integer for it to be returned. If null or cannot be parsed to an integer, returns the default
     * port number (14001).
     *
     * @param args - An array of strings of the arguments given by the user at the execution of the file.
     * @param fieldDefault - The default value for this field ie. 14001
     * @return - Returns the port number to be used to establish a connection. Either the one entered from the list of
     * arguments or the default.
     */
    public static int setPort(String[] args, String argType, int fieldDefault){
        //Assumes the default port number will be used.
        int tempPort = fieldDefault;
        String inputtedArg;

        inputtedArg = getArgs(argType, args);

        if (inputtedArg != null){
            //Attempts to parse the entered port number to a integer, otherwise uses the default
            try {
                tempPort = Integer.parseInt(inputtedArg);
            } catch (Exception e) {
                System.out.println("Invalid port, default port("+ tempPort + ") used.");
            }
        }
        else{
            System.out.println("Invalid port, default port("+ tempPort + ") used.");
        }

        return tempPort;
    }


    /**
     * A method that handles the request for an IP address from a array of arguments passed to the program. Calls the
     * getArgs method with the appropriate arguments , tests if the returned IP address is not null. If not null,
     * parses it to a integer for it to be returned. If null, returns the default IP address (localhost).
     *
     * @param args - An array of strings of the arguments given by the user at the execution of the file.
     * @param fieldDefault - The default value for this field ie. localhost
     * @return - Returns the IP address to be used to establish a connection. Either the one entered from the list of
     * arguments or the default.
     */
    public static String setAddress(String[] args, String fieldDefault){
        //Assumes the default IP address will be used
        String tempAddress = fieldDefault;
        String inputtedArg;

        inputtedArg = getArgs("-cca",args);

        if (inputtedArg != null){
            tempAddress = inputtedArg;
        }
        else{
            System.out.println("Invalid address, default address("+ tempAddress+") used.");
        }

        return tempAddress;
    }
}
