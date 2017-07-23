# riemann-ack

Riemann-ack is a plugin for riemann that uses redis as a cache for acknowledgements.

It backs to redis so that other, external interfaces (think chatbots, frontends, etc) can easily ack and unack events in riemann.

Special thanks to @cafreeman (https://github.com/cafreeman) who basically did the heavy lifting for creating this. He saved me countless hours of frustration figuring out how to handle streams to get events to and from this plugin. Thanks Chris!

## Installation

### Configure your redis server

I haven't figured out how to pass this in yet to the plugin, so for now, modify the following in src/riemann_ack/core.clj to specify the redis server:

```
(def server1-conn {:pool {} :spec {:host "redis"}})
```

### Building the plugin jar

You will need to build this module for now and push it on riemann's classpath, for this
you will need a working JDK, JRE and [leiningen](http://leiningen.org).

First build the project:

```
lein uberjar
```

### Adding the plugin to riemann

The resulting artifact will be in `target/riemann-ack-0.0.1-standalone.jar`.
You will need to push that jar on the machine(s) where riemann runs, for instance, in
`/usr/lib/riemann/riemann-ack.jar`.

You can then add the jar into riemann via the EXTRA_CLASSPATH environment variable:

```
EXTRA_CLASSPATH=/usr/lib/riemann/riemann-ack.jar
```

Once the Jar is available, add it to your riemann.config

```
(require '[riemann-ack.core :as ack])
```

## Usage

riemann-ack functions by looking into redis for a key consisting of:

ack-[host]-[service]

RE:

ack-10.1.1.1-myservice

If it finds that key with a value of "1", it will append the tag "acked" to the event. You can then process events on that tag. To silence acked events from info output, you would do something like:

```
(streams
      ; Index all events immediately.
      ; index
      (where ( not (service #"riemann.*"))
        (ack/alert-stream
          (where (not (tagged "acked"))
            #(info %)
          )
        )
      )
)
```
