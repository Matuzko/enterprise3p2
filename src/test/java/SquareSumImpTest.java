import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class SquareSumImpTest {

    @Test
    public void getSquareSum() throws Exception {
        SquareSumImp sumImp = new SquareSumImp();
        int mass [] = {1,2,3,4,5,6,7,8,9,10,11,12,13};
        long result = sumImp.getSquareSum(mass, 4);
        Assert.assertEquals(819, result );

    }
}