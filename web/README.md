# aletheia-web
## Prerequisites
The following node-based tools are utilized by the sbt web-pack plugin to manage the dependencies
on React and other .js libraries we may wrap in the future.
### npm
```
$ brew install npm`
```
### nvm 
```
$ brew install nvm
$ mkdir ~/.nvm
$ echo "export NVM_DIR=~/.nvm" >> ~/.zshrc
$ echo "source $(brew --prefix nvm)/nvm.sh" >> ~/.zshrc
$ source ~/.zshrc
$ nvm install 14
$ nvm use 14
```

## Resources

### Public resources
The following resources are pulled into the package during build.

[create-react-scala-app.g8](https://github.com/shadaj/create-react-scala-app.g8) - The g8 file that generated this initial package<br>
[Scala.js DOM](https://github.com/scala-js/scala-js-dom) - DOM wrappers for scala.js<br>
[Scala.js Bundler](https://github.com/scalacenter/scalajs-bundler) - an sbt plugin that wraps webpack <br>
[Sinky](slinky.dev) - a scalajs react wrapper, HTML DSL, and hot reloading<br>
[Webpack](https://webpack.js.org/) - basic webpack tools, wrapped for use by the webpack sbt plugin

### scala-js-d3v4

This project depends upon D3v4. To access this from scala.js, we utilize a fork of [fdietze/scala-js-d3v4](https://github.com/fdietze/scala-js-d3v4). 
This 3rd party facade is incomplete, so we have a fork at [intelligence4s/scala-js-d3v4](https://github.com/intelligence4s/scala-js-d3v4). In order to 
build this project, you will need to clone that repo, build and publish locally.

```
# do this one time only
$ git clone git@github.com:intelligence4s/scala-js-d3v4.git

# do this every time you make changes to the scala-js-d3v4 repo
$ sbt "+ publishLocal"
```

After publishing locally you need to reload this project with `sbt dev` again.

## Running

There are two tasks to run: 

#### sbt dev
```
$ sbt ";project web; dev" 
```
This will run an interactive development environment. Updates to sources will automatically 
reload, as expected.

#### sbt build
```
$ sbt ";project web; build" 
```
This will generate and package the javascript a release build located in the `build`
directory

### Notes
1. If you are running on Apple Silicon, you need to use the legacy SSL module:
```
$ export NODE_OPTIONS=--openssl-legacy-provider
$ sbt ";project web; dev"
```