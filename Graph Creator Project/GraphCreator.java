/*This is the GraphCreator project. It lets the user draw nodes and edges on JFrame, and it can also be able to check if two nodes are connected, 
 *and the travelling salesmen problems too. 
 * Name: Thomas Fang
 * Date: January 25th
 */

//Here are the imports of GraphCreator
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//This section sets up the basic elements needs to include in the project.
public class GraphCreator extends GraphPanel implements ActionListener, MouseListener {
	JFrame frame = new JFrame();
	GraphPanel panel = new GraphPanel();
	JButton nodeB = new JButton("Node");
	JButton edgeB = new JButton("Edge");
	JTextField labelsTF = new JTextField();
	JTextField firstNode = new JTextField("First");
	JTextField secondNode = new JTextField("Second");
	JButton connectedB = new JButton("Test Connected");
	Container west = new Container();
	Container east = new Container();
	Container south = new Container();
	JTextField salesmanStartTF1 = new JTextField("Start");
	JButton salesmanB = new JButton("Shortest Path");
	final int NODE_CREATE = 0;
	final int EDGE_FIRST = 1;
	final int EDGE_SECOND = 2;
	int state = NODE_CREATE;
	Node first = null;
	ArrayList<ArrayList<Node>> completed = new ArrayList<ArrayList<Node>>();

	public GraphCreator() {
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		west.setLayout(new GridLayout(3, 1));
		west.add(nodeB);
		nodeB.addActionListener(this);
		nodeB.setBackground(Color.GREEN);
		west.add(edgeB);
		edgeB.addActionListener(this);
		edgeB.setBackground(Color.LIGHT_GRAY);
		west.add(labelsTF);
		frame.add(west, BorderLayout.WEST);
		east.setLayout(new GridLayout(3, 1));
		panel.addMouseListener(this);
		
		east.add(firstNode);
		east.add(secondNode);
		east.add(connectedB);
		connectedB.addActionListener(this);
		frame.add(east, BorderLayout.EAST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		south.setLayout(new GridLayout(1, 2));
		south.add(salesmanStartTF1);
		south.add(salesmanB);
		salesmanB.addActionListener(this);
		frame.add(south, BorderLayout.SOUTH);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GraphCreator();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	//If a mouse click is released on a node, then the Graph will be following actions.
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (state == NODE_CREATE) {
			panel.addNode(e.getX(), e.getY(), labelsTF.getText());
		} else if (state == EDGE_FIRST) {
			Node n = panel.getNode(e.getX(), e.getY());
			if (n != null) {
				first = n;
				state = EDGE_SECOND;
				n.setHighlighted(true);
				// add highlighting
			}
		} else if (state == EDGE_SECOND) {
			Node n = panel.getNode(e.getX(), e.getY());
			if (n != null && !first.equals(n)) {
				String s = labelsTF.getText();
				boolean valid = true;
				for (int a = 0; a < s.length(); a++) {
					if (Character.isDigit(s.charAt(a)) == false) {
						valid = false;
					}
				}
				if (valid == true) {
					first.setHighlighted(false);
					// remove highlighting
					panel.addEdge(first, n, labelsTF.getText());
					first = null;
					state = EDGE_FIRST;
				} else {
					JOptionPane.showMessageDialog(frame, "Can only have digits in edge label.");
				}
			}
		}
		frame.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(nodeB)) {
			nodeB.setBackground(Color.GREEN);
			edgeB.setBackground(Color.LIGHT_GRAY);
			state = NODE_CREATE;
		}
		if (e.getSource().equals(edgeB)) {
			edgeB.setBackground(Color.GREEN);
			nodeB.setBackground(Color.LIGHT_GRAY);
			state = EDGE_FIRST;
			panel.stopHighlightling();
			frame.repaint();
	}
		if (e.getSource().equals(connectedB)) {
			if (panel.nodeExists(firstNode.getText()) == false) {
				JOptionPane.showMessageDialog(frame, "First node is not in your graph.");
			}
			else if (panel.nodeExists(secondNode.getText()) == false) {
				JOptionPane.showMessageDialog(frame, "Second node is not in your graph.");
			}
		
		
		else {
			Queue queue = new Queue();
			ArrayList<String> connectedList = new ArrayList<String>();
			connectedList.add(panel.getNode(firstNode.getText()).getLabel());
			ArrayList<String> edges = panel.getConnectedLabels(firstNode.getText());
			for (int a = 0; a < edges.size(); a++) {
				//check each connected node
				queue.enqueue(edges.get(a));
			}
			while (queue.isEmpty() == false) {
				String currentNode = queue.dequeue();
				if (connectedList.contains(currentNode) == false) {
					connectedList.add(currentNode);
				}
				 edges = panel.getConnectedLabels(currentNode);
				for (int a = 0; a < edges.size(); a++) {
					if (connectedList.contains(edges.get(a)) == false) {
						//check each connected node
						queue.enqueue(edges.get(a));
					}
				}
			}
			//Check if the two nodes are connected.
			if (connectedList.contains(secondNode.getText())) {
				JOptionPane.showMessageDialog(frame, "Connected!");
			}
			else { 
				JOptionPane.showMessageDialog(frame, "Not connected.");
			}
		}
	
	}
	
		
	if(e.getSource().equals(salesmanB)) {
		if (panel.getNode(salesmanStartTF1.getText()) != null ) {
			ArrayList<Node> path = new ArrayList<Node>();
			path.add(panel.getNode(salesmanStartTF1.getText()));
			completed.clear();
		travelling(panel.getNode(salesmanStartTF1.getText()),  path, 0);
		if (completed.size() > 0) {
			int shortest = 0;
			for (int i = 0; i < completed.size(); i++) {
				ArrayList<Node> complete = completed.get(i);
				ArrayList<Node> shortestList = completed.get(shortest);
				int length = Integer.parseInt(complete.get(complete.size() - 1).getLabel());
				int length2 = Integer.parseInt(shortestList.get(shortestList.size() - 1).getLabel());
				if (length < length2) {
					shortest = i;
				}
			}
			String output = "";
			for (int i = 0; i < completed.get(shortest).size(); i++) {
				output += completed.get(shortest).get(i).getLabel() + " ";
			}
			JOptionPane.showMessageDialog(frame, "Shortest path:" + output);
		}
		else {
			JOptionPane.showMessageDialog(frame, "Not valid path!");
		}
		// make sure completed has a path
		// find the shortest path, print out its value, and the path
		}
		else {
			JOptionPane.showMessageDialog(frame, "Not a valid starting node!");
		}
	
	}
	
	}

