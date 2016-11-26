import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import com.typesafe.config._

trait Commons {
  val conf = ConfigFactory.load("gatling.conf")
  val baseUrlStr = conf.getString("baseUrl")

  val httpConf = http
    .baseURL(baseUrlStr)
    .acceptEncodingHeader("""gzip, deflate""")
}
