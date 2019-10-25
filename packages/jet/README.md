<p align="center">
  <a href="https://invertase.io">
    <img height="256" src="https://static.invertase.io/assets/jet.png"><br/>
  </a>
  <h2 align="center">Jet</h2>
</p>

<p align="center">
  <a href="https://www.npmjs.com/package/jet"><img src="https://img.shields.io/npm/dm/jet.svg?style=flat-square" alt="NPM downloads"></a>
  <a href="https://www.npmjs.com/package/jet"><img src="https://img.shields.io/npm/v/jet.svg?style=flat-square" alt="NPM version"></a>
  <a href="/LICENSE"><img src="https://img.shields.io/npm/l/jet.svg?style=flat-square" alt="License"></a>
  <a href="https://discord.gg/C9aK28N"><img src="https://img.shields.io/discord/295953187817521152.svg?logo=discord&style=flat-square&colorA=7289da&label=discord" alt="Chat"></a>
  <a href="https://twitter.com/invertaseio"><img src="https://img.shields.io/twitter/follow/invertaseio.svg?style=social&label=Follow" alt="Follow on Twitter"></a>
</p>

> **WARNING:** Jet is currently a Proof of Concept, APIs and usage is likely to change by the first release version.

> This repo is in development and does not have a full release version yet. v0.1.0 is the latest stable version in it's current form - this works on Android & iOS on React Native ^0.56-59.5 and Detox ^10.0.13.
----

Jet lets you bring your React Native JS code into NodeJS and test it mock free and native testing code free - ideal for testing React Native modules e2e.

Jet extends upon [`wix/detox`](https://github.com/wix/detox) and by default the [Mocha testing framework](https://mochajs.org/).

Detox provides all the functionality you'll need to control your testing app, device and it's UI (if you have one) whilst Jet allows JS code execution in the context of your RN app via Node.js - giving you full access to all the Native api's as you would have inside your app.

----

> Latest supported React Native version: **^0.59.5**

> Latest supported Detox version: **^10.0.13**

----

## Features

### ⏩ Test with JavaScript 

Your test suites and your React Native code run inside NodeJS - making testing your modules with NodeJS testing frameworks (Mocha only currently, to be replaced with Jest) possible.

![test suite](https://static.invertase.io/assets/jet/tests-1.gif)


### 🐞 Debugging

Supports debugging your test suites and your React Native JS bundle using the standard NodeJS debugger protocol.

![debugging](https://static.invertase.io/assets/jet/debugging.gif)


### 💯 Coverage

Get full code coverage output for your React Native module's JS API using [istanbul/nyc](https://github.com/istanbuljs/nyc) coverage tools.

![coverage](https://static.invertase.io/assets/jet/coverage.png)


### ☕️ Full Detox API support

Supports the full [Detox API](https://github.com/wix/detox/blob/master/docs/README.md#api-reference); reloading or relaunching your app automatically reconnects to your React Native JS bundle.

![detox](https://static.invertase.io/assets/jet/detox.png)


### ✨ Full access to React Native bundle context

Jet gives you full access to the JS context of your React Native app inside NodeJS ⚡️. 

## Quick Setup

```
$ yarn add jet
```

```
$ react-native link jet
```

in your mocha.opts add

```
--require jet/platform/node
```

You can update your package.json scripts with a task for packager-jet:
```
"packager-jet": "REACT_DEBUGGER='echo nope' react-native start",
```

Before starting your tests launch the packager-jet and afterwards start your detox tests.

## 💛 How can I help?

For now please see the open issues tracking work that needs doing discussions and thoughts on these issues and on Jet will help us mature the project into a useful tool.

----

## 😎 Projects using Jet

These projects use Jet to test their modules:

- [React Native Firebase](https://github.com/invertase/react-native-firebase): 🔥 A well tested feature rich modular Firebase implementation for React Native. Supports both iOS & Android platforms for over 15 Firebase services.

Submit a PR to add your project here.
