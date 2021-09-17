import java.awt.*;

public class MyNode implements Comparable<MyNode> {

    int width, height, nodeX, nodeY, x, y, margin, distance, globalDistance;
    boolean isObstacle, isStart, isEnd;
    MyNode parentNode;

    MyNode(int x, int y, int width, int height, int margin){
        this.nodeX = x;
        this.nodeY = y;
        this.width = width;
        this.height = height;
        this.margin = margin;
        this.x = nodeX * (width+margin);
        this.y = nodeY * (height+margin);
        isObstacle = false;
    }

    void drawNode(Graphics g){
        Color original = g.getColor();
        if(isObstacle){ g.setColor(Color.RED); }
        g.fillRect(x, y, width, height);
        g.setColor(original);
    }

    public void toggleObstacle() {
        isObstacle = !isObstacle;
    }

    // uses Math.sqrt to avoid Math.pow for efficiency
    public int getDistanceToNode(MyNode n){
        return (int)Math.round(Math.sqrt(((n.getX() - getX()) * (n.getX() - getX()) + (n.getY() - getY()) * (n.getY() - getY()))));
    }

    // parse for stroke
    public void drawPath(Graphics g, MyNode n){
        Graphics2D g2 = (Graphics2D)g;
        Color original = g.getColor();
        g2.setColor(Color.magenta);
        g2.setStroke(new BasicStroke(5.0f));
        g2.drawLine(x + width/2, y + height/2, n.getX() + getWidth()/2, n.getY() + getHeight()/2);
        g.setColor(original);
    }

    // compare global distance
    @Override
    public int compareTo(MyNode o) {
        if(getGlobalDistance() > o.getGlobalDistance()){
            return 1;
        } else if(getGlobalDistance() < o.getGlobalDistance()){
            return -1;
        }
        return 0;
    }

    // get()s
    public int getX() { return x; }
    public int getY() { return y; }
    int getNodeX() { return nodeX; }
    int getNodeY() { return nodeY; }
    public boolean getObstacle() { return isObstacle; }
    public MyNode getParentNode() { return parentNode; }
    public int getDistance() { return distance; } 
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getGlobalDistance() { return globalDistance; }  
    
    // set()s
    void setNodeX(int nx) { nodeX = nx; }
    void setNodeY(int ny) { nodeY = ny; }
    public void setDistance(int dist) { distance = dist; }
    public void setParentNode(MyNode n) { parentNode = n; }
    public void setObstacle(boolean b) { isObstacle = b; }
    public void setGlobalDistance(int distanceToNode) { globalDistance = distanceToNode; }
}