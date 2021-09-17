import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class DisplayPanel extends JPanel implements MouseListener, MouseMotionListener {

    // creating grid of 25px by 25px with 5px margin
    MyNode[][] grid;
    int margin = 5;
    boolean setObstacle;

    DisplayPanel(){
        // setting attributes for graphics
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);

        // creating grid of nodes
        grid = new MyNode[15][15];

        // initializing nodes
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new MyNode(i, j, 25, 25, margin);
            }
        }
    }

    @Override
    public void paint(Graphics g){

        g.setColor(Color.WHITE);    // background color
        g.fillRect(0, 0, 500, 500);
        g.setColor(Color.black);    // node color

        // draws nodes
        for(int x = 0; x < grid.length; x++){
            for(int y = 0; y < grid[x].length; y++){
                grid[x][y].drawNode(g);
            }
        }

/////// A* Algorithm ////////////////////////////////////////////////////////////////////////

        // starts all nodes equally
        for(int x = 0; x < grid.length; x++){
            for(int y = 0; y < grid[x].length; y++){
                grid[x][y].setParentNode(null);
                grid[x][y].setDistance(Integer.MAX_VALUE);
            }
        }

        // list of nodes that have been looked at
        ArrayList<MyNode> nodes = new ArrayList<>();

        // sets initial and final node to not have parents/be manipulated
        nodes.add(grid[0][0]);
        grid[0][0].setParentNode(null);
        grid[0][0].setDistance(0);
        grid[grid.length-1][grid[0].length-1].setObstacle(false);
        grid[0][0].setObstacle(false);

        // compares distance of neighbor node to previous node
        while(!nodes.isEmpty()){
        
            MyNode currentNode = nodes.remove(0);

            // gets neighbor node
            for(int x = -1; x < 2; x++){
                for(int y = -1; y < 2; y++){
                    int currX = currentNode.getNodeX() + x;
                    int currY = currentNode.getNodeY() + y;

                    // if neighbor node is within bounds and shorter than previous node, set as parent node
                    if(currX >= 0 && currX < grid.length){
                        if(currY >= 0 && currY < grid[0].length){
                            if(!grid[currX][currY].getObstacle()){
                                if(currentNode.getDistance() + currentNode.getDistanceToNode(grid[currX][currY]) < grid[currX][currY].getDistance()){
                                    grid[currX][currY].setParentNode(currentNode);
                                    grid[currX][currY].setDistance(currentNode.getDistance() + currentNode.getDistanceToNode(grid[currX][currY]));
                                    grid[currX][currY].setGlobalDistance(grid[currX][currY].getDistanceToNode(grid[grid.length-1][grid[0].length-1]));
                                    nodes.add(grid[currX][currY]);
                                }
                            }
                        }
                    }
                }
            }

            Collections.sort(nodes);

        }

        // draws path
        MyNode currentNode = grid[grid.length-1][grid[0].length-1];
        while(currentNode.getParentNode() != null){
            currentNode.drawPath(g, currentNode.getParentNode());
            currentNode = currentNode.getParentNode();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try{
            int nodeX, nodeY;
            nodeX = e.getX() / (margin+25);
            nodeY = e.getY() / (margin+25);
            grid[nodeX][nodeY].toggleObstacle();
        } catch(IndexOutOfBoundsException ignored){}
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try{
            int nodeX, nodeY;
            nodeX = e.getX() / (margin+25);
            nodeY = e.getY() / (margin+25);
            setObstacle = !grid[nodeX][nodeY].getObstacle();
        } catch (IndexOutOfBoundsException ignored){}
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        try{
            int nodeX, nodeY;
            nodeX = e.getX() / (margin+25);
            nodeY = e.getY() / (margin+25);
            grid[nodeX][nodeY].setObstacle(setObstacle);
        } catch (IndexOutOfBoundsException ignored){}
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}