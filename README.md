# Escape_The_Trolls

Trapped in a randomly gereated maze, can you escape to the goal $? Developed in Java, all code wrote by Harry Schneider apart from the AsciiPanel package.

![Screen shot of the game](https://github.com/Haza290/Escape_The_Trolls/blob/master/Escape%20The%20Trolls%20Screenshot.PNG "test")

## What I used

### Maze generation
Each game generates a new maze using a depth first search aproche to maze generation, this leads to a complet maze with no loops or sectioned off parts of the maze.

### Path finding
A* path finding was implmented in the Enemy abstract class so all enimes could make use of A* path finding.

### Inheritance of Enemy
All eneimes inherited from the abstract class Enemy so they could make use of comonly used fuctionality like path finding and dieing.

### Break between display and game logic
The game can run completly independatly of the display meaning that it is realatily simple to create a completly different style of display.

---

Long Description

---
Bugs:
