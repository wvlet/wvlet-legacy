wvlet-ui
====



```
$ npm install -g browser-sync

$ browser-sync start --server wvlet-ui/src/main/public --server wvlet-ui/target/scala-2.12  --files wvlet-ui/src/main/public wvlet-ui/target/scala-2.12/wvlet-ui-fastopt.js
```

```
$ ./sbt
> project wvlet-ui
> ~fastOptJS
```

## Server

```
$ ./sbt
> project wvlet-server
> ~re-start
```
