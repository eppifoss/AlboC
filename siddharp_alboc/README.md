#AlboC
=====
INF2100-Project Assignment. Creating a compiler for AlboC.

Version 2.0.0
> December 2014.

Version 1.0
> November 2014


##Compilation
To compile the program run the command :
````
ant jar

````

## USAGE

There are multiple ways to run the program. To simply compile the *alboc* file run:
````
java -jar AlboC.jar -c <filename.alboc>
````

### Testing

We can test the compliler for its scanning and parsing using the following command:

````
java -jar AlboC.jar -c -test{scanner|parser} <filename.alboc>
````

### Logging

We can log the different functionalities of the compilator using the command:
````
java -jar <AlboC.jar> [-c] [-log{P|I|S|B|T}] <filename.alboc>
````
Where,
- logP : Log Bindings
- logI : Log Parsing Tree
- logS : Log Parsing method calls
- logB : Log symbols from scanner
- logT : Log Typechecking


## Uninstalling
To clean run:
````
ant clean
````