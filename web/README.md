# aletheia-web
## Prerequisites
The following node-based tools are utilized by the sbt web-pack plugin to manage the dependencies
on React and other .js libraries we may wrap in the future.
### npm
`$ brew install npm`
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

[create-react-scala-app.g8](https://github.com/shadaj/create-react-scala-app.g8) - The g8 file that generated this initial package<br>
[Scala.js DOM](https://github.com/scala-js/scala-js-dom) - DOM wrappers for scala.js<br>
[Scala.js Bundler](https://github.com/scalacenter/scalajs-bundler) - an sbt plugin that wraps webpack <br>
[Sinky](slinky.dev) - a scalajs react wrapper, HTML DSL, and hot reloading<br>
[Webpack](https://webpack.js.org/) - basic webpack tools, wrapped for use by the webpack sbt plugin

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