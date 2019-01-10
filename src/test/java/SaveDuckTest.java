import ru.jolkin.countDucks.duck.Duck;
import ru.jolkin.countDucks.duck.Type;

import java.awt.*;

public class SaveDuckTest extends TestCase {

    public void testGsonRecover() {

        Duck duck = new Duck(new Point(1, 1), Type.MEN);
        String json = duck.toJson();

        Duck duck1 = Duck.createByJson(json);

        assertEquals(duck.getPosition(), duck1.getPosition());
        assertEquals(duck.getSize(), duck1.getSize());
        assertEquals(duck.getType().getName(), duck1.getType().getName());
        assertEquals(duck.getType().getColor(), duck1.getType().getColor());
    }

    public void testEqualsType() {
        Type women = Type.WOMEN;
        Type women2 = Type.WOMEN;
        Type women3 = new Type(Color.RED, "Women");

        assertTrue(women.equals(women2));
        assertTrue(women.equals(women3));
    }
}
