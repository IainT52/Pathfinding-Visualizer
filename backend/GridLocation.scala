package backend

class GridLocation(val x: Int, val y: Int){

  override def toString = s"($x, $y)"

  override def equals(that: Any): Boolean = {
    that match {
      case other: GridLocation =>
        this.x == other.x && this.y == other.y
      case _ => false
    }
  }
}
