public class Merge
{
    public static void main(String[] args)
    {
        String[] list = StdIn.readAllStrings();

        String[] sorted_list = mergeSort(list);

        for (int i = 0; i < sorted_list.length; i++)
        {
            String line = sorted_list[i];
            StdOut.println(line);
        }
    }

    private static String[] mergeSort(String[] list)
    {
        do
        {
            // final case
            if (list.length == 1) break;

            // set list bounds
            int upper_bound = list.length - 1;
            int lower_bound = 0;
            int middle = (upper_bound - lower_bound)/2;

            // generate lists and sort them
            String[] upper, lower;
            lower = mergeSort(slice(list, lower_bound, middle));
            upper = mergeSort(slice(list, middle + 1, upper_bound));

            // now we merge the result
            list = merge(lower, upper);

        } while (false);

        // and return
        return list;
    }

    private static String[] merge(String[] lower, String[] upper)
    {
        // indices (lower, upper, merger)
        int i = 0, j = 0, k = 0;
        // resulting string array
        String[] merger = new String[lower.length + upper.length];

        while (k < merger.length)
        {
            // first we check if there are any limitations
            if (i == lower.length) {
                // and if so, we can finish iterating with the other array
                for (; j < upper.length; j++)
                    merger[k++] = upper[j];
                // and exit
                break;
            }
            else if (j == upper.length) {
                for (; i < lower.length; i++)
                    merger[k++] = lower[i];
                break;
            }

            // else, we just compare
            /* reasoning:
               the = guarantees we'll keep the original ordering as the
               else case is taking from the upper array and, thus, would
               revert the order */
            if (lower[i].compareTo(upper[j]) <= 0)
                merger[k] = lower[i++];
            else
                merger[k] = upper[j++];

            // as in every iteration we will use either,
            // we can always increase the merger's index
            k++;
        }

        return merger;
    }

    // array slicing a la java
    private static String[] slice(String[] list, int start, int end)
    {
        String[] new_list = new String[end - start + 1];
        
        for (int i = start; i <= end; i++) new_list[i - start] = list[i];

        return new_list;
    }
}
