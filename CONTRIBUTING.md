# Getting Started

## Prerequisites

Ensure you have the following software installed:

- Java 11 (the default in Android Studio 2020.3.1 and higher)

## Step 1: Clone the repository

```bash
git clone https://github.com/invertase/notifee.git
cd notifee/
```

## Step 2: Install test project dependencies

```bash
yarn
```

Note: During this step, the `package.json` script `prepare` is called, which includes a call to `build:core:ios`.
During that step, the current "NotifeeCore" iOS files are copied into `packages/react-native/ios/...`. If you modify
iOS core code and want to test it you will want to re-run that step, or temporarily modify `packages/react-native/RNNotifee.podspec`
to contain `$NotifeeCoreFromSources=true` so that the up to date source files are actually incorporated in the final build.

The same issue applies to Android code if you need to see development changes to the NotifeeCore Android code in an Android build. Run `yarn build:core:android` to generate a new AAR file for Android then rebuild/restart the Android app for core Android
changes to take effect.

This "core build" process may change in the future and we are open to suggestions that maintain the NotifeeCore code as a separate item
so that it may be re-used by other projects (e.g. Flutter), not just the react-native API wrapper.

## Step 3: Start React Native packager

```bash
yarn tests_rn:packager
```

## Step 4

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

Maintainers with write access to the repo and the npm organization can publish new versions by following the release checklist below.

### Release Checklist

#### Automated Process

**Note: release is fully automated now in `.github/workflows/publish.yml` and controlled by the `.releaserc` file**

Simply navigate to the release publish workflow, and use the manual trigger to publish from main branch:

1. https://github.com/invertase/notifee/actions/workflows/publish.yml

Afterwards, you may verify that everything worked by checking the expected work products:

1. Verify that there is a new github release: https://github.com/invertase/notifee/releases
1. Verify that there is a new npmjs release (may take a moment to update): https://www.npmjs.com/package/@notifee/react-native?activeTab=versions
1. Verify that the changelog is updated: https://docs.page/invertase/notifee/react-native/release-notes
1. Verify that there is a new tag correctly created: https://github.com/invertase/notifee/tags
1. Verify that there is a commit with that tag, with the updated release notes: https://github.com/invertase/notifee/commits/main/

#### Manual Process

If for some reason the automated process is not working, or you want to re-automate it using some other tools, these are the steps to take to correctly create and publish a release:

1. Navigate to the React Native package: `cd packages/react-native`
1. Update release notes [here](https://github.com/invertase/notifee/blob/main/docs/react-native/release-notes.mdx)
1. Bump version: `npm version {major/minor/patch} --legacy-peer-deps`
1. Publish to npm: `npm publish` (this generates a new core AAR, and requires a valid NPM login with permission to publish the Notifee package on npmjs.com)
1. Commit those changes (after npm publish so new AAR files are committed)
1. Tag the repo (current format is `@notifee/react-native@x.y.z`)
1. Push the release notes / version / tag to the repo: `git push --tags`
1. Create a release on the repo:

    ```bash
    export TAGNAME=`git tag --list|sort -r|head -1`
    gh release create ${TAGNAME} --title "${TAGNAME}" --notes "[Release Notes](https://github.com/invertase/notifee/blob/main/docs/react-native/release-notes.mdx)"
    ```
