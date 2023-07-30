#### AKKA: actor mode in java
```
Actors
In the actor model, everything is an actor. Actors are objects that can:

	Receive messages from other actors.
	Process the received messages as they see fit.
	Send messages to other actors.
	Create new Actors.

Actors do not have any direct access to other actors. All communication is accomplished via message passing. 
This provides a rich model to simulate real-world objects that are loosely-coupled and have limited knowledge of each others internals.

```

```
很显然actor模式和协程模式这一套很类似: 一个个actor就类似一个个coroutine,互相之间异步通信,独立运行,比线程轻量.
```

```
java中基于actor模式的AKKA框架,以及基于AKKA的web框架play,
flink底层的分布式也是使用的akka写的。
```
