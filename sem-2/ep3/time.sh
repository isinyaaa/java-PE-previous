#!/bin/sh

java_file="minimalMergeSort"

javac-introcs "$java_file.java"

current_iter=$1
samples=$3
rand_str=$(mktemp)

gnu_time_path=$(which time)
final_results="results.csv"
echo 'sample size,avg time' > $final_results
for i in $(seq $2)
do
    current_avg=$(\
    for j in $(seq $samples); do
        ./str_gen $current_iter $rand_str
        output=$({
            $gnu_time_path -f'%e' java-introcs $java_file < $rand_str
            } 2>&1 >/dev/null)
    	echo "$current_iter,$output"
        rm $rand_str
    done )
    ./get_averaged_table $current_avg >> $final_results
    current_iter=$((2 * $current_iter))
done