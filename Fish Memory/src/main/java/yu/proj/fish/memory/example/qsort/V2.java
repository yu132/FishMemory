package yu.proj.fish.memory.example.qsort;

import java.util.concurrent.ThreadLocalRandom;

/**  
 * @ClassName: V2  
 *
 * @Description: TODO(这里用一句话描述这个类的作用)  
 *
 * @author 余定邦  
 *
 * @date 2021年4月5日  
 *  
 */
public class V2 {

    static//

    public class Qsort {
        public static void qsort(int[] arr, int low, int high) {
            if (low >= high) {
                return;
            }
            int mid = randomPartition(arr, low, high);
            qsort(arr, low, mid);
            qsort(arr, mid + 1, high);
        }

        // 随机选择主元，避免快排陷入最糟糕的时间复杂度
        private static int randomPartition(int[] arr, int low, int high) {
            randomChooseAIndexAndSwap(arr, low, high);
            return partition(arr, low, high);
        }

        // 将数组分成3部分，左边的部分小于等于主元，中间就是主元一个元素，右边的部分大于等于主元
        private static int partition(int[] arr, int low, int high) {
            int pivot = arr[low];
            while (low < high) {
                high = fillLowIndexOfArrWithValueLowerThanPivot(arr, low, high,
                    pivot);
                low = fillHighIndexOfArrWithValueGreaterThanPivot(arr, low,
                    high, pivot);
            }
            arr[low] = pivot;
            return low;
        }

        private static int fillHighIndexOfArrWithValueGreaterThanPivot(
            int[] arr, int low, int high, int pivot) {
            while (low < high && arr[low] <= pivot) {
                ++low;
            }
            arr[high] = arr[low];
            return low;
        }

        private static int fillLowIndexOfArrWithValueLowerThanPivot(int[] arr,
            int low, int high, int pivot) {
            while (low < high && arr[high] >= pivot) {
                --high;
            }
            arr[low] = arr[high];
            return high;
        }

        private static void randomChooseAIndexAndSwap(int[] arr, int low,
            int high) {
            ThreadLocalRandom r = ThreadLocalRandom.current();
            int index = r.nextInt(high - low) + low;
            swap(arr, low, index);
        }

        private static void swap(int[] arr, int x, int y) {
            int temp = arr[x];
            arr[x] = arr[y];
            arr[y] = temp;
        }
    }

}
