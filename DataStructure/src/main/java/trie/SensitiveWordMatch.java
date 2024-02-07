package trie;

import java.util.*;

/**
 * @Author: huangpenglong
 * @Date: 2023/9/3 11:29
 */
public class SensitiveWordMatch {
    public static void main(String[] args) {
        System.out.println("请输入需要进行敏感词匹配的句子：");
        Scanner scanner = new Scanner(System.in);
        String sentence = scanner.next();
        List<Integer> wordListNums = Arrays.asList(500, 5000, 50000, 500000, 1000000);
        for(Integer wordListNum: wordListNums) {
            List<String> words = generateRandomWords(wordListNum);
            words.addAll(Arrays.asList("白痴", "色色", "脑残", "PORN", "porN", "sb", "sm"));
            testTrieMatch(words, sentence);
            testNormalMatch(words, sentence);
        }
    }
    public static void testTrieMatch(List<String> words, String sentence){
        System.out.println("\n==================================================");
        System.out.println("正在使用Trie组件进行敏感词匹配...");
        Trie trie = new Trie();

        trie.insertList(words);

        long startTime = System.currentTimeMillis();

        Set<String> matched = trie.matchItems(sentence);

        System.out.println("敏感词表长度为：" + words.size() + "。当前匹配完毕，消耗时间：" + (System.currentTimeMillis() - startTime) + "ms");

        System.out.println("匹配到的敏感词：");
        matched.forEach(System.out::println);
        System.out.println("==================================================\n");
    }
    public static void testNormalMatch(List<String> words, String sentence){
        System.out.println("\n==================================================");
        System.out.println("正在使用普通String.contains方法进行敏感词匹配...");
        Set<String> matched = new HashSet<>();

        long startTime = System.currentTimeMillis();
        for(String word: words){
            if(sentence.contains(word)){
                matched.add(word);
            }
        }
        System.out.println("敏感词表长度为：" + words.size() + "。当前匹配完毕，消耗时间：" + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("匹配到的敏感词：");
        matched.forEach(System.out::println);
        System.out.println("==================================================\n");
    }

    public static List<String> generateRandomWords(int num){
        List<String> randomWordList = new ArrayList<>();

        // 在randomWordList中随机生成一些单词
        for(int i = 0; i < num; i++){
            // 生成随机单词的方法
            String randomWord = generateRandomWord();
            randomWordList.add(randomWord);
        }

        return randomWordList;
    }

    /**
     * 生成随机字符串的方法
     */
    private static String generateRandomWord(){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        // 随机生成长度为4~20的字符串
        int length = random.nextInt(17) + 4;
        for(int i = 0; i <length; i++){
            int randomIndex = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }

}
