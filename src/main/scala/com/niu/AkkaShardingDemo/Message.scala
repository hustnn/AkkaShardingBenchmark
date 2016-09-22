package com.niu.AkkaShardingDemo

/**
 * Created by niuzhaojie on 21/9/16.
 */

import spray.json.DefaultJsonProtocol._

import com.niu.AkkaShardingDemo.Entity.{City, Bundle}

object Message {

  case class WhereToPost(city: City, bundle: Bundle)

  case class Post(address: String)

  object Post {
    implicit val postJson = jsonFormat1(Post.apply)
  }

}
