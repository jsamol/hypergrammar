package pl.edu.agh.gg.ui.graph;

public class StylesheetProvider {
    public static String STYLESHEET =
            "node {" +
                    "text-alignment: above;" +
                    "text-background-mode: plain;" +
            "}" +
            "node." + HtmlClass.HYPER_EDGE + "{" +
                    "shape: rounded-box;" +
                    "size: 30px, 30px;" +
                    "text-size: 15px;" +
                    "text-style: bold;" +
                    "text-alignment: center;" +
                    "fill-color: #fff;" +
                    "stroke-mode: plain;" +
                    "stroke-color: #000;" +
                    "text-background-mode: none;" +
            "}" +
            "sprite." + HtmlClass.HYPER_EDGE + "{" +
                    "shape: rounded-box;" +
                    "size: 30px;" +
                    "text-size: 15px;" +
                    "text-alignment: above;" +
                    "fill-mode: none;" +
            "}";
}
