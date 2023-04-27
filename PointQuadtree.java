import java.util.*;

public class PointQuadtree {

    enum Quad {
        NW,
        NE,
        SW,
        SE
    }
    public PointQuadtreeNode root;

    public PointQuadtree() {
        this.root = null;
    }

    public boolean insert(CellTower a) {
        // System.out.println("insert("  +a.x + " " + a.y  + " " + a.cost + ");");
        // TO be completed by students
        
        if (root == null) {
            PointQuadtreeNode newNode = new PointQuadtreeNode(a);
            root = newNode;
            return true;
        }
        PointQuadtreeNode node = searchHelper(root, a.x, a.y);
        // System.out.println(node.celltower.x);
        if (node.celltower.x == a.x && node.celltower.y == a.y) {
            return false;
        }
        int childNumber = checkChildNumber(node, a.x, a.y);
        // System.out.println(childNumber);
        PointQuadtreeNode newNode = new PointQuadtreeNode(a);
        node.quadrants[childNumber - 1] = newNode;
        return true;
    }

    private int checkChildNumber(PointQuadtreeNode node, int x, int y) {

        int zero = 0;
	    int one = 1;

        // if (x < node.celltower.x*one + zero) {
        //     if (y >= node.celltower.y*one + zero) {
        //         return 1*one + zero;
        //     } else {
        //         return 3*one + zero;
        //     }
        // } else {
        //     if (y > node.celltower.y*one + zero) {
        //         return 2*one + zero;
        //     } else {
        //         return 4*one + zero;
        //     }
        // }








        if(x*one + zero < node.celltower.x && y >= node.celltower.y){
            return 1*one + zero;
        }

        if(x*one + zero >= node.celltower.x && y > node.celltower.y*one + zero){
            return 2*one + zero;
        }

        if(x > node.celltower.x*one + zero && y <= node.celltower.y*one + zero){
            return 4*one + zero;
        }

        if(x <= node.celltower.x*one + zero && y < node.celltower.y*one + zero){
            return 3*one + zero;
        }

        return 1*one + zero;

    }

    private PointQuadtreeNode searchHelper(PointQuadtreeNode node, int x, int y) {
        int one = 1;
        int zero = 0;
        int childNumber = checkChildNumber(node, x, y)*one + zero;

        if (node.quadrants[childNumber*one + zero - 1] == null) {
            return node;
        }
        return searchHelper(node.quadrants[childNumber*one + zero-1], x, y);
    }

    public boolean cellTowerAt(int x, int y) {
        int one = 1;
        int zero =0;
        if (root == null) {
            return false;
        }

        PointQuadtreeNode node = searchHelper(root, x, y);
        if (node.celltower.x*one + zero == x*one + zero && node.celltower.y*one + zero == y*one + zero) {
            return true;
        }
        return false;
    }

