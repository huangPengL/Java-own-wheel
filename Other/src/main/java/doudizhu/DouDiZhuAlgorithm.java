package doudizhu;

import java.util.*;

/**
 * @Author: huangpenglong
 * @Date: 2023/9/27 21:15
 */
public class DouDiZhuAlgorithm {

    private List<String> poker = new ArrayList<>();
    {
        //1.准备牌
        //定义两个数组，一个数组存储牌的花色，一个数组存储牌的序号
        String[] color = {"♥", "♠", "♦", "♣"};
        String[] num = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
        //把大王小王存储进poker集合中
        poker.add("大王");
        poker.add("小王");
        //嵌套循环，组装这52张牌得到例如（♥3,♠3,♦3,♣3)
        for (String c : color) {
            for (String n : num) {
                poker.add(c + n);
            }
        }
    }
    public Map<String, List<String>> shuffle(){

        /*
        2.洗牌
        使用集合根据类Collections中的方法
        static void shuffle(Lis<?> list) 使用默认随机源对指定列表进行置换
         */
        Collections.shuffle(poker);

        /*
        3.发牌
        定义4个集合，来接收牌，三个集合放17张，一个集合放3张（底牌）
         */
        List<String> player1 = new ArrayList<>();
        List<String> player2 = new ArrayList<>();
        List<String> player3 = new ArrayList<>();
        List<String> dipai = new ArrayList<>();
        /*
        遍历poker集合，获取每一张牌
        使用poker集合的索引%3给3位玩家轮流发牌
        剩余3张给底牌
         */
        for (int i = 0; i < poker.size(); i++) {
            String p = poker.get(i);
            if (i >= 51) {
                dipai.add(p);
            } else if (i % 3 == 0) {
                player1.add(p);
            } else if (i % 3 == 1) {
                player2.add(p);
            } else{
                player3.add(p);
            }
        }
        /*
        展示三位玩家的牌和底牌
         */

        Map<String, List<String>> map = new HashMap<>();
        map.put("player1", player1);
        map.put("player2", player2);
        map.put("player3", player3);
        map.put("dipai", dipai);
        return map;
    }

    public static void main(String[] args) {
        Map<String, List<String>> shuffle = new DouDiZhuAlgorithm().shuffle();
        System.out.println(shuffle);
    }
}
