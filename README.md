# Console-Drawing-Program

Asked in Many companies like Credit Suisse. I have given my own solution in java. But open for all suggestions and modifications.

Run Instructions:
- Run Main.java file using any version of eclipse to correctly read from console.
- JDK 8 used while development.


Classes are grouped into files based on their cohesiveness.

Unique Feature of a program:
- It can draw lines at any angle and not just verticle and horizontal.
- Line cordinates can be given in any order.
  L 6 4 6 3 will also produce desired output.
- This program works fine when rectangle cordinates given in any order. So user can give bottom right and then upper left corners.
  R 18 3 14 1 will also produce desired output.
-Right now created Mossaic Canvas. But the same program can be extended to support any other canvas implementation.
-created cordinates class so that 3d input params can also be considered in future.
-Program can be easily extended to add new drawing command. Eg.
-Program can be easily extended to change existing command character. 
 For example "Line" instead of "L", then changes required only for ICommandFactory.
- Program can be used safely into multithreaded environment also. 
  For this purpose it uses enums and double checked implementation for singletone implementation.
- It can have any number and type of dimensions for canvas. 
  Eg. It can incorporate dimension system having below dimensions. Not necessarily integer.
  ISpace, ITime, Ix, Iy, Iz
- Also data stored at particular dimension could be of any type. Not necessarily integer.
  Eg. IDimension

Assumption:
Negative x,y inputs not considered.
If canvas is given 0 height or width, then no output should return. This is because canvas can not exist with 0 height or width.
User input cordinates which lie on or outside canvas boundary not legal.
Providing same value x and y for rectange is considered acceptable and will draw single pixel.
