public class Line {
	private Point start;
	private Point end;

	public Line(Point s, Point e){
		Point st = new Point(s);
		Point en = new Point(e);
		start = st;
		end = en;
	}

	public Point getStart() {
		Point s = new Point(start);
		return s;
	}
	public Point getEnd() {
		Point e = new Point(end);
		return e;
	}

	public void setStart(Point s) {
		Point st = new Point(s);
		start = st;
	}
	public void setEnd(Point e) {
		Point en = new Point(e);
		end = en;
	}

	public double length() {
		return start.distance(end);
	}
}
