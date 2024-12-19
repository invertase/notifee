/** @type {Partial<import("typedoc").TypeDocOptions>} */
import { OptionDefaults } from 'typedoc';

const config = {
  entryPoints: ['./packages/react-native/src/index.ts'],
  hostedBaseUrl: 'https://docs.page/invertase/notifee/react-native/reference',

  // If we generate markdown directly then replace the internal links with
  // a version that strips the `.mdx` suffix, we can match what docs.page
  // expects directly, no need for separate hosting.
  plugin: ['typedoc-plugin-markdown', 'typedoc-plugin-remark'],

  // Generate the typedoc in a subtree of our main / manually-created docs
  out: './docs/react-native/reference',

  // Make the main file index.mdx (instead of 'README.mdx')
  entryFileName: 'index',

  // we don't need to duplicate the Notifee README in the reference docs
  readme: 'none',

  // We don't want specific commit SHAs in our links because it makes the whole
  // reference site change with every commit. Just link to `main`
  gitRevision: 'main',

  // docs.page works with `.mdx` file extensions, not the default `.md` extensions
  // ...but we do need to make internal links that strip the suffix
  fileExtension: '.mdx',

  // docs.page has a different base URL then the output expects and tree hierarchies
  // don't work the way typedoc expects. If typedoc generates a flat structure the
  // problem of tree hierarchies is completely avoided
  flattenOutputFiles: true,

  // it is possible to do a single page or control output if you like
  // mergeReadme: true,
  // enumMembersFormat: 'table',
  // parametersFormat: 'table',
  // propertiesFormat: 'table',
  // typeDeclarationFormat: 'table',
  // indexFormat: 'table',
  // outputFileStrategy: 'modules',

  // We use a `@platform` tag which is non-default, educate typedoc about it
  blockTags: [...OptionDefaults.blockTags, '@platform'],

  remarkPlugins: [
    [
      'remark-link-rewrite',
      {
        replacer: url => {
          if (url.startsWith('/react-native/')) return url;

          let modifiedUrl = url;

          // Remove `.mdx` from all other URLs so docs.page links work
          // (docs.page uses .mdx files but expects links to be without the `.mdx`)
          modifiedUrl = url.replace('.mdx', '');

          // For relative links we need to add the path components docs.page expects
          if (!url.includes('/')) modifiedUrl = 'react-native/reference/' + modifiedUrl;

          return modifiedUrl;
        },
      },
    ],
  ],
};

export default config;
