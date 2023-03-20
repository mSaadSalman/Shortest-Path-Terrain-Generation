# Assignment A2: Mesh Generator

-   Mahad Ahmed [ahmem73@mcmaster.ca]
-   Saad Salman [salmam12@mcmaster.ca]
-   Ali Zia [ziam8@mcmaster.ca]


## Installation instructions

```
mosser@azrael A2 % mvn install
```
It creates two jars:

`generator/generator.jar` to generate meshes  
`visualizer/visualizer.jar` to visualize such meshes as SVG files

## Examples of Execution

### Generating a mesh, grid or irregular

```
mosser@azrael A2 % java -jar generator/generator.jar -k grid -h 1080 -w 1920 -p 1000 -s 20 -o img/grid.mesh
mosser@azrael A2 % java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1000 -s 20 -o img/irregular.mesh
```
One can run the generator with `-help` as option to see the different command line arguments that are available

## Visualizing a mesh, (regular or debug)

```
mosser@azrael A2 % java -jar visualizer/visualizer.jar -i img/grid.mesh -o img/grid.svg          
mosser@azrael A2 % java -jar visualizer/visualizer.jar -i img/grid.mesh -o img/grid_debug.svg -x
mosser@azrael A2 % java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular.svg   
mosser@azrael A2 % java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular_debug.svg -x
```
Note: PDF versions of the SVG files were created with `rsvg-convert`

## Generating the Island
Run island generator using a mesh named `irregular.mesh` as input and producing a mesh named `lagoon.mesh` as output
```
java -jar island/island.jar -i img/irregular.mesh -o img/lagoon.mesh --mode lagoon
```

## Visualizing the Island
```
java -jar visualizer/visualizer.jar -i img/lagoon.mesh -o img/lagoon.svg
```

## Backlog

### Product Backlog

| Id  | Feature title                                                   | Who?         | Start    | End      | Status |
| :-: | --------------------------------------------------------------- | ------------ | -------- | -------- | ------ |
| F01 | Tile Creation Water and Land (Differentiation)                   | Mahad         | 1/03/23  | 7/3/23 | D      |
| F02 | Centering Function (Creating Shapes figure)                     | Ali            | 7/03/23 | 14/03/23 | D      |
| F03 | Adding Lagoons                                                  | Saad         | 15/02/23 | 19/03/23 | D      |
| F04 |                                                                 |           |  | |       |
| F05 |                                                                 |           |  |  |      |
| F06 |                                                                 |           |  |  |       |
