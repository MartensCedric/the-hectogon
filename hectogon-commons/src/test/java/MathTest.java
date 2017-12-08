import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.util.FuzzyDirection;
import com.cedricmartens.commons.util.MathUtil;
import org.junit.Assert;
import org.junit.Test;

public class MathTest
{
    @Test
    public void distanceBetweenPoints()
    {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 2);

        Assert.assertEquals(2, p1.distanceBetweenPoints(p2), 0);

        p1 = new Point(1, 2);
        p2 = new Point(4, 6);

        Assert.assertEquals(5, p1.distanceBetweenPoints(p2), 0);
    }

    @Test
    public void fuzzyDirection()
    {
        FuzzyDirection direction = MathUtil.getFuzzyDirection(0);
        Assert.assertEquals(FuzzyDirection.RIGHT, direction);

        direction = MathUtil.getFuzzyDirection(6);
        Assert.assertEquals(FuzzyDirection.RIGHT, direction);

        direction = MathUtil.getFuzzyDirection(3);
        Assert.assertEquals(FuzzyDirection.LEFT, direction);

        direction = MathUtil.getFuzzyDirection(4.5f);
        Assert.assertEquals(FuzzyDirection.DOWN, direction);

        direction = MathUtil.getFuzzyDirection(0);
        Assert.assertEquals(FuzzyDirection.RIGHT, direction);

        direction = MathUtil.getFuzzyDirection(2);
        Assert.assertEquals(FuzzyDirection.UP, direction);

        direction = MathUtil.getFuzzyDirection(1.7270707f);
        Assert.assertEquals(FuzzyDirection.UP, direction);

        direction = MathUtil.getFuzzyDirection(-1.9866968f);
        Assert.assertEquals(FuzzyDirection.DOWN, direction);

        direction = MathUtil.getFuzzyDirection(-2.5140026f);
        Assert.assertEquals(FuzzyDirection.LEFT, direction);
    }
}
