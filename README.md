# Genetic Algorithms
An example of a Java genetic algorithm that finds the correct sequence for a given text.


## Operation
A genetic algorithm is one of the most used optimization algorithms. It uses genetic principles like elitism, cross-over, mutation to optimize and it's adaptable to many problems.  

Basically it represents the thing we want to optimize as genes of a chromosome. In this example it's a sequence of letters.
So every letter is a gene, and the sequence of these genes is a chromosome. Every one cycle of optimization is called a generation.
And every generation contains a number of chromosomes. A generation is like a snapshot of chromosomes (and sequences of genes) in a particular moment.

## Setting Up

For beginning, this program takes a password as input and a number of chromosomes.
Creates the very first generation that contains declared number of chromosomes, each created by shuffling the letters in the password.
The first generation is ready now; it has chromosomes as specified number and every chromosome is a variation.

> Please note that password field is limited by maximum 17 characters. Using a phrase longer than it may cause errors.

## Optimization

Then this generation passes the fitness control. Fitness control means calculating the optimality for every one of the chromosomes in a generation.
In this example, fitness control done by counting the letters in the correct position. For example if the password `ABCDE`, and a chromosome is `DBCEA`, the fitness value of this chromosome will be 2, since 2 letters are in correct positions.
If there are any chromosomes that reached to the optimum point, algorithm terminated and results are returned. If not, continues to operate followings.  

After the fitness control, 20% of the best valued chromosomes are copied to the next generation. For example, if it is a model with 100 chromosomes, the best 20 chromosomes are passed on to the next generation.
The missing part (80% of it) in the new generation is produced by randomly selecting 2 chromosomes at each step among the best selected chromosomes and making crossing-over between them.
Hence, the missing part is filled with best chromosomes and this new generation is better than last one.  

Mutation is for avoiding local optimum points. Two genes are randomly selected on each chromosome and mutations are applied on them by switching them.
Hence, the quality of somes chromosomes decreased on purpose. After the mutation, optimization processes will repeat for next generation.

## Terminating

A genetic algorithm model can be terminated;
- if stilness occurs (if optimization does not continue),
- if the specified number of generations is reached,
- if the specified time is reached,
- if the solution is known and reached.

In this example, algorithm uses the 4th cause since solution is known.

## Comments

Examine how performance is affected by the different number of chromosomes for the password "ChatGPT and GPT-4".

<img width="1125" alt="Performance" src="https://github.com/muhammetsanci/Genetic-Algorithms/assets/77257193/769bbccc-f6b8-4721-a94a-22ec81e8fb3a">

Increasing the number of chromosomes improves the accuracy of predictions.
This reduces the generations needed to find a solution when we have a lot of chromosomes.
However, having more chromosomes in each generation makes the time it takes to complete each generation longer because of the extra processing work.
Even though this adds more computational work, the solution is reached faster across generations.
