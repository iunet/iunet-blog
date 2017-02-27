'use strict';

import baseConfig from './base';

let config = {
  appEnv: 'dev'
};

export default Object.freeze(Object.assign({}, baseConfig, config));
