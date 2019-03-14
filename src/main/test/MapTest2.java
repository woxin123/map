import static java.lang.Math.PI;

public class MapTest2 {
    public static double[] getAround(double lat, double lon, int raidus) {

        Double latitude = lat;
        Double longitude = lon;

        Double degree = (24901 * 1609) / 360.0;
        double raidusMile = raidus;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;
        System.out.format("%.6f\n", radiusLat);
        Double mpdLng = degree * Math.cos(latitude * (PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
        System.out.format("%.6f\n",radiusLng);
        return new double[]{minLat, minLng, maxLat, maxLng};
    }

    public static void main(String[] args) {
        double[] a = getAround(34.58895, 108.43936, 10);
        System.out.println(a[0] + " " + a[1] + " " + a[2] + " " + a[3]);
    }
}
