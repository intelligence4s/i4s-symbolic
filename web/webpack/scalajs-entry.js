if (process.env.NODE_ENV === "production") {
    const opt = require("./i4s-symbolic-web-opt.js");
    opt.main();
    module.exports = opt;
} else {
    var exports = window;
    exports.require = require("./i4s-symbolic-web-fastopt-entrypoint.js").require;
    window.global = window;

    const fastOpt = require("./i4s-symbolic-web-fastopt.js");
    fastOpt.main()
    module.exports = fastOpt;

    if (module.hot) {
        module.hot.accept();
    }
}
