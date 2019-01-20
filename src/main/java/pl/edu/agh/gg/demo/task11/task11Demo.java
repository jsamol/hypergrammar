package pl.edu.agh.gg.demo.task11;

import com.google.common.collect.Sets;
import pl.edu.agh.gg.production.*;
import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.ui.HyperGraphDrawer;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class task11Demo {

    int[] parseNumbersInLine(String line){
        String delimiterRegex = "[ ]*,[ ]*";
        return Arrays.stream(line.split(delimiterRegex)).mapToInt(Integer::parseInt).toArray();
    }


    HyperGraph runProduction(int productionNumber, int[] coordinates, int[] rgb, HyperGraph graph, BufferedImage image) throws IOException {

        switch(productionNumber){
            case 1:
                int x1 = coordinates[0];
                int x2 = coordinates[1];
                int y1 = coordinates[2];
                int y2 = coordinates[3];

                RgbColor point1Color = new RgbColor(rgb[0],rgb[1],rgb[2]);
                RgbColor point2Color = new RgbColor(rgb[3],rgb[4],rgb[5]);
                RgbColor point3Color = new RgbColor(rgb[6],rgb[7],rgb[8]);
                RgbColor point4Color = new RgbColor(rgb[9],rgb[10],rgb[11]);

                P1Production p1 = new P1Production(x1,x2,y1,y2,point1Color,point2Color,point3Color,point4Color);
                p1.apply(graph);
                return graph;

            case 2:

                int xP2 = (coordinates[0] + coordinates[1])/2;
                int yP2 = (coordinates[2] + coordinates[3])/2;

                HyperEdge edgeP2 = graph.getEdges()
                        .stream()
                        .filter(e -> e.getType() == HyperEdgeType.INTERIOR &&
                                e.edgeContains(coordinates[0],coordinates[1]) &&
                                e.edgeContains(coordinates[2],coordinates[3]))
                        .findFirst().get();

                //Production p5 = new P5Production(edgeP2);
                //p5.apply(graph);

                Color p2Color = new Color(rgb[0],rgb[1],rgb[2]);
                image.setRGB(xP2,yP2,p2Color.getRGB());
                P2Production p2 = new P2Production(image, edgeP2);
                p2.apply(graph);
                return graph;


            case 3:
                int xP3 = (coordinates[0] + coordinates[1])/2;
                int yP3 = (coordinates[2] + coordinates[3])/2;
                Color p3Color = new Color(rgb[0],rgb[1],rgb[2]);
                image.setRGB(xP3,yP3,p3Color.getRGB());

                HyperEdge edgeP3 = graph.getEdges()
                        .stream()
                        .filter(edge -> edge.getType() == HyperEdgeType.BOUNDARY &&
                                edge.isEdgeBetween(coordinates[0],coordinates[1], coordinates[2], coordinates[3]))
                        .findFirst().get();
                try {
                    Production p3 = new P3Production(image, edgeP3);
                    p3.apply(graph);
                } catch (Throwable e){
                    System.out.println("Could not apply p3");
                }
                return graph;

            case 4:

                int xP4 = (coordinates[0]+coordinates[2])/2;
                int yP4 = coordinates[1];
                Color p4Color = new Color(rgb[0],rgb[1],rgb[2]);
                image.setRGB(xP4,yP4,p4Color.getRGB());

                getCombinationOfHyperEdges(edgesOfType(HyperEdgeType.FACE, graph, coordinates), 3).forEach(combination -> {
                    try {
                        P4Production p4Production = new P4Production(image, combination.toArray(new HyperEdge[0]));
                        p4Production.apply(graph);
                    } catch (Throwable e) {
                        System.out.println("Could not apply p4");
                    }
                });

                return graph;

            case 5:

                int xP5 = coordinates[0];
                int yP5 = coordinates[1];

                HyperEdge edgeP5 = graph.getEdges()
                        .stream()
                        .filter(e -> e.getType() == HyperEdgeType.INTERIOR &&
                                e.edgeContains(xP5,yP5))
                        .findFirst().get();

                Production p5 = new P5Production(edgeP5);
                p5.apply(graph);

                return graph;

            default:
                System.out.println("Production number is wrong");
        }
        return null;
    }

    private Set<Set<HyperEdge>> getCombinationOfHyperEdges(List<HyperEdge> hyperEdges, int combinationLength) {
        return Sets.powerSet(new HashSet<>(hyperEdges))
                .stream()
                .filter(set -> set.size() == combinationLength)
                .collect(Collectors.toSet());
    }

    private List<HyperEdge> edgesOfType(HyperEdgeType type, HyperGraph graph, int []coordinates) {
        return graph.getEdges()
                .stream()
                .filter(edge -> edge.getType() == type &&
                        ((edge.edgeContains(coordinates[0],coordinates[1]) && (edge.getDir() == HyperEdgeDirection.RIGHT || edge.getDir() == HyperEdgeDirection.LEFT )||
                                (edge.edgeContains(coordinates[2],coordinates[3])&& (edge.getDir() == HyperEdgeDirection.RIGHT || edge.getDir() == HyperEdgeDirection.LEFT )) ||
                                (edge.edgeContains(coordinates[4],coordinates[5])))))
                .collect(Collectors.toList());
    }


    public void parseFileAndRunProductions(String pathname) {
        Scanner scanner;
        int width = 100;
        int height = 50;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        File f = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(pathname)).getFile());
        try {
            scanner = new Scanner(f);
            HyperGraph graph = new HyperGraph();

            while (scanner.hasNext()) {
                int productionNumber = Integer.parseInt(scanner.nextLine());
                int[] coordinates = parseNumbersInLine(scanner.nextLine());
                int[] rgb;
                if (scanner.hasNext() && productionNumber != 5)
                    rgb = parseNumbersInLine(scanner.nextLine());
                else
                    rgb = null;

                graph = runProduction(productionNumber, coordinates, rgb, graph, img);
                if(graph == null){
                    return;
                }
            }
            HyperGraphDrawer.draw(graph);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    public static void main(String[] args) throws IOException {
        task11Demo parser = new task11Demo();
        parser.parseFileAndRunProductions("task11/productions.txt");
    }
}
