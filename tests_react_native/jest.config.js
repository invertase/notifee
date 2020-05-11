module.exports = {
  maxConcurrency: 10,
  preset: './node_modules/react-native/jest-preset.js',
  transform: {
    '^.+\\.(js)$': 'babel-jest',
    '\\.(ts|tsx)$': 'ts-jest',
  },
  testMatch: ['**/__tests__/**/*.test.ts'],
  modulePaths: ['node_modules', '../node_modules'],
  moduleDirectories: ['node_modules', '../node_modules'],
  moduleFileExtensions: ['ts', 'tsx', 'js'],
};
