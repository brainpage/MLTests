#!/bin/sh
RUNNER_CP=$CLASSPATH:/usr/lib/mahout/mahout-core-0.7-cdh4.1.1.jar:/usr/lib/mahout/mahout-math-0.7-cdh4.1.1.jar

java -cp $RUNNER_CP:target/test_recommender-1.0.jar RecommenderIntro

