# i4s SymbΘlic
A symbolic representation of logic from facts

## Overview
This repo contains 3 subprojects - 
* common - common nlp, analysis and persistence tools for processing english language statements into grammar graphs and tokens
* server - a lightweight https server implementation designed to produce access to grammer & nlp processing logic for the frontend
* web - a `scala.js` implementation of a react web application (via [Slinky](https://slinky.dev/)) that is set up to provide web-based access to symbolic processing.

## Getting Started
A quick primer on sbt projects that utilizes subprojects. You can read the documentation [here](https://www.scala-sbt.org/1.x/docs/Multi-Project.html). 
The tldr; version for web development is: 
```
$ sbt
sbt:i4s-symbolic> project server
sbt:i4s-symbolic-server> reStart # reStop will stop the server

sbt:i4s-symbolic> project web
[info] set current project to i4s-symbolic-web (in build file:/Users/brent/Development/intelligence4s/i4s-symbolic/)
sbt:i4s-symbolic-web> dev #
...
[info] 1. Monitoring source files for web/fastOptJS...
[info]    Press <enter> to interrupt or '?' for more options.
```
At this point, change you make to scala.js code in the web application will automatically get built and deployed in the local broswer at 
`http://localhost:9000`.

**Note** - at this time, the web project does not rely on data from the server project. So work on web does not yet need server to run.