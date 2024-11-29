import java.util.stream.Stream;

public class EX1 {
    public static void main(String[] args) {
        System.out.println("Sum :" + Stream.of(
                new Widget(Widget.Color.YELLOW, 2),
                new Widget(Widget.Color.WHITE, 5),
                new Widget(Widget.Color.BLACK, 10),
                new Widget(Widget.Color.BLUE, 20),
                new Widget(Widget.Color.RED, 15),
                new Widget(Widget.Color.GREEN, 5)
        ).mapToInt(Widget::getWeight).sum());
    }

}
