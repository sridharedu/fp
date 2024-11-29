public class Widget {

    // Enum for Color
    public enum Color {
        RED, BLUE, GREEN, YELLOW, BLACK, WHITE
    }

    private final Color color;
    private final int weight;

    // Constructor
    public Widget(Color color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    // Getter for color
    public Color getColor() {
        return color;
    }

    // Getter for weight
    public int getWeight() {
        return weight;
    }
}
