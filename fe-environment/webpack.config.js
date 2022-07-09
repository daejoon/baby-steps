const path = require("path")
const webpack = require("webpack")
const { CleanWebpackPlugin } = require("clean-webpack-plugin")
const MiniCssExtractPlugin = require('mini-css-extract-plugin')

module.exports = {
    mode: "development",
    entry: {
        main: "./src/app.js",
    },
    output: {
        filename: "js/[name].js",
        path: path.resolve("./dist")
    },
    module: {
        rules: [
            {
                test: /\.(css)$/,
                // use: ['style-loader', 'css-loader']
                use: [
                    {
                        loader: MiniCssExtractPlugin.loader,
                        options: {
                            publicPath: './dist/css'
                        }
                    }
                    , 'css-loader'
                ]
            },
            {
                test: /\.(png|svg)$/,
                loader: 'file-loader',
                options: {
                    publicPath: './dist/images',
                    name: 'images/[name].[ext]?[hash]'
                }
            }
        ]
    },
    plugins: [
        new MiniCssExtractPlugin({
            filename: `css/[name].css`
        }),
        new CleanWebpackPlugin(),
    ]
}
