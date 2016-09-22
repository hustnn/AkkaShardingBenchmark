package com.niu.AkkaShardingDemo

/**
 * Created by niuzhaojie on 21/9/16.
 */

import akka.actor.{Actor, Props, ActorLogging}
import akka.cluster.sharding.ShardRegion.{ExtractShardId, ExtractEntityId}
import com.niu.AkkaShardingDemo.Message.{WhereToPost, Post}

object BundleAddressSearcher {

  def name = "bundleAddressSearch"

  def props = Props[BundleAddressSearcher]

  def extractShardId: ExtractShardId = {
    case WhereToPost(city, _) => (city.id % 20).toString
  }

  def extractEntityId: ExtractEntityId = {
    case msg @ WhereToPost(city, _) => (city.id.toString, msg)
  }
}

class BundleAddressSearcher extends Actor with ActorLogging {

  def receive = {
    case WhereToPost(city, bundle) =>
      val num = util.Random.nextInt(100000) // searching for a postcode for the bundle to be send
      val address = s"Addr_${bundle.id}_${num}"
      log.info("The address for the bundle {} in City {} : {}", bundle.id, city.id, address)
      sender ! Post(address)
  }
}
