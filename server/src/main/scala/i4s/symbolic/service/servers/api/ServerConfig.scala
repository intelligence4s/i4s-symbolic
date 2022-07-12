package i4s.symbolic.service.servers.api

import pureconfig._
import pureconfig.generic.auto._

case class ServerConfig(enabled: Boolean, interface: String, port: Int)

object ServerConfig {
  //  implicit val productHint: ProductHint[RestServer] = ProductHint[RestServer](ConfigFieldMapping(CamelCase,KebabCase))
  private lazy val _config = ConfigSource.default
    .at("rest-server")
    .load[ServerConfig]
    .fold(
      failure => throw new IllegalStateException(failure.toString),
      config => config
    )

  def config: ServerConfig = _config
}