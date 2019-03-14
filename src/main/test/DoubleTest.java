import java.math.BigDecimal;
import java.text.NumberFormat;

public class DoubleTest {

    public static void main(String[] args) {
        double f = 111231.318312;
        BigDecimal b = new BigDecimal(f);
        double c = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(c);

        double x = 23.5455;
        NumberFormat ddf1 = NumberFormat.getNumberInstance();

        ddf1.setMaximumFractionDigits(2);
        String s = ddf1.format(x);
        System.out.println(s);
    }
}
