package bitmap;

import java.util.BitSet;

/**
 * @Author: huangpenglong
 * @Date: 2023/8/26 10:12
 */
public class Bitmap {
    private final long[] bits;

    public Bitmap(int size) {
        // 每个long型可以表示64位，所以除以64取整
        bits = new long[(size >> 6) + 1];
    }

    public void set(int index) {
        // 除以64取整，即确定在数组中的索引
        int arrayIndex = index >> 6;
        // 模上64，即确定在long型中的位索引
        int bitIndex = index & 0x3F;
        // 将对应位设置为1
        bits[arrayIndex] |= (1L << bitIndex);
    }

    public void clear(int index) {
        int arrayIndex = index >> 6;
        int bitIndex = index & 0x3F;
        // 将对应位设置为0
        bits[arrayIndex] &= ~(1L << bitIndex);
    }

    public boolean get(int index) {
        int arrayIndex = index >> 6;
        int bitIndex = index & 0x3F;
        // 判断对应位是否为1
        return ((bits[arrayIndex] >> bitIndex) & 1) == 1;
    }

    public static void main(String[] args) {
        /**
         * JDK1.0 有的数据结构
         */
//        BitSet bitSet = new BitSet(10);
//        bitSet.set(9);
//        boolean b = bitSet.get(9);
//        System.out.println(b);

        /**
         * 造轮子
         */
        int n = 4*10^9 / 64;
        Bitmap bitmap = new Bitmap(n);
        bitmap.set(4*10^9);
        System.out.println(bitmap.get(4*10^9));
    }
}

