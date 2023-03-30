# Assignment A4: Urbanism

-   Saad Salman [salmam12@mcmaster.ca]


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
mosser@azrael A2 % java -jar generator/generator.jar -k grid -h 1080 -w 2000 -p 2000 -s 20 -o img/grid.mesh
mosser@azrael A2 % java -jar generator/generator.jar -k irregular -h 2000 -w 2000 -p 1000 -s 20 -o img/irregular.mesh
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

Run island generator using a mesh named `irregular.mesh` as input and producing a mesh named `output.mesh` as output
Inputs:

1. --mode lagoon generates a lagoon, to not create a lagoon, exclude the --mode argument
2. --shape arguments include circle, shape, oval
3. --lakes argument is number of lakes you want to create
4. --aquifers argument is number of aquifers you want to create
5. --biomes: arguments include grassland and desert
6. --seed argument can be included to generate a previous mesh
7. --elevation argument include volcano/rockymountain/both

```
java -jar island/island.jar -i img/irregular.mesh -o img/output.mesh --mode lagoon --shape circle --lakes 10 --rivers 10 --aquifers 20 --biomes grassland --elevation volcano
```

## Visualizing the Island

```
java -jar visualizer/visualizer.jar -i img/output.mesh -o img/output.svg
```

## Backlog

### Product Backlog

| Id  | Feature title                                                   | Who?         | Start    | End      | Status |
| :-: | --------------------------------------------------------------- | ------------ | -------- | -------- | ------ |

