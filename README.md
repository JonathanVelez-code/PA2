# Minotaur’s Birthday Party
## How to run the program
1. Call the filepath in the command line
2. Type the following command:
```bash
javac birthday.java or javac vase.java
```
3. Press enter then write this in the terminal:
```bash
java birthday or java vase
```
4. Press enter then a printable result will appear in the command line.

## Proof of Correctness
The guests are required to enter the labyrinth, one at a time and only when he invites them at random. Every guest entering their first time in the labyrinth tell Minotaur and then eat the cupcake. This prevents large waiting time between each guest because they will know when it is their first time entering. Also, prevent a guest from being greedy and deciding to eat a large portion of the cupcakes. One thread will keep the count of how many cupcakes have been eaten, so once it hit that number we know all the guesses have entered the labyrinth. Each thread will wait until it has been called if one thread has entered already and the cupcake is gone he is required to request a cupcake for the next thread.

## Experimental Evaluation
One other solution I decided to try was brought up in the lecture, which was to find a designated person to eat the cupcake then it follows in order from then on. However, the person being called at random can make it hard to get the designated person then moving on to the next person will take forever and be a waste of resources because the person is not allowed to eat it cupcake until it is his turn when it possible he could have been called a few times already. At that point, I knew the solution would be inefficient due to its randomness it would probably run at $O(n^2)$.

## Efficiency

# Minotaur’s Crystal Vase

## Proof of Correctness


## Experimental Evaluation


## Efficiency
