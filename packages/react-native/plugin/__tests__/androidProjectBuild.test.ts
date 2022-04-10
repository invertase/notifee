import fs from 'fs/promises';
import path from 'path';

import { setCompileSdkVersion, setMavenRepository } from '../src/withNotifeeProjectGradlePlugin';

describe('[Android] project build.gradle test', () => {
  it('updates java compileSdkVersion to 31 in project build.gradle', async () => {
    let projectGradle = await fs.readFile(
      path.resolve(__dirname, '../fixtures/project.build.gradle'),
      {
        encoding: 'utf-8',
      },
    );

    projectGradle = setCompileSdkVersion(projectGradle);

    expect(projectGradle).toContain('compileSdkVersion = 31');

    console.log(projectGradle);
  });

  it('adds custom maven repository to project build.gradle', async () => {
    let projectGradle = await fs.readFile(
      path.resolve(__dirname, '../fixtures/project.build.gradle'),
      {
        encoding: 'utf-8',
      },
    );

    projectGradle = setMavenRepository(projectGradle);

    expect(projectGradle).toMatchSnapshot();

    console.log(projectGradle);
  });
});
