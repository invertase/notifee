/* tslint:disable:non-literal-fs-path */
import { default as parser, stringToHashCode } from './../build/index.min';
import * as fs from 'fs';
import * as path from 'path';

describe('exports', () => {
  it('export the parser as default', () => {
    expect(parser).toBeInstanceOf(Function);
  });

  it('exports the stringToHashCode utility', () => {
    expect(stringToHashCode).toBeInstanceOf(Function);
  });
});

let sample;

describe('Android 28+', () => {
  beforeAll(async () => {
    sample = fs
      .readFileSync(path.resolve(__dirname, './samples/android_28.txt'))
      .toString('utf-8');
  });

  it('returns parsed notification records in date descending order (newest first)', () => {
    const output = parser(sample);
    expect(output).toBeTruthy();
    expect(output).toBeInstanceOf(Array);
    expect(output.length).toBe(4);

    let timestampPrevious = 999999999999999;
    for (let i = 0; i < output.length; i++) {
      const outputElement = output[i];
      expect(outputElement.creationTimeMs).toBeLessThan(timestampPrevious);
      timestampPrevious = outputElement.creationTimeMs;
    }
  });

  describe('extras', () => {
    it('reads android. prefixed extras and places them on root object', () => {
      const output = parser(sample);
      expect(output).toBeTruthy();
      expect(output[0]).toMatchSnapshot();
    });
  });
});
