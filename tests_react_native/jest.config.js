module.exports = {
  maxConcurrency: 30,
  preset: './tests_react_native/node_modules/react-native/jest-preset.js',
  transform: {
    '^.+\\.(js)$': 'babel-jest',
    '\\.(ts|tsx)$': 'ts-jest',
  },
  rootDir: '..',
  testMatch: [
    '<rootDir>/tests_react_native/__tests__/**/*.test.ts',
    '<rootDir>/packages/react-native/plugin/__tests__/**/*.test.ts',
  ],
  modulePaths: ['node_modules', '<rootDir>/tests_react_native/node_modules'],
  collectCoverage: true,

  collectCoverageFrom: [
    '<rootDir>/packages/react-native/src/**/*.{ts,tsx}',
    '<rootDir>/packages/react-native/plugin/**/*.{ts,tsx}',
    '!**/node_modules/**',
    '!**/vendor/**',
  ],

  setupFilesAfterEnv: ['<rootDir>/tests_react_native/jest-mock.js'],

  moduleFileExtensions: ['ts', 'tsx', 'js'],
};
