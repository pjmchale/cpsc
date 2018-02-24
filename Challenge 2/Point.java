import java.lang.Math;


public class Point {
	private int xcoord;
	private int ycoord;

	public Point(int x, int y) {
		if (x >= 0) {
			xcoord = x;
		} else {
			xcoord = 0;
		}
		if (y >= 0) {
			ycoord = y;
		} else {
			ycoord = 0;
		}	
		
	}

	public Point(Point aPoint) {
		this(aPoint.getXCoord(), aPoint.getYCoord());
	}

	public int getYCoord() {
		return ycoord;
	}

	public int getXCoord() {
		return xcoord;
	}

	public void setXCoord(int x) {
		if(x >= 0) {
			xcoord = x;
		} 
	}

	public void setYCoord(int y) {
		if (y >= 0) {
			ycoord = y;
		}
	}

	public void moveUp(int move) {
		ycoord = ycoord - move;
	}
	public void moveDown(int move) {
		ycoord = ycoord + move;
	}
	public void moveRight(int move) {
		xcoord = xcoord + move;
	}
	public void moveLeft(int move) {
		xcoord = xcoord - move;
	}

	public double distance(Point newPoint){
		int x1 = xcoord;
		int y1 = ycoord;
		int x2 = newPoint.getXCoord();
		int y2 = newPoint.getYCoord();

		int rhs = x1 - x2;
		int lhs = y1 - y2;

		rhs = rhs * rhs;
		lhs = lhs * lhs;

		double output = rhs + lhs;
		output = Math.sqrt(output);

		return output;
	}

	public boolean equals(Point newPoint){
		return newPoint.getXCoord() == xcoord && newPoint.getYCoord() == ycoord;		
	}

}
