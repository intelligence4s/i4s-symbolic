const path = require("path")
const { colors: defaultColors } = require('tailwindcss/defaultTheme')

const colors = {
  ...defaultColors,
  'green': '#12A420',
  'light-gray': '#E9E8E8',
  'dark-gray': '#929292',
  // background: linear-gradient(#03B3E9, #1b69F0);
  'gradient-light-blue': '#03B3E9',
  'gradient-dark-blue': '#1b69F0',
}

module.exports = {
  mode: 'jit',
  content: [
    path.resolve(__dirname, '*.{html,js}'),
    './node_modules/tw-elements/dist/js/**/*.js'
  ],
  theme: {
    colors,
    extend: {
      colors,
    },
  },
  plugins: [
    require('tw-elements/dist/plugin')
  ],
}