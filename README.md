# Cracking `java.util.Random`

This is a very simple program demonstrating how easy it is to predict the output of the `java.util.Random` class.  
I take zero credit for this; the code was lifted and adapted from this blog entry and its comments:

* Roper, James.  ["Cracking Random Number Generators - Part 1."](https://jazzy.id.au/2010/09/20/cracking_random_number_generators_part_1.html)  Blog entry, September 20, 2010.  Accessed Apr 25, 2016. 

This program works successfully in Java 8, and I believe also in Java 7 but I haven't checked.  I'm pretty certain it
won't work on Java 6, because some of the constants in the `java.util.Random` class have changed, but it should be
trivial to adapt it to that (the original blog entry used the Java 6 values after all).