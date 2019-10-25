import { stringToHashCode } from './../build/index.min';

describe('stringToHashCode', () => {
  it('correctly converts a string to a Java HashCode equivalent', () => {
    const output = stringToHashCode('test_string');
    expect(output).toBe(-240766882); // copied from Java test
  });
});
