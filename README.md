# Escape_The_Trolls

Trapped in a randomly generated maze, can you escape to the goal $? Developed in Java, all code wrote by Harry Schneider apart from the AsciiPanel package.

![Screen shot of the game](https://github.com/Haza290/Escape_The_Trolls/blob/master/Escape%20The%20Trolls%20Screenshot.PNG)

## Key Features

### Maze Generation
Each game generates a new maze using a depth first search approach to maze generation, this leads to a complete maze with no loops or sectioned off parts of the maze.

### Path Finding
A* path finding was implemented in the Enemy abstract class so all enemies could make use of A* path finding.

### Inheritance of Enemy
All enemies inherited from the abstract class Enemy so they could make use of commonly used functionality like path finding and dieing. It also makes developing new enemy types quicker and easier as well as makes keeping track of enemies easier.

### Multiple Enemy Types
Thanks to the abstract class Enemy, having multiple enemy types is relatively easy.

### Trolls
Trolls wander randomly until they see the player, they then change colour to red and head on the shortest path to the player. If they lose sight of the player they head to their last know location of the player and turn yellow.

### Spiders
Spiders wander randomly making webs on un-webbed tiles. If the player walked of a web that is connected to a spider then the spider head on the shortest path that is webbed to the player and changes colour to red. If the player leaves the web then the spider goes back to wandering and changes colour back to cyan. Multiple spiders can join webs.

### Ghosts
Ghost always move randomly but they can move into and through walls.

### Break Between Display and Game Logic
The game logic can run completely independently of the display meaning that it is relatively simple to create a completely different style of display. For example it would be relatively simple to create a 3D display for the game.

---

Long Description

---
Bugs:
