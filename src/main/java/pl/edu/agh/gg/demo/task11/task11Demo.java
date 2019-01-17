package pl.edu.agh.gg.demo.task11;

import pl.edu.agh.gg.production.*;

import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.ui.HyperGraphDrawer;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


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
                HyperEdge edgeP2 = graph.getEdges().stream().filter(e -> e.getType() == HyperEdgeType.INTERIOR).findFirst().get();

                Production p5 = new P5Production(edgeP2);
                p5.apply(graph);

                int xP2 = (coordinates[0] + coordinates[1])/2;
                int yP2 = (coordinates[2] + coordinates[3])/2;
                Color p2Color = new Color(rgb[0],rgb[1],rgb[2]);
                image.setRGB(xP2,yP2,p2Color.getRGB());
                P2Production p2 = new P2Production(image, edgeP2);
                p2.apply(graph);
                return graph;


            case 3:
                int xP3 = (coordinates[0]+coordinates[1])/2;
                int yP3 = coordinates[2];
                Color p3Color = new Color(rgb[0],rgb[1],rgb[2]);
                image.setRGB(xP3,yP3,p3Color.getRGB());

                HyperEdge edgeP3 = graph.getEdges()
                        .stream()
                        .filter(edge -> edge.getType() == HyperEdgeType.BOUNDARY &&
                                edge.isEdgeBetween(coordinates[0],coordinates[1], coordinates[2], coordinates[2]))
                        .findFirst().get();
                try {
                    Production p3 = new P3Production(image, edgeP3);
                    p3.apply(graph);
                } catch (Throwable e){
                    System.out.println("Could not apply p3");
                }
                HyperGraphDrawer drawer1 = new HyperGraphDrawer(graph);
                drawer1.draw();
                return graph;
            default:
                System.out.println("Production number is wrong");
        }
        return null;
    }

    /*public BufferedImage getBitMap(int[] coordinates, int[] rgb, BufferedImage image){


        for(int i = 0; i < coordinates.length / 2; i++ ) {
            int x = coordinates[i*2];
            int y = coordinates[i*2+1];

            for (int j = 0; j < rgb.length / 3; j++) {
                image.setRGB(x,y,new Color(rgb[j * 3], rgb[j * 3 + 1], rgb[j * 3 + 2]).getRGB());
            }
        }
        return image;
    }*/

    public void parseFileAndRunProduction(String pathname) {
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
                if (scanner.hasNext())
                    rgb = parseNumbersInLine(scanner.nextLine());
                else
                    rgb = null;

                graph = runProduction(productionNumber, coordinates, rgb, graph, img);
                if(graph == null){
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    public static void main(String[] args) throws IOException {
        task11Demo parser = new task11Demo();
        parser.parseFileAndRunProduction("task11/productions.txt");
    }
}
