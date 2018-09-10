# PaperFootball-Java


version 0.2 (14.07.2018)

	- Now the "active Point" is called the "ball" and is displayed with an icon of a football.
	- When it's a human player's turn all currently directly reachable Points (possible next simple moves) are highlighted by blinking golden (like the "active point" used to)
	- Major clean up and Lieutenant comment coverage
	
version 0.2.1 (14.07.2018)

	- Application prints version on start.
	- Show an image of a red/blue football player at the ball's location at the start of each move.
	- The ball now slowly transitions from it's current to the chosen position during a move.
	
version 0.2.2 (14.07.2018)

	- Fixed a bug where human player choking would reset the game like it should, but not count it towards the opposing player's score
	
version 0.3 (10.09.2018)

	- Peer-to-Peer based online play.
	- Disabled user input outside of "local human" player's turns.