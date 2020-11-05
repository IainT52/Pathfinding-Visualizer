package backend

import backend.physics.PhysicsVector
import DataStructures._

object PathFinding {

  def findPath(start: GridLocation, end: GridLocation, map: List[List[MapTile]]): List[GridLocation] = {
    var verticalCounter: Int = 0
    var nameCounter: Int = 0
    var horizontalCounter: Int = 0
    var previousTile: MapTile = null
    var previousList: List[MapTile] = null
    val graph: Graph = new Graph()
    for (adjacencyList <- map){
      for (tile <- adjacencyList){
        graph.addNode(nameCounter, tile)
        if(horizontalCounter > 0){
          graph.addEdge(nameCounter - 1,nameCounter)
        }
        previousTile = tile
        nameCounter += 1
        horizontalCounter += 1
      }
      if(verticalCounter > 0){
          for ((previous, current) <- previousList.take(adjacencyList.size) zip adjacencyList.take(previousList.size)) { // ensures they are the same size
            graph.addEdge(graph.reverseNodes(previous),graph.reverseNodes(current))
          }
      }
      previousList = adjacencyList
      verticalCounter += 1
      horizontalCounter = 0
    }
    val startTile: MapTile = map(start.y)(start.x)
    val endTile: MapTile = map(end.y)(end.x)
    val tilePath: List[MapTile] = graph.distance(graph.reverseNodes(endTile), graph.reverseNodes(startTile))
    var finalPath: List[GridLocation] = List(start)
//    Add tiles to the final path to be returned
    if(endTile.passable){
      for (tile <- tilePath){
        for (lists <- map){
          if(lists.contains(tile)){
            finalPath = finalPath :+ new GridLocation(lists.indexOf(tile), map.indexOf(lists))
          }
        }
      }
    }
    finalPath
  }


  def getVelocity(path: List[GridLocation], currentLocation: PhysicsVector): PhysicsVector = {
    if (path.isEmpty){
      return new PhysicsVector(0,0,0)
    }
    val tileLocation: GridLocation = new GridLocation(Math.floor(currentLocation.x).toInt, Math.floor(currentLocation.y).toInt)
    var nextLocation: PhysicsVector = new PhysicsVector(0,0,0)
    var returnedVelocity: PhysicsVector = new PhysicsVector(0,0,0)
    if (path.size > path.indexOf(tileLocation) + 1) {
      val nextGrid_Location: GridLocation = path(path.indexOf(tileLocation) + 1)
      nextLocation = new PhysicsVector(nextGrid_Location.x + .5, nextGrid_Location.y + .5, 0)
      returnedVelocity = new PhysicsVector(nextLocation.x - currentLocation.x, nextLocation.y - currentLocation.y, 0).normal2d()
      returnedVelocity.x *= 5
      returnedVelocity.y *= 5
      returnedVelocity
    }
    else{
      nextLocation = new PhysicsVector(tileLocation.x + .5, tileLocation.y + .5, 0)
      if(nextLocation.distance2d(currentLocation) <= 0.1){
        new PhysicsVector(0,0,0)
      }
      else{
        returnedVelocity = new PhysicsVector(nextLocation.x - currentLocation.x, nextLocation.y - currentLocation.y, 0).normal2d()
        returnedVelocity.x *= 5
        returnedVelocity.y *= 5
        returnedVelocity
      }
    }
  }

  // Secondary function to compute the shortest path using BFS
  //  this function was replaced with findPath() but remains
  //  here for future reference.
  def BFS(start: Int, end: Int, graph: Graph, map: List[List[MapTile]]): List[GridLocation] = {
    var explored: Set[Int] = Set(start)
    var mapOfNode: Map[Int,Int]= Map()
    var mapTiles : List[MapTile] = List()
    var bfsPath: List[GridLocation] = List()
    val toExplore: Queue[Int] = new Queue()
    toExplore.enqueue(start)
    while (!toExplore.empty()) {
      val nodeToExplore = toExplore.dequeue()
      for (node <- graph.adjacencyList(nodeToExplore)) {
        if (graph.nodes(node).passable) {
          if (!explored.contains(node)) {
            toExplore.enqueue(node)
            explored = explored + node
            mapOfNode += (node -> nodeToExplore)
            if (node == end) {
              var nodestart: Int = node
              while (nodestart != start) {
                nodestart = mapOfNode(nodestart)
                mapTiles = mapTiles :+ graph.nodes(nodestart)
              }
              for (tile <- mapTiles) {
                for (lists <- map) {
                  for (tile2 <- lists) {
                    if (tile2 == tile) {
                      val gridlocation: GridLocation = new GridLocation(lists.indexOf(tile2), map.indexOf(lists))
                      bfsPath = bfsPath :+ gridlocation
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    bfsPath
  }

}
