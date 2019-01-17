package pl.edu.agh.gg.util;

import pl.edu.agh.gg.hypergraph.Vertex;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class VertexUtil {

    private VertexUtil() {
        throw new UnsupportedOperationException();
    }

    public static Optional<Vertex> findMaxXMaxY(Set<Vertex> vertices) {
        int maxX = Collections.max(mapToXCord(vertices));
        int maxY = Collections.max(mapToYCord(vertices));
        return findWithXAndY(vertices, maxX, maxY);
    }

    public static Optional<Vertex> findMaxXMaxY(List<Vertex> vertices) {
        int maxX = Collections.max(mapToXCord(vertices));
        int maxY = Collections.max(mapToYCord(vertices));
        return findWithXAndY(vertices, maxX, maxY);
    }

    public static Optional<Vertex> findMinXMinY(Set<Vertex> vertices) {
        int minX = Collections.min(mapToXCord(vertices));
        int minY = Collections.min(mapToYCord(vertices));
        return findWithXAndY(vertices, minX, minY);
    }

    public static Optional<Vertex> findMinXMinY(List<Vertex> vertices) {
        int minX = Collections.min(mapToXCord(vertices));
        int minY = Collections.min(mapToYCord(vertices));
        return findWithXAndY(vertices, minX, minY);
    }

    public static Optional<Vertex> findMinXMaxY(Set<Vertex> vertices) {
        int minX = Collections.min(mapToXCord(vertices));
        int maxY = Collections.max(mapToYCord(vertices));
        return findWithXAndY(vertices, minX, maxY);
    }

    public static Optional<Vertex> findMinXMaxY(List<Vertex> vertices) {
        int minX = Collections.min(mapToXCord(vertices));
        int maxY = Collections.max(mapToYCord(vertices));
        return findWithXAndY(vertices, minX, maxY);
    }

    public static Optional<Vertex> findMaxXMinY(Set<Vertex> vertices) {
        int maxX = Collections.max(mapToXCord(vertices));
        int minY = Collections.min(mapToYCord(vertices));
        return findWithXAndY(vertices, maxX, minY);
    }

    public static Optional<Vertex> findMaxXMinY(List<Vertex> vertices) {
        int maxX = Collections.max(mapToXCord(vertices));
        int minY = Collections.min(mapToYCord(vertices));
        return findWithXAndY(vertices, maxX, minY);
    }

    public static Optional<Vertex> findWithXAndY(Set<Vertex> vertices, int x, int y) {
        return vertices.stream()
                       .filter(vertex -> vertex.getX() == x)
                       .filter(vertex -> vertex.getY() == y)
                       .findFirst();
    }

    private static Optional<Vertex> findWithXAndY(List<Vertex> vertices, int x, int y) {
        return vertices.stream()
                .filter(vertex -> vertex.getX() == x)
                .filter(vertex -> vertex.getY() == y)
                .findFirst();
    }

    private static List<Integer> mapToXCord(Set<Vertex> vertices) {
        return vertices.stream().map(Vertex::getX).collect(Collectors.toList());
    }

    private static List<Integer> mapToXCord(List<Vertex> vertices) {
        return vertices.stream().map(Vertex::getX).collect(Collectors.toList());
    }

    private static List<Integer> mapToYCord(Set<Vertex> vertices) {
        return vertices.stream().map(Vertex::getY).collect(Collectors.toList());
    }

    private static List<Integer> mapToYCord(List<Vertex> vertices) {
        return vertices.stream().map(Vertex::getY).collect(Collectors.toList());
    }
}
