package backend

import play.api.libs.json.{JsValue, Json}


object GameMap {

//  case "g" => new MapTile("grass", true)
//  case "f" => new MapTile("forest", true)
//  case "m" => new MapTile("mountain", true)
//  case "w" => new MapTile("water", false)

  def apply(number: Int): GameMap ={
    if(number == 0){
      new GameMap{
        tiles = List(
          MapTile.generateRow("wwwwwwwwwwwwwwwwwwwwwwwwwwwwww"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wwwwwwwwwwwwwwwwwwwwwwwwwwwwww")
        )
        startingLocation = new GridLocation(1, 3)
      }
    }else if(number == 1) {
      new GameMap{
        tiles = List(
          MapTile.generateRow("wwwwwwwwwwwwwwwwwwwwwwwwwwwwww"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wggggmmmmmmmmmgggggggggggggggw"),
          MapTile.generateRow("wggmmmmmmmmggggggggggggggggggw"),
          MapTile.generateRow("wmmmmmmmggggggwwwwgggggggggggw"),
          MapTile.generateRow("wgggggggggwwwwwwwwwwwggggggggw"),
          MapTile.generateRow("wggggggggwwwgfffggwwwwwwgggggw"),
          MapTile.generateRow("wgggggggwwggfffffgggwwwggggggw"),
          MapTile.generateRow("wgggggggwwwgfffffggggwgggggggw"),
          MapTile.generateRow("wggggggggwwggfffggggwwwggggggw"),
          MapTile.generateRow("wgggggggggwwwgggggggwwwggggggw"),
          MapTile.generateRow("wggggggggggggggwwwwwwwwwwwwggw"),
          MapTile.generateRow("wgggggggggwwwwwwwwgggggggggggw"),
          MapTile.generateRow("wggffffgggggggggwwwwwggggggggw"),
          MapTile.generateRow("wgffffffggggggggwwwwwggggggggw"),
          MapTile.generateRow("wgffffffgggggggggggggggggggggw"),
          MapTile.generateRow("wggffffggggggggggggggggggggggw"),
          MapTile.generateRow("wggggggggggggggggggggggggggggw"),
          MapTile.generateRow("wwwwwwwwwwwwwwwwwwwwwwwwwwwwww")
        )

        startingLocation = new GridLocation(15, 10)

      }
    }else{
      new GameMap
    }
  }

}

class GameMap {
  var tiles:List[List[MapTile]] = List()
  var startingLocation = new GridLocation(1, 3)

  def tilesJSON(): JsValue = {
    Json.toJson(tiles.map((row: List[MapTile]) => row.map((tile: MapTile) => Json.toJson(Map("type" -> Json.toJson(tile.tileType), "passable" -> Json.toJson(tile.passable))))))
  }

}
