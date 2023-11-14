package pathsearch.astar;

import java.util.*;

/**
 * @Author: huangpenglong
 * @Date: 2023/11/14 17:40
 */
public class AStar {
    // 障碍值
    public final static int BAR = 1;

    // 路径
    public final static int PATH = 2;

    // 横竖移动代价
    public final static int DIRECT_VALUE = 10;

    // 斜移动代价
    public final static int OBLIQUE_VALUE = 14;

    /**
     * 待处理优先队列(升序)
    */
    Queue<Node> openList = new PriorityQueue<>();

    /**
     * 已处理节点
     */
    List<Node> closeList = new ArrayList<>();

    /**
     * 判断结点是否是最终结点
     */
    private boolean isEndNode(Coord end,Coord coord) {
        return end != null && end.equals(coord);
    }

    /**
     * 判断结点能否放入Open列表
     */
    private boolean canAddNodeToOpen(MapInfo mapInfo,int x, int y) {
        // 是否在地图中
        if (x < 0 || x >= mapInfo.width || y < 0 || y >= mapInfo.high){
            return false;
        }
        // 判断是否是不可通过的结点
        if (mapInfo.maps[y][x] == BAR){
            return false;
        }
        // 判断结点是否存在close表
        if (isCoordInClose(x, y)){
            return false;
        }

        return true;
    }

    /**
     * 判断坐标是否在close表中
     */
    private boolean isCoordInClose(Coord coord) {
        return coord != null && isCoordInClose(coord.x, coord.y);
    }

    /**
     * 判断坐标是否在close表中
     */
    private boolean isCoordInClose(int x, int y) {
        if (closeList.isEmpty()){
            return false;
        }
        for (Node node : closeList)
        {
            if (node.coord.x == x && node.coord.y == y)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算H值，“曼哈顿”法，坐标分别取绝对差值相加
     * @param end
     * @param coord
     * @return
     */
    private int calcH(Coord end,Coord coord) {
        return Math.abs(end.x - coord.x) + Math.abs(end.y - coord.y);
    }


    /**
     * 从Open列表中查找节点
     * @param coord
     * @return
     */
    private Node findNodeInOpen(Coord coord) {
        if (coord == null || openList.isEmpty()){
            return null;
        }
        for (Node node : openList)
        {
            if (node.coord.equals(coord))
            {
                return node;
            }
        }
        return null;
    }

    /**
     * 添加所有邻结点到open表
     */
    private void addNeighborNodeInOpen(MapInfo mapInfo,Node current) {
        int x = current.coord.x;
        int y = current.coord.y;
        // 左
        addNeighborNodeInOpen(mapInfo,current, x - 1, y, DIRECT_VALUE);
        // 上
        addNeighborNodeInOpen(mapInfo,current, x, y - 1, DIRECT_VALUE);
        // 右
        addNeighborNodeInOpen(mapInfo,current, x + 1, y, DIRECT_VALUE);
        // 下
        addNeighborNodeInOpen(mapInfo,current, x, y + 1, DIRECT_VALUE);
        // 左上
        addNeighborNodeInOpen(mapInfo,current, x - 1, y - 1, OBLIQUE_VALUE);
        // 右上
        addNeighborNodeInOpen(mapInfo,current, x + 1, y - 1, OBLIQUE_VALUE);
        // 右下
        addNeighborNodeInOpen(mapInfo,current, x + 1, y + 1, OBLIQUE_VALUE);
        // 左下
        addNeighborNodeInOpen(mapInfo,current, x - 1, y + 1, OBLIQUE_VALUE);
    }

    /**
     * 添加一个邻结点到open表
     */
    private void addNeighborNodeInOpen(MapInfo mapInfo, Node current, int x, int y, int value) {
        if (canAddNodeToOpen(mapInfo, x, y)) {
            Node end = mapInfo.end;
            Coord coord = new Coord(x, y);

            // 计算邻结点的G值
            int G = current.G + value;
            Node child = findNodeInOpen(coord);
            if (child == null) {
                // 计算H值
                int H = calcH(end.coord,coord);
                if(isEndNode(end.coord,coord)) {
                    child = end;
                    child.parent = current;
                    child.G = G;
                    child.H = H;
                }
                else
                {
                    child = new Node(coord, current, G, H);
                }
                openList.add(child);
            }
            else if (child.G > G)
            {
                child.G = G;
                child.parent = current;
                // 重新调整堆
                openList.add(child);
            }
        }
    }

    // 绘制路径
    private void drawPath(MapInfo mapInfo) {
        int[][] maps = mapInfo.maps;
        Node end = mapInfo.end;
        if(end == null || maps == null){
            return;
        }
        System.out.println("总代价：" + end.G);
        while (end != null) {
            Coord c = end.coord;
            maps[c.y][c.x] = PATH;
            end = end.parent;
        }

        for(int[] map: maps){
            for(int item: map){
                System.out.print(item + " ");
            }
            System.out.println();
        }

    }


    public void start(MapInfo mapInfo) {
        if(mapInfo == null){
            return;
        }
        // clean
        openList.clear();
        closeList.clear();
        // 开始搜索
        openList.add(mapInfo.start);
        moveNodes(mapInfo);
    }

    /**
     * 移动当前结点
     */
    private void moveNodes(MapInfo mapInfo) {
        while (!openList.isEmpty())
        {
            Node current = openList.poll();
            closeList.add(current);
            addNeighborNodeInOpen(mapInfo,current);
            if (isCoordInClose(mapInfo.end.coord))
            {
                drawPath(mapInfo);
                break;
            }
        }
    }

    public static void main(String[] args) {
        AStar aStar = new AStar();
        int[][] maps = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }
        };
        Node start = new Node(0, 0);
        Node end = new Node(4, 6);
        MapInfo mapInfo = new MapInfo(maps, start, end);

        aStar.start(mapInfo);
    }
}
