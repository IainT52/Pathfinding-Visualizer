package backend

object MapTile {

  def generateRow(row: String): List[MapTile] = {
    row.map((ch: Char) => MapTile(ch.toString)).toList
  }

  def apply(tileType: String): MapTile = {
    tileType match {
      case "g" => new MapTile("grass", true)
      case "f" => new MapTile("forest", true)
      case "m" => new MapTile("mountain", true)
      case "w" => new MapTile("water", false)
    }
  }

}

class MapTile(val tileType: String, val passable: Boolean) {

}
