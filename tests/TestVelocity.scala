package tests

import backend.GridLocation
import org.scalatest.FunSuite
import backend.PathFinding
import backend.physics.PhysicsVector

class TestVelocity extends FunSuite{
  val EPSILON: Double = 0.05

//  Approximation function to account for floating point error
  def equalDoubles(d1: Double, d2: Double): Boolean = {
    (d1 - d2).abs < EPSILON
  }

// Test suite to test the functionality of velocity calculations for a user
  test("Functionality of returned velocity"){
    val pathList: List[GridLocation] = List(new GridLocation(0,0), new GridLocation(2,2), new GridLocation(2,6), new GridLocation(-2,5))
    val velocity: PhysicsVector = PathFinding.getVelocity(pathList, new PhysicsVector(2.3, 2.4, 0))
    println(velocity.x," " + velocity.y," " + 0.0)
    assert(equalDoubles(velocity.x + velocity.y, .24361276856704822 + 4.994061755624484))
  }
  test("Functionality of returned velocity at last point in path"){
    val pathList: List[GridLocation] = List(new GridLocation(0,0), new GridLocation(2,2), new GridLocation(2,6), new GridLocation(1,3))
    val velocity: PhysicsVector = PathFinding.getVelocity(pathList, new PhysicsVector(1.3, 3.5, 0))
    println(velocity.x," " + velocity.y," " + 0.0)
    assert(equalDoubles(velocity.x + velocity.y, 5.0))
  }
  test("No Stopping"){
    val pathList: List[GridLocation] = List(new GridLocation(0,0), new GridLocation(2,2), new GridLocation(2,6), new GridLocation(1,3))
    val velocity: PhysicsVector = PathFinding.getVelocity(pathList, new PhysicsVector(1.55, 3.55, 0))
    println(velocity.x," " + velocity.y," " + 0.0)
    assert(equalDoubles(velocity.x + velocity.y, 0.0))
  }
}
