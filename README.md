# Ass1DAA
Depth and memory control
Recursion depth:
All algorithms keep recursion depth logarithmic (O(log n))
QuickSort only recurses on the smaller partition
MergeSort and Closest Pair use balanced splitting

Memory usage:
MergeSort reuses one buffer instead of creating new arrays
QuickSort and Select work in-place without extra memory
We avoid creating new objects during recursion


Algorithm analysis
MergeSort:
Splits array in half, sorts both sides, then merges
Master theorem case 2: Θ(n log n)
Always balanced, very predictable

QuickSort:
Picks random pivot, partitions, then recurses
Average case: Θ(n log n)
Fast in practice due to good cache usage

Deterministic select:
Finds k-th smallest element without full sorting
Uses median-of-medians for good pivots
Akra-Bazzi shows linear time: Θ(n)

Closest pair:
Finds two closest points in 2D space
Divides plane, solves subproblems, checks middle strip
Master Theorem Case 2: Θ(n log n)


Results
Time measurements:
MergeSort and QuickSort show n log n growth
Select shows linear growth as expected
Closest pair is slowest due to geometric calculations

Depth measurements:
All algorithms stay under 40 levels even for 100,000 elements
Depth grows logarithmically with input size
QuickSort has slightly higher depth due to uneven splits


Summary
All algorithms work correctly and match their theoretical time complexity. The practical performance depends on constant factors and memory access patterns, not just big-O notation. The recursion depth control successfully prevents stack overflow even for large inputs.
