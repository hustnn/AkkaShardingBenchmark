package com.niu.AkkaShardingDemo

/**
 * Created by niuzhaojie on 21/9/16.
 */


import akka.actor.{ActorSystem, ActorRef}
import akka.pattern.ask
import akka.cluster.sharding.{ClusterShardingSettings, ClusterSharding}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.niu.AkkaShardingDemo.Entity.{City, Bundle}
import com.niu.AkkaShardingDemo.Message._
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future
import scala.concurrent.duration._

object ShardingApp extends App {

  val config = ConfigFactory.load("sharding")

  implicit val system = ActorSystem(config getString "application.name", config)

  ClusterSharding(system).start(
    typeName = BundleAddressSearcher.name,
    entityProps = BundleAddressSearcher.props,
    settings = ClusterShardingSettings(system),
    extractShardId = BundleAddressSearcher.extractShardId,
    extractEntityId = BundleAddressSearcher.extractEntityId
  )

  val searcher: ActorRef = ClusterSharding(system).shardRegion(BundleAddressSearcher.name)

  val route: Route =
    get {
      path("cities" / IntNumber / "addressForBundle" / IntNumber) { (cityId, bundleId) =>

        val city = City(cityId)
        val bundle = Bundle(bundleId)
        val address = searcher.ask(WhereToPost(city, bundle))(5 seconds)

        onSuccess(address) {
          case Post => complete(address.mapTo[Post])
        }
      }
    }

}
