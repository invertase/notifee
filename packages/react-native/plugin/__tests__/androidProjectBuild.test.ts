import fs from 'fs/promises';
import path from 'path';

import { setMavenRepository } from '../src/withNotifeeProjectGradlePlugin';

describe('[Android] project build.gradle test', () => {
  it('applies changes to project build.grade', async () => {
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
