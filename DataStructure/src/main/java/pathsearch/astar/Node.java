package pathsearch.astar;

import java.util.Objects;

/**
 * @Author: huangpenglong
 * @Date: 2023/11/14 17:29
 */
public class Node implements Comparable<Node> {
    // 坐标
    public Coord coord;

    // 父结点
    public Node parent;

    // G：是个准确的值，是起点到当前结点的代价
    public int G;

    // H：是个估值，当前结点到目的结点的估计代价
    public int H;

    public Node(int x, int y)
    {
        this.coord = new Coord(x, y);
    }

    public Node(Coord coord, Node parent, int g, int h)
    {
        this.coord = coord;
        this.parent = parent;
        G = g;
        H = h;
    }

    @Override
    public int compareTo(Node o)
    {
        if (o == null){
            return -1;
        }

        return (G + H) - (o.G + o.H);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Node node = (Node) o;
        return G == node.G && H == node.H && Objects.equals(coord, node.coord) && Objects.equals(parent, node.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coord, parent, G, H);
    }

    @Override
    public String toString() {
        return "Node{" +
                "coord=" + coord +
                ", G=" + G +
                ", H=" + H +
                '}';
    }
}