	private void travelling(Node n, ArrayList<Node> path, int total) {
		// If the number of nodes in the path is equal to the number of nodes
		//add this path to the completed list
		//	remove the last thing in the path
		//return
		//else 
		if (path.size() == panel.getNodeList().size()) {
			ArrayList<Node> newpath = new ArrayList<Node>();
			for (int a = 0; a < path.size(); a++) {
				newpath.add(path.get(a)); 
			}
			newpath.add(new Node(0, 0 , total + ""));
			completed.add(newpath);
			path.remove(path.size() - 1);
		}
		else {
		for (int a = 0; a < panel.getEdgeList().size(); a++) {
			Edge e = panel.getEdgeList().get(a);
			if (e.getOtherEnd(n) != null) {
				if (path.contains(e.getOtherEnd(n)) == false) {
					path.add(e.getOtherEnd(n));
		

				travelling(e.getOtherEnd(n), path, total + Integer.parseInt(e.getLabel()));
				} 
			
	}	
		//	see if they are connected to the current node
		//if they are connected to the current node
		//add node to path
		//	travelling(connected node, path, total + edge cost)
		//remove the last thing in the path
		
}
		
		path.remove(path.size() - 1);
		System.out.println(total);
		}
}

}

/*
 * Adjacency Matrix
 * 		A	B	C
 * A	1	1	1
 * B	1	1	0
 * C	1	0	1
 */




