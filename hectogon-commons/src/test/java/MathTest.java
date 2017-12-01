import com.cedricmartens.commons.Point;
import org.junit.Assert;
import org.junit.Test;

public class MathTest
{
    @Test
    public void distanceBetweenPoints()
    {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 2);

        Assert.assertEquals(2, p1.distanceBetweenPoint(p2), 0);

        p1 = new Point(1, 2);
        p2 = new Point(4, 6);

        Assert.assertEquals(5, p1.distanceBetweenPoint(p2), 0);
    }
}
