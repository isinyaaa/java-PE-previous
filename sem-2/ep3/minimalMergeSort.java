public class minimalMergeSort {
    public static void main(String[] args) {
        String[] list = mergeSort(StdIn.readAllStrings());
    }

    private static String[] mergeSort(String[] list) {
        if (list.length == 1) return list;

        int upper_bound = list.length - 1;
        int lower_bound = 0;
        int middle = (upper_bound - lower_bound)/2;

        return merge(
                mergeSort(slice(list, lower_bound, middle)),
                mergeSort(slice(list, middle + 1, upper_bound))
                );
    }

    private static String[] merge(String[] lower, String[] upper)
    {
        int i = 0, j = 0, k = 0;
        String[] merger = new String[lower.length + upper.length];

        while (k < merger.length)
        {
            if (i == lower.length) {
                for (; j < upper.length; j++)
                    merger[k++] = upper[j];
                break;
            }
            else if (j == upper.length) {
                for (; i < lower.length; i++)
                    merger[k++] = lower[i];
                break;
            }

            if (lower[i].compareTo(upper[j]) <= 0)
                merger[k] = lower[i++];
            else
                merger[k] = upper[j++];

            k++;
        }
        return merger;
    }

    private static String[] slice(String[] list, int s, int e) {
        String[] n_list = new String[e - s + 1];
        for (int i = s; i <= e; i++)
             n_list[i - s] = list[i];
        return n_list;
    }
}
