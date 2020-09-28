package backend

import akka.actor.{Actor, ActorSystem, Props}
import backend.physics.PhysicsVector
import com.corundumstudio.socketio.{AckRequest, Configuration, SocketIOClient, SocketIOServer}
import play.api.libs.json.{JsValue, Json}


class Server extends Actor {

  var locations: Map[SocketIOClient, PhysicsVector] = Map()
  var velocities: Map[SocketIOClient, PhysicsVector] = Map()
  var paths: Map[SocketIOClient, List[GridLocation]] = Map()
  var lastUpdateTime: Long = System.nanoTime()

  val map: GameMap = GameMap(1)

  val config: Configuration = new Configuration {
    setHostname("localhost")
    setPort(8080)
  }

  val server: SocketIOServer = new SocketIOServer(config)


  server.addConnectListener(
    (socket: SocketIOClient) => {
      locations += socket -> new PhysicsVector(map.startingLocation.x + 0.5, map.startingLocation.y + 0.5)
      paths += socket -> List()
      velocities += socket -> new PhysicsVector(0.0, 0.0)
    }
  )

  server.addDisconnectListener(
    (socket: SocketIOClient) => {
      locations -= socket
      paths -= socket
      velocities -= socket
    }
  )


  server.addEventListener("destination", classOf[String],
    (socket: SocketIOClient, clickLocation: String, _: AckRequest) => {
      val dataParsed = Json.parse(clickLocation)
      val x = (dataParsed \ "x").as[Int]
      val y = (dataParsed \ "y").as[Int]
      val currentLocation: PhysicsVector = locations(socket)
      val path = PathFinding.findPath(
        new GridLocation(Math.floor(currentLocation.x).intValue(), Math.floor(currentLocation.y).intValue()),
        new GridLocation(x, y),
        map.tiles)

      paths += socket -> path
    }
  )

  server.start()

  override def postStop(): Unit = {
    println("stopping server")
    server.stop()
  }

  def gameState(): String = {
    val gameState: Map[String, JsValue] = Map(
      "tiles" -> map.tilesJSON(),
      "locations" -> Json.toJson(locations.values.map((v: PhysicsVector) => Json.toJson(Map("x" -> v.x, "y"-> v.y))))
    )
    Json.stringify(Json.toJson(gameState))
  }

  def updateVelocities(): Unit = {
    for ((socket, location) <- locations) {
      val path = paths(socket)
      velocities += socket -> PathFinding.getVelocity(path, location)
    }
  }


  def updateLocations(dt: Double): Unit = {
    for ((socket, velocity) <- velocities) {
      val location = locations(socket)
      locations += socket -> new PhysicsVector(location.x + velocity.x * dt, location.y + velocity.y * dt)
    }
  }

  override def receive: Receive = {

    case Update =>
      val newTime = System.nanoTime()
      val dt = (newTime - lastUpdateTime) / 1000000000.0
      updateVelocities()
      updateLocations(dt)
      lastUpdateTime = newTime
      server.getBroadcastOperations.sendEvent("gameState", gameState())
  }

}


object Server {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem()

    import actorSystem.dispatcher
    import scala.concurrent.duration._

    val server = actorSystem.actorOf(Props(classOf[Server]))

    actorSystem.scheduler.schedule(0.milliseconds, 33.milliseconds, server, Update)
  }
}