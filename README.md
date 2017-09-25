# Test driven development with Noe4j in Java

This Repository is the source for a coding kata I created in order to practice test driven development with [Neo4j](https://neo4j.com/) which is a graph database.

The main aim of this repository is to provide a simple implementation for querying Neo4j with its available Java driver.

## About graph databases

It is a prerequisite that you know a little bit of [Neo4j](https://neo4j.com/) and the ideas behind. [Here is a good link](https://neo4j.com/blog/data-modeling-basics/) where you can get basics. If you are interested in a deeper understanding I'd advise you to read [this book](http://amzn.to/2yugwPp).

## The domain of the exercise

As you might have read on [my blog](http://sandordargo.com/blog/2017/07/11/meet-neo4j-again) I am experimenting with graph databases and many times I use wines and related terms when I represent a graph. I find it useful because wine is something many people are familiar with. It is convivial, it brings people closer to each other. At a certain point it's the aim of a code kata too. To learn about the coding style of each other, to understand the way the other thinks.

We start off with wine regions. Think about __Provence__ in France. It is a world famous wine region. It's well known mostly about their rosé wines.

The next concept you have to known is the one of wine subregions. Each wine region consists of one or more subregions. Like in __Provence__ you can find __Bandol__ among many others.

The last concept we need is grapes. Usually wines are made of grapes. Many different types of grapes exists, I assume we are on a scale of thousands. Each has its own attributes, which if you have the necessary skills you can feel tasting a nice wine. For example in __Bandol__ a dominant type is __Mourvèdre__.

In this exercise as test data I use some Hungarian counterparts, because if you are not a connoisseur, it is less likely you know Hungarian wines, even though they are great.

## The exercise

You will start off with a code where there is one class implemented which represent a WineRegion. In happens that in our graph database there are also nodes labeled as WineRegion.

There is also an available service which provides an API to query wine regions by name.
 
Your tasks are the following:
1. Implement a similar service to query wine subregions by name, including of course a class which will represent the wine subregion.
2. Modify the existing WineRegion interface and its implementation so that it can return not just the names of the subregions it includes, but the objects representing them.
3. Implement a third service for the grapes.
4. Modify the interface and implementation of wine subregions so that they don't just provide the name of the grapes, but a set of objects representing them.
5. This is an extra. Make it a web application. Possibly using [SpringBoot](https://projects.spring.io/spring-boot/). For this it's better if you [install Neo4j locally](https://neo4j.com/download/). Even though it is not necessary, you can also inject the in-memory database you use in the unit tests.

## Things not covered by this kata

It does not aim to provide the simplest or the fastest way to write a service querying Neo4j. There are other ways to do, where the library you use would infer what you want from the database based on some annotations. They are also nice, I might create some katas involving those libraries later on. But with them it's not so easy to stay in total control of your database access. When you are starting off with Neo4j I think it serves your purposes to write these queries on your own.
 
It also does not aim to provide an implementation where every exceptional case is nicely handled as you would do it production code. I keep it more simple to focus on the data handling.

You might be surprised why the different queries are scattered across different files (if you already had a peek on the final solution). I think it's easier to organize the kata like this, plus I tend to agree with [Yegor Bugayenko's view on ORMs](http://www.yegor256.com/2014/12/01/orm-offensive-anti-pattern.html) and I'm experimenting with the way he proposes.
