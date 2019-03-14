import java.util.Random;

public class RandomTest {
    public static void main(String[] args) {
        Random random = new Random();
        System.out.println((int)(random.nextDouble() * 90000 + 10000));
    }
}
