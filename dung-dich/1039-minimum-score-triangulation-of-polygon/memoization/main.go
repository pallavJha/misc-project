package main

import (
	"fmt"
	"math"
)

func main() {
	// f, err := os.Create("./cpu_profile_2")
	// if err != nil {
	// 	log.Fatal(err)
	// }
	// err = pprof.StartCPUProfile(f)
	// if err != nil {
	// 	log.Fatal(err)
	// }
	// defer pprof.StopCPUProfile()
	// 6
	// fmt.Println(minScoreTriangulation([]int{1, 2, 3}))
	// 144
	// fmt.Println(minScoreTriangulation([]int{3, 7, 4, 5}))
	// 38
	fmt.Println(minScoreTriangulation([]int{1, 2, 3, 4, 5}))
	// 68
	// fmt.Println(minScoreTriangulation([]int{1, 2, 3, 4, 5, 6}))
	// 110
	// fmt.Println(minScoreTriangulation([]int{1, 2, 3, 4, 5, 6, 7}))
	// 13
	// fmt.Println(minScoreTriangulation([]int{1, 3, 1, 4, 1, 5}))
	// 98
	// fmt.Println(minScoreTriangulation([]int{3, 5, 2, 5, 2, 6}))
	// 140295
	// fmt.Println(minScoreTriangulation([]int{35, 73, 90, 27, 71, 80, 21, 33, 33, 13, 48, 12, 68, 70, 80, 36, 66, 3, 70, 58}))
	// 153657
	// fmt.Println(minScoreTriangulation([]int{38, 76, 69, 32, 24, 35, 82, 30, 86, 77, 92, 3, 35, 20, 84, 67, 23, 58, 94, 10}))
	// 103598
	// fmt.Println(minScoreTriangulation([]int{5, 80, 62, 45, 96, 100, 17, 72, 67, 64, 20, 66, 41, 68, 34, 67, 35, 24, 76, 2}))
}

type Node struct {
	Val    int
	Index  int
	Length int
	Next   *Node
	Prev   *Node
}

var cache map[uint64]int
var minSum int
var rec int

func minScoreTriangulation(A []int) int {
	start := &Node{
		Val:   A[0],
		Index: 0,
	}
	temp := start
	for i := 1; i < len(A); i++ {
		temp.Next = &Node{
			Val:   A[i],
			Prev:  temp,
			Index: i,
		}
		temp = temp.Next
	}
	temp.Next = start
	start.Prev = temp
	minSum = math.MaxInt32
	cache = make(map[uint64]int)
	hash := createHash(start)
	rec = 0
	minScoreTriangulationLLC(start, 0, len(A), hash)
	fmt.Println("rec", rec, len(A))
	rec = 0
	return minSum
}

func createHash(start *Node) uint64 {
	var hash uint64
	temp := start
	for temp != nil {
		hash |= 1 << temp.Index
		if temp.Next == start {
			break
		}
		temp = temp.Next
	}
	return hash
}

func print(start *Node) {
	temp := start
	for temp != nil {
		fmt.Print(temp.Val)
		if temp.Next == start {
			break
		}
		temp = temp.Next
	}
	fmt.Println()
}

func updateHash(currentHash uint64, indexToBeRemoved int) uint64 {
	return currentHash ^ (1 << indexToBeRemoved)
}

func minScoreTriangulationLLC(start *Node, sum, length int, hash uint64) int {
	if cacheSum, ok := cache[hash]; ok {
		sum += cacheSum
		if minSum > sum {
			minSum = sum
		}
		return sum
	}
	if length == 3 {
		sum += start.Val * start.Next.Val * start.Next.Next.Val
		if minSum > sum {
			minSum = sum
		}
		return sum
	}
	rec++
	print(start)
	currentSum := math.MaxInt32
	temp := start
	for temp != nil {
		remove := temp
		sum += remove.Val * remove.Next.Val * remove.Prev.Val
		remove.Prev.Next = remove.Next
		remove.Next.Prev = remove.Prev
		newHash := updateHash(hash, remove.Index)
		tempSum := minScoreTriangulationLLC(remove.Next, sum, length-1, newHash)
		if tempSum-sum+(remove.Val*remove.Next.Val*remove.Prev.Val) < currentSum {
			currentSum = tempSum - sum + (remove.Val * remove.Next.Val * remove.Prev.Val)
		}
		remove.Prev.Next = remove
		remove.Next.Prev = remove
		sum -= remove.Val * remove.Next.Val * remove.Prev.Val
		if temp.Next == start {
			break
		}
		temp = temp.Next
	}
	cache[hash] = currentSum
	return currentSum + sum
}
