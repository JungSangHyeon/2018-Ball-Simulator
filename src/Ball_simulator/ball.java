package Ball_simulator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ball {

	 int w = 40;
	 int h = 40;

	Color cr = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));

	List<Integer> point_x = new ArrayList<Integer>();
	List<Integer> point_y = new ArrayList<Integer>();

	public void setPoint() {
		point_x.add(point_x.get(0));
		point_y.add(point_y.get(0));
		
		point_x.remove(0);
		point_y.remove(0);
	}
}
