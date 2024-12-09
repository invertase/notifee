/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

const { promises: fs } = require('fs');
const path = require('path');
const { Application, TSConfigReader } = require('typedoc');

const cwd = process.cwd();
const output = path.resolve(cwd, 'docs-react-native', 'typedoc.json');
const outputMin = path.resolve(cwd, 'docs-react-native', 'typedoc.min.json');

const app = new Application();
app.options.addReader(new TSConfigReader());

try {
  // https://github.com/TypeStrong/typedoc/issues/956
  const { inputFiles } = app.bootstrap({
    mode: 'file',
    includeDeclarations: true,
    excludeExternals: true,
    exclude: '**/node_modules/**',
    tsconfig: path.resolve(cwd, 'tsconfig.docs.json'),
  });

  app.generateJson(inputFiles, output);

  // Minify the generated JSON
  fs.readFile(output, 'utf8')
    .then((data) => {
      const minifiedJson = JSON.stringify(JSON.parse(data));
      return fs.writeFile(outputMin, minifiedJson, 'utf8');
    })
    .then(() => console.log(`✅ Minified JSON saved at ${outputMin}`))
    .catch((error) => console.error('Error while processing JSON:', error));
  
} catch (error) {
  console.error('Error occurred while generating documentation:', error.message);
}