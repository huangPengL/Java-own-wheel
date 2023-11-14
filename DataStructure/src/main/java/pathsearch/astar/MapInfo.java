package pathsearch.astar;

/**
 * @Author: huangpenglong
 * @Date: 2023/11/14 17:39
 */
public class MapInfo {
    // 二维数组的地图
    public int[][] maps;

    // 地图的宽
    public int width;

    // 地图的高
    public int high;

    // 起始结点
    public Node start;

    // 最终结点
    public Node end;


    public MapInfo(int[][] maps, Node start, Node end)
    {
        this.maps = maps;
        this.width = maps[0].length;
        this.high = maps.length;
        this.start = start;
        this.end = end;
    }
}


