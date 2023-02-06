# BattleshipNetwork
Server-Client Battleship game in a 10 x 10 grid. Each player has three ships that are 3 squares long.
It has two applications: The server needs to be run once and the client for each player.
Each player will take a turn and try to guess the location of the opponent's ships. The player to sink all of the opponent ships first, wins.

The file `config.dat`has the host and port information. By default is `localhost` and `9876`. 
* The host takes a IPv4 address or `localhost`.
* The port takes an integer.
The format is the following:
```
<host>
<port>
```


