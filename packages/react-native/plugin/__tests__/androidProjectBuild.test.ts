import fs from 'fs/promises';
import path from 'path';

import { setCompileSdkVersion } from '../src/withNotifeeProjectGradlePlugin';

const getProjectGradle = () => {
  return fs.readFile(path.resolve(__dirname, './fixtures/project.build.gradle'), {
    encoding: 'utf-8',
  });
};

describe('[Android] project build.gradle test', () => {
  it('updates java compileSdkVersion to 33 in project build.gradle', async () => {
    const projectGradle = await getProjectGradle();
    const newProjectGradle = setCompileSdkVersion(projectGradle);

    expect(newProjectGradle).toContain('compileSdkVersion = 33');
  });
});
