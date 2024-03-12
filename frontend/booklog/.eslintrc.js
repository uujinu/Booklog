module.exports = {
  env: {
    browser: true,
    es6: true,
    node: true,
    jest: true
  },
  parser: '@babel/eslint-parser',
  parserOptions: {
    ecmaVersion: 2022,
    requireConfigFile: false
  },
  extends: ['react-app', 'eslint:recommended', 'plugin:prettier/recommended'],
  rules: {
    'max-depth': ['error', 3],
    'max-lines-per-function': 'off',
    'operator-linebreak': ['error', 'before'],
    'no-unused-expressions': ['error', { allowTernary: true }],
    'prettier/prettier': ['error', { endOfLine: 'auto' }],
    'no-unused-vars': 'off',
    'no-useless-escape': 'off'
  }
};
