## Getting Started

### Prerequisites

Ensure you have the following software installed:
- Xcode 12.4
- Java 11

### Step 1: Clone the repository

```bash
git clone https://github.com/invertase/notifee.git
cd notifee/
```

### Step 2: Install test project dependencies

```bash
yarn
```

### Step 3: Start React Native packager

```bash
yarn tests_rn:packager
```

### Step 4

Ensure you have TypeScript compiler running to listen to `react-native` submodule changes:

```bash
yarn build:rn:watch
```

## Testing Code

### Unit Testing

The following package scripts are exported to help you run tests;

- `yarn tests_rn:test` - run Jest tests once and exit.
- `yarn tests_rn:jest-watch` - run Jest tests in interactive mode and watch for changes.
- `yarn tests_rn:jest-coverage` - run Jest tests with coverage. Coverage is output to `./coverage`.

### End-to-end Testing

Tests can be found in the `tests_react_native/specs` directory.

FIXME: Currently to get native code working correctly you need to do this:
`cd tests_react_native/node_modules/@notifee && \rm -fr react-native && ln -s ../../../packages/react-native .`
...that allows xcodebuild and android to link in the raw / maybe-unpublished code from local files instead of NPM package

To run tests, use these commands:

- `Android`: run `yarn tests_rn:android:test`
- `iOS`: run `yarn tests_rn:ios:test`

### Linting & type checking files

Runs ESLint and respective type checks on project files

```bash
yarn validate:all:js
yarn validate:all:ts
```

## Publishing

### Release Checklist

1. Bump version: `npm version {minor/patch}`
1. Publish to npm: `npm publish`
1. Update release notes [here](https://github.com/invertase/notifee/blob/master/docs/react-native/docs/release-notes.md)
