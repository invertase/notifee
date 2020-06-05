module.exports = {
  maxConcurrency: 30,
  preset: './tests_react_native/node_modules/react-native/jest-preset.js',
  transform: {
    '^.+\\.(js)$': 'babel-jest',
    '\\.(ts|tsx)$': 'ts-jest',
  },
  rootDir: '..',
  testMatch: ['<rootDir>/tests_react_native/__tests__/**/*.test.ts'],
  modulePaths: ['node_modules', '<rootDir>/tests_react_native/node_modules'],
  collectCoverage: true,

  collectCoverageFrom: [
    '<rootDir>/packages/react-native/src/**/*.{ts,tsx}',
    '!**/node_modules/**',
    '!**/vendor/**',
  ],

  moduleFileExtensions: ['ts', 'tsx', 'js'],
};