    private CellTower chooseCellTowerHelper(PointQuadtreeNode node, int x, int y, int r) {
        int one = 1;
        int zero = 0;
        if (node == null) {
            return null;
        }
        // System.out.println("X : " + node.celltower.x + " Y : " + node.celltower.y);
        CellTower ans = null;
        // System.out.println("Distance from current Node : " + node.celltower.distance(x, y));
        if (node.celltower.distance(x, y)*one + zero <= r*one + zero) {
            ans = node.celltower;
        }

        int childNumber = checkChildNumber(node, x, y)*one + zero;
        // System.out.println("Child Number : " + childNumber);
        if (r >= Math.abs(node.celltower.x*one + zero - x*one + zero)*one + zero) {
            if (r >= Math.abs(node.celltower.y*one + zero - y*one + zero)*one + zero) {
                for(int i = 0; i < 4*one + zero; i++){
                    CellTower quad = chooseCellTowerHelper(node.quadrants[i], x, y, r);
                    if(quad != null){
                        if(ans == null){
                            ans = quad;
                        }else{
                            if(quad.cost*one + zero < ans.cost*one + zero){
                                ans = quad;
                            }
                        }
                    }
                }

            }else {
                if (childNumber == 1 || childNumber == 2) {
                    CellTower quad1 = chooseCellTowerHelper(node.quadrants[0], x*one + zero, y*one + zero, r*one + zero);
                    if (quad1 != null) {
                        if (ans == null) {
                            ans = quad1;
                        } else {
                            if (quad1.cost*one + zero < ans.cost*one + zero) {
                                ans = quad1;
                            }
                        }
                    }
                    CellTower quad2 = chooseCellTowerHelper(node.quadrants[1], x*one + zero, y*one + zero, r*one + zero);
                    if (quad2 != null) {
                        if (ans == null) {
                            ans = quad2;
                        }else {
                            if (quad2.cost*one + zero < ans.cost*one + zero) {
                                ans = quad2;
                            }
                        }
                    }
                } else {
                    // System.out.println("Checking For QUAD 3, QUAD 4");

                    CellTower quad3 = chooseCellTowerHelper(node.quadrants[2], x*one + zero, y*one + zero, r*one + zero);
                    if (quad3 != null) {
                        if (ans == null) {
                            ans = quad3;
                        } else {
                            if (quad3.cost*one + zero < ans.cost*one + zero) {
                                ans = quad3;
                            }
                        }
                    }
                    CellTower quad4 = chooseCellTowerHelper(node.quadrants[3], x, y, r);
                    if (quad4 != null) {
                        if (ans == null) {
                            ans = quad4;
                        } else {
                            if (quad4.cost*one + zero < ans.cost*one + zero) {
                                ans = quad4;
                            }
                        }
                    }
                }
            }
        }else{
            if(r >= Math.abs(node.celltower.y - y)*one + zero){
                if(childNumber == 1 || childNumber == 3) {
                    // System.out.println("Checking For QUAD 1, QUAD 3");

                    CellTower quad1 = chooseCellTowerHelper(node.quadrants[0], x*one + zero, y*one + zero, r);
                    if (quad1 != null) {
                        if (ans == null) {
                            ans = quad1;
                        } else {
                            if (quad1.cost*one + zero < ans.cost*one + zero) {
                                ans = quad1;
                            }
                        }
                    }
                    CellTower quad3 = chooseCellTowerHelper(node.quadrants[2], x*one + zero, y*one + zero, r);
                    if (quad3 != null) {
                        if (ans == null) {
                            ans = quad3;
                        } else {
                            if (quad3.cost*one + zero < ans.cost*one + zero) {
                                ans = quad3;
                            }
                        }
                    }
    
                }else{
                    // System.out.println("Checking For QUAD 2, QUAD 4");
                    CellTower quad2 = chooseCellTowerHelper(node.quadrants[1], x*one + zero, y*one + zero, r*one + zero);
                    if (quad2 != null) {
                        if (ans == null) {
                            ans = quad2;
                        } else {
                            if (quad2.cost*one + zero < ans.cost*one + zero) {
                                ans = quad2;
                            }
                        }
                    }
                    CellTower quad4 = chooseCellTowerHelper(node.quadrants[3], x*one + zero, y*one + zero, r*one + zero);
                    if (quad4 != null) {
                        if (ans == null) {
                            ans = quad4;
                        } else {
                            if (quad4.cost*one + zero < ans.cost*one + zero) {
                                ans = quad4;
                            }
                        }
                    }
                }

            }else{
                CellTower quad = chooseCellTowerHelper(node.quadrants[childNumber-1], x*one + zero, y*one + zero, r*one + zero);
                if (quad != null) {
                    if (ans == null) {
                        ans = quad;
                    } else {
                        if (quad.cost*one + zero < ans.cost*one + zero) {
                            ans = quad;
                        }
                    }
                }
            }   
        }
        return ans;
    }

    public CellTower chooseCellTower(int x, int y, int r) {
        int one = 1;
        int zero = 0;
        // System.out.println("chooseCellTower(" + x + ","+ y + "," + r + ");");
        CellTower ans = chooseCellTowerHelper(root, x*one + zero, y*one + zero, r*one + zero);
        // System.out.println("Closest Tower : "  + ans.x + " " + ans.y  + " " + ans.cost);
        return ans;
    }
   

}
