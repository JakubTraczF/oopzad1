public class Segment {
    private Point p1;
    private static Point p2;

    Segment(Point p1, Point p2){
        this.p1=p1;
        this.p2=p2;
    }
    public double length() {
       return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }
    public Point getP1() {
        return p1;
    }
    public Point getP2() {
        return p2;
    }
    public String toSvg(){
        return "<line x1=\""+p1.x+"\" y1=\""+p1.y+"\" x2=\""+p2.x+"\" y2=\""+p2.y+"\" style=\"stroke:black;stroke-width:1\" />";
    }
    public static Segment[] perpendicularSegments(Segment segment, Point point){
        double dx = segment.p2.x - segment.p1.x ;
        double dy = segment.p2.y - segment.p1.y;
        double  new_dx1=-dy;
        double new_dy1=dx;
        double  new_dx2=dy;
        double new_dy2=-dx;
        double endx1 = point.x + new_dx1;
        double endy1 = point.x + new_dy1;
        double endx2 = point.x + new_dx2;
        double endy2 = point.x + new_dy2;

        Point first_end = new Point(endx1, endy1);
        Point second_end = new Point(endx2,endy2);

        Segment first=new Segment(point, first_end);
        Segment second=new Segment(point,second_end);

        return new Segment[]{first,second};
    }
}
