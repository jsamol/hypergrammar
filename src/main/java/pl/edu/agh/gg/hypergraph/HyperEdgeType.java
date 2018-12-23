package pl.edu.agh.gg.hypergraph;

public enum HyperEdgeType {
    BOUNDARY("B"),
    INTERIOR("I"),
    FACE("F");

    public String label;

    private HyperEdgeType(String label) {
        this.label = label;
    }
}
