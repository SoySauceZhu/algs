# 2 Union Find

## Dynamic Connectivity

## Quick Find

`union(a, b)`: Iterate through the array update all entries whose value is same as `arr[a]` to `arr[b]`
`find(a, b)`: return `arr[a] == arr[b]`

Always flat but the price is hight, union cost O(n)

## Quick Union

`union(a,b)` find the root of `a` and `b` and update `arr[root(b)]` to `arr[root(a)]`
`find(a, b)`: return `arr[root(a)] == arr[root(b)]`

Cost of union is less than O(n). Yet find cost is greater than Quick find (O(n))



## Improvement of Quick Union
Improvement: weighted tree. Always connect small tree to big. (You should record `size[]`)
Improvement: path compression (very flat). Update nodes that are examined in finding root (`root(a)`) directly point to root
Improvement: path compression (halved path). Only one line of code added,Update nodes that are examined in finding root (`root(a)`) directly point to its **grandparent**

- Weighted  
![alt text](image.png)
- Update path compression
    - Two-pass: The most flat version
    - One-pass variant: Half the path (not the best)

## Assignment: Percolation

- If it is required to *avoid compressing all iteration operations into a single method*,
you should consider splitting the tasks in to each method.  
The best case is making method runtime O(1), that means runtime doesn't increase with the input size.

- To avoiding iteration in a method, you don't want iterative searching.  
Instead, store data in a special position or structure (i.e. the `-1` index or hash table), then look up them directly.



# 4 Stack and Queue

![alt text](image-1.png)

# 5 Sorting Algorithms

Callback function: is a function that passed as arguments, and then is invoked by other function. aka. Reference to a executable code.

![alt text](image-4.png)

Natural numbers satisfies 'total order'

A total order is a binary relation ≤ that satisfies:
・Antisymmetry: if v ≤ w and w ≤ v, then v = w.
・Transitivity: if v ≤ w and w ≤ x, then v ≤ x.
・Totality: either v ≤ w or w ≤ v or both.

![alt text](image-2.png)

## Selection sort: $\theta(n^2)$
## Insertion sort: $\Omega(n), O(n^2)$
Swap iteratively until the focused items is in proper position
## Shell sort: $O(n^{3/2})$ when $n=3x+1$   
Based on insertion sort. But the exchange operations could be reduced by focus on non-consecutive items.  
Given any $m, n$, sort on m and then n, m-sorted remains after n-sorted.  
![alt text](image-3.png)
## Shuffle:  
* Assign random numbers index and sort by index
* Knuth shuffle: In iteration `i`, pick integer `r` between `0` and `i`. Then shuffle `i` and `r`
## Convex Hull
Graham Scan: 
* Iterate through node in counter-clockwise. 
* Add the current node to vertices candidates.
* Test recursively, if any previous candidates (expect current) create a 'right turn' with the current node, remove them from candidates.
* The remains are vertices of convex hull 

## Stability
A stable sort preserves the relative order of items with equal keys.

* Insertion: stable
* Selection: not stable
* Shell: not stable
* Merge: Stable


# 6 Quick sort

## Quick sort
`int pivot = partition(arr, lo, hi)` function: no less on right side of pivot and no greater on left

## Select the k-th largest

Only sort on one side.
Until the pivot is equal to `k` -> no less on right side -> `k`-th largest

O(n): N + N/2 + N/4 + ... + 1 = 2N (logN terms)

```java
public static Comparable select(Comparable[] a, int k)
{
 StdRandom.shuffle(a);
 int lo = 0, hi = a.length - 1;
 while (hi > lo)
 {
 int j = partition(a, lo, hi);
 if (j < k) lo = j + 1;
 else if (j > k) hi = j - 1;
 else return a[k];
 }
 return a[k];
}
```

## Duplicate Keys

When array is huge, keys are in small number

- Quicksort goes quadratic with duplicate keys, unless partitioning stops on equal keys
- Merge sort is `O(NlogN)` no matter the keys.  
Since what grantees an `NlogN` is half-sized partitioning. Only evenly partitioned can the logic reaches logN depth of iteration. Where the keys are all same, we only partitioned with `[n-1]`, `[]` (empty). Which required `(N-1)` depth and thus `O(N(N-1)) = O(N^2)`

Solution:
- Stop at duplicated key
- 3-way partitioning

```
Scan i from left to right.
– (a[i] < v): exchange a[lt] with a[i]; increment both lt and i
– (a[i] > v): exchange a[gt] with a[i]; decrement gt
– (a[i] == v): increment i
```

## System sort

C `qsort()` do quicksort to primitive data types and merge sort to objects. Since objects may require for stable sorting.

