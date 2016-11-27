import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class GnResolverVersionSimulation extends Simulation with Commons {
  val versionScn = scenario("Version Request")
    .exec(
      http("Version GET").get("/api/version")
        .check(
            status.is(200)
          , substring("version")
        )
    ).pause(75)

  setUp(
    versionScn.inject(
        nothingFor(2.seconds)
      , rampUsersPerSec(5).to(10).during(2.minutes)
    ).uniformPauses(25)
  ).protocols(httpConf)
}
