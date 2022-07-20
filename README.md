# i4s SymbΘlic
A symbolic representation of logic from facts

## Overview
This repo contains 3 subprojects -
* common - common code cross built for jvm and scala.js
* nlp - tools for nlp
* server - an akka-http server that expresses a graphql interface for language and concept services
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
`http://localhost:9000/language`.

**Note** - at this time, the web project does not rely on data from the server project. So work on web does not yet need server to run.

## Development
We use standard PRs and require reviews to contribute. For easing the ramp for beginners, the process is outlined here. In the following 
example we will create a branch, push it to origin and commit. Pushes to dev (the main branch) are not allowed.

```
$ git checkout -b <initials>-a-descriptive-name 
$ git push -u origin HEAD

# make your changes

$ git add . 
$ git commit -m "Meaningful commit message"
$ git push

# go to github and create a pull request (PR), be descriptive, add a reviewer from the contributors list  
```
