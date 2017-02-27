'use strict';

import baseConfig from './base';

let config = {
  appEnv: 'dist'
};

export default Object.freeze(Object.assign({}, baseConfig, config));
