module.exports = {
  env: {
    browser: true,
    es6: true,
    node: true,
    jest: true,
  },
  parser: '@babel/eslint-parser',
  parserOptions: {
    ecmaVersion: 2022,
    requireConfigFile: false,
  },
  extends: [
    'react-app',
    'eslint:recommended',
    'plugin:prettier/recommended',
    /*
    'react-app',
    'airbnb-base',
    'plugin:prettier/recommended',
    'prettier',
    */
  ],
  rules: {
    'max-depth': ['error', 2],
    'max-lines-per-function': ['error', 16],
    'operator-linebreak': ['error', 'before'],
    'no-unused-expressions': ['error', { allowTernary: true }],
    'prettier/prettier': ['error', { endOfLine: 'auto' }],
  },
};
