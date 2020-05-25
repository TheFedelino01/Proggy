package proggyGradle.server.Trilateration;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import proggyGradle.server.coordinate;

import java.util.function.Function;


/**
 * Classe che effettua la trilaterazione di un dispositivo partendo da tre coordinate e le relative distanze
 *
 * @author Giacomo Orsenigo
 */
public class Trilateration {
    private static final int earthR = 6371;

    private Trilateration() {
    }

    /**
     * @brief effettua la trilaterazione
     *
     * Converte le coordinate geografiche in coordinate del piano cartesiano (xyz) del sistema ECEF
     * @see <a href="https://it.wikipedia.org/wiki/ECEF">https://it.wikipedia.org/wiki/ECEF</a>
     * @see <a href="https://it.wikipedia.org/wiki/ECEF">https://it.wikipedia.org/wiki/ECEF</a>
     *
     * Fa delle trasformazioni per avere il primo cerchio con centro nell'origine e il secondo cerchio con centro sull'asse x
     * Fa calcoli che non ho ancora capito a cosa servono :/
     * Riconverte le coordinate del piano euclideo in coordinate geografiche
     * @see <a href="https://gis.stackexchange.com/a/415">https://gis.stackexchange.com/a/415</a>
     * @see <a href="https://stackoverflow.com/a/2862714">https://stackoverflow.com/a/2862714</a>
     *
     * @param ap1 punto 1
     * @param ap2 punto 2
     * @param ap3 punto 3
     * @return coordinate del dispositivo
     */
    public static coordinate getPoint(APInfo ap1, APInfo ap2, APInfo ap3) {
        return getPoint(ap1, ap2, ap3, APInfo::getDistanzaOld);
    }


