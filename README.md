# Set

A Set is a recursively defined set being one of two types: XY or RGB which define the 
space in which it exists.

Using Set:

1. Basic Step : Define the inital value of the Set with the constructor Set( BufferedImage , SetType , initial point )

2. Recursive Step : Use method clusterTo( Set , threshold ) to define new points in teh set from the initial point

3. Up to the user: 
	3.1. Recluster with a bigger threshold value
	3.2. Show the Set using SettoIMG( Set )
	3.3. Perform some operation to find the expected position or the expected color of a set



A Set is composed of two data type depending on its SetType

For SetType.RGB, set operation transfer points between a hashtable representing all the 
	color in the image and an array of point representing the set itself. The Point have 
	value P (red, green, blue, array of location); the array of location give the position of every
	pixel in the image having the defined color, allowing the image to be redrawn only from the set

For SetType.XY, set operation transfer points between the image of the set
	and an array of point representing the set itself. The Point have value P( x,y, color);
	allowing to redraw the image only from the set 
	 
In both cases, the array of point and its associated data structure (image or hashtable) 
	form 2 disjoint set, hence, one can redefine a new Set from the image or hashtable 
	(note that the constructor does not take hashtable as a argument, it most be transformed 
	into a bufferedimage before defining a new set)