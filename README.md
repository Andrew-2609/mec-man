# <img src="https://raw.githubusercontent.com/Andrew-2609/mec-man/main/logo-gif.gif" alt="Mec-Man Logo gif" width="50"/> Mec-Man

## About the Game
Hello there, here's Andrew. I've been developing this little game since 2019, but only now I managed to spend more time to work on it. I started this project aiming to do a Pec-Man copy, but the more I felt comfortable to program it, more I tried to make something more... original.  The game base was created from [Danki Code's course](https://cursos.dankicode.com/), but soon I diverged **a lot** from the tutorials.

You control a high school student that needs to do homeworks, while dealing with the obligations of an I.T. developer.

Ok, I know it is just a simple little game in Java, but I really liked the result. So, lets get started:

### Gameplay Aspects and Enemies

Use the arrows or W,S,A,D to move, and catch homeworks for increasing your score and for passing levels. Catch cups of coffee to slow enemies down (but that reduces your health a bit), and catch graduation hats to gain even more points (but they accelerate the enemies). If an enemy comes close enough, you will suffer damage. About them, here they are:
 
 * Sublime Text
 * ENEM (the Brazilian National High School Exam)
 * Eclipse IDE
 * Visual Studio Code
 * Photoshop CS6

They are quite the same in behaviour, but the last two levels have special enemies to difficult your journey. The game has **10 Levels**, with different scenarios but the same goal: to catch all the homeworks.

### All in Java
I never tried to make a real game before, and since Java is my favorite programming language, I thought "why not?". I hope you all can understand the code, but, in short, the game is divided in:

 * World and Graphics classes to handle the rendering of the game
 * Entity classes to handle the programming and behaviour of the Player, and of all enemies and items
 * Main classes that handle texts, triggers, and the general functioning of the game.

One of the most complex classes to understand (at least for me) is the **A*** algorithm class. It handles the enemies' behaviour when it comes to chasing the player.

The Spritesheet and all the levels are nothing but simples images, that are read to generate the graphical elements.  
For example, in *World.java* you can see that everything that has the color "0xFFFF0000" on Spritesheet will become an instance of an Enemy_1.

### Game Languages

The game has three available languages: Brazilian Portuguese (my beloved one), English and German. I've been studying German for a while, so, for study purposes, I decided to include it on the game. It is an amazing language.

## Special Thanks

This game probably never would've come out of my Eclipse's Workspace if it wasn't for them:
 * [Ayrton Alexander](https://github.com/Ayrtonms): My brother
 * Keivyla Queiroz: My beloved girlfriend

## Soundtrack

Here are the musics I used on the game. I took care of **not using any copyrighted music**, but I must give the credits for all of them, and my sincere thanks.

* [Level 1-3](https://www.youtube.com/watch?v=bFBgHoUtCQ8)
* [Level 4](https://www.youtube.com/watch?v=dAOr8XkMMps)
* [Level 5](https://www.youtube.com/watch?v=uFZ35E6qYvw)
* [Level 6](https://youtu.be/wo22yjg6s5Y?t=110)
* [Level 7](https://www.youtube.com/watch?v=sVCeqYmRwJ0&list=PLobY7vO0pgVKn4FRDgwXk5FUSiGS8_jA8&index=14)
* [Level 8](https://www.youtube.com/watch?v=orooE4jp1ak&list=PL-6iEAVaTOdUehR3xL_S9zm6Lu7Hv42Kl&index=18)
* [Level 9](https://youtu.be/wo22yjg6s5Y?t=738)
* [Level 10](https://www.youtube.com/watch?v=C-ImO_e62-w&list=PLobY7vO0pgVKn4FRDgwXk5FUSiGS8_jA8&index=3)
* [Game Won BGM](https://www.youtube.com/watch?v=xPOy2pkImiU)

## All By Myself (don't wanna be)

I did pretty much everything by myself (beside my brother's help in some programming and testing). I used Paint.net to edit the game graphics, Sony Vegas Pro 11 to edit the game sounds (and that was the hardest part). I also used a lot of websites for converting and cutting images and sounds, and to learn how to handle some programming puzzles.

This is the first game I made, and I hope it won't be the last. I intend to keep updating it when I have more ideas, but the most important thing to say is that it is now public, here on GitHub, so you all can see and suggest changes (probably there are a lot of them, I've been studying Java for only about two years and a half).

**My sincere thanks to those who read until here, and I hope you enjoy it.**

*soon I'll be adding more images of the game on this README, tschüss*