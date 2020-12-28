/*
 * Copyright (c) 2016-present Invertase Limited
 */

const { readFileSync, writeFileSync } = require('fs');
const path = require('path');
const { Application } = require('typedoc');

const output = path.resolve(process.cwd(), 'docs', 'typedoc.json');
const outputMin = path.resolve(process.cwd(), 'docs', 'typedoc.min.json');

const app = new Application();

// https://github.com/TypeStrong/typedoc/issues/956
const { inputFiles } = app.bootstrap({
  mode: 'file',
  includeDeclarations: true,
  excludeExternals: true,
  exclude: '**/node_modules/**',
  tsconfig: path.resolve(process.cwd(), 'tsconfig.docs.json'),
});

app.generateJson(inputFiles, output);

const sourceJson = readFileSync(output);
writeFileSync(outputMin, JSON.stringify(JSON.parse(sourceJson)));
