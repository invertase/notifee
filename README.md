<p align="center">
  <a href="https://invertase.io">
    <img width="140px" src="https://static.invertase.io/assets/invertase-logo.png"><br/>
  </a>
  <h3 align="center">Notifee Notifications Product</h3>
</p>


---

## Getting Started

### Step 1: Clone the repository
Ensure you call `git clone` with the `recursive` flag to ensure submodules (packages/react-native) are included:

```bash
git clone https://github.com/invertase/notifee.git --recursive
cd notifee/
```

### Step 2: Install test project dependencies

```bash
yarn
yarn tests_rn:ios:pod:install
```

### Step 3: Start React Native packager:
```bash
yarn run tests_rn:packager
```

### Step 4:
Ensure you have TypeScript compiler running to listen to `react-native` submodule changes:

```bash
yarn run build:rn:watch
```

## Testing Code

### Unit Testing

The following package scripts are exported to help you run tests;

- `yarn tests_rn:test` - run Jest tests once and exit.
- `yarn tests_rn:jest-watch` - run Jest tests in interactive mode and watch for changes.
- `yarn tests_rn:jest-coverage` - run Jest tests with coverage. Coverage is output to `./coverage`.

### End-to-end Testing

Install Cavy CLI if not already installed

 ```yarn global add cavy-cli```

Tests can be found in the `tests_react_native/specs` directory.

To run end-to-end tests for `Android`, please run:
- `yarn tests_rn:android:test`

To run end-to-end tests for `iOS`, please run:
- `yarn tests_rn:ios:test`

### Linting & type checking files

Runs ESLint and respective type checks on project files

```bash
yarn validate:all:js
yarn validate:all:ts
```