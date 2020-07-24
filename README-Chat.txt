-------------------------
Simple Chat System readme
-------------------------

---Custom Ports & Addresses---
Custom IP adddresses and Port Numbers can be assigned to the program to make messages be sent and received on different ports to the default (Address: "localhost" & Port: "14001"). This is done at the program's execution through the use of command line arguments. 

The argument -ccp followed by the desired port number can be used to change the port number of the Clients (DoD and ChatBot included). 

The argument -csp followed by the desired port number can be used to change the port number the server.

The argument -cca followed by the desired IP address can be used to change the IP address of the Clients (DoD and ChatBot included).


---Message format---
*Please not that this is not a requirement for messages sent by the client, or requiring any action by the user, this is simply the format the messages are sent in*

All messages are sent with the following format:

		ID-Name:Message

ID: This is the numerical ID of the client that it was assigned when it connected to the server. These are assigned in order starting at 1 for the first client to connect to the server, 2 for the second client to connect to the server etc. The bot is also assigned an ID, however it will automatically change it's name when it first connects. 
If the client enters the command 'Name:' followed directly (no space after the colon) the ID will be set to 0 to denote this change and the name will be assigned.

Name: This is the string of characters assigned to the client by the client. By default the name will be empty and the ID will be greater than 0. Therefore, due to the algorithm in the ClientReceiving class the name will not be printed in the message instead the name will be assumed to be 'Client ' followed by the ID. If the name has been assigned, the ID of the client, the first part of the message will be 0 and, due to the algorithm in the ClientReceiving class the name will be printed as assinged by the client.

Message: This is the main body of the message that the user has sent to the server to be broadcast to all connected clients. 

Messages will appear in one of two formats:
1) 	If the client has assigned themselves a name, their messages will appear in the chat as:
			<NAME> MESSAGE

2)	If the client has not assigned themselves a name, their messages will appear in the chat as:
			<CLIENT ID> MESSAGE



---Server commands---
1)	The 'exit' command (case insensitive) can be entered into the server terminal by the server user at any time in order to disconnect all connected clients and close the server.



---Client commands---
1)	The @dod command (case insensitive) can be entered into the client terminal by the client user once the DoD client has been connected. This can then be followed by a second command specified in the DoD Read Me (included)

2)	The @bot command (case insensitive) can be entered into the client terminal by the client user once the Bot client has been connected. This can then be followed by the message you wish for the bot to receive. 