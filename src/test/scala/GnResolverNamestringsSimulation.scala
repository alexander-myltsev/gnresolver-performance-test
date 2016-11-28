import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class GnResolverNamestringsSimulation extends Simulation with Commons {

  val feedsCount = 20

  def nameResolversGetScn(name: String) = scenario(name)
    .feed(tsv("feeds/name_strings.tsv").circular.random, feedsCount)
    .exec(
      http("Name Resolvers").get("/api/name_resolvers")
        .queryParam("names",
                    (1 to feedsCount).map { i => """{"value":"${name""" + i + """}"}""" }
                                     .mkString("[", ",", "]"))
        .check(
            status.is(200)
          , substring("data")
        )
    ).pause(75.millis)

  setUp(
      nameResolversGetScn("Name Resolvers - GET - 1 user").inject(
        constantUsersPerSec(1).during(2.minutes)
      ).uniformPauses(25)
    , nameResolversGetScn("Name Resolvers - GET - rampup to 20").inject(
        rampUsersPerSec(1).to(20).during(60.seconds)
      ).uniformPauses(25)
  ).protocols(httpConf)
}
