# Maze_Solver

Maze Solver is a simple simulation of evolution using a simple genetic algorithm and shallow neural networks (NN) in Java.

At the start of the simulation a population of agents are generated. Each agent has genes that describe the architecture of a shallow NN. The simulation also loads the maze from a CSV file.

The simulation then starts a round of training for the population. Each training consists of testing the population and then updating the population.
* The testing phase has each agent in the population interact with the environment (the maze). Every agent is placed at the same position in a maze to start and is tasked with finding the same exit. After the agent has either reached the time limit or reached the end of the maze, the agent is given a score for how well it did. (Or how close it got to the end of the maze).
* The updating phase randomly combines the genes of agents to create a new population of agents of the same size. It is important to note that when mating agents, there is a small chance for a mutation to happen while mixing genes. This allows a new agent to behave in different ways than its parents. The five best scoring agents are always included in the new population with unaltered genes.
This new population then begins a new round of training. This repeats until an agent is able to solve the maze or until the max number of training rounds, set by the user, is reached.

This simulation includes three custom GUIs for observing and controlling the simulation.
	* Maze GUI: Displays the maze and the current agent being tested.
	* Status GUI: Display simulation information such as: the current training round, the highest score from the last round, and the genes of the highest scoring agent. This window also contains a prompt that provides the user with fine grain control over the simulation.
	* Network GUI: Displays a representation of the NN of the current agent being trained. This representation of the NN also shows the inputs and outputs of the NN for each time step of the agent interacting with the maze.
