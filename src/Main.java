
public class Main {
    public static void main(String[] args) {

        Segment segment = new Segment(new Point(0,0),new Point(100,100));
        System.out.println(segment.toSvg());
        Segment[] segments = Segment.perpendicularSegments(segment,new Point(100/2,100/2));
        System.out.println(segment.length());
        System.out.println(segments[0].toSvg());
    }
}