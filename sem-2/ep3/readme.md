# Merge Sort Analysis

The `time.sh` script is responsible for generating analysis data which in turn can be taken by the `report.tex` file for plotting.

It takes in three arguments:

```sh
./time.sh `first_value` `runs` `samples`
```

> Make sure you enable shell execution of the included files by doing
>
> ```sh
> chmod +x {time.sh,get_averaged_table,str_gen}
> ```
>
> ⚠️ A more readable version of the `minimalMergeSort.java` file is included for lookup purposes (namely the `MergeSort.java`), which includes comments as well as it prints results to the terminal.