    /**
     * @brief effettua la trilaterazione
     *
     * Converte le coordinate geografiche in coordinate del piano cartesiano (xyz) del sistema ECEF
     * @see <a href="https://it.wikipedia.org/wiki/ECEF">https://it.wikipedia.org/wiki/ECEF</a>
     * @see <a href="https://it.wikipedia.org/wiki/ECEF">https://it.wikipedia.org/wiki/ECEF</a>
     *
     * Fa delle trasformazioni per avere il primo cerchio con centro nell'origine e il secondo cerchio con centro sull'asse x
     * Fa calcoli che non ho ancora capito a cosa servono :/
     * Riconverte le coordinate del piano euclideo in coordinate geografiche
     * @see <a href="https://gis.stackexchange.com/a/415">https://gis.stackexchange.com/a/415</a>
     * @see <a href="https://stackoverflow.com/a/2862714">https://stackoverflow.com/a/2862714</a>
     *
     * @param ap1 punto 1
     * @param ap2 punto 2
     * @param ap3 punto 3
     * @param calcolaDistanza funzione di calcolo della distanza da un AP
     * @return coordinate del dispositivo
     */
    public static coordinate getPoint(APInfo ap1, APInfo ap2, APInfo ap3, Function<APInfo, Double> calcolaDistanza) {

        final double xA = earthR * (Math.cos(Math.toRadians(ap1.getCoordinate().getLatitudine())) * Math.cos(Math.toRadians(ap1.getCoordinate().getLongitudine())));
        final double yA = earthR * (Math.cos(Math.toRadians(ap1.getCoordinate().getLatitudine())) * Math.sin(Math.toRadians(ap1.getCoordinate().getLongitudine())));
        final double zA = earthR * (Math.sin(Math.toRadians(ap1.getCoordinate().getLatitudine())));

        final double xB = earthR * (Math.cos(Math.toRadians(ap2.getCoordinate().getLatitudine())) * Math.cos(Math.toRadians(ap2.getCoordinate().getLongitudine())));
        final double yB = earthR * (Math.cos(Math.toRadians(ap2.getCoordinate().getLatitudine())) * Math.sin(Math.toRadians(ap2.getCoordinate().getLongitudine())));
        final double zB = earthR * (Math.sin(Math.toRadians(ap2.getCoordinate().getLatitudine())));

        final double xC = earthR * (Math.cos(Math.toRadians(ap3.getCoordinate().getLatitudine())) * Math.cos(Math.toRadians(ap3.getCoordinate().getLongitudine())));
        final double yC = earthR * (Math.cos(Math.toRadians(ap3.getCoordinate().getLatitudine())) * Math.sin(Math.toRadians(ap3.getCoordinate().getLongitudine())));
        final double zC = earthR * (Math.sin(Math.toRadians(ap3.getCoordinate().getLatitudine())));

//        INDArray P1 = Nd4j.create(new double[]{xA, yA, zA}, 3);
//        INDArray P2 = Nd4j.create(new double[]{xB, yB, zB}, 3);
//        INDArray P3 = Nd4j.create(new double[]{xC, yC, zC}, 3);

//        INDArray ex = P2.sub(P1).div(Nd4j.norm2(P2.sub(P1)));
//        System.out.println(ex);
//        INDArray i = ex.mmul(P3.sub(P1));       //dot
//        System.out.println(i);
//
//        INDArray tmp = P3.sub(P1).sub(ex.mul(i.getColumn(0)));
//        INDArray ey = tmp.div(tmp.norm2());
//        System.out.println(ey);
//
//        System.out.println("ex: " + ex);
//        System.out.println("ey: " + ey);
//
//        INDArray ez = ey.mmul(ex);
//        System.out.println("ez: " + ez);
//
//        INDArray te = ex.mmul(ey, Nd4j.create(new double[]{1}, 1));
//        System.out.println("ez: " + te);
//
//        System.out.println(i.mmul(ex));
//        System.out.println(ey);


        final RealVector P1 = new ArrayRealVector(new double[]{xA, yA, zA});
        final RealVector P2 = new ArrayRealVector(new double[]{xB, yB, zB});
        final RealVector P3 = new ArrayRealVector(new double[]{xC, yC, zC});

//        System.out.println(P1);
//        System.out.println(P2);
//        System.out.println(P3);

        final RealVector _ex = P2.subtract(P1).mapDivide(P2.subtract(P1).getNorm());
//        System.out.println(_ex);

        final double i = _ex.dotProduct(P3.subtract(P1));
//        System.out.println(i);

        final RealVector _tmp = P3.subtract(P1).subtract(_ex.mapMultiply(i));
        final RealVector _ey = _tmp.mapDivide(_tmp.getNorm());
//        System.out.println(_ey);

        final Vector3D ex = new Vector3D(_ex.getEntry(0), _ex.getEntry(1), _ex.getEntry(2));
        final Vector3D ey = new Vector3D(_ey.getEntry(0), _ey.getEntry(1), _ey.getEntry(2));
        final Vector3D ez = ex.crossProduct(ey);
//        System.out.println(ez);

        final double d = P2.subtract(P1).getNorm();
        final double j = _ey.dotProduct(P3.subtract(P1));

        final double x = (Math.pow(calcolaDistanza.apply(ap1), 2) - Math.pow(calcolaDistanza.apply(ap2), 2) + Math.pow(d, 2)) / (2 * d);
        final double y = ((Math.pow(calcolaDistanza.apply(ap1), 2) - Math.pow(calcolaDistanza.apply(ap3), 2) + Math.pow(i, 2) + Math.pow(j, 2)) / (2 * j)) - ((i / j) * x);
        final double z = Math.sqrt(Math.abs(Math.pow(calcolaDistanza.apply(ap1), 2) - Math.pow(x, 2) - Math.pow(y, 2)));

        final Vector3D _P1 = new Vector3D(P1.getEntry(0), P1.getEntry(1), P1.getEntry(2));
        final Vector3D triPt = _P1.add(ex.scalarMultiply(x)).add(ey.scalarMultiply(y)).add(ez.scalarMultiply(z));
//        System.out.println(triPt);

        final double lat = Math.toDegrees(Math.asin(triPt.getZ() / earthR));
        final double lon = Math.toDegrees(Math.atan2(triPt.getY(), triPt.getX()));

        return new coordinate(lat, lon);
    }

}
