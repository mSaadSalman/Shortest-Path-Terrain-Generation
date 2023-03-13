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

## Backlog

### Product Backlog

| Id  | Feature title                                                   | Who?         | Start    | End      | Status |
| :-: | --------------------------------------------------------------- | ------------ | -------- | -------- | ------ |
| F01 | Creating a minimal mesh ADT                                     | Mahad & Saad | 7/02/23  | 20/02/23 | D      |
| F02 | Producing full meshes                                           | Saad & Mahad | 11/02/23 | 22/02/23 | D      |
| F03 | Playing with rendering (Setting colors/thicknesses)             | Ali          | 11/02/23 | 21/02/23 | D      |
| F04 | Visualization mode                                              | Ali          | 15/02/23 | 23/02/23 | D      |
| F05 | Generate Random Points                                          | Saad & Ali   | 15/02/23 | 24/02/23 | D      |
| F06 | Compute the Voronoi Diagram                                     | Ali          | 18/02/23 | 27/02/23 | D      |
| F07 | Crop the mesh to the expected size                              | Mahad        | 20/02/23 | 27/02/23 | D      |
| F08 | Apply Lloyd relaxation                                          | Mahad        | 22/02/23 | 27/02/23 | D      |
| F09 | Compute neiborhood relationships using Delaunay’s triangulation |              |          |          | P      |
| F10 | For each irregular polygon, reorder its segments                |              |          |          | P      |
