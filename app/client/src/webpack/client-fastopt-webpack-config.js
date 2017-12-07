var path = require('path');

var ExtractTextPlugin = require("extract-text-webpack-plugin");
var webpack = require('webpack');

var extractStylesheets = new ExtractTextPlugin({
  filename: '[name].css',
  disable: true
});

module.exports = {
  entry: {
    'client-fastopt-library': [
      '../../../../src/main/assets/stylesheets/vendor.less',
      './client-fastopt-entrypoint.js'
    ],
    'client-fastopt-stylesheets': [
      '../../../../src/main/assets/stylesheets/main.less'
    ]
  },
  output: {
    filename: '[name].js',
    library: 'ScalaJSBundlerLibrary',
    libraryTarget: 'var'
  },
  module: {
    rules: [{
      test: /\.(css|less)$/,
      use: extractStylesheets.extract({
        use: [{
          loader: 'css-loader',
          options: {
            import: false
          }
        }, {
          loader: 'less-loader',
          options: {
            paths: [
              path.resolve(__dirname, "node_modules")
            ]
          }
        }],
        fallback: 'style-loader'
      })
    }]
  },

  plugins: [
    new webpack.NamedModulesPlugin(),
    extractStylesheets
  ],

  devServer: {
    proxy: {
      '/': 'http://localhost:6790'
    }
  }
};
