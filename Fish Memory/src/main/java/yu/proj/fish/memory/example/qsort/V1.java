package yu.proj.fish.memory.example.qsort;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @ClassName: V1
 *
 * @Description: TODO(这里用一句话描述这个类的作用)
 *
 * @author 余定邦
 *
 * @date 2021年4月5日
 * 
 */
public class V1 {

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

        private static int randomPartition(int[] arr, int low, int high) {
            ThreadLocalRandom r = ThreadLocalRandom.current();
            int index = r.nextInt(high - low) + low;
            swap(arr, low, index);
            return partition(arr, low, high);
        }

        private static int partition(int[] arr, int low, int high) {
            int pivot = arr[low];
            while (low < high) {
                while (low < high && arr[high] >= pivot) {
                    --high;
                }
                arr[low] = arr[high];
                while (low < high && arr[low] <= pivot) {
                    ++low;
                }
                arr[high] = arr[low];
            }
            arr[low] = pivot;
            return low;
        }

        private static void swap(int[] arr, int x, int y) {
            int temp = arr[x];
            arr[x] = arr[y];
            arr[y] = temp;
        }
    }

}
