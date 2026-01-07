# Window Components

## Problem Description

Define a class `Component` that stores:
* color
* weight
* collection of internal components (references from the `Component` class)

In this class, define the following methods:
* `Component(String color, int weight)` - constructor with color and weight arguments
* `void addComponent(Component component)` - for adding a new component to the internal collection (in this collection, components are always sorted by weight in ascending order; if they have the same weight, they are sorted alphabetically by color)

Define a class `Window` that stores:
* name
* components

In this class, define the following methods:
* `Window(String name)` - constructor
* `void addComponent(int position, Component component)` - adds a new component at a given position (integer). Each position can have only one component; if we try to add a component to an occupied position, an exception of class `InvalidPositionException` should be thrown with the message `Invalid position [pos], alredy taken!`. Components are sorted in ascending order by position.
* `String toString()` - returns a string representation of the object (format shown in example output)
* `void changeColor(int weight, String color)` - changes the color of all components with weight less than the provided weight
* `void swichComponents(int pos1, int pos2)` - swaps the components at the provided positions

## Custom Exception

Create a custom exception class:
* `InvalidPositionException` - thrown when attempting to add a component to an already occupied position

## Example 1

### Input
```
FIREFOX
1
RED
30
3
MAGENTA
90
0
1
2
GREEN
40
3
RED
50
2
BLUE
50
2
CYAN
60
1
YELLOW
80
3
WHITE
35
0
2
4
60
BLACK
1 2
```

### Output
```
=== ORIGINAL WINDOW ===
WINDOW FIREFOX
1:30:RED
---40:GREEN
------50:BLUE
---------60:CYAN
------50:RED
---90:MAGENTA
2:80:YELLOW
---35:WHITE

=== CHANGED COLOR (60, BLACK) ===
WINDOW FIREFOX
1:30:BLACK
---40:BLACK
------50:BLACK
---------60:CYAN
------50:BLACK
---90:MAGENTA
2:80:YELLOW
---35:BLACK

=== SWITCHED COMPONENTS 1 <-> 2 ===
WINDOW FIREFOX
1:80:YELLOW
---35:BLACK
2:30:BLACK
---40:BLACK
------50:BLACK
---------60:CYAN
------50:BLACK
---90:MAGENTA
```

## Example 2

### Input
```
CHROME
1
WHITE
10
3
GRAY
20
0
1
0
1 
1
BLACK
30
3
YELLOW
50
3
PINK
30
0
2
1
RED
60
0
3
3
PINK
50
3
GREEN
5
2
BLUE
30
3
WHITE
60
3
GRAY
10
2
GREEN
15
2
RED
45
3
YELLOW
40 
1
CYAN
15
0
4 
4
20
ORANGE
1
2
```

### Output
```
Invalid position 1, alredy taken!
=== ORIGINAL WINDOW ===
WINDOW CHROME
1:10:WHITE
---20:GRAY
2:30:BLACK
---30:PINK
---50:YELLOW
3:60:RED
---5:GREEN
---30:BLUE
------10:GRAY
------15:GREEN
---------45:RED
------------40:YELLOW
------60:WHITE
---50:PINK
4:15:CYAN

=== CHANGED COLOR (20, ORANGE) ===
WINDOW CHROME
1:10:ORANGE
---20:GRAY
2:30:BLACK
---30:PINK
---50:YELLOW
3:60:RED
---5:ORANGE
---30:BLUE
------10:ORANGE
------15:ORANGE
---------45:RED
------------40:YELLOW
------60:WHITE
---50:PINK
4:15:ORANGE

=== SWITCHED COMPONENTS 1 <-> 2 ===
WINDOW CHROME
1:30:BLACK
---30:PINK
---50:YELLOW
2:10:ORANGE
---20:GRAY
3:60:RED
---5:ORANGE
---30:BLUE
------10:ORANGE
------15:ORANGE
---------45:RED
------------40:YELLOW
------60:WHITE
---50:PINK
4:15:ORANGE
```

## Implementation Notes

### Component Sorting Rules
* Components in the internal collection are sorted by:
    1. Weight (ascending order)
    2. Color (alphabetically) if weights are equal

### ToString Format
* Window name: `WINDOW [name]`
* Each component level is represented by:
    * Format: `[position]:[weight]:[color]`
    * Indentation: Three dashes (`---`) per nesting level
    * Components are displayed recursively with their nested components

### ChangeColor Behavior
* Changes the color of all components (including nested components) with weight **strictly less than** the provided weight parameter

### SwitchComponents Behavior
* Swaps the components at two specified positions
* Only top-level components at those positions are swapped