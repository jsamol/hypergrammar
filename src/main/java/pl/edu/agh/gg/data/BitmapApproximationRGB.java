package pl.edu.agh.gg.data;

public class BitmapApproximationRGB {

    private int[][] approx_r;
    private int[][] approx_g;
    private int[][] approx_b;

    public BitmapApproximationRGB(int[][] approx_r, int[][] approx_g, int[][] approx_b) {
        this.approx_r = approx_r;
        this.approx_g = approx_g;
        this.approx_b = approx_b;
    }

    public int[][] getApprox_r() {
        return approx_r;
    }

    public int[][] getApprox_g() {
        return approx_g;
    }

    public int[][] getApprox_b() {
        return approx_b;
    }
}
