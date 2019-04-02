# ServerGroupAddBot

A small Java ServerGroup Bot for TeamSpeak 3.

On 1. April 2019 we needed a method to add a list of groups to all clients in our Ts3-database of one specific virtual server. 

In less an hour we created this project. We used it to give each client a random set of server groups. 

## Usage
1. Clone or Download this project
2. We didn't have enough time to create a fancy configuration file for database settings. Set it directly in the java class "Configuration.java" located under `src/main/java/net/biocrafting/groupbot/bot/Configuration.java`
3. Package it with maven `mvn package`
4. Run it with parameters

```
java -jar target/*.jar server=5 random=true debug=false 1,2,3,4,5,6
```

- `server=` Unique server address 
- `random=` If true it assigns a random half of given server groups to all clients
- `debug=` In case of true (which is the default value) a more verbose output with stack traces will printed when any errors occurs
- A comma seperated list of group ids which should be assigned to all clients in database
