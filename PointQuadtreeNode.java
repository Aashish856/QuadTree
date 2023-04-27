class CellTower {
    int x;
    int y;
    int cost;

    public CellTower(int x, int y, int cost) {
        this.x = x*1;
        this.y = y*1;
        this.cost = cost*1;
    }

    public double distance(int x, int y) {
        // TO be completed by students
        // Make use of this in chooseCellTower in PointQuadtree.java
        double ans = Math.sqrt((x-this.x)*(x-this.x) + (y-this.y)*(y-this.y));
        return ans;
    }
}

public class PointQuadtreeNode {

    public CellTower celltower;
    public PointQuadtreeNode[] quadrants;

    public PointQuadtreeNode(CellTower a) {
        this.celltower = a;
        this.quadrants = new PointQuadtreeNode[4];
    }

}
