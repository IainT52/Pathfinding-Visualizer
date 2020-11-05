package DataStructures

import backend.MapTile

class Graph {

  var nodes: Map[Int, MapTile] = Map()
  var reverseNodes: Map[MapTile, Int] = Map()
  var adjacencyList: Map[Int, List[Int]] = Map()


  def addNode(index: Int, a: MapTile): Unit = {
    nodes += index -> a
    reverseNodes += a -> index
    adjacencyList += index -> List()
  }

  def addEdge(index1: Int, index2: Int): Unit = {
    adjacencyList += index1 -> (index2 :: adjacencyList(index1))
    adjacencyList += index2 -> (index1 :: adjacencyList(index2))
  }

  def connected(index1: Int, index2: Int): Boolean = {
    adjacencyList(index1).contains(index2)
  }

  def isPath(path: List[Int]): Boolean = {
    // initialize prev to an invalid node id
    var prev = nodes.keys.min - 1
    var valid = true
    for (index <- path) {
      if (prev != nodes.keys.min - 1) {
        if (!connected(prev, index)) {
          valid = false
        }
      }
      prev = index
    }
    valid
  }

// Return true if the two nodes are connected by a path, false otherwise
  def areConnected(index1: Int, index2: Int): Boolean = {
    var explored: Set[Int] = Set(index1)
    val toExplore: Queue[Int] = new Queue()
    toExplore.enqueue(index1)
    while (!toExplore.empty()) {
      val nodeToExplore = toExplore.dequeue()
      for (node <- this.adjacencyList(nodeToExplore)) {
        if(!explored.contains(node)){
          println("exploring: " + this.nodes(node))
          toExplore.enqueue(node)
          explored = explored + node
          if(node == index2){
            return true
          }
        }
      }
    }
    false
  }
//   BFS to find and store the path from index1 tile to index2 tile
//   Returns a list of tiles considered a "path" from index1 to index2
  def distance(index1: Int, index2: Int): List[MapTile] = {
    var explored: Set[Int] = Set(index1)
    var mapNode: Map[Int, Int] = Map()
    var pathList: List[MapTile] = List()
    var total: Int = 0
    val toExplore: Queue[Int] = new Queue()
    toExplore.enqueue(index1)
    while (!toExplore.empty()) {
      val nodeToExplore = toExplore.dequeue()
      for (node <- this.adjacencyList(nodeToExplore) if this.nodes(node).passable) {
        if(!explored.contains(node)){
          toExplore.enqueue(node)
          explored = explored + node
          mapNode += (node -> nodeToExplore)
          if(node == index2){
            var startNode: Int = node
            while(startNode != index1){
              startNode = mapNode(startNode)
              pathList = pathList :+ this.nodes(startNode)
            }
            return pathList
          }
        }
      }
    }
    pathList
  }
}
