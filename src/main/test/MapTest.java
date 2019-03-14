import com.map.utils.MapUtil;

public class MapTest {
    public static void main(String[] args) {
        double longtitude = 108.439184;
        double latitude = 34.274872;
        double dlat = MapUtil.dlat(100, latitude);
        double dlon = MapUtil.dlon(100, longtitude);
        System.out.format("%.6f %.6f\n", dlat, dlon);
//        System.out.println((longtitude - dlon) + "," + (longtitude + dlon));
//        System.out.println((latitude - dlat) + "," + (latitude + dlat));
    }
}
