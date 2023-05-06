wvlet-ui
---



```sh
# Compiling UI
$ cd wvlet
$ ./sbt 
> ~ui/fastLinkJS 

# In another console, start an RPC server
$ ./sbt
> ~server/reStart server

# In another console, start a development server with Vite.js
$ yarn dev --open
```


Building a package

```
$ ./sbt
> ui/fullLinkJS

$ yarn build

# To test the production build
$ yarn preview --open
```
