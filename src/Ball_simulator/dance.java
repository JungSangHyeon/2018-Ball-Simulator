package Ball_simulator;

import java.awt.Color; 
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;

public class dance {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException {
		Ball_Dance start = new Ball_Dance();
	}
}

@SuppressWarnings("serial")
class Ball_Dance extends JFrame implements MouseListener, MouseMotionListener, KeyListener, Runnable {

	int f_width = 1920;
	int f_height = 1080;

	Thread th;

	int cnt = 0;

	List<List<Integer>> point_x = new ArrayList<List<Integer>>();
	List<List<Integer>> point_y = new ArrayList<List<Integer>>();

	List<Integer> child_point_x = new ArrayList<Integer>();
	List<Integer> child_point_y = new ArrayList<Integer>();

	Vector<ball> BallVector = new Vector<ball>();

	boolean Black_mode_on = false;
	int BMOC = 0;

	ball B;

	boolean m1 = true;
	boolean m2 = false;
	boolean m3 = false;
	boolean m4 = false;
	boolean m5 = false;
	
	Ball_Dance() {
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);

		th = new Thread(this);
		th.start();

		setSize(f_width, f_height);
		setTitle("Dance floor");

		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 1));//투명도있는 배경이면 깜빡이가 없네. 
		setVisible(true);
	}

	public void run() {
		try {
			while (true) {
				repaint();
				Thread.sleep(10);
				cnt++;
			}
		} catch (Exception e) {}
	}

	public void paint(Graphics g) {
		g.clearRect(0, 0, f_width, f_height);

		//선 보일까말까?
		if (Black_mode_on) {g.setColor(Color.BLACK);}
		else {g.setColor(Color.WHITE);}

		//완료 전의선
		for (int i = 0; i < child_point_x.size() - 1; i++) {
			g.drawLine(child_point_x.get(i), child_point_y.get(i), child_point_x.get(i + 1), child_point_y.get(i + 1));
		}

		//선
		for (int i = 0; i < point_x.size(); i++) {
			for (int v = 0; v < point_x.get(i).size() - 1; v++) {
				g.drawLine(point_x.get(i).get(v), point_y.get(i).get(v), point_x.get(i).get(v + 1),point_y.get(i).get(v + 1));
			}
		}

		for (ball b : this.BallVector) {
			b.setPoint();
			g.setColor(b.cr);

			// 노말(원)
			if (m1) {g.fillOval(b.point_x.get(0) - b.w / 2, b.point_y.get(0) - b.h / 2, b.w, b.h);}

			// 화면의중심과 연결선
			if (m2) {
				g.fillOval(b.point_x.get(0) - b.w / 2, b.point_y.get(0) - b.h / 2, b.w, b.h);
				g.drawLine(f_width / 2, f_height / 2, b.point_x.get(0), b.point_y.get(0));
			}

			// trace
			if (m3) {
				for (int i = 0; i < b.point_x.size() / 4; i++) {
					g.fillOval(b.point_x.get(i) - i / 2, b.point_y.get(i) - i / 2, i, i);
				}
			}

			if (m4 || m5)
				for (ball b2 : this.BallVector) {
					// 원의중점끼리 잇기
					if (m4) {g.drawLine(b.point_x.get(0), b.point_y.get(0), b2.point_x.get(0), b2.point_y.get(0));}
					if (m5) {
						// 선의 모든점 잇기
						for (int i = 0; i < b.point_x.size(); i++) {
							try {
								g.drawLine(b.point_x.get(i), b.point_y.get(i), b2.point_x.get(i), b2.point_y.get(i));
							} catch (Exception e) {}
						}
					}
				}
		}
	}

	public void mousePressed(MouseEvent e) {
		B = new ball();

		child_point_x = new ArrayList<Integer>();
		child_point_y = new ArrayList<Integer>();
	}
	
	public void mouseReleased(MouseEvent arg0) {
		BallVector.add(B);

		point_x.add(child_point_x);
		point_y.add(child_point_y);
	}

	public void mouseDragged(MouseEvent e) {
		B.point_x.add(e.getX());
		B.point_y.add(e.getY());

		child_point_x.add(e.getX());
		child_point_y.add(e.getY());
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {System.exit(0);}

		if (e.getKeyCode() == KeyEvent.VK_Q) {
			if (BMOC % 2 == 0) {Black_mode_on = true;}
			else {Black_mode_on = false;}
			BMOC++;
		}

		if (e.getKeyCode() == KeyEvent.VK_Z) {//이전으로
			point_x.remove(point_x.size() - 1);
			point_y.remove(point_y.size() - 1);
			BallVector.remove(BallVector.size() - 1);
			child_point_x.clear();
			child_point_y.clear();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_D) {
			point_x.clear();
			point_y.clear();
			BallVector.clear();
			child_point_x.clear();
			child_point_y.clear();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_1) {
			ready_to_change();
			m1 = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_2) {
			ready_to_change();
			m2 = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_3) {
			ready_to_change();
			m3 = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_4) {
			ready_to_change();
			m4 = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_5) {
			ready_to_change();
			m5 = true;
		}
	}

	private void ready_to_change() {
		m1 = false;
		m2 = false;
		m3 = false;
		m4 = false;
		m5 = false;
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent e) {}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent e) {}
}
