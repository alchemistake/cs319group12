## CS 319 - Object Oriented Software Engineering Project Group 12
#Astunc

### Game Description
We are planning to make a “Bullet Hell” game. “Bullet hell” is a subgenre of the “Shoot ‘em Up” game genre. “Shoot ‘em Up”s are games in which the protagonist combats a large number of enemies by shooting at them while dodging their fire. The controlling player must rely primarily on reaction times to succeed.[1] ”Bullet Hell” is a shoot 'em up in which the entire screen is often almost completely filled with enemy bullets.[2] These kind of games can be explained as Chicken Invaders 2 with a lot of projectiles in screen.

### Project Description
Like as other “Shoot ‘em up” games, our game will be set in space. Player will be shown as a spaceship. Player will be shooting projectiles from down to top and the enemies (will be explained later) will be coming and shooting towards spaceship from the top of the screen. Player will be able to move every point on the screen to avoid enemy projectiles.
There will be numerous enemies, including bosses. After defeating a wave of normal enemies, there will be a boss encounter. Every enemy will take a number of hits to be destroyed, boss enemies will take relatively more projectiles to defeat. After a boss is defeated there will be another wave of normal enemies until every boss enemy is encountered. Game will be ended after every boss or player is defeated.
Enemies will be heavily dependent on inheritance, there will be an abstract Enemy class and it will have two abstract subclasses: Normal Enemy and Boss Enemy. There will be at least 5 subclasses of Normal Enemy and at least 3 subclasses of Boss Enemy.
There will be no scoring system, ranking will be dependent on the time that player managed to finish the game. There will be no health system either, every hit the player takes will give a time penalty of seconds.
Since there will be gigantic number of projectiles that will fill the screen, we wanted to focus on object-oriented design not the shooting patterns. We will solve this problem with using commonly used mathematical functions and equations such as trigonometric functions, parabolas etc. We are planning to take the computational code from outside, since it is not a essential part of the object-oriented design if Uğur Hoca approves.
We are planing to implement the game in Java 8.0 for desktop platforms such as Mac OS X, Microsoft Windows, Linux distros with JavaFX framework that is extension of Swing framework. Control method will be keyboard using usual function keys such as “Alt”,”Control”,”Space” and arrow keys.

### References
1. Bielby, Matt, "The Complete YS Guide to Shoot 'Em Ups", Your Sinclair, July, 1990 (issue 55), p. 33
2. Ashcraft, B. & Snow, J. (2008). Arcade mania! : the turbo-charged world of Japan's game centers. Tokyo New York: Kodansha International Distributed in the United States by Kodansha America. p. 66
